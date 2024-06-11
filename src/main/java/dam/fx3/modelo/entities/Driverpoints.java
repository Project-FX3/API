package dam.fx3.modelo.entities;
// Generated 14 may 2024 1:33:31 by Hibernate Tools 4.3.6.Final

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Setter;

import java.io.Serial;

/**
 * Driverpoints generated by hbm2java
 */
@Setter
@Entity
@Table(name = "driverpoints", catalog = "fx3")
public class Driverpoints implements java.io.Serializable {
	
	@Serial
	private static final long serialVersionUID = 1L;
	private int driverNumber;
	private Integer points;

	public Driverpoints() {
	}

	public Driverpoints(int driverNumber) {
		this.driverNumber = driverNumber;
	}

	public Driverpoints(int driverNumber, Integer points) {
		this.driverNumber = driverNumber;
		this.points = points;
	}

	@Id
	@Column(name = "driver_number", unique = true, nullable = false)
	public int getDriverNumber() {
		return this.driverNumber;
	}

	@Column(name = "points")
	public Integer getPoints() {
		return this.points;
	}

}