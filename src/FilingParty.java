import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by J.D. Isenhart on 6/8/2015
 * 1:07 AM
 */
public class FilingParty {
    static HashMap<String, HashMap> incomingSaveData;
    static ObjectMapper mapper = new ObjectMapper();
    static DecimalFormat formatter = new DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale.getDefault()));


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
        return new Object[]{"STATISTIC", "VALUE"};
    }

    public static Object[][] rowDataPSTable() {
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        incomingSaveData = readSave();
        Object[][] partyStatsRowData = new Object[][]{
                {"Dice Rolled", incomingSaveData.get("Party").get("Dice")},
                {"Weapons Swung", incomingSaveData.get("Party").get("Swords")},
                {"Arrows Shot", incomingSaveData.get("Party").get("Arrows")},
                {"Spells Cast", incomingSaveData.get("Party").get("Spells")},
                {"Enemies Hit", incomingSaveData.get("Party").get("Hits")},
        };
        for (int i = 0; i < partyStatsRowData.length; i++) {
            partyStatsRowData[i][1] = formatter.format(Integer.parseInt(String.valueOf(partyStatsRowData[i][1])));
        }
        return partyStatsRowData;
    }

    public static Object[][] hitPerRowData() {
        incomingSaveData = readSave();
        int attempts = Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("Swords"))) + Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("Arrows"))) + Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("Spells")));
        int hits = Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("Hits")));
        double ratio = (double) Math.round((double) hits / ((double) attempts) * 10000) / 10000;
        Object[][] hitPercentage = new Object[][]{
                {attempts, hits, ratio}
        };
        hitPercentage[0][0] = formatter.format(Integer.parseInt(String.valueOf(hitPercentage[0][0])));
        hitPercentage[0][1] = formatter.format(Integer.parseInt(String.valueOf(hitPercentage[0][1])));
        return hitPercentage;
    }

    public static Object[] hitPerColumnHeaders() {
        return new Object[]{"ATTEMPTS", "HITS", "RATIO"};
    }

    public static Object[][] avgDamRowData() {
        HashMap saveParty = incomingSaveData.get("Party");
        Object[][] friendCom = (Object[][]) saveParty.get("FriendCom");
        int friendDam = (int) friendCom[0][0];
        incomingSaveData = readSave();
        int hits = Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("Hits")));
        double ratio = (double) Math.round(((double) friendDam / (double) hits) * 10000) / 10000;
        Object[][] avgDamage = new Object[][]{{hits, friendDam, ratio}};
        avgDamage[0][0] = formatter.format(Integer.parseInt(String.valueOf(avgDamage[0][0])));
        avgDamage[0][1] = formatter.format(Integer.parseInt(String.valueOf(avgDamage[0][1])));
        return avgDamage;
    }

    public static Object[] avgDamColumnHeaders() {
        return new Object[]{"HITS", "DAMAGE DONE", "RATIO"};
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
        FtoEArray[0][0] = formatter.format(Integer.parseInt(String.valueOf(FtoEArray[0][0])));
        FtoEArray[0][1] = formatter.format(Integer.parseInt(String.valueOf(FtoEArray[0][1])));
        return FtoEArray;
    }

    public static Object[] FtoEColumnHeaders() {
        return new Object[]{"FRIENDLY DAMAGE", "ENEMY DAMAGE", "FRIEND v ENEMY"};
    }
}