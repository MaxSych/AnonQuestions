package askme.mapper;

import askme.dto.response.InboxResponse;
import askme.entity.Post;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class InboxMapper {
    public InboxResponse toInboxResponse(Post post){

        if (post == null) return null;

        return new InboxResponse(post.getId(), post.getQuestion());
    }

    public List<InboxResponse> toInboxPostResponseList(List<Post> posts) {
        if (posts == null || posts.isEmpty()) {
            return Collections.emptyList();
        }

        List<InboxResponse> inboxResponses = new ArrayList<>();
        for ( Post post : posts) {

            InboxResponse inboxResponse = toInboxResponse(post);
            inboxResponses.add(inboxResponse);

        }

        return inboxResponses;
    }
}
