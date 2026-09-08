package askme.service;

import askme.User;
import askme.data.QuestionRepository;
import askme.data.UserRepository;
import askme.entity.Question;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AskingQuestionsService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public AskingQuestionsService(QuestionRepository postRepository, UserRepository userRepository) {
        this.questionRepository = postRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public void addQuestion( String questionText, String userName) {
        log.info("Attempting to add a new question for user: {}", userName);

        User receiver = userRepository.findByUsername(userName)
                .orElseThrow(() -> {
                    log.error("Failed to add question: User '{}' not found", userName);
                    return new RuntimeException("User not found: " + userName);
                });

        Question post = new Question();
        post.setQuestion(questionText);
        post.setUser(receiver);
        questionRepository.save(post);

        log.info("Question successfully saved for user: {}. Post ID: {}", userName, post.getId());
    }


}