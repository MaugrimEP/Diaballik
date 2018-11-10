package diaballik.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import diaballik.model.game.GameManager;
import diaballik.model.game.GameManagerBuilder;
import diaballik.model.game.TypePartie;
import diaballik.model.joueur.JoueurHumain;
import diaballik.model.plateau.PlateauEnemyAmongUs;
import diaballik.restWrapper.InitGameClasses;
import diaballik.serialization.DiabalikJacksonProvider;
import java.awt.Color;
import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMarshalling {
	static Stream<Object> getInstancesToMarshall() {
//		final Player p1 = new Player(); //TODO to update
//		etc.
		GameManager gm = new GameManager();
		JoueurHumain j1 = new JoueurHumain(Color.RED,"ringo",gm);
		JoueurHumain j2 = new JoueurHumain(Color.BLACK,"start",gm);
		InitGameClasses initGameClasses = new InitGameClasses(j1,j2,new PlateauEnemyAmongUs(), TypePartie.TYPE_J_VS_J);

        GameManager gmA = new GameManagerBuilder().typePartie(initGameClasses.getTypePartie()).joueur1(initGameClasses.getJ1()).joueur2(initGameClasses.getJ2()).plateau(initGameClasses.getPlateau()).build();
        return Stream.of(initGameClasses,gmA); //p1); // Add other marshallable elements
	}

	@ParameterizedTest
	@MethodSource("getInstancesToMarshall")
	void testMarshall(final Object objectToMarshall) throws IOException {
		final ObjectMapper mapper = new DiabalikJacksonProvider().getMapper();
		final String serializedObject = mapper.writeValueAsString(objectToMarshall);
		System.out.println(serializedObject);
		final Object readValue = mapper.readValue(serializedObject, objectToMarshall.getClass());
        final String serializedObject2 = mapper.writeValueAsString(readValue);
        System.out.println(serializedObject2);
        assertEquals(objectToMarshall, readValue);
	}
}
