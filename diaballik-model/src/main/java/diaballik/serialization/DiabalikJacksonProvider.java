package diaballik.serialization;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import diaballik.model.coordonnee.Coordonnee;
import diaballik.model.game.GameManager;

import javax.inject.Singleton;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.awt.Color;

@Provider
@Singleton
public class DiabalikJacksonProvider implements ContextResolver<ObjectMapper> {
    private final ObjectMapper mapper;

    public DiabalikJacksonProvider() {
        super();
        mapper = new ObjectMapper().registerModule(new GuavaModule()).setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        final SimpleModule module = new SimpleModule();
        module.addSerializer(Color.class, new ColorSerializer());
        module.addDeserializer(Color.class, new ColorDeserializer());
        module.addKeyDeserializer(Coordonnee.class, new MapCoordonneeDeserializer());
        module.addDeserializer(GameManager.class, new GameManagerDeserializer());
        mapper.registerModule(module);
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    @Override
    public ObjectMapper getContext(final Class<?> type) {
        return mapper;
    }
}
