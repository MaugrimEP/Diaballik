package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMarshalling {
	static Stream<Object> getInstancesToMarshall() {
//		final Player p1 = new Player("foo", Color.RED);
//		final Player p2 = new Player("bar", Color.YELLOW);
//		final Piece piece = new Piece(10, 20, true, false);
//		final Game game = new NewGameBuilder(p1, p2, new StdScenarioBuilder()).build();
//		final MovePieceCommand action1 = new MovePieceCommand(game, game.getBoard().getPiecesOfP1().get(1), 1, 4);
//		final MoveBallCommand action2 = new MoveBallCommand(game, game.getBoard().getPiecesOfP1().get(3), game.getBoard().getPiecesOfP1().get(2));
//
//		game.playAction(action1);
//		game.playAction(action2);
//
//		return Stream.of(p1, piece, new ComputerPlayer("barbar", Color.GREEN, new NoobStrategy()),
//			new StdScenarioBuilder().build(p1, p2),
//			action1, action2, new Turn(),
//			new NewGameBuilder(p1, p2, new StdScenarioBuilder()).build(),
//			game);

		final Farm farm = new Farm();
		farm.addAnimal(new Cow("Harry", false));

		return Stream.of(
			new Cow("Holly", true),
			farm
		);
	}

	@ParameterizedTest
	@MethodSource("getInstancesToMarshall")
	void testMarshall(final Object objectToMarshall) throws IOException {
		final ObjectMapper mapper =  new ObjectMapper(); // new DiabalikJacksonProvider().getMapper();
		final String serializedObject = mapper.writeValueAsString(objectToMarshall);
		System.out.println(serializedObject);
		final Object readValue = mapper.readValue(serializedObject, objectToMarshall.getClass());
		assertEquals(objectToMarshall, readValue);
	}
}
