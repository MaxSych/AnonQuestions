package anonquestions.web;

import anonquestions.data.UserRepository;
import anonquestions.service.QuestionsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class QuestionsController {

    QuestionsService questionsService;
    UserRepository userRepository;

    QuestionsController(QuestionsService questionsService, UserRepository userRepository){
        this.questionsService = questionsService;
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public String addQuestion(@RequestParam("text") String questionText, @RequestParam("userName") String userName) {



        questionsService.addQuestion( questionText, userName);

        return "redirect:/profile/" + userName;
    }
}