package dam.fx3.controller;

import dam.fx3.modelo.dto.UserDTO;
import dam.fx3.service.UserService;
import dam.fx3.utils.EncryptSha256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping("")
    public List<UserDTO> list() {
        return userService.listAllUsers();
    }

    @GetMapping("/login/{name}/{password}")
    public ResponseEntity<UserDTO> login(@PathVariable String name, @PathVariable String password) {
        try {
           UserDTO user = userService.getByName(name);
            String pass = EncryptSha256.encrypt(password);
            if(user.getPassword().equals(pass)) {
                return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
            } else throw new NoSuchElementException();
        } catch (NoSuchElementException e) {
            return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable int id) {
        try {
            UserDTO user = userService.getById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<?> add(@RequestBody UserDTO userDTO){
        try {
        userDTO.setPassword(EncryptSha256.encrypt(userDTO.getPassword()));
        userService.save(userDTO);
        return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
