package dam.fx3.service;

import dam.fx3.api.repository.DriverRepository;
import dam.fx3.api.repository.UserLeagueRepository;
import dam.fx3.modelo.dto.UserLeagueDTO;
import dam.fx3.modelo.entities.UserleagueId;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dam.fx3.modelo.entities.Userleague;

import java.util.List;

@Service
@Transactional
public class UserLeagueService {
    @Autowired
    private UserLeagueRepository userLeagueRepository;
    @Autowired
    private DriverRepository driverRepository;

    public UserLeagueService() {

    }

    public List<UserLeagueDTO> getByUserId(int iduser) {
        List<Userleague> userleague = userLeagueRepository.findByUserId(iduser);
        return userleague
                                    .stream()
                                    .map(this::mapUserLeagueToDTO)
                                    .toList();
    }
    public List<UserLeagueDTO> getByLeagueId(int idleague) {
        return userLeagueRepository
                                    .findByLeagueId(idleague)
                                    .stream()
                                    .map(this::mapUserLeagueToDTO)
                                    .toList();
    }

    public Userleague getByUserIdAndLeagueId(int iduser, int idleague) {
        return userLeagueRepository.findByUserIdAndLeagueId(iduser, idleague).get();
    }

    public UserLeagueDTO getByUserIdAndLeagueIdDTO(int iduser, int idleague) {
        return mapUserLeagueToDTO(getByUserIdAndLeagueId(iduser, idleague));
    }

    public void save(UserLeagueDTO userLeagueDTO) {
        userLeagueRepository.save(mapUserLeagueDTOToEntity(userLeagueDTO));
    }
    public void delete(int userId, int leagueId) {
        userLeagueRepository.delete(getByUserIdAndLeagueId(userId, leagueId));
    }

    public UserLeagueDTO mapUserLeagueToDTO(Userleague userleague) {
        UserLeagueDTO userLeagueDTO = new UserLeagueDTO();
        userLeagueDTO.setUser(userleague.getUser().getId());
        userLeagueDTO.setLeague(userleague.getLeague().getId());
        userLeagueDTO.setUserName(userleague.getUser().getName());
        userLeagueDTO.setDriver4Number(userleague.getDriver4Number().getDriverNumber());
        userLeagueDTO.setDriver2Number(userleague.getDriver2Number().getDriverNumber());
        userLeagueDTO.setDriver3Number(userleague.getDriver3Number().getDriverNumber());
        userLeagueDTO.setDriver1Number(userleague.getDriver1Number().getDriverNumber());
        userLeagueDTO.setPuntuation(userleague.getPuntuation());
        return userLeagueDTO;
    }

    public Userleague mapUserLeagueDTOToEntity(UserLeagueDTO userLeagueDTO) {
        Userleague userleague = new Userleague();
        userleague.setId(new UserleagueId(userLeagueDTO.getUser(), userLeagueDTO.getLeague()));
        userleague.setDriver4Number(driverRepository.findById(userLeagueDTO.getDriver4Number()).get());
        userleague.setDriver2Number(driverRepository.findById(userLeagueDTO.getDriver2Number()).get());
        userleague.setDriver3Number(driverRepository.findById(userLeagueDTO.getDriver3Number()).get());
        userleague.setDriver1Number(driverRepository.findById(userLeagueDTO.getDriver1Number()).get());
        userleague.setPuntuation(userLeagueDTO.getPuntuation());
        return userleague;
    }
}
