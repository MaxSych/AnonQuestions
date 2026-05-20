package anonquestions.web;

import anonquestions.User;
import anonquestions.data.PostRepository;
import anonquestions.data.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

   PostController(PostRepository postRepository, UserRepository userRepository) {
       this.postRepository = postRepository;
       this.userRepository = userRepository;
   }

    @PostMapping("/profile/{userId}")
    String addPost(Model model, @RequestParam String text, @PathVariable Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));



        return "redirect:/profile/" + userId;
    }

}
