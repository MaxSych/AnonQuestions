package askme.service;

import askme.User;
import askme.data.PostRepository;
import askme.data.UserRepository;
import askme.entity.Post;
import jakarta.transaction.Transactional;
import org.hibernate.sql.Delete;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionsPopInInboxService {

    private final UserRepository userRepository;
    private  PostRepository postRepository ;

    public QuestionsPopInInboxService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<Post> getUnansweredPosts(String username) {
        User user;
        List<Post> unansweredQuestions  = new ArrayList<>();
        user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));


        Long UserId = user.getId();
        List<Post> rawPosts = postRepository.findByUserId(UserId);

        for (Post post : rawPosts) {
            if (Boolean.FALSE.equals(post.getIsAnswered())) {
                unansweredQuestions.add(rawPosts.get(rawPosts.indexOf(post)));
            }
        }

        return unansweredQuestions;
    }

    @Transactional
    public boolean deleteInboxPost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
