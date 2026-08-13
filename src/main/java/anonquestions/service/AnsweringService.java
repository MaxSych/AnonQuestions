package anonquestions.service;

import anonquestions.User;
import anonquestions.data.PostRepository;
import anonquestions.data.UserRepository;
import anonquestions.entity.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnsweringService {

    private final UserRepository userRepository;
    private  PostRepository postRepository ;

    public AnsweringService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<String> getUnansweredPosts(String username) {
        User user;
        List<String> unansweredQuestions  = new ArrayList<>();
        user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));


        Long UserId = user.getId();
        List<Post> rawPosts = postRepository.findByUserId(UserId);

        for (Post post : rawPosts) {
            if (Boolean.FALSE.equals(post.getIsAnswered())) {
                unansweredQuestions.add(post.getQuestion());
            }
        }

        return unansweredQuestions;
    }
}
