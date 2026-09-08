function openInboxModal() {
    const modal = document.getElementById('inboxModalWindow');
    modal.style.display = 'block';
    document.getElementById('answerSection').style.display = 'none';
    currentQuestionId = null;
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

// Function for deleting a question from an inbox
document.addEventListener('click', function(e) {
    if (e.target && e.target.classList.contains('btn-delete')) {
        const questionItem = e.target.closest('.question-item');
        const questionId = e.target.dataset.id;

        if (!questionItem || !questionId) return;

        fetch(`/questions/${questionId}/delete`, {
            method: 'POST'
        })
            .then(response => {
                if (response.ok) {
                    questionItem.remove();
                }
            });
    }
});

//Functions below are responsible for the answering to questions
function openInboxAnswerForm(questionId) {
    const answerSection = document.getElementById('answerSection');
    const hiddenIdInput = document.getElementById('currentQuestionId');

    // Save the clicked question's ID into the hidden input
    hiddenIdInput.value = questionId;

    // Make the answer form visible on the page
    answerSection.style.display = 'block';

    // Smoothly scroll the page down to the answer section
    answerSection.scrollIntoView({ behavior: 'smooth' });
}

// Hides the answer form and resets its input fields
function cancelAnswer() {
    // Retrieve the form DOM elements
    const answerSection = document.getElementById('answerSection');
    const answerText = document.getElementById('answerText');
    const hiddenIdInput = document.getElementById('currentQuestionId');


    answerText.value = '';

    hiddenIdInput.value = '';

    answerSection.style.display = 'none';
}

function submitAnswer() {

    const questionId = document.getElementById('currentQuestionId').value;
    const text = document.getElementById('answerText').value.trim();


    if (!text) {
        alert('Please write an answer before sending.');
        return;
    }

    // Send an HTTP POST request to the backend API using Fetch
    fetch(`/posts/${questionId}/answer`, {
        method: 'POST', // HTTP method
        headers: {
            'Content-Type': 'application/json' // Indicate JSON payload format
        },
        // Convert the answer object into a JSON string
        body: JSON.stringify({ answer: text })
    })
        .then(response => {

            if (response.ok) {
                cancelAnswer();
                location.reload(); // Reload the page to update questions list
            } else {
                alert('Failed to send answer.'); // Notify user of server error
            }
        })
        .catch(error => {
            console.error('Error sending response:', error);
        });
}