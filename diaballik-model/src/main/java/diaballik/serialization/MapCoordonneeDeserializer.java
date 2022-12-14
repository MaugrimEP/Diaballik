package diaballik.serialization;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import diaballik.model.coordonnee.FabriquePoidsMoucheCoordonnees;

import java.io.IOException;

public class MapCoordonneeDeserializer extends KeyDeserializer {
    @Override
    public Object deserializeKey(final String s, final DeserializationContext deserializationContext) throws IOException {
        final String[] split = s.split(":");
        return FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }
}
