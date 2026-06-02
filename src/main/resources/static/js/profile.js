function toggleAskForm() {
    const modal = document.getElementById('modalWindow');
    if (modal.style.display === 'none' || modal.style.display === '') {
        modal.style.display = 'block';
    } else {
        modal.style.display = 'none';
    }
}


document.getElementById('modalWindow').addEventListener('click', function(e) {
    if (e.target === this) {
        toggleAskForm();
    }
});

