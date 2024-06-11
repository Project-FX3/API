package dam.fx3.api.repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class ExternApiRepository implements ExternApiInterface{
    private static final String POSITION_URL = "https://api.openf1.org/v1/position?date=";
    private static final String CHEQUERED_URL = "https://api.openf1.org/v1/race_control?flag=CHEQUERED&date=";

    public ExternApiRepository() {
        
    }

    private String makeApiCall(String apiUrl) throws Exception {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();
        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public Map<Integer, Integer> getPositionToQualifying(String date)throws Exception {
        String jsonResponse = makeApiCall("https://api.openf1.org/v1/sessions?session_type=Qualifying&date_start="+date+"&date_end="+date);

        List<Map<String, Object>> dataList = parseJsonToList(jsonResponse);

        int session_key = 0;

        for (Map<String, Object> entry : dataList) {
             session_key = (int) entry.get("session_key");
             break;
        }

        jsonResponse = makeApiCall("https://api.openf1.org/v1/position?session_key="+session_key);

        return filterAndRetrieveLatestPositions(parseJsonToList(jsonResponse));
    }


    public Map<Integer, Integer> getPositionMap(String date, int sessionType)throws Exception {
            String jsonResponse = makeApiCall(POSITION_URL + date);
            List<Map<String, Object>> dataList = parseJsonToList(jsonResponse, sessionType);
            return filterAndRetrieveLatestPositions(dataList);
    }

    public boolean isRaceFinished(String date) throws Exception {
        String apiUrl =  CHEQUERED_URL + date;
        String jsonResponse = makeApiCall(apiUrl);
        List<Map<String, Object>> dataList = parseJsonToList(jsonResponse);

        if (dataList.isEmpty()) {
            return false;
        }

        for (Map<String, Object> entry : dataList) {
            String flag = (String) entry.get("flag");
            if ("CHEQUERED".equals(flag)) {
                return true;
            }
        }

        return false;
    }

    private List<Map<String, Object>> parseJsonToList(String jsonResponse, int sessionType) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> dataList = mapper.readValue(jsonResponse, new TypeReference<List<Map<String, Object>>>(){});


        if (sessionType == 0) {
            return dataList;
        } else {
            List<Map<String, Object>> filteredList = new ArrayList<>();
            Map<String, Object> firstMap = dataList.get(0);
            int firstSessionKey = (int) firstMap.get("session_key");
            for (Map<String, Object> entry : dataList) {
                int currentSessionKey = (int) entry.get("session_key");
                if (sessionType == 1 && currentSessionKey == firstSessionKey) {
                    filteredList.add(entry);
                } else if (sessionType == 2 && currentSessionKey != firstSessionKey) {
                    filteredList.add(entry);
                }
            }
            return filteredList;
        }
    }

    private List<Map<String, Object>> parseJsonToList(String jsonResponse) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonResponse, new TypeReference<List<Map<String, Object>>>(){});
    }

    private Map<Integer, Integer> filterAndRetrieveLatestPositions(List<Map<String, Object>> dataList) {
        Map<Integer, Integer> positionMap = new HashMap<>();
        for (Map<String, Object> entry : dataList) {
            int driverNumber = (int) entry.get("driver_number");
            int position = (int) entry.get("position");
            positionMap.put(driverNumber, position);
        }
        positionMap = positionMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return positionMap;
    }
}
