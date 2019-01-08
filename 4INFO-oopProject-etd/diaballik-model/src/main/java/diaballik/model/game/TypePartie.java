package diaballik.model.game;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import diaballik.model.states.EtatJ1vsIA;
import diaballik.model.states.EtatJ1vsJ2;
import diaballik.model.states.EtatTour;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

// We add a unique identifier to the Json object
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public enum TypePartie {
    TYPE_J_VS_J(new EtatJ1vsJ2()),
    TYPE_J_VS_IA(new EtatJ1vsIA());

    @JsonIgnore
    private transient EtatTour etatInitial;


    TypePartie() {

    }

    TypePartie(final EtatTour etatInitial) {
        this.etatInitial = etatInitial;
    }


    @JsonIgnore
    public EtatTour getEtatInitial() {
        return etatInitial;
    }

}
