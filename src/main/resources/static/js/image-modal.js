(() => {
    const images = Array.from(document.querySelectorAll(".js-image-modal"));
    if (images.length === 0) return;

    const overlay = document.createElement("div");
    overlay.className = "image-modal-overlay";
    overlay.innerHTML = `
        <div class="image-modal-dialog">
            <button class="image-modal-close" type="button" aria-label="Закрыть">x</button>
            <div class="image-modal-toolbar">
                <button type="button" data-action="zoom-out">-</button>
                <button type="button" data-action="reset">100%</button>
                <button type="button" data-action="zoom-in">+</button>
            </div>
            <div class="image-modal-content">
                <img class="image-modal-image" alt="Увеличенное изображение">
            </div>
        </div>
    `;

    document.body.appendChild(overlay);

    const modalImage = overlay.querySelector(".image-modal-image");
    const closeBtn = overlay.querySelector(".image-modal-close");
    const modalContent = overlay.querySelector(".image-modal-content");
    const toolbar = overlay.querySelector(".image-modal-toolbar");

    let scale = 1;

    const applyScale = () => {
        modalImage.style.transform = `scale(${scale})`;
    };

    const openModal = (src, alt) => {
        modalImage.src = src;
        modalImage.alt = alt || "Увеличенное изображение";
        scale = 1;
        applyScale();
        overlay.classList.add("is-open");
        document.body.classList.add("no-scroll");
    };

    const closeModal = () => {
        overlay.classList.remove("is-open");
        document.body.classList.remove("no-scroll");
    };

    images.forEach((img) => {
        img.style.cursor = "zoom-in";
        img.addEventListener("click", () => openModal(img.src, img.alt));
    });

    closeBtn.addEventListener("click", closeModal);

    overlay.addEventListener("click", (event) => {
        if (event.target === overlay) {
            closeModal();
        }
    });

    toolbar.addEventListener("click", (event) => {
        const action = event.target.dataset.action;
        if (!action) return;
        if (action === "zoom-in") scale = Math.min(4, scale + 0.25);
        if (action === "zoom-out") scale = Math.max(0.5, scale - 0.25);
        if (action === "reset") scale = 1;
        applyScale();
    });

    modalContent.addEventListener("wheel", (event) => {
        event.preventDefault();
        scale = event.deltaY < 0 ? Math.min(4, scale + 0.1) : Math.max(0.5, scale - 0.1);
        applyScale();
    });

    document.addEventListener("keydown", (event) => {
        if (!overlay.classList.contains("is-open")) return;
        if (event.key === "Escape") closeModal();
    });
})();
