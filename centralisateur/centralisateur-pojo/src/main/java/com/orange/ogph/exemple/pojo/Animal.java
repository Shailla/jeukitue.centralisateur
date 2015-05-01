package com.orange.ogph.exemple.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Exemple de classe manufacturée, c'est à dire écrite à la main.
 * Si une table nommée Animal existe dans le modèle c'est quand même la classe ici présente
 * qui sera déployée telle quelle dans le projet cible.
 * 
 * Attention : toute modification du modèle n'aura aucun impact sur la classe manufacturée <code>Animal</code>
 */
@Entity
@Table(name = "animal")
public class Animal implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	static public final String _id = "id";
	static public final String _race = "race";

	private Long id;
	private String race;

	public Animal() {
	}

	public Animal(String race) {
		this.race = race;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	/*BasicAnnotation:getId*/
	/*AnnColumnAnnotation:getId*/@Column(name = "ANI_ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*BasicAnnotation:getRace*/
	/*AnnColumnAnnotation:getRace*/@Column(name = "ANI_RACE", length = 250)
	public String getRace() {
		return this.race;
	}

	public void setRace(String race) {
		this.race = race;
	}
}
