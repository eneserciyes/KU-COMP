package tr.com.teamfaster.domain.services.storage;

import java.util.ArrayList;

/**
 * ISaveLoader is the interface managers are implementing. It is used for getting and setting game information.
 */

public interface ISaveLoader {

    ArrayList<ArrayList<String>> getInfo();

    void loadInfo(ArrayList<ArrayList<String>> info);
}
