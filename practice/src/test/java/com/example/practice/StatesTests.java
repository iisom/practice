package com.example.practice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GoingController.class)
public class StatesTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GoingController goingController;


    private List<State> states;

    @Order(2)
    @Test
    void getListOfAllStatesTests() throws Exception {
        mockMvc.perform(get("/api/states"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));
        ResponseEntity<List<State>> states = goingController.getStates();
        assertNotNull(states.getBody());
    }

    @Order(3)
    @Test
    void getWeatherListTest() throws Exception {
        mockMvc.perform(get("/api/states/weather"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$", containsInAnyOrder("hot", "cold", "mild", "ice-cold", "breezy")));
    }

    @Order(1)
    @Test
    void getVisitedListTest() throws Exception {

        mockMvc.perform(get("/api/states/visited"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", containsInAnyOrder(false, true, true, false, false)));
    }

    @Order(4)
    @Test
    void getTime_ZoneListTest() throws Exception {
        mockMvc.perform(get("/api/states/time_zones"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", containsInAnyOrder("north", "south", "eastern", "western", "mid-west")));
    }

    @Order(5)
    @Test
    void getWant_To_VisitListTest() throws Exception {
        mockMvc.perform(get("/api/states/want_to_visit"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", containsInAnyOrder(false, true, true, true, false)));
    }
    @Order(6)
    @Test
    void addStateTest() throws Exception {
        String statesJson = "{\"weather\":\"chilly\",\"timeZone\":\"central\",\"visited\":true,\"wantToVisit\":true}";
        mockMvc.perform(post("/api/states")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(statesJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(6));
    }
    @Order(7)
    @Test
    void removeStateTest() throws Exception {
        // Assuming 'goingController' has a method to fetch states that returns a List<State>
        ResponseEntity<List<State>> response = goingController.getStates();
        List<State> initialStates = response.getBody();
        // Check the initial size of the state list
        int initialSize = initialStates.size();
        int stateIdToDelete = 2;
        // Perform the delete operation
        mockMvc.perform(delete("/api/states/{id}", stateIdToDelete)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        // Fetch the states again to verify the deletion
        ResponseEntity<List<State>> updatedResponse = goingController.getStates();
        List<State> updatedStates = updatedResponse.getBody();
        // Check the new size should be one less than the initial size
        assertEquals(initialSize - 1, updatedStates.size());
        // Optionally check that the deleted state is no longer in the list
        assertFalse(updatedStates.stream().anyMatch(state -> state.getId() == stateIdToDelete));
    }

    @Order(8)
    @Test
    void updateStateTest() throws  Exception{
        ResponseEntity<List<State>> response = goingController.getStates();
        List<State> statesBeforeUpdate = response.getBody();
        State stateToUpdate = null;
        int stateIdToUpdate = 1;
        for (State state : statesBeforeUpdate) {
            if (state.getId() == stateIdToUpdate) {
                stateToUpdate = state;
                break;
            }
        }
        assertNotNull(stateToUpdate, "State with ID " + stateIdToUpdate + " should exist");
        String jsonRequest = "chilly";
        mockMvc.perform(put("/api/states/{id}", stateIdToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.weather").value("chilly"));
    }

@Order(9)
@Test
void updateStateWantToVisitTest() throws  Exception{
    ResponseEntity<List<State>> response = goingController.getStates();
    List<State> statesBeforeUpdate = response.getBody();
    State stateToUpdate = null;
    int stateIdToUpdate = 1;
    for (State state : statesBeforeUpdate) {
        if (state.getId() == stateIdToUpdate) {
            stateToUpdate = state;
            break;
        }
    }
    assertNotNull(stateToUpdate, "State with ID " + stateIdToUpdate + " should exist");
    String jsonRequest = "false";
    mockMvc.perform(patch("/api/states/{id}", stateIdToUpdate)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.want_to_visit").value("false"));
}}