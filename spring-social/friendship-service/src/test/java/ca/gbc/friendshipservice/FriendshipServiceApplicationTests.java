package ca.gbc.friendshipservice;

import ca.gbc.friendshipservice.dto.FriendRequestResponse;
import ca.gbc.friendshipservice.model.FriendRequest;
import ca.gbc.friendshipservice.repository.FriendRequestRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureMockMvc
class FriendshipServiceApplicationTests extends AbstractContainerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FriendRequestRepository friendRequestRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private List<FriendRequest> getFriends() {
        return Arrays.asList(
                FriendRequest.builder().senderId(1L).timestamp("June 3").build(),
                FriendRequest.builder().senderId(2L).timestamp("June 4").build()
                // Add more FriendRequest objects as needed
        );
    }
    private FriendRequest getFriendRequest() {
        return FriendRequest.builder()
                .senderId(3L)
                .timestamp("June 3")
                .build();
    }


    @Test
    @Order(1)
    void createFriendRequest() throws Exception {
        FriendRequest friendRequest = getFriendRequest();
        String friendRequestJsonString = objectMapper.writeValueAsString(friendRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/friend-requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(friendRequestJsonString))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        // Assertions
        List<FriendRequest> allFriendRequests = friendRequestRepository.findAll();
        Assertions.assertTrue(allFriendRequests.size() > 0);

    }

    @Test
    @Order(2)
    void getAllFriends() throws Exception {
        friendRequestRepository.saveAll(getFriends());
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/friend-requests")
                .accept(MediaType.APPLICATION_JSON)
        );
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andDo(MockMvcResultHandlers.print());

        MvcResult result = response.andReturn();
        String jsonResponse = result.getResponse().getContentAsString();

        List<FriendRequestResponse> friendRequests = objectMapper.readValue(jsonResponse, new TypeReference<List<FriendRequestResponse>>() {});
        Assertions.assertTrue(friendRequests.size() > 0);
    }
    @Test
    @Order(3)
    void updateFriendRequest() throws Exception {
        List<FriendRequest> friends = getFriends();
        friendRequestRepository.saveAll(friends);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/friend-requests")
                .accept(MediaType.APPLICATION_JSON)
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andDo(MockMvcResultHandlers.print());

        MvcResult result = response.andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        List<FriendRequestResponse> friendRequests = objectMapper.readValue(jsonResponse, new TypeReference<List<FriendRequestResponse>>() {
        });
        Assertions.assertTrue(friendRequests.size() > 0);
    }
    @Test
    @Order(3)
    void deleteFriendRequest() throws Exception {
        FriendRequest savedFriendRequest = friendRequestRepository.save(getFriendRequest());

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/friend-requests/" + savedFriendRequest.getId())
                .contentType(MediaType.APPLICATION_JSON));


        response.andExpect(MockMvcResultMatchers.status().isNoContent());
        response.andDo(MockMvcResultHandlers.print());

        boolean friendRequestExists = friendRequestRepository.existsById(savedFriendRequest.getId());

        Assertions.assertFalse(friendRequestExists);
    }

}