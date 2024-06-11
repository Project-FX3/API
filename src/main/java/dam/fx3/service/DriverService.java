package dam.fx3.service;

import dam.fx3.modelo.dto.DriverDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dam.fx3.api.repository.DriverRepository;
import dam.fx3.modelo.entities.Driver;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class DriverService {
	@Autowired
	private  DriverRepository driverRepository;

	public DriverService(){

	}
	
	public DriverDTO findByNumber(int number) {
		return mapDriverToDTO(driverRepository.findById(number).get());
	}

	public List<DriverDTO> listAllDrivers() {
		return driverRepository
								.findAll()
								.stream()
								.map(this::mapDriverToDTO)
								.toList();
	}

	public List<DriverDTO> findByUserIdLeagueId(int userId, int leagueId){
		return driverRepository
								.findByIdUserIdLeague(userId, leagueId)
								.stream()
								.map(this::mapDriverToDTO)
								.toList();
	}

	private DriverDTO mapDriverToDTO(Driver driver) {
		DriverDTO driverDTO = new DriverDTO();
		driverDTO.setDriverNumber(driver.getDriverNumber());
		driverDTO.setCountryCode(driver.getCountryCode());
		driverDTO.setImage(driver.getImage());
		driverDTO.setLastName(driver.getLastName());
		driverDTO.setTeamColour(driver.getTeamColour());
		driverDTO.setTeamName(driver.getTeamName());
		driverDTO.setNameAcronym(driver.getNameAcronym());
		return driverDTO;
	}
}
