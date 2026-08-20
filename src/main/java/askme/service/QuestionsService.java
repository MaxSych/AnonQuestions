package askme.service;

import askme.User;
import askme.data.PostRepository;
import askme.data.UserRepository;
import askme.entity.Post;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class QuestionsService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public QuestionsService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
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

        Post post = new Post();
        post.setQuestion(questionText);
        post.setUser(receiver);
        postRepository.save(post);

        log.info("Question successfully saved for user: {}. Post ID: {}", userName, post.getId());
    }


}