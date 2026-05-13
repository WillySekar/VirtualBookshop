const API_BASE = 'http://localhost:8080'; // Backend base URL

function parseJwt(token) {
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split('')
        .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join('')
    );
    return JSON.parse(jsonPayload);
  } catch (e) {
    return null;
  }
}

function isTokenExpired(token) {
  const decoded = parseJwt(token);
  if (!decoded || !decoded.exp) return true;
  const now = Date.now() / 1000;
  return decoded.exp < now;
}

function logout(forceMsg) {
  localStorage.removeItem("vb_token");
  localStorage.removeItem("vb_email");
  alert(forceMsg || "Session expired. Please log in again.");
  location.href = "/login.html";
}

// Check every 30 seconds if JWT expired
setInterval(() => {
  const token = localStorage.getItem("vb_token");
  if (token && isTokenExpired(token)) {
    logout();
  }
}, 30000);

// -------------------- AUTH Helpers --------------------
function isLoggedIn() {
  return !!localStorage.getItem('vb_token');
}

function getToken() {
  return localStorage.getItem('vb_token');
}

function updateAuthUI() {
  const email = localStorage.getItem('vb_email');
  const token = localStorage.getItem('vb_token');
  const loginLink = document.getElementById('loginLink');
  const signupLink = document.getElementById('signupLink');
  const logoutLink = document.getElementById('logoutLink');
  const userEmail = document.getElementById('userEmail');
  
  if (token && isTokenExpired(token)) {
    logout();
    return;
  }

  if (email && isLoggedIn()) {
    if (loginLink) loginLink.style.display = 'none';
    if (signupLink) signupLink.style.display = 'none';
    if (logoutLink) logoutLink.style.display = 'inline';
    if (userEmail) userEmail.textContent = email;
  } else {
    if (loginLink) loginLink.style.display = 'inline';
    if (signupLink) signupLink.style.display = 'inline';
    if (logoutLink) logoutLink.style.display = 'none';
    if (userEmail) userEmail.textContent = '';
  }

  if (logoutLink) {
    logoutLink.onclick = (e) => {
      e.preventDefault();
      localStorage.removeItem('vb_token');
      localStorage.removeItem('vb_email');
      location.href = '/';
    };
  }
}

// -------------------- CART Helpers --------------------
function getCart() {
  try {
    return JSON.parse(localStorage.getItem('vb_cart') || '[]');
  } catch (e) { return []; }
}
function saveCart(cart) {
  localStorage.setItem('vb_cart', JSON.stringify(cart));
}
function clearCart() {
  localStorage.removeItem('vb_cart');
}

async function addToCart(bookId, qty = 1) {
  if (!isLoggedIn()) {
    alert('Please login to add books to your cart.');
    location.href = '/login.html';
    return;
  }

  // Find in cache or fetch fresh
  let b = booksCache.find(x => x.id === bookId);
  if (!b) b = await fetchBookById(bookId);
  if (!b) {
    alert('Book not found');
    return;
  }

  const cart = getCart();
  const existing = cart.find(x => x.id === bookId);
  if (existing) existing.qty = Math.max(1, existing.qty + qty);
  else cart.push({ id: b.id, title: b.title, price: b.price, qty: qty });

  saveCart(cart);
  await loadCartCount();
  alert(`Added "${b.title}" to cart`);
}

function removeFromCart(bookId) {
  const cart = getCart().filter(x => x.id !== bookId);
  saveCart(cart);
  loadCartCount();
  renderCartIfPresent();
}

function updateQty(bookId, qty) {
  const cart = getCart();
  const item = cart.find(x => x.id === bookId);
  if (!item) return;
  item.qty = Math.max(1, parseInt(qty || 1, 10));
  saveCart(cart);
  renderCartIfPresent();
  loadCartCount();
}

async function loadCartCount() {
  const cart = getCart();
  const el = document.getElementById('cartCount');
  if (el) el.innerText = (cart || []).reduce((s, i) => s + i.qty, 0);
}

function renderCartIfPresent() {
  if (typeof renderCart === 'function') renderCart();
}

// -------------------- BOOKS --------------------
let booksCache = [];

async function fetchBooksFromServer() {
  try {
    const res = await fetch(`${API_BASE}/books`);
    if (!res.ok) throw new Error('Failed to fetch books');
    const js = await res.json();
    booksCache = js;
    return js;
  } catch (err) {
    console.warn('Fetch books failed, fallback:', err);
    return booksCache;
  }
}

async function loadBooks() {
  const books = await fetchBooksFromServer();
  displayBooks(books);
}

function displayBooks(list) {
  const grid = document.getElementById('booksGrid');
  if (!grid) return;
  booksCache = list;
  grid.innerHTML = list.map(b => `
    <div class="card">
      <div>
        <h3>${escapeHtml(b.title)}</h3>
        <div class="muted">by ${escapeHtml(b.author)}</div>
        <div class="price">₹${(+b.price).toFixed(2)}</div>
        <div class="muted">In stock: ${b.quantity ?? '—'}</div>
      </div>
      <div class="actions">
        <a class="secondary" href="/book.html?id=${b.id}">Details</a>
        <button class="primary" onclick="addToCart(${b.id})" ${b.quantity === 0 ? 'disabled' : ''}>Add to Cart</button>
      </div>
    </div>
  `).join('');
}

function filterBooks(q) {
  q = q || '';
  const filtered = booksCache.filter(b =>
    (b.title || '').toLowerCase().includes(q) ||
    (b.author || '').toLowerCase().includes(q)
  );
  displayBooks(filtered);
}

async function fetchBookById(id) {
  try {
    const res = await fetch(`${API_BASE}/books/${id}`);
    if (!res.ok) throw new Error('Book not found');
    return await res.json();
  } catch (err) {
    console.warn('fetchBookById error', err);
    return booksCache.find(x => x.id == id);
  }
}

// -------------------- Utilities --------------------
function escapeHtml(str) {
  if (!str && str !== 0) return '';
  return String(str)
    .replaceAll('&', '&amp;')
    .replaceAll('<', '&lt;')
    .replaceAll('>', '&gt;')
    .replaceAll('"', '&quot;')
    .replaceAll("'", '&#39;');
}
