package askme.web;

import askme.entity.Post;
import askme.User;
import askme.data.PostRepository;
import askme.data.UserRepository;
import askme.service.AnsweringService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AnsweringService answeringService;


    UserController(UserRepository userRepository, PostRepository postRepository, AnsweringService answeringService) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.answeringService = answeringService;    }

    @GetMapping("/profile/{username}")
    public String showProfile(Model model, @PathVariable String username) {


        User userFromDb = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setUser(userFromDb);



        model.addAttribute("posts", postRepository.findByUserIdAndIsAnsweredTrue(userFromDb.getId()));
        model.addAttribute("user", userFromDb);

        List<String> unanswered = answeringService.getUnansweredPosts(username);
        model.addAttribute("unanswered", unanswered);


        return "profile";
    }
}
