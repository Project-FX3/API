package dam.fx3.modelo.entities;
// Generated 3 may 2024 1:08:40 by Hibernate Tools 4.3.6.Final


import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Setter;

import java.io.Serial;

/**
 * Userleague generated by hbm2java
 */

@Setter
@Entity
@Table(name = "userleague", catalog = "fx3")
public class Userleague implements java.io.Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	private UserleagueId id;
	private Driver driver4Number;
	private Driver driver2Number;
	private Driver driver3Number;
	private Driver driver1Number;
	private League league;
	private User user;
	private int puntuation;

	public Userleague() {
	}

	public Userleague(League league, User user) {
		this.id = new UserleagueId(user.getId(),league.getId());
		this.league = league;
		this.user = user;
	}

	public Userleague(int idUser, int idLeague, Driver driver4Number, Driver driver2Number,
					  Driver driver3Number, Driver driver1Number, League league, User user, int puntuation) {
		this.id = new UserleagueId(idUser, idLeague);
		this.driver4Number = driver4Number;
		this.driver2Number = driver2Number;
		this.driver3Number = driver3Number;
		this.driver1Number = driver1Number;
		this.league = league;
		this.user = user;
		this.puntuation = puntuation;
	}
	
	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "iduser", column = @Column(name = "iduser", nullable = false)),
			@AttributeOverride(name = "idleague", column = @Column(name = "idleague", nullable = false)) })
	public UserleagueId getId() {
		return this.id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driver4number")
	public Driver getDriver4Number() {
		return this.driver4Number;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driver2number")
	public Driver getDriver2Number() {
		return this.driver2Number;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driver3number")
	public Driver getDriver3Number() {
		return this.driver3Number;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driver1number")
	public Driver getDriver1Number() {
		return this.driver1Number;
	}

	@Column(name = "puntuation")
	public int getPuntuation() {
		return this.puntuation;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idleague", nullable = false, insertable = false, updatable = false)
	public League getLeague() {
		return this.league;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "iduser", nullable = false, insertable = false, updatable = false)
	public User getUser() {
		return this.user;
	}


}