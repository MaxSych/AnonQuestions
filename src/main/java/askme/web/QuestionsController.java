package askme.web;

import askme.data.UserRepository;
import askme.service.QuestionsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class QuestionsController {

    QuestionsService questionsService;
    UserRepository userRepository;

    QuestionsController(QuestionsService questionsService, UserRepository userRepository){
        this.questionsService = questionsService;
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public String addQuestion(@RequestParam("text") String questionText, @RequestParam("userName") String userName, RedirectAttributes redirectAttributes) {

        if (questionText == null || questionText.trim().length() < 5) {

            redirectAttributes.addFlashAttribute("errorMessage", "Text is too short!");
            return "redirect:/profile/" + userName;
        }

        questionsService.addQuestion( questionText, userName);

        return "redirect:/profile/" + userName;
    }
}