package diaballik.model.plateau;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import diaballik.model.joueur.Joueur;

import java.util.Objects;

// We want to add a JSON attribute to know the type of the object.
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
// We add a unique identifier to the Json object
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Case {
	private Pion pion;

	private Balle balle;

	public Case() {

	}

	@JsonCreator
	private Case(@JsonProperty("pion") final Pion pion, @JsonProperty("balle") final Balle balle) {
		this.pion = pion;
		this.balle = balle;
	}

	public Pion getPion() {
		return pion;
	}

	public void setPion(final Pion pion) {
		this.pion = pion;
	}

	public Balle getBalle() {
		return balle;
	}

	public void setBalle(final Balle balle) {
		this.balle = balle;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Case aCase = (Case) o;
		return Objects.equals(pion, aCase.pion) &&
				Objects.equals(balle, aCase.balle);
	}

	@Override
	public int hashCode() {
		return Objects.hash(pion, balle);
	}
}
