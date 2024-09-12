package com.example.practice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class GoingController {

    private final List<State> states;

    public GoingController() {
        states = new ArrayList<>();
        states.add(new State(1, "hot", "eastern", false, true));
        states.add(new State(2, "cold", "western", true, false));
        states.add(new State(3, "mild", "south", true, true));
        states.add(new State(4, "ice-cold", "mid-west", false, false));
        states.add(new State(5, "breezy", "north", false, true));
    }

    @GetMapping("/api/states")
    public ResponseEntity<List<State>> getStates() {
        return ResponseEntity.ok(states);
    }

    @GetMapping("/api/states/weather")
    public ResponseEntity<List<String>> getWeather() {
        List<String> weatherList = new ArrayList<>();
        for (State state : states) {
            weatherList.add(state.getWeather());
        }
        return ResponseEntity.ok(weatherList);
    }

    @GetMapping("/api/states/visited")
    public ResponseEntity<List<Boolean>> getVisited() {
        List<Boolean> visitedList = new ArrayList<>();
        for (State state : states) {
            visitedList.add(state.getVisited());
        }
        return ResponseEntity.ok(visitedList);
    }

    @GetMapping("/api/states/time_zones")
    public ResponseEntity<List<String>> getTime_Zones() {
        List<String> timezoneList = new ArrayList<>();
        for (State state : states) {
            timezoneList.add(state.getTime_zone());
        }
        return ResponseEntity.ok(timezoneList);
    }

    @GetMapping("/api/states/want_to_visit")
    public ResponseEntity<List<Boolean>> getWant_to_visit() {
        List<Boolean> Want_to_visitList = new ArrayList<>();
        for (State state : states) {
            Want_to_visitList.add(state.getWant_to_visit());
        }
        return ResponseEntity.ok(Want_to_visitList);
    }

    @PostMapping("/api/states")
    public State postStates(@RequestBody State newState) {
        int nextId = states.size() + 1;
        newState.setId(nextId);
        states.add(newState);
        return newState;
    }

    @DeleteMapping("/api/states/{id}")
    public void deleteState(@PathVariable int id) {
        states.removeIf(state -> state.getId() == id);
    }

    @PutMapping("/api/states/{id}")
    public State updateStateWeather(@PathVariable int id, @RequestBody String weather) {
        for (State state : states) {
            if (state.getId() == id) {
                state.setWeather(weather);
                return state;
            }
        }
        throw new RuntimeException("State with id " + id + " not found");
    }

    @PatchMapping("/api/states/{id}")
    public State updateStateWantToVisit(@PathVariable int id, @RequestBody Boolean want_to_visit) {
        for (State state : states) {
            if (state.getId() == id) {
                state.setWant_to_visit(want_to_visit);
                return state;
            }
        }
        throw new RuntimeException("State with id " + id + " not found");
    }


}