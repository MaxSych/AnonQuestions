package askme.mapper;

import askme.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    default String toQuestionText(Question post) {
        return post != null ? post.getQuestion() : null;
    }

    List<String> toQuestionTexts(List<Question> posts);

}