import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Created by J.D. Isenhart on 6/7/2015
 * 12:56 PM
 */
public class Conversion {
    static HashMap<String, HashMap> saveData = null;
    static int saveVersion = 3;

    public static HashMap checkVersion(HashMap<String, HashMap> incomingSaveData) {
        saveData = incomingSaveData;

        if (!saveData.containsKey("File")) {
            saveData.put("File", new HashMap());
            saveData.get("File").put("Version", saveVersion);
        }
        checkPlayers();
        checkParty();
        checkLoot();
        try {
            FileOutputStream fileOut = new FileOutputStream(String.valueOf(Start.saveFilePath));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(incomingSaveData);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done checking version of save file!");
        return saveData;
    }

    public static void checkPlayers() {
        for (int i = 0; i < saveData.get("Players").size(); i++) {
            String player = String.valueOf(saveData.get("Players").get(i));
            if (!saveData.get(player).containsKey("BR")) {
                int damageStat = Integer.parseInt(String.valueOf(saveData.get(player).get("Damage")));
                int killStat = Integer.parseInt(String.valueOf(saveData.get(player).get("Kills")));
                int healingStat = Integer.parseInt(String.valueOf(saveData.get(player).get("Healing")));
                int BR = (int) ((damageStat * 2) + ((double) healingStat * 2.1) + (killStat * 6));
                saveData.get(player).put("BR", BR);
            } else {
                int damageStat = Integer.parseInt(String.valueOf(saveData.get(player).get("Damage")));
                int killStat = Integer.parseInt(String.valueOf(saveData.get(player).get("Kills")));
                int healingStat = Integer.parseInt(String.valueOf(saveData.get(player).get("Healing")));
                int BR = (int) ((damageStat * 2) + ((double) healingStat * 2.1)) + (killStat * 6);
                saveData.remove("BR");
                saveData.get(player).put("BR", BR);

            }
        }
    }

    public static void checkParty() {
        if (!saveData.containsKey("Party")) {
            HashMap partyStats = new HashMap();
            partyStats.put("Dice", 1365);
            partyStats.put("Swords", 365);
            partyStats.put("Arrows", 146);
            partyStats.put("Spells", 97);
            partyStats.put("XP", 1575);
            partyStats.put("Hits", 130);
            saveData.put("Party", partyStats);
        } else if (!saveData.get("Party").containsKey("Hits")) {
            saveData.get("Party").put("Hits", 130);
        }
    }

    public static void checkLoot() {
        if (!saveData.containsKey("Loot")) {
            HashMap lootStats = new HashMap();
            lootStats.put("Data", new Object[][]{});
            lootStats.put("Notes", new String(""));
            saveData.put("Loot", lootStats);
        }
        Object[][] savedTable = (Object[][]) saveData.get("Loot").get("Data");
        if (savedTable[0].length == 2) {
            Object[][] newTable = new Object[savedTable.length][3];
            for (int i = 0; i < savedTable.length; i++) {
                newTable[i][0] = savedTable[i][0];
                newTable[i][1] = savedTable[i][1];
                newTable[i][2] = 1;
            }
            savedTable = newTable;
        }
        saveData.get("Loot").remove("Data");
        saveData.get("Loot").put("Data", savedTable);
    }
}
