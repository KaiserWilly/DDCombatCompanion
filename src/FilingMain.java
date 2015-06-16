import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by J.D. Isenhart on 6/10/2015
 * 7:44 PM
 */
public class FilingMain {
    public static void createSave() {
        Map<String, HashMap> newMap = new HashMap<String, HashMap>();
        newMap.put("Players", new HashMap<Integer, String>());
        newMap.get("Players").put(0, "Enemy");
        newMap.put("Enemy", new HashMap<String, Integer>());
        newMap.get("Enemy").put("Damage", 0);
        newMap.get("Enemy").put("Kills", 0);
        newMap.get("Enemy").put("Healing", 0);
        HashMap partyStats = new HashMap();
        partyStats.put("Dice", 0);
        partyStats.put("Swords", 0);
        partyStats.put("Arrows", 0);
        partyStats.put("Spells", 0);
        partyStats.put("XP", 0);
        partyStats.put("Hits", 0);
        newMap.put("Party", partyStats);
        try {
            System.out.println("Save Path: " + String.valueOf(Start.saveFilePath));
            FileOutputStream fileOut = new FileOutputStream(String.valueOf(Start.saveFilePath));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(newMap);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            System.out.println("Error in File Creation; New Save; Filing.createSave");
            e.printStackTrace();
        }
    }
}
