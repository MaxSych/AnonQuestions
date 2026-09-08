function toggleAskForm() {
    const modal = document.getElementById('modalWindow');
    if (modal.style.display === 'none' || modal.style.display === '') {
        modal.style.display = 'block';
    } else {
        modal.style.display = 'none';
    }
}

 const modalWindow = document.getElementById('modalWindow');

if (modalWindow) {
    modalWindow.addEventListener('click', function(e) {
        if (e.target === this) {
            toggleAskForm();
        }
    });
}


document.addEventListener('click', function (e) {
    const btn = e.target.closest('.btn-post-options');

    if (btn) {
        e.stopPropagation();
        const container = btn.closest('.post-menu-container') || btn.parentElement;
        const dropdown = container ? container.querySelector('.post-options-dropdown') : null;

        document.querySelectorAll('.post-options-dropdown').forEach(d => {
            if (d !== dropdown) d.style.display = 'none';
        });

        if (dropdown) {
            const isShown = dropdown.style.display === 'block';
            dropdown.style.display = isShown ? 'none' : 'block';
        }
        return;
    }

    document.querySelectorAll('.post-options-dropdown').forEach(d => {
        d.style.display = 'none';
    });
});

document.addEventListener('click', function(e) {
    const btn = e.target.closest('.btn-post-delete');

    if (!btn) return;

    const postItem = btn.closest('.post-item');
    const postId = btn.dataset.id;

    if (!postItem || !postId) return;

    fetch(`/questions/${postId}/delete`, {
        method: 'POST'
    })
        .then(response => {
            if (response.ok) {
                postItem.remove();
            }
            });

});