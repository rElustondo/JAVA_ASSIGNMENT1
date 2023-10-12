package ca.gbc.socialservice;

import ca.gbc.socialservice.dto.PostRequest;
import ca.gbc.socialservice.model.Post;
import ca.gbc.socialservice.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class SocialServiceApplicationTests extends AbstractContainerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    private PostRequest getPostRequest(){
        return PostRequest.builder()
                .content("This is my test post")
                .timestamp("Oct 12")
                .build();
    }
    private List<Post> getPosts(){
        List<Post> products = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        Post post = Post.builder()
                .id(uuid.toString())
                .content("This is a test post 2")
                .timestamp("Oct 13")
                .build();
        products.add(post);
        return products;
    }

    @Test
    void  createPost() throws Exception {
        PostRequest postRequest = getPostRequest();
        String productRequestJsonString = objectMapper.writeValueAsString(postRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestJsonString)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        //Assertions
        Assertions.assertTrue(postRepository.findAll().size()>0);

        Query query = new Query();
        query.addCriteria(Criteria.where("content").is("This is my test post"));
        List<Post> post = mongoTemplate.find(query,Post.class);
        Assertions.assertTrue(post.size() > 0 );
    }


}
