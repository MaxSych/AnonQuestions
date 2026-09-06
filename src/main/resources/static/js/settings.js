document.addEventListener('DOMContentLoaded', () => {
    const dialog = document.getElementById('settingsWindow');
    const openBtn = document.querySelector('.btn-settings');
    const closeBtn = document.getElementById('closeBtn');

    if (openBtn && dialog && closeBtn) {
        // 1. Window open
        openBtn.addEventListener('click', () => {
            dialog.showModal();
        });


        closeBtn.addEventListener('click', () => {
            dialog.close();
        });

        // 2. backdrop
        dialog.addEventListener('click', (e) => {
            const rect = dialog.getBoundingClientRect();
            const isInDialog = (
                rect.top <= e.clientY && e.clientY <= rect.top + rect.height &&
                rect.left <= e.clientX && e.clientX <= rect.left + rect.width
            );

            if (!isInDialog) {
                dialog.close();
            }
        });
    } else {
        console.error('One or more elements were not found in the DOM!');
    }
});