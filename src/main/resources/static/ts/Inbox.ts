interface Post {
    id: number;
    question: string;
}

async function openUnansweredQuestions() {
    const overlay = document.getElementById('QForm') as HTMLElement;
    const listContainer = document.getElementById('unansweredList') as HTMLElement;


    const response = await fetch('/api/questions/unanswered');
    const questions: Post[] = await response.json();


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
}

async function submitAnswer(postId: number) {
    const text = (document.getElementById(`answer-${postId}`) as HTMLTextAreaElement).value;

    await fetch(`/api/questions/${postId}/answer`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ answer: text })
    });

    alert('Answered!');
    openUnansweredQuestions();
}

async function deleteQuestion(postId: number) {
    if (confirm('Are you sure?')) {
        await fetch(`/api/questions/${postId}`, { method: 'DELETE' });
        openUnansweredQuestions();
    }
}

function closeModal() {
    const overlay = document.getElementById('QForm') as HTMLElement;
    overlay.style.display = 'none';
}

export {};