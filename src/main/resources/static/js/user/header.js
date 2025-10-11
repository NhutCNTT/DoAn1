/* =========================================================
   HEADER.JS – LOAD HEADER + LOGIN/LOGOUT + USER MENU
   ========================================================= */
document.addEventListener("DOMContentLoaded", loadHeader);

function loadHeader() {
    // đổi đường dẫn sang static/components/user/
    fetch("/components/user/header.html")
        .then(res => res.text())
        .then(html => {
            const host = document.getElementById("header-placeholder");
            if (!host) return;

            host.innerHTML = html;

            setupUserMenu();
            highlightActiveLink(); // ✅ Đánh dấu link active

            document.body.dataset.headerLoaded = "true";
            if (typeof checkLayoutReady === "function") {
                checkLayoutReady();
            }
        })
        .catch(err => console.error("Lỗi khi load header:", err));
}

/* =========================================================
   AUTH UTILS
   ========================================================= */
function getAuth() {
    if (localStorage.getItem("loggedIn") === "true") {
        return { store: localStorage, username: localStorage.getItem("username") };
    }
    if (sessionStorage.getItem("loggedIn") === "true") {
        return { store: sessionStorage, username: sessionStorage.getItem("username") };
    }
    return null;
}

/* =========================================================
   SETUP USER MENU + LOGIN FORM
   ========================================================= */
function setupUserMenu() {
    const loginBtn = document.getElementById("login-btn");
    const userBox  = document.getElementById("user-menu");
    const auth = getAuth();

    if (auth?.username) {
        loginBtn?.classList.add("d-none");
        userBox?.classList.remove("d-none");

        const nameSpan = document.querySelector("#userMenu .user-name");
        if (nameSpan) nameSpan.textContent = auth.username;

        const avatar = document.querySelector("#userMenu .user-avatar");
        if (avatar) avatar.textContent = auth.username[0].toUpperCase();

        const bellBadge = document.querySelector("#bell-badge");
        const chatBadge = document.querySelector("#chat-badge");
        if (bellBadge) bellBadge.textContent = "3";
        if (chatBadge) chatBadge.textContent = "2";
    } else {
        userBox?.classList.add("d-none");
        loginBtn?.classList.remove("d-none");
    }

    // Đăng xuất → điều hướng về /user/login.html
    document.getElementById("logout-btn")?.addEventListener("click", (e) => {
        e.preventDefault();
        localStorage.clear();
        sessionStorage.clear();
        window.location.href = "/user/login.html";
    });

    // Xử lý form login nếu đang ở login.html
    const loginForm = document.getElementById("loginForm");
    if (loginForm) {
        loginForm.addEventListener("submit", (e) => {
            e.preventDefault();
            const u = document.getElementById("username")?.value.trim();
            const p = document.getElementById("password")?.value.trim();
            const remember = document.getElementById("rememberMe")?.checked;
            const err = document.getElementById("error-msg");

            const demoUser = "phuoc";
            const demoPass = "123456";

            if (u === demoUser && p === demoPass) {
                const store = remember ? localStorage : sessionStorage;
                store.setItem("loggedIn", "true");
                store.setItem("username", u);
                // điều hướng về /user/index.html
                window.location.href = "/user/index.html";
            } else {
                if (err) {
                    err.textContent = "❌ Tên đăng nhập hoặc mật khẩu không đúng!";
                    err.classList.remove("d-none");
                } else {
                    alert("Tên đăng nhập hoặc mật khẩu không đúng!");
                }
            }
        });
    }
}

/* =========================================================
   HIGHLIGHT ACTIVE NAV LINK
   ========================================================= */
function highlightActiveLink() {
    const links = document.querySelectorAll(".navbar-nav .nav-link, .dropdown-item");
    const currentUrl = window.location.pathname.split("/").pop(); // ví dụ: introduce.html

    links.forEach(link => {
        const linkUrl = link.getAttribute("href");

        if (linkUrl === currentUrl) {
            link.classList.add("active");

            const parentDropdown = link.closest(".dropdown");
            if (parentDropdown) {
                const parentLink = parentDropdown.querySelector(".nav-link");
                if (parentLink) parentLink.classList.add("active");
            }
        }
    });
}

/* ============================================================
   HEADER NAV — Dropdown: Desktop=Hover, Mobile=Click (Bootstrap)
   ============================================================ */
(() => {
    const DESKTOP_MIN = 992;

    function applyDropdownMode() {
        const toggles = document.querySelectorAll('.navbar .dropdown-toggle');
        const isMobile = window.innerWidth < DESKTOP_MIN;

        toggles.forEach(t => {
            if (isMobile) {
                t.setAttribute('data-bs-toggle', 'dropdown');
                t.setAttribute('data-bs-auto-close', 'outside');
            } else {
                t.removeAttribute('data-bs-toggle');
                t.removeAttribute('data-bs-auto-close');

                const menu = t.parentElement && t.parentElement.querySelector('.dropdown-menu');
                if (menu && menu.classList.contains('show') && window.bootstrap) {
                    const inst = bootstrap.Dropdown.getInstance(t) || new bootstrap.Dropdown(t);
                    inst.hide();
                    t.setAttribute('aria-expanded', 'false');
                }
            }
        });
    }

    function preventHashJumpOnDesktop(e) {
        const link = e.target.closest('.navbar .dropdown-toggle');
        if (!link) return;
        const isMobile = window.innerWidth < DESKTOP_MIN;

        if (!isMobile && link.getAttribute('href') === '#') {
            e.preventDefault();
        }
    }

    function closeOnItemClickMobile(e) {
        const item = e.target.closest('.dropdown-menu .dropdown-item');
        if (!item) return;
        const isMobile = window.innerWidth < DESKTOP_MIN;
        if (!isMobile) return;

        const parentDropdown = item.closest('.nav-item.dropdown');
        if (!parentDropdown) return;
        const toggle = parentDropdown.querySelector('.dropdown-toggle');
        if (toggle && window.bootstrap) {
            const inst = bootstrap.Dropdown.getInstance(toggle) || new bootstrap.Dropdown(toggle);
            inst.hide();
            toggle.setAttribute('aria-expanded', 'false');
        }
    }

    function init() {
        applyDropdownMode();

        let tmr;
        window.addEventListener('resize', () => {
            clearTimeout(tmr);
            tmr = setTimeout(applyDropdownMode, 150);
        });

        document.addEventListener('click', preventHashJumpOnDesktop);
        document.addEventListener('click', closeOnItemClickMobile);
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }
})();
