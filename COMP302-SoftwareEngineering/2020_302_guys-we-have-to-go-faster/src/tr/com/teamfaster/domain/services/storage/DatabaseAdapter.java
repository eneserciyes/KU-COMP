package tr.com.teamfaster.domain.services.storage;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import tr.com.teamfaster.domain.utils.GameSettings;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

/**
 * DatabaseAdapter is the class for saving games to and loading from MongoDB collection.
 */
public class DatabaseAdapter implements IStorageAdapter {

    private static DatabaseAdapter instance;
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    /**
     * Initializes a new DatabaseAdapter making the connections to Comp302 Database in MongoDB
     */
    private DatabaseAdapter() {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE); // e.g. or Log.WARNING, etc.
        MongoClient mongoClient = MongoClients.create("mongodb+srv://comp302_user:comp302_password@sandbox.v2mqr.mongodb.net/"); // url connection to the server
        database = mongoClient.getDatabase("Comp302"); // selecting the database
        collection = database.getCollection("KuvidCollection"); // collection*/
    }

    /**
     * @return DatabaseAdapter
     */
    public static DatabaseAdapter getInstance() {
        if (instance == null) instance = new DatabaseAdapter();
        return instance;
    }

    /**
     * Creates a new Document with the information from saveInfo, inserts the doc to the collection
     *
     * @param saveInfo
     */
    @Override
    public void save(SaveInfo saveInfo) {
        System.out.println("Saving to database.");
        Document doc = new Document();

        doc.append("username", saveInfo.getUsername());
        doc.append("game_settings", saveInfo.getGameSettings());
        doc.append("shooter_info", saveInfo.getShooterInfo());
        doc.append("atom_info", saveInfo.getAtomInfo());
        doc.append("molecule_info", saveInfo.getMoleculeInfo());
        doc.append("powerup_info", saveInfo.getPowerupInfo());
        doc.append("blocker_info", saveInfo.getBlockerInfo());

        System.out.println("saved");
        System.out.println(doc);
        collection.deleteMany(eq("username", GameSettings.getUsername()));
        collection.insertOne(doc);
    }

    /**
     * Gets the save document from database and sets saveInfo's fields.
     *
     * @return saveInfo
     */
    @Override
    public SaveInfo getSave() {
        System.out.println("Loading from database.");

        Document doc = collection.find(eq("username", GameSettings.getUsername())).first();
        if (doc == null) return null;

        SaveInfo saveInfo = new SaveInfo();

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
