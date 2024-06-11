package dam.fx3.modelo.dto;

import dam.fx3.modelo.entities.Driver;
import dam.fx3.modelo.entities.League;
import dam.fx3.modelo.entities.User;
import lombok.Data;


import java.io.Serial;
import java.io.Serializable;


@Data
public class UserLeagueDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	
	private Integer league;
	private Integer user;
	private String userName;
	private Integer driver4Number;
	private Integer driver2Number;
	private Integer driver3Number;
	private Integer driver1Number;
	private Integer puntuation;
	
}
