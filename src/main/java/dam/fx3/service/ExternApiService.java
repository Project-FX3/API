package dam.fx3.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dam.fx3.api.repository.ExternApiRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ExternApiService {
	@Autowired
	private ExternApiRepository externApiRepository;
	
	
	public ExternApiService(){
	}
	
	
	public Map<Integer, Integer> getPositions(String date, int sessionType) {
       try {
		return externApiRepository.getPositionMap(date, sessionType);
	   } catch (Exception e) {
           return null;
       }
    }
	public boolean isRaceFinished(String date) {
		try {
			return externApiRepository.isRaceFinished(date);
		} catch (Exception e) {
			return false;
		}
	}
}
