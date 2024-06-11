package dam.fx3.service;

import dam.fx3.api.repository.LeagueRepository;
import dam.fx3.api.repository.UserRepository;
import dam.fx3.modelo.dto.UserDTO;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dam.fx3.modelo.entities.User;

import java.util.List;

@Service
@Transactional
public class UserService {
	@Autowired
    private UserRepository userRepository;
    @Autowired
    private LeagueRepository leagueRepository;

    public UserService() {

    }

    public UserDTO getById(int id) {
        return mapUserToDTO(userRepository.findById(id).get());
    }

    public UserDTO getByName(String name) {
        return mapUserToDTO(userRepository.findByName(name).get());
    }

    public List<UserDTO> listAllUsers() {
        return userRepository
                            .findAll()
                            .stream()
                            .map(this::mapUserToDTO)
                            .toList();
    }

    public void save(UserDTO userDTO) {
        userRepository.save(mapUserDTOToEntity(userDTO));
    }

    public void delete(UserDTO userDTO) {
        userRepository.delete(mapUserDTOToEntity(userDTO));
    }

    private User mapUserDTOToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public UserDTO mapUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }
}
