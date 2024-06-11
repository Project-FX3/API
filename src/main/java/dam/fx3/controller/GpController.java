package dam.fx3.controller;

import dam.fx3.modelo.dto.GpDTO;
import dam.fx3.service.GpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gp")
public class GpController {
    @Autowired
    GpService gpService;

    @GetMapping("")
    public List<GpDTO> list(){
        return gpService.listAllGp();
    }

    @GetMapping("/withoutResults")
    public List<GpDTO> listWithoutResults(){
        return gpService.listAllGpWithoutResults();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GpDTO> get(@PathVariable int id){
        try {
            GpDTO gp = gpService.findGpById(id,true);
            return ResponseEntity.ok(gp);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/next")
    public ResponseEntity<GpDTO> getNextGp(){
        try {
            GpDTO gp = gpService.findNextGpWithoutResults();
            return ResponseEntity.ok(gp);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/session/{sessionType}/{idGp}")
    public ResponseEntity<Map<Integer, Integer>> searchResultsBySessionType(@PathVariable String sessionType, @PathVariable int idGp){
        try {
            return ResponseEntity.ok(gpService.searchResultsBySessionType(sessionType, idGp));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
         }
    }

