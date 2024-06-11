package dam.fx3.service;

import dam.fx3.api.repository.ExternApiRepository;
import dam.fx3.api.repository.GpRepository;
import dam.fx3.modelo.dto.GpDTO;
import dam.fx3.modelo.entities.Gp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class GpService {
    @Autowired
    GpRepository gpRepository;
    
    @Autowired
    ExternApiRepository externApiRepository;

    public GpService(){
    	
    }

    public List<GpDTO> listAllGp() {
        List<Gp> gpList = gpRepository.findAll();
        return gpList.stream()
                .sorted(Comparator.comparing(Gp::getRacedate))
                .map(gp -> mapGpToDTO(gp, false))
                .collect(Collectors.toList());
    }
    public GpDTO findGpById(int id, boolean includeResults) {
        return gpRepository.findById(id).map(gp -> mapGpToDTO(gp, includeResults)).orElse(null);
    }


    public Gp findById(int id) {
        return gpRepository.findById(id).get();
    }

    //Map<DriverNumber, Position>
    public Map<Integer, Integer> searchResultsBySessionType(String sessionType, int id) {
        Gp gp = findById(id);

        return switch (sessionType) {
            case "race" -> determineResultsBySessionType("race", gp);
            case "firstPractice" -> determineResultsBySessionType("firstPractice", gp);
            case "secondPractice" -> determineResultsBySessionType("secondPractice", gp);
            case "thirdPractice" -> determineResultsBySessionType("thirdPractice", gp);
            case "qualifying" -> determineResultsBySessionType("qualifying", gp);
            case "qualifyingSprint" -> determineResultsBySessionType("qualifyingSprint", gp);
            case "sprint" -> determineResultsBySessionType("sprint", gp);
            default -> null;
        };
    }

    public List<GpDTO> listAllGpWithoutResults() {
        List<Gp> gpList = gpRepository.findAll();
        return gpList.stream().map(gp -> mapGpToDTO(gp, false)).collect(Collectors.toList());
    }

    public void save(Gp gp) {
        gpRepository.save(gp);
    }


    private GpDTO mapGpToDTO(Gp gp, boolean includeResults) {
        GpDTO gpDTO = new GpDTO();
        gpDTO.setId(gp.getId());
        gpDTO.setUrl(gp.getUrl());
        gpDTO.setCircuitname(gp.getCircuitname());
        gpDTO.setLocality(gp.getLocality());
        gpDTO.setCountry(gp.getCountry());
        gpDTO.setRacename(gp.getRacename());

        gpDTO.setRacedate(gp.getRacedate().toString());
        gpDTO.setRacetime(gp.getRacetime().toString());


        gpDTO.setFirstpracticedate(gp.getFirstpracticedate().toString());
        gpDTO.setFirstpracticetime(gp.getFirstpracticetime().toString());

        gpDTO.setQualifyingdate(gp.getQualifyingdate().toString());
        gpDTO.setQualifyingtime(gp.getQualifyingtime().toString());

        if (gp.getSprintdate() != null)
            {
                gpDTO.setQualifyingSprintdate(gp.getSecondpracticedate().toString());
                gpDTO.setQualifyingSprinttime(gp.getSecondpracticetime().toString());
                gpDTO.setSprintdate(gp.getSprintdate().toString());
                gpDTO.setSprinttime(gp.getSprinttime().toString());

            } else{
            gpDTO.setSecondpracticedate(gp.getSecondpracticedate().toString());
            gpDTO.setSecondpracticetime(gp.getSecondpracticetime().toString());

            gpDTO.setThirdpracticedate(gp.getThirdpracticedate().toString());
            gpDTO.setThirdpracticetime(gp.getThirdpracticetime().toString());
        }

            if (includeResults) {
                if (gpDTO.getThirdpracticedate() == null) {
                    gpDTO.setQualifyingSprintResults(determineResultsBySessionType("qualifyingSprint", gp));
                    gpDTO.setSprintResults(determineResultsBySessionType("sprint", gp));
                } else{
                    gpDTO.setSecondPracticeResults(determineResultsBySessionType("secondPractice", gp));
                    gpDTO.setThirdPracticeResults(determineResultsBySessionType("thirdPractice", gp));
                }
                gpDTO.setRaceResults(determineResultsBySessionType("race", gp));
                gpDTO.setFirstPracticeResults(determineResultsBySessionType("firstPractice", gp));
                gpDTO.setQualifyingResults(determineResultsBySessionType("qualifying", gp));

            }

        return gpDTO;
    }

    public boolean isProcessed(int id) {
        return gpRepository.findById(id).map(Gp::getProcessed).orElse(false);
    }

    public GpDTO findNextGpWithoutResults() {
        return gpRepository.getNextGp().map(gp -> mapGpToDTO(gp, false)).orElse(null);
    }
    public GpDTO findNextGp() {
        return gpRepository.getNextGp().map(gp -> mapGpToDTO(gp, true)).orElse(null);
    }

    public boolean actualGPIsFinished() {
        try {
            return externApiRepository.isRaceFinished(findNextGp().getRacedate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    private Map<Integer, Integer> determineResultsBySessionType(String sessionType, Gp gp) {
//        String date;
//        int mapperType;
//        boolean thirdPracticeIsNotNull = gp.getThirdpracticedate() != null;
//        try {
//            switch (sessionType) {
//                case "race":
//                    date = gp.getRacedate().toString();
//                    mapperType = 0; // Race
//                    break;
//                case "firstPractice":
//                    date = gp.getFirstpracticedate().toString();
//                    mapperType = 1; // Free practice 1
//                    break;
//                case "secondPractice":{
//                    if (thirdPracticeIsNotNull) {
//                        date = gp.getThirdpracticedate().toString();
//                        mapperType = 2; // Free practice 2
//                    } else {
//                        return null;
//                    }
//                    break;
//                }
//                case "thirdPractice":
//                    if (thirdPracticeIsNotNull) {
//                        date = gp.getThirdpracticedate().toString();
//                        mapperType = 1; // Free practice 3
//                    } else {
//                        return null;
//                    }
//                    break;
//                case "qualifying":
//                    date = gp.getQualifyingdate().toString();
//                    mapperType = 2; // Qualifying
//                case "qualifyingSprint":
//                    if (!thirdPracticeIsNotNull) {
//                        date = gp.getSecondpracticedate().toString();
//                        mapperType = 2; // Sprint qualifying
//                    } else {
//                        return null;
//                    }
//                    break;
//                case "sprint":
//                    if (!thirdPracticeIsNotNull) {
//                        date = gp.getSprintdate().toString();
//                        mapperType = 1; // Sprint
//                    } else {
//                        return null;
//                    }
//                    break;
//                default:
//                    return null; // Valor de sesión no válido
//            }
//            return externApiRepository.getPositionMap(date, sessionType);
//        }catch (Exception e) {
//            return null;
//        }
//    }
// Practice 1, Practice 2, Practice 3, Sprint Qualifying, Sprint, Qualifying, Race

//https://api.openf1.org/v1/sessions?session_type=Qualifying&date_start=2024-03-08&date_end=2024-03-08
    //Coger session_key
    //https://api.openf1.org/v1/position?session_key=9476

    private Map<Integer, Integer> determineResultsBySessionType(String sessionType, Gp gp) {
        String date;
        int mapperType;
        boolean thirdPracticeIsNotNull = gp.getThirdpracticedate() != null;
        try {
            switch (sessionType) {
                case "race":
                    date = gp.getRacedate().toString();
                    mapperType = 0; // Race
                    break;
                case "firstPractice":
                    date = gp.getFirstpracticedate().toString();
                    mapperType = 1; // Free practice 1
                    break;
                case "secondPractice":{
                    if (thirdPracticeIsNotNull) {
                        date = gp.getThirdpracticedate().toString();
                        mapperType = 2; // Free practice 2
                    } else {
                        return null;
                    }
                    break;
                }
                case "thirdPractice":
                    if (thirdPracticeIsNotNull) {
                        date = gp.getThirdpracticedate().toString();
                        mapperType = 1; // Free practice 3
                    } else {
                        return null;
                    }
                    break;
                case "qualifying":
                    date = gp.getQualifyingdate().toString();
                    return externApiRepository.getPositionToQualifying(date);
                case "qualifyingSprint":
                    if (!thirdPracticeIsNotNull) {
                        date = gp.getSecondpracticedate().toString();
                        mapperType = 2; // Sprint qualifying
                    } else {
                        return null;
                    }
                    break;
                case "sprint":
                    if (!thirdPracticeIsNotNull) {
                        date = gp.getSprintdate().toString();
                        mapperType = 1; // Sprint
                    } else {
                        return null;
                    }
                    break;
                default:
                    return null; // Valor de sesión no válido
            }
            return externApiRepository.getPositionMap(date, mapperType);
        }catch (Exception e) {
            return null;
        }
    }
}
