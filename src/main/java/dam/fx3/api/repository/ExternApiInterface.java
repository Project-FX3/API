package dam.fx3.api.repository;

import java.util.Map;

public interface ExternApiInterface {
	Map<Integer, Integer> getPositionMap(String date, int sessionType) throws Exception;
	boolean isRaceFinished(String date) throws Exception;
}
