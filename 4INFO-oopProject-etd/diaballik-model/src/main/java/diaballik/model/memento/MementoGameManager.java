package diaballik.model.memento;


import com.fasterxml.jackson.databind.ObjectMapper;
import diaballik.model.exceptions.MyIOMemoException;
import diaballik.model.game.GameManager;
import diaballik.serialization.DiabalikJacksonProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Date;

public class MementoGameManager {

    static final String DIRECTORY = "mementosFile";

    private final Date date;

    private final GameManager etat;

    public MementoGameManager(final Date date, final GameManager etat) {
        this.date = new Date(date.getTime());
        this.etat = etat;
    }

    /**
     * @param f
     * @return le Memento chargé à partir du fichier f si il existe, null sinon
     */
    public static MementoGameManager fromFile(final File f) {

        final StringBuilder str = new StringBuilder();
        BufferedReader br = null;
        InputStreamReader inputStreamReader = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(f);
            inputStreamReader = new InputStreamReader(fileInputStream, Charset.defaultCharset());
            br = new BufferedReader(inputStreamReader);
            br.lines().forEach(str::append);

            final String json = str.toString();
            final String dateWithExtension = f.getName();
            final Date date = new Date(Long.parseLong(dateWithExtension.split("\\.")[0]));
            final ObjectMapper mapper = new DiabalikJacksonProvider().getMapper();
            final GameManager gm = mapper.readValue(json, GameManager.class);
            return new MementoGameManager(date, gm);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
        FileOutputStream outputStream = null;

        try {
            final String serializedObject = mapper.writeValueAsString(etat);
            final File dir = new File(DIRECTORY);
            if (!dir.exists()) {
                final boolean mkdir = dir.mkdir();
                if (!mkdir) {
                    throw new MyIOMemoException("Erreur de creation du dossier");
                }
            }
            final File file = new File(getFileName());
            final boolean newFile = file.createNewFile();
            if (!newFile) {
                throw new MyIOMemoException("Erreur de creation de fichier");
            }
            outputStream = new FileOutputStream(file, false);
            outputStream.write(serializedObject.getBytes(Charset.defaultCharset()), 0, serializedObject.length());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public GameManager getEtat() {
        return etat;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    /**
     * supprime le fichier assiocié à un état
     */
    public void deleteFile() {
        final boolean delete = new File(getFileName()).delete();
        if (!delete) {
            throw new MyIOMemoException("Erreur de suppression du fichier");
        }
    }
}
