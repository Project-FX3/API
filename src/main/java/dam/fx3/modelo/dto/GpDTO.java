package dam.fx3.modelo.dto;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import lombok.Data;

@Data
public class GpDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String url;
	private String circuitname;
	private String locality;
	private String country;
	private String racename;
	private String racedate;
	private String racetime;
	private Map<Integer, Integer> raceResults;
	private String firstpracticedate;
	private String firstpracticetime;
	private Map<Integer, Integer> firstPracticeResults;
	private String secondpracticedate;
	private String secondpracticetime;
	private Map<Integer, Integer> secondPracticeResults;
	private String thirdpracticedate;
	private String thirdpracticetime;
	private Map<Integer, Integer> thirdPracticeResults;
	private String qualifyingdate;
	private String qualifyingtime;
	private Map<Integer, Integer> qualifyingResults;
	private String qualifyingSprintdate;
	private String qualifyingSprinttime;
	private Map<Integer, Integer> qualifyingSprintResults;
	private String sprintdate;
	private String sprinttime;
	private Map<Integer, Integer> sprintResults;
}