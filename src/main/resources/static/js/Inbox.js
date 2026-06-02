// Переменные для хранения состояния
let currentQuestionId = null;

// Открытие модального окна Inbox
function openInboxModal() {
    const modal = document.getElementById('inboxModalWindow');
    modal.style.display = 'block';
    document.getElementById('answerSection').style.display = 'none';
    currentQuestionId = null;
    fetchUnansweredQuestions();
}

// Закрытие модального окна Inbox
function closeInboxModal() {
    const modal = document.getElementById('inboxModalWindow');
    modal.style.display = 'none';
    document.getElementById('answerSection').style.display = 'none';
    currentQuestionId = null;
}

// Закрытие при клике на фон
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



// Показать форму ответа
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

    // Прокрутка к форме ответа
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


// Вспомогательная функция для экранирования HTML
function escapeHtml(text) {
    if (!text) return '';
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

