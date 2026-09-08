package askme.web;

import askme.service.QuestionsLifecycleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class PostAnswerController {

    private final QuestionsLifecycleService questionsLifecycleService;

    public PostAnswerController(QuestionsLifecycleService questionsLifecycleService) {
        this.questionsLifecycleService = questionsLifecycleService;
    }

    @PostMapping("/posts/{postId}/answer")
    public ResponseEntity<Void> answerQuestion(@PathVariable Long postId,
                                               @RequestBody Map<String, String> body) {


        String answerText = body.get("answer");


        questionsLifecycleService.answerQuestion(answerText, postId);


        return ResponseEntity.ok().build();
    }
}