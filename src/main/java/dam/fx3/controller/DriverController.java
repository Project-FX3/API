package dam.fx3.controller;

import dam.fx3.modelo.dto.DriverDTO;
import dam.fx3.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/driver")
public class DriverController {
    @Autowired
    DriverService driverService;

    @GetMapping("")
    public List<DriverDTO> list() {
        return driverService.listAllDrivers();
    }

    @GetMapping("/{number}")
    public ResponseEntity<DriverDTO> get(@PathVariable int number) {
        try {
            DriverDTO driver = driverService.findByNumber(number);
            return ResponseEntity.ok(driver);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{idUser}/league/{idLeague}")
    public ResponseEntity<List<DriverDTO>> getByUserId(@PathVariable int idUser, @PathVariable int idLeague) {
        try {
            List<DriverDTO> driver = driverService.findByUserIdLeagueId(idUser, idLeague);
            return ResponseEntity.ok(driver);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
