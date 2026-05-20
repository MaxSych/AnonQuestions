function toggleAskForm() {
    const modal = document.getElementById('askForm');
    if (modal.style.display === 'none' || modal.style.display === '') {
        modal.style.display = 'block';
    } else {
        modal.style.display = 'none';
    }
}


document.getElementById('askForm').addEventListener('click', function(e) {
    if (e.target === this) {
        toggleAskForm();
    }
});

