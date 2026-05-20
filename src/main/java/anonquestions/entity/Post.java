package anonquestions.entity;

import anonquestions.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Post {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @CreationTimestamp()
    private LocalDate createdAt;

    @Size(min = 5, max = 100)
    private String question;

    @Size(min = 1, max = 100)
    private String response;


    private Boolean isAnswered = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
