package askme.mapper;

import askme.entity.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    default String toQuestionText(Post post) {
        return post != null ? post.getQuestion() : null;
    }

    List<String> toQuestionTexts(List<Post> posts);

}