package anonquestions.web;

import anonquestions.entity.Post;
import anonquestions.User;
import anonquestions.data.PostRepository;
import anonquestions.data.UserRepository;
import anonquestions.service.AnsweringService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;


    UserController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/profile/{username}")
    public String showProfile(Model model, @PathVariable String username) {


        User userFromDb = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setUser(userFromDb);



        model.addAttribute("posts", postRepository.findByUserIdAndIsAnsweredTrue(userFromDb.getId()));
        model.addAttribute("user", userFromDb);



        return "profile";
    }
}
