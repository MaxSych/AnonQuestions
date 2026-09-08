package askme.web;

import askme.dto.response.InboxResponse;
import askme.User;
import askme.data.QuestionRepository;
import askme.data.UserRepository;
import askme.service.QuestionsLifecycleService;
import askme.mapper.InboxMapper;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final QuestionsLifecycleService answeringService;
    private final InboxMapper inboxMapper;


    UserController(UserRepository userRepository, QuestionRepository questionRepository, QuestionsLifecycleService answeringService, InboxMapper inboxMapper) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answeringService = answeringService;
        this.inboxMapper = inboxMapper;}

    @GetMapping("/profile/{username}")
    public String showProfile(Model model, @PathVariable String username) {


        User userFromDb = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));


        model.addAttribute("posts", questionRepository.findByUserIdAndIsAnsweredTrue(userFromDb.getId()));
        model.addAttribute("user", userFromDb);

        List<InboxResponse> unanswered = inboxMapper.toInboxPostResponseList(answeringService.getUnansweredPosts(username));
        model.addAttribute("unanswered", unanswered);


        return "profile";
    }
}
