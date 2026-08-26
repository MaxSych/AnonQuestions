// Variables for storing state
let currentQuestionId = null;

// Open the modal window
function openInboxModal() {
    const modal = document.getElementById('inboxModalWindow');
    modal.style.display = 'block';
    document.getElementById('answerSection').style.display = 'none';
    currentQuestionId = null;
    fetchUnansweredQuestions();
}

// Close the modal window
function closeInboxModal() {
    const modal = document.getElementById('inboxModalWindow');
    modal.style.display = 'none';
    document.getElementById('answerSection').style.display = 'none';
    currentQuestionId = null;
}

// Close the modal window when clicking outside of it
document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('inboxModalWindow');
    if (modal) {
        modal.addEventListener('click', function(e) {
            if (e.target === this) {
                closeInboxModal();
            }
        });
    }
});



//Show the answer section
function showAnswerForm(questionId) {
    currentQuestionId = questionId;
    const answerSection = document.getElementById('answerSection');
    if (answerSection) {
        answerSection.style.display = 'block';
    }
    const answerText = document.getElementById('answerText');
    if (answerText) {
        answerText.value = '';
    }

    // Scroll to the answer section
    answerSection.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

// Отмена ответа
function cancelAnswer() {
    const answerSection = document.getElementById('answerSection');
    if (answerSection) {
        answerSection.style.display = 'none';
    }
    currentQuestionId = null;
}


// A helper function for HTML escaping
function escapeHtml(text) {
    if (!text) return '';
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}


// Function for deleting a question from an inbox
document.addEventListener('click', function(e) {
    if (e.target && e.target.classList.contains('btn-delete')) {
        const questionItem = e.target.closest('.question-item');
        const questionId = e.target.dataset.id;

        if (!questionItem || !questionId) return;

        fetch(`/posts/${questionId}/delete`, {
            method: 'POST'
        })
            .then(response => {
                if (response.ok) {
                    questionItem.remove(); // We delete the element only if the server responds successfully
                }
            });
    }
});