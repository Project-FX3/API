package dam.fx3.controller;

import dam.fx3.modelo.dto.UserLeagueDTO;
import dam.fx3.service.UserLeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userleague")
public class UserLeagueController {
    @Autowired
    UserLeagueService userLeagueService;

    @GetMapping("/user/{id}")
    public ResponseEntity<List<UserLeagueDTO>> getByUserId(@PathVariable int id){
        try {
            List<UserLeagueDTO> userLeagueDTO = userLeagueService.getByUserId(id);
            return ResponseEntity.ok(userLeagueDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/league/{id}")
    public ResponseEntity<List<UserLeagueDTO>> getByLeagueId(@PathVariable int id){
        try {
            List<UserLeagueDTO> userLeagueDTO = userLeagueService.getByLeagueId(id);
            return ResponseEntity.ok(userLeagueDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{userId}/{leagueId}")
    public ResponseEntity<UserLeagueDTO> getByUserIdAndLeagueId(@PathVariable int userId, @PathVariable int leagueId){
        try {
            UserLeagueDTO userLeagueDTO = userLeagueService.getByUserIdAndLeagueIdDTO(userId, leagueId);
            return ResponseEntity.ok(userLeagueDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<UserLeagueDTO> add(@RequestBody UserLeagueDTO userLeagueDTO){
        try {
            userLeagueDTO.setPuntuation(0);
            userLeagueService.save(userLeagueDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{userId}/{leagueId}")
    public ResponseEntity<?> delete(@PathVariable int userId, @PathVariable int leagueId){
        try {
            userLeagueService.delete(userId, leagueId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
