package diaballik.model.states;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import diaballik.model.joueur.Joueur;

import java.util.Objects;

// We want to add a JSON attribute to know the type of the object.
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
// We have to identify the sub-classes to help the marshalling
@JsonSubTypes({
        @JsonSubTypes.Type(value = EtatJ1vsIA.class),
        @JsonSubTypes.Type(value = EtatIAvsJ1.class),
        @JsonSubTypes.Type(value = EtatJ1vsJ2.class),
        @JsonSubTypes.Type(value = EtatJ2vsJ1.class)
})
// We add a unique identifier to the Json object
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class EtatTour {
    private static int NB_COUP_INIT = 3;

    private int nbCoup;
    protected EtatTour() {
        this(NB_COUP_INIT);
    }
    @JsonCreator
    protected EtatTour(@JsonProperty("nbCoup") final int nbCoup) {
        this.nbCoup = nbCoup;
    }

    public int jouer(final AutomateGameManager automate) {
        return 0;
    }

    public abstract Joueur getJoueurCourant(AutomateGameManager automate);

    @JsonIgnore
    public abstract EtatTour getEtatSuivant();

    public boolean tourFini() {
        return false;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EtatTour etatTour = (EtatTour) o;
        return nbCoup == etatTour.nbCoup;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nbCoup);
    }
}
