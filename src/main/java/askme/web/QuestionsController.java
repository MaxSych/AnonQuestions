package askme.web;

import askme.service.AskingQuestionsService;
import askme.service.QuestionsPopInInboxService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class QuestionsController {

    private final QuestionsPopInInboxService questionsPopInInboxService;
    private final AskingQuestionsService askingQuestionsService;

    public QuestionsController(AskingQuestionsService questionsService,QuestionsPopInInboxService questionsPopInInboxService){
        this.askingQuestionsService = questionsService;
        this.questionsPopInInboxService = questionsPopInInboxService;
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

    @PostMapping("/posts/{id}/delete")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id){
        questionsPopInInboxService.deleteInboxPost(id);
        return ResponseEntity.noContent().build();

    }
}