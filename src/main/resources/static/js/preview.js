document.getElementById("images").addEventListener("change", function (event) {
    const preview = document.getElementById("preview");
    preview.innerHTML = "";

    const files = event.target.files;
    if (!files) return;

    Array.from(files).forEach((file, index) => {
        const reader = new FileReader();

        reader.onload = function (e) {
            const img = document.createElement("img");
            img.src = e.target.result;
            img.style.width = "120px";
            img.style.height = "120px";
            img.style.objectFit = "cover";
            img.style.borderRadius = "10px";
            img.style.border = index === 0 ? "3px solid #0077cc" : "1px solid #c8d3df";
            preview.appendChild(img);
        };

        reader.readAsDataURL(file);
    });
});
