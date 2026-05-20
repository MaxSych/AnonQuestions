var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
function openUnansweredQuestions() {
    return __awaiter(this, void 0, void 0, function* () {
        const overlay = document.getElementById('QForm');
        const listContainer = document.getElementById('unansweredList');

        const response = yield fetch('/api/questions/unanswered');
        const questions = yield response.json();

        listContainer.innerHTML = '';
        questions.forEach(post => {
            const item = document.createElement('div');
            item.className = 'question-item';
            item.innerHTML = `
            <p>${post.question}</p>
            <textarea id="answer-${post.id}" placeholder="Your answer..."></textarea>
            <div class="actions">
                <button onclick="submitAnswer(${post.id})">✅ Answer</button>
                <button onclick="deleteQuestion(${post.id})" class="btn-delete">🗑️ Delete</button>
            </div>
        `;
            listContainer.appendChild(item);
        });
        overlay.style.display = 'flex';
    });
}
function submitAnswer(postId) {
    return __awaiter(this, void 0, void 0, function* () {
        const text = document.getElementById(`answer-${postId}`).value;
        yield fetch(`/api/questions/${postId}/answer`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ answer: text })
        });
        alert('Answered!');
        openUnansweredQuestions();
    });
}
function deleteQuestion(postId) {
    return __awaiter(this, void 0, void 0, function* () {
        if (confirm('Are you sure?')) {
            yield fetch(`/api/questions/${postId}`, { method: 'DELETE' });
            openUnansweredQuestions();
        }
    });
}
function closeModal() {
    const overlay = document.getElementById('QForm');
    overlay.style.display = 'none';
}
export {};
