package diaballik.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
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

		return Stream.of(Color.RED); //p1); // Add other marshallable elements
	}

	@ParameterizedTest
	@MethodSource("getInstancesToMarshall")
	void testMarshall(final Object objectToMarshall) throws IOException {
		final ObjectMapper mapper = new DiabalikJacksonProvider().getMapper();
		final String serializedObject = mapper.writeValueAsString(objectToMarshall);
		System.out.println(serializedObject);
		final Object readValue = mapper.readValue(serializedObject, objectToMarshall.getClass());
		assertEquals(objectToMarshall, readValue);
	}
}
