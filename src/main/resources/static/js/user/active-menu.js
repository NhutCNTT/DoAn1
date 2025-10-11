fetch("/components/user/header.html")
    .then(res => res.text())
    .then(html => {
        document.getElementById("header-placeholder").innerHTML = html;

        // Lấy tên trang hiện tại theo 2 dạng:
        // - Dạng file tĩnh: /user/lookup.html  -> "lookup.html"
        // - Dạng route Thymeleaf: /user/lookup -> "lookup"
        const path = window.location.pathname;               // ví dụ: /user/lookup hoặc /user/lookup.html
        const lastSeg = path.split("/").filter(Boolean).pop() || "index";
        const currentPageNoExt = lastSeg.replace(/\.html$/i, "") || "index";
        const currentFile = `${currentPageNoExt}.html`;      // ví dụ: lookup.html

        // Reset active
        document
            .querySelectorAll(
                "#header-placeholder .navbar-nav .nav-link, #header-placeholder .dropdown-item"
            )
            .forEach(el => el.classList.remove("active"));

        // Map tên route/file thực tế trong thư mục templates/user/
        // (dựa theo cây bạn đã gửi)
        const userPages = [
            "index", "login", "register", "introduce",
            "lookup", "announcement", "documents",
            "feedback", "support"
        ];

        // Active cho menu chính (so khớp cả dạng /user/xxx và /user/xxx.html)
        document
            .querySelectorAll("#header-placeholder .navbar-nav .nav-link[href]")
            .forEach(link => {
                const href = link.getAttribute("href") || "";
                // Lấy segment cuối cùng của href
                const hrefSeg = href.split("/").filter(Boolean).pop() || "";
                const hrefNoExt = hrefSeg.replace(/\.html$/i, "");
                // Trường hợp link kiểu /user hoặc / (trang chủ)
                const isHomeLink =
                    href === "/" || href === "/user" || href.endsWith("/user/index") || href.endsWith("/user/index.html");

                if (
                    (hrefNoExt && hrefNoExt === currentPageNoExt) ||
                    (isHomeLink && (currentPageNoExt === "index" || currentPageNoExt === ""))
                ) {
                    link.classList.add("active");
                }
            });

        // Nếu có dropdown Danh mục (ví dụ), hãy liệt kê đúng các trang con thực tế
        const danhMucPages = [
            // đặt các file/route con thực sự thuộc dropdown "Danh mục" của bạn (nếu có)
            // ví dụ: "documents", "announcement"
        ];

        if (danhMucPages.includes(currentPageNoExt)) {
            // id của nút dropdown trong header user (đổi cho đúng id bạn dùng)
            document.querySelector("#header-placeholder #dropdownMenu")?.classList.add("active");

            document
                .querySelectorAll("#header-placeholder .dropdown-item[href]")
                .forEach(item => {
                    const href = item.getAttribute("href") || "";
                    const seg = href.split("/").filter(Boolean).pop() || "";
                    const segNoExt = seg.replace(/\.html$/i, "");
                    if (segNoExt === currentPageNoExt) {
                        item.classList.add("active");
                    }
                });
        }
    })
    .catch(err => console.error("Không thể tải header user:", err));