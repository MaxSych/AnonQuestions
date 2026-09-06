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
                    questionItem.remove();
                }
            });
    }
});


//Functions below are responsible for the answering to questions
// Opens the answer form and stores the ID of the selected question
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

    // Clear the entered response text
    answerText.value = '';
    // Reset the saved question ID
    hiddenIdInput.value = '';
    // Hide the answer section block
    answerSection.style.display = 'none';
}

// Handles submitting the entered answer to the backend server
function submitAnswer() {

    const questionId = document.getElementById('currentQuestionId').value;
    const text = document.getElementById('answerText').value.trim();

    // Validate that the answer text is not empty
    if (!text) {
        alert('Please write an answer before sending.');
        return; // Abort execution if validation fails
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