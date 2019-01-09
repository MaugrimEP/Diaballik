package diaballik.serialization;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import diaballik.model.coordonnee.Coordonnee;
import diaballik.model.game.GameManager;

import java.awt.Color;
import java.io.IOException;

public class GameManagerDeserializer extends StdDeserializer<GameManager> {
    protected GameManagerDeserializer() {
        super(GameManager.class);
    }

    @Override
    public GameManager deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper().registerModule(new GuavaModule()).setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        final SimpleModule module = new SimpleModule();
        module.addSerializer(Color.class, new ColorSerializer());
        module.addDeserializer(Color.class, new ColorDeserializer());
        module.addKeyDeserializer(Coordonnee.class, new MapCoordonneeDeserializer());
        mapper.registerModule(module);


        final StringBuilder stringBuilder = new StringBuilder(jsonParser.readValueAsTree().toString());
        stringBuilder.insert(1, "\"type\" : \"GameManager\",");

        final GameManager gameManager = mapper.readValue(stringBuilder.toString(), GameManager.class);
        gameManager.getJoueur1().setGameManager(gameManager);
        gameManager.getJoueur2().setGameManager(gameManager);
        gameManager.getAutomate().setGameManager(gameManager);
        return gameManager;
    }
}
