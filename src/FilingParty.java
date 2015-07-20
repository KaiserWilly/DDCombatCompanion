import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Created by J.D. Isenhart on 6/8/2015
 * 1:07 AM
 */
public class FilingParty {
    public static Object[][] hitPercentage, avgDamage;
    static HashMap<String, HashMap> incomingSaveData;
    static ObjectMapper mapper = new ObjectMapper();
    static Object[][] partyStatsRowData;

    public static HashMap readSave() {
        HashMap incomingSaveData = null;
        try {
            FileInputStream fileIn = new FileInputStream(String.valueOf(Start.saveFilePath));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            incomingSaveData = (HashMap<String, HashMap>) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception i) {
            i.printStackTrace();
        }
        return incomingSaveData;
    }

    public static void writeFile(HashMap saveData) {
        try {
            FileOutputStream fileOut = new FileOutputStream(String.valueOf(Start.saveFilePath));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(saveData);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object[] columnDataPSTable() {
        return new Object[]{"Statistic", "Value"};
    }

    public static Object[][] rowDataPSTable() {
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        incomingSaveData = readSave();
        partyStatsRowData = new Object[][]{
                {"Dice Rolled", incomingSaveData.get("Party").get("Dice")},
                {"Weapons Swung", incomingSaveData.get("Party").get("Swords")},
                {"Arrows Shot", incomingSaveData.get("Party").get("Arrows")},
                {"Spells Cast", incomingSaveData.get("Party").get("Spells")},
                {"Enemies Hit", incomingSaveData.get("Party").get("Hits")},
        };
        return partyStatsRowData;
    }

    public static Object[][] hitPerRowData() {
        incomingSaveData = readSave();
        int attempts = Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("Swords"))) + Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("Arrows"))) + Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("Spells")));
        int hits = Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("Hits")));
        double ratio = (double) Math.round((double) hits / ((double) attempts) * 10000) / 10000;
        hitPercentage = new Object[][]{
                {attempts, hits, ratio}
        };
        return hitPercentage;
    }

    public static Object[] hitPerColumnHeaders() {
        return new Object[]{"Attempts", "Hits", "Ratio"};
    }

    public static Object[][] avgDamRowData() {
        HashMap saveParty = incomingSaveData.get("Party");
        Object[][] friendCom = (Object[][]) saveParty.get("FriendCom");
        int friendDam = (int) friendCom[0][0];
        incomingSaveData = readSave();
        int hits = Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("Hits")));
        double ratio = (double) Math.round(((double) friendDam / (double) hits) * 10000) / 10000;
        avgDamage = new Object[][]{{hits, friendDam, ratio}};
        return avgDamage;
    }

    public static Object[] avgDamColumnHeaders() {
        return new Object[]{"Hits", "Damage Done", "Ratio"};
    }

    public static Object[][] FtoERowData() {
        incomingSaveData = readSave();
        HashMap saveEnemy = incomingSaveData.get("Enemy");
        int enemyDam = (int) saveEnemy.get("Damage");
        HashMap saveParty = incomingSaveData.get("Party");
        Object[][] friendCom = (Object[][]) saveParty.get("FriendCom");
        int friendDam = (int) friendCom[0][0];
        double ratio = (double) Math.round((double) friendDam / ((double) enemyDam) * 10000) / 10000;
        Object[][] FtoEArray = new Object[][]{
                {friendDam, enemyDam, ratio}
        };
        return FtoEArray;
    }

    public static Object[] FtoEColumnHeaders() {
        return new Object[]{"Friendly Damage", "Enemy Damage", "Friend to Enemy Ratio"};
    }
}