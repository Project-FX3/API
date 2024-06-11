package dam.fx3.modelo.dto;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class DriverDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	
	private int driverNumber;
	private String countryCode;
	private String image;
	private String lastName;
	private String teamColour;
	private String teamName;
	private String nameAcronym;
	
}
