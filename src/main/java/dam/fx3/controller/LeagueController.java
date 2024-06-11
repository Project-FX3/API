package dam.fx3.controller;

import dam.fx3.modelo.dto.LeagueDTO;
import dam.fx3.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/league")
public class LeagueController {
    @Autowired
    LeagueService leagueService;

    @GetMapping("")
    public List<LeagueDTO> list(){
        return leagueService.findAll();
    }
    @GetMapping("/accesscode/{accesscode}")
    public  ResponseEntity<LeagueDTO> getByAccessCode(@PathVariable String accesscode){
        try {
        LeagueDTO league = leagueService.findByAccesscode(accesscode);
            return ResponseEntity.ok(league);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeagueDTO> getById(@PathVariable int id){
        try {
            LeagueDTO league = leagueService.findById(id);
            return ResponseEntity.ok(league);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<LeagueDTO> add(@RequestBody LeagueDTO leagueDTO){
        try {
        return ResponseEntity.ok(leagueService.save(leagueDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
