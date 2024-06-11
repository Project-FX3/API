package dam.fx3.service;

import dam.fx3.api.repository.LeagueRepository;
import dam.fx3.modelo.dto.LeagueDTO;
import dam.fx3.modelo.entities.League;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LeagueService {

	@Autowired
    private LeagueRepository leagueRepository;

    public LeagueService(){
    }

    public LeagueDTO findByAccesscode(String accesscode) {
        return leagueRepository
                                .findByAccesscode(accesscode)
                                .map(this::mapLeagueToDTO)
                                .orElse(null);
    }

    public List<LeagueDTO> findAll(){
        return leagueRepository
                                .findAll()
                                .stream()
                                .map(this::mapLeagueToDTO)
                                .toList();
    }

    public LeagueDTO findById(int id) {
        return leagueRepository
                                .findById(id)
                                .map(this::mapLeagueToDTO)
                                .orElse(null);
    }


    public List<LeagueDTO> listAllLeagues() {
        return leagueRepository
                                .findAll()
                                .stream()
                                .map(this::mapLeagueToDTO)
                                .toList();
    }


    public LeagueDTO save(LeagueDTO leagueDTO) {
       League league = leagueRepository.save(mapLeagueDTOToEntity(leagueDTO));

       return mapLeagueToDTO(league);
    }

    private League mapLeagueDTOToEntity(LeagueDTO leagueDTO) {
        League league = new League();
        league.setName(leagueDTO.getName());
        return league;
    }

    public LeagueDTO mapLeagueToDTO(dam.fx3.modelo.entities.League league) {
        LeagueDTO leagueDTO = new LeagueDTO();
        leagueDTO.setId(league.getId());
        leagueDTO.setName(league.getName());
        leagueDTO.setAccesscode(league.getAccesscode());
        return leagueDTO;
    }
}
