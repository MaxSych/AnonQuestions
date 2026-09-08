package askme.service;

import askme.User;
import askme.data.QuestionRepository;
import askme.data.UserRepository;
import askme.entity.Question;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionsLifecycleService {

    private final UserRepository userRepository;
    private  QuestionRepository questionRepository ;

    public QuestionsLifecycleService(UserRepository userRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    public List<Question> getUnansweredPosts(String username) {
        User user;
        List<Question> unansweredQuestions  = new ArrayList<>();
        user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long UserId = user.getId();
        List<Question> rawPosts = questionRepository.findByUserId(UserId);

        for (Question post : rawPosts) {
            if (Boolean.FALSE.equals(post.getIsAnswered())) {
                unansweredQuestions.add(rawPosts.get(rawPosts.indexOf(post)));
            }
        }

        return unansweredQuestions;
    }

    @Transactional
    public boolean deleteQuestion(Long id, Long UserId) {
        Question post = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        if (!post.getUser().getId().equals(UserId)) {
            throw new AccessDeniedException("You are not allowed to delete this post");
        }
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return true;
        }
        return false;
    }
    @Transactional
    public void answerQuestion( String answerText, Long postId) {
        Question post = questionRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        post.setResponse(answerText);
        post.setIsAnswered(true);

    }
}
