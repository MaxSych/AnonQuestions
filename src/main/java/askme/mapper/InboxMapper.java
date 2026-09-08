package askme.mapper;

import askme.dto.response.InboxResponse;
import askme.entity.Question;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class InboxMapper {
    public InboxResponse toInboxResponse(Question post){

        if (post == null) return null;

        return new InboxResponse(post.getId(), post.getQuestion());
    }

    public List<InboxResponse> toInboxPostResponseList(List<Question> posts) {
        if (posts == null || posts.isEmpty()) {
            return Collections.emptyList();
        }

        List<InboxResponse> inboxResponses = new ArrayList<>();
        for ( Question post : posts) {

            InboxResponse inboxResponse = toInboxResponse(post);
            inboxResponses.add(inboxResponse);

        }

        return inboxResponses;
    }
}
