package dam.fx3.modelo.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String password;

}
