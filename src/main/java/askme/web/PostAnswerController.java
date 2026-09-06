package askme.web;

import askme.service.QuestionsPopInInboxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class PostAnswerController {

    private final QuestionsPopInInboxService questionsPopInInboxService;

    public PostAnswerController(QuestionsPopInInboxService questionsPopInInboxService) {
        this.questionsPopInInboxService = questionsPopInInboxService;
    }

    @PostMapping("/posts/{postId}/answer")
    public ResponseEntity<Void> answerQuestion(@PathVariable Long postId,
                                               @RequestBody Map<String, String> body) {


        String answerText = body.get("answer");


        questionsPopInInboxService.answerQuestion(answerText, postId);


        return ResponseEntity.ok().build();
    }
}