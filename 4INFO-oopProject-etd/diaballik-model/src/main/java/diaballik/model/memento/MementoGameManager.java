package diaballik.model.memento;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import diaballik.model.game.GameManager;
import diaballik.serialization.DiabalikJacksonProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

public class MementoGameManager {

    static final String DIRECTORY = "mementosFile";

    private Date date;

    private GameManager etat;

    public MementoGameManager(final Date date, final GameManager etat) {
        this.date = date;
        this.etat = etat;
    }

    /**
     * @param f
     * @return le Memento chargé à partir du fichier f si il existe, null sinon
     */
    public static MementoGameManager fromFile(final File f) {
        try {

            final String json = new String(Files.readAllBytes(f.toPath()));
            final String dateWithExtension = f.getName();
            final Date date = new Date(Long.parseLong(dateWithExtension.split("\\.")[0]));

            final ObjectMapper mapper = new DiabalikJacksonProvider().getMapper();
            final GameManager gm = mapper.readValue(json, GameManager.class);

            return new MementoGameManager(date, gm);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFileName() {
        return DIRECTORY + "/" + date.getTime() + ".mem";
    }

    /**
     * sauvegarde l'état dans un fichier
     */
    public void saveFile() {
        final ObjectMapper mapper = new DiabalikJacksonProvider().getMapper();
        try {
            final String serializedObject = mapper.writeValueAsString(etat);
            final File dir = new File(DIRECTORY);
            if (!dir.exists()) {
                dir.mkdir();
            }
            final File file = new File(getFileName());
            file.createNewFile();
            final FileOutputStream outputStream = new FileOutputStream(file, false);
            final byte[] strToBytes = serializedObject.getBytes();
            outputStream.write(strToBytes);
            outputStream.close();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameManager getEtat() {
        return etat;
    }

    public Date getDate() {
        return date;
    }

    /**
     * supprime le fichier assiocié à un état
     */
    public void deleteFile() {
        new File(getFileName()).delete();
    }
}
