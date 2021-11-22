package tr.com.teamfaster.domain.services.storage;

/**
 * IStorageAdapter is the interface implemented by DatabaseAdapter and FileStorageAdapter
 * It has save() and getSave() methods
 */

public interface IStorageAdapter {

    void save(SaveInfo saveInfo);

    SaveInfo getSave();

}
