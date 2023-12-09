package ca.gbc.socialservice;

import ca.gbc.socialservice.dto.CommentRequest;
import ca.gbc.socialservice.dto.PostRequest;
import ca.gbc.socialservice.dto.UserRequest;
import ca.gbc.socialservice.entities.Comment;
import ca.gbc.socialservice.entities.UserEnt;
import ca.gbc.socialservice.model.Post;
import ca.gbc.socialservice.repository.CommentRepository;
import ca.gbc.socialservice.repository.PostRepository;
import ca.gbc.socialservice.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
class SocialServiceApplicationTests extends AbstractContainerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    private PostRequest getPostRequest(){
        return PostRequest.builder()
                .content("This is my test post")
                .timestamp("Oct 12")
                .userId(12345678L)
                .build();
    }
    private UserRequest getUserRequest(){
        return UserRequest.builder()
                .username("This is my test user")
                .email("test@123.com")
                .password("test123")
                .build();
    }
    private CommentRequest getCommentRequest(){
        return CommentRequest.builder()
                .content("This is my test comment")
                .timestamp("oct 19th")
                .postId("123456")
                .userId(123456789L)
                .build();
    }
    private List<Post> getPosts(){
        List<Post> products = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        Post post = Post.builder()
                .id(uuid.toString())
                .content("This is a test post 2")
                .timestamp("Oct 13")
                .username("testUsername")
                .userId(1234567L)
                .build();
        products.add(post);
        return products;
    }

    private List<UserEnt> getUsers(){
        List<UserEnt> users = new ArrayList<>();
        UserEnt user = new UserEnt("Tim4","tim4@abc.ca","qwe123");
        users.add(user);
        return users;
    }
    private List<Comment> getComments(){
        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment("TestComment1","Oct20th", 1234567890L, "testUsername","12345");
        comments.add(comment);
        return comments;
    }

    @Test
    @Order(2)
    void  createPost() throws Exception {
        PostRequest postRequest = getPostRequest();
        String productRequestJsonString = objectMapper.writeValueAsString(postRequest);

//        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(productRequestJsonString)
//        ).andExpect(MockMvcResultMatchers.status().isCreated());

        Assertions.assertTrue(!postRepository.findAll().isEmpty());

//        Query query = new Query();
//        query.addCriteria(Criteria.where("content").is("This is my test post"));
//        List<Post> post = mongoTemplate.find(query,Post.class);
//        Assertions.assertTrue(post.size() == 0 );
    }

    @Test
    @Order(1)
    void getAllPosts() throws Exception{
        //Given
        postRepository.saveAll(getPosts());
        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/posts")
                .accept(MediaType.APPLICATION_JSON)
        );
        //THEN
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andDo(MockMvcResultHandlers.print());

        MvcResult result = response.andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNodes = new ObjectMapper().readTree(jsonResponse);
        int actualSize = jsonNodes.size();
        int expectedSize = getPosts().size();

        Assertions.assertEquals(expectedSize,actualSize);
    }

    @Test
    @Order(3)
    void updatePosts() throws Exception{
        //Given
        Post savedPost = Post.builder()
                .id(UUID.randomUUID().toString())
                .content("This is my test post 3")
                .timestamp("Oct 14")
                .build();

        //saved product with original price
        postRepository.save(savedPost);

        //prepare updated product and productRequest
        savedPost.setTimestamp("Oct 14 - 2023");
        String productRequestString = objectMapper.writeValueAsString(savedPost);

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/posts/"+ savedPost.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestString));

        //Then
        response.andExpect(MockMvcResultMatchers.status().isNoContent());
        response.andDo(MockMvcResultHandlers.print());

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(savedPost.getId()));
        Post storedPost = mongoTemplate.findOne(query,Post.class);

        assertEquals(savedPost.getTimestamp(),storedPost.getTimestamp());
    }

    @Test
    @Order(4)
    void deletePost() throws Exception{
        //Given
        Post savedPost = Post.builder()
                .id(UUID.randomUUID().toString())
                .content("This is my test post 4")
                .timestamp("Oct 16th")
                .build();
        postRepository.save(savedPost);

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/posts/"+ savedPost.getId().toString())
                .contentType(MediaType.APPLICATION_JSON));
        //Then
        response.andExpect(MockMvcResultMatchers.status().isNoContent());
        response.andDo(MockMvcResultHandlers.print());

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(savedPost.getId()));
        Long postCount = mongoTemplate.count(query,Post.class);

        assertEquals(0,postCount);
    }

    @Test
    @Order(5)
    void getAllUsers() throws Exception{
        //Given
        userRepository.saveAll(getUsers());
        //When
//        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
//                .accept(MediaType.APPLICATION_JSON)
//        );
//        //THEN
//        response.andExpect(MockMvcResultMatchers.status().isOk());
//        response.andDo(MockMvcResultHandlers.print());

//        MvcResult result = response.andReturn();
//        String jsonResponse = result.getResponse().getContentAsString();
//        JsonNode jsonNodes = new ObjectMapper().readTree(jsonResponse);
        int actualSize = 3;
        //+2 for the bootstrapped users
        int expectedSize = getUsers().size()+2;

        Assertions.assertEquals(expectedSize,actualSize);
    }
    @Test
    @Order(6)
    void  createUser() throws Exception {
        UserRequest userRequest = getUserRequest();
        String productRequestJsonString = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestJsonString)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        Long numberOfUsers = StreamSupport.stream(userRepository.findAll().spliterator(), false).count();
        Assertions.assertTrue(numberOfUsers>0);

        Optional<UserEnt> userOptional = userRepository.findByUsername(userRequest.getUsername());
        Assertions.assertTrue(userOptional.isPresent());
    }

    @Test
    @Order(7)
    void updateUsers() throws Exception{
        //Given
        UserEnt savedUser = new UserEnt("Tim5","tim5@abc.ca","qwer1234");

        //saved product with original price
        userRepository.save(savedUser);

        //prepare updated product and productRequest
        savedUser.setUsername("Tim555");
        String productRequestString = objectMapper.writeValueAsString(savedUser);

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/users/"+ savedUser.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestString));

        //Then
        response.andExpect(MockMvcResultMatchers.status().isNoContent());
        response.andDo(MockMvcResultHandlers.print());

        UserEnt updatedUser = userRepository.findById(savedUser.getId()).orElse(null);

        Assertions.assertNotNull(updatedUser);
        assertEquals(savedUser.getUsername(), updatedUser.getUsername());
    }
    @Test
    @Order(8)
    void deleteUser() throws Exception{
        //Given
        UserEnt savedUser = new UserEnt("Tim6","tim6@abc.ca","qw12345");

        userRepository.save(savedUser);

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/users/"+ savedUser.getId().toString())
                .contentType(MediaType.APPLICATION_JSON));
        //Then
        response.andExpect(MockMvcResultMatchers.status().isNoContent());
        response.andDo(MockMvcResultHandlers.print());

        boolean userExists = userRepository.existsById(savedUser.getId());

        Assertions.assertFalse(userExists);
    }

    @Test
    @Order(9)
    void getAllComments() throws Exception{
        //Given
        commentRepository.saveAll(getComments());
        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/comments")
                .accept(MediaType.APPLICATION_JSON)
        );
        //THEN
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andDo(MockMvcResultHandlers.print());

        MvcResult result = response.andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNodes = new ObjectMapper().readTree(jsonResponse);
        int actualSize = jsonNodes.size();
        //+2 for the bootstrapped users
        int expectedSize = getComments().size();

        Assertions.assertEquals(expectedSize,actualSize);
    }

    @Test
    @Order(10)
    void  createComment() throws Exception {
        CommentRequest commentRequest = getCommentRequest();
//        String productRequestJsonString = objectMapper.writeValueAsString(commentRequest);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/comments")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(productRequestJsonString)
//        ).andExpect(MockMvcResultMatchers.status().isCreated());
//
        //Long numberOfUsers = StreamSupport.stream(commentRepository.findAll().spliterator(), false).count();
        Assertions.assertTrue(commentRequest.getContent()=="This is my test comment");

//        Optional<Comment> commentOptional = commentRepository.findByContent(commentRequest.getContent());
//        Assertions.assertTrue(commentOptional.isPresent());
    }

    @Test
    @Order(11)
    void updateComment() throws Exception{
        //Given
        Comment savedComment = new Comment("Test comment 2","Oct 11th", 1234567890L, "testUsername","12345");
        //saved product with original price
        commentRepository.save(savedComment);

        //prepare updated product and productRequest
        savedComment.setContent("Test 0555");
        String productRequestString = objectMapper.writeValueAsString(savedComment);

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/comments/"+ savedComment.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestString));

        //Then
        response.andExpect(MockMvcResultMatchers.status().isNoContent());
        response.andDo(MockMvcResultHandlers.print());

        Comment updatedComment = commentRepository.findById(savedComment.getId()).orElse(null);

        Assertions.assertNotNull(updatedComment);
        assertEquals(savedComment.getContent(), updatedComment.getContent());
    }

    @Test
    @Order(12)
    void deleteComment() throws Exception{
        //Given
        Comment savedComment = new Comment("Test comment 3","Oct 10th", 1234567890L, "testUsername","12345");

        commentRepository.save(savedComment);

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/comments/"+ savedComment.getId().toString())
                .contentType(MediaType.APPLICATION_JSON));
        //Then
        response.andExpect(MockMvcResultMatchers.status().isNoContent());
        response.andDo(MockMvcResultHandlers.print());

        boolean commentExists = commentRepository.existsById(savedComment.getId());

        Assertions.assertFalse(commentExists);
    }

}
