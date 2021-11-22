package tr.com.teamfaster.domain.services.storage;

import org.bson.Document;
import org.json.simple.parser.JSONParser;
import tr.com.teamfaster.domain.utils.GameSettings;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * FileStorageAdapter is the class for saving and loading to a Document
 */
public class FileStorageAdapter implements IStorageAdapter {

    private static FileStorageAdapter instance;

    /**
     * @return FileStorageAdapter
     */
    public static FileStorageAdapter getInstance() {
        if (instance == null) instance = new FileStorageAdapter();
        return instance;
    }

    /**
     * Returns the saveFile with the right username
     *
     * @param username
     * @return Bson Document
     */
    private Document getSaveFile(String username) {
        try {
            FileReader reader = new FileReader("saves/" + username + ".json");
            return Document.parse(new JSONParser().parse(reader).toString());
        } catch (FileNotFoundException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sets the save file to the parameter doc
     *
     * @param doc
     */
    private void setSaveFile(Document doc) {
        try {
            String username = (String) doc.get("username");
            Path path = Paths.get("saves/" + username + ".json");
            Files.deleteIfExists(path);
            Files.createFile(path);
            Files.write(path, doc.toJson().getBytes());
        } catch (FileAlreadyExistsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new document with the parameter saveInfo and sets save file to that document
     *
     * @param saveInfo
     */
    @Override
    public void save(SaveInfo saveInfo) {
        System.out.println("Saving to file.");
        Document doc = new Document();

        doc.append("username", saveInfo.getUsername());
        doc.append("game_settings", saveInfo.getGameSettings());
        doc.append("shooter_info", saveInfo.getShooterInfo());
        doc.append("atom_info", saveInfo.getAtomInfo());
        doc.append("molecule_info", saveInfo.getMoleculeInfo());
        doc.append("powerup_info", saveInfo.getPowerupInfo());
        doc.append("blocker_info", saveInfo.getBlockerInfo());

        setSaveFile(doc);

        System.out.println("saved");
        System.out.println(doc);
    }

    /**
     * SaveInfo is setted to the information in the doc
     *
     * @return saveInfo
     */
    @Override
    public SaveInfo getSave() {
        System.out.println("Loading from file.");
        SaveInfo saveInfo = new SaveInfo();
        Document doc = getSaveFile(GameSettings.getUsername());
        if (doc == null) return null;

        saveInfo.setUsername((String) doc.get("username"));

        saveInfo.setGameSettings((ArrayList<ArrayList<String>>) doc.get("game_settings"));

        saveInfo.setShooterInfo((ArrayList<ArrayList<String>>) doc.get("shooter_info"));

        saveInfo.setAtomInfo((ArrayList<ArrayList<String>>) doc.get("atom_info"));
        saveInfo.setMoleculeInfo((ArrayList<ArrayList<String>>) doc.get("molecule_info"));
        saveInfo.setPowerupInfo((ArrayList<ArrayList<String>>) doc.get("powerup_info"));
        saveInfo.setBlockerInfo((ArrayList<ArrayList<String>>) doc.get("blocker_info"));

        System.out.println("getSave():");
        System.out.println(doc);

        return saveInfo;
    }
}
