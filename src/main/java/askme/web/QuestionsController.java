package askme.web;

import askme.User;
import askme.service.AskingQuestionsService;
import askme.service.QuestionsLifecycleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class QuestionsController {

    private final QuestionsLifecycleService questionsLifecycleService;
    private final AskingQuestionsService askingQuestionsService;

    public QuestionsController(AskingQuestionsService questionsService, QuestionsLifecycleService questionsLifecycleService){
        this.askingQuestionsService = questionsService;
        this.questionsLifecycleService = questionsLifecycleService;
    }

    @PostMapping("/add")
    public String addQuestion(@RequestParam("text") String questionText, @RequestParam("userName") String userName, RedirectAttributes redirectAttributes) {

        if (questionText == null || questionText.trim().length() < 5) {

            redirectAttributes.addFlashAttribute("errorMessage", "Text is too short!");
            return "redirect:/profile/" + userName;
        }

        askingQuestionsService.addQuestion( questionText, userName);

        return "redirect:/profile/" + userName;
    }

    @PostMapping("/questions/{id}/delete")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id, @AuthenticationPrincipal User user){
        questionsLifecycleService.deleteQuestion(id, user.getId());
        return ResponseEntity.noContent().build();

    }
}