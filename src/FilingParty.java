import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

/**
 * Created by J.D. Isenhart on 6/8/2015
 * 1:07 AM
 */
public class FilingParty {
    public static Object[][] hitPercentage, avgDamage;
    public static int Dam = 0;
    static HashMap<String, HashMap> incomingSaveData;
    static ObjectMapper mapper = new ObjectMapper();
    static Object[][] partyStatsRowData;

    public static Object[] columnDataPSTable() {
        return new Object[]{"Statistic", "Value"};
    }

    public static Object[][] rowDataPSTable() {
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        try {
            FileInputStream fileIn = new FileInputStream(String.valueOf(Start.saveFilePath));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            incomingSaveData = (HashMap<String, HashMap>) in.readObject();
            in.close();
            fileIn.close();


            mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        } catch (Exception i) {
            i.printStackTrace();
        }
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
        int attempts = Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("Swords"))) + Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("Arrows"))) + Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("Spells")));
        int hits = Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("Hits")));
        double ratio = (double) Math.round((double) hits / ((double) attempts) * 10000) / 10000;
        hitPercentage = new Object[][]{
                {attempts, hits, ratio}
        };
        return hitPercentage;
    }

    public static Object[] hitPerColumnData() {
        return new Object[]{"Attempts", "Hits", "Ratio"};
    }

    public static Object[][] avgDamRowData() {
        int hits = Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("Hits")));
        double ratio = (double) Math.round(((double) Dam / (double) hits) * 10000) / 10000;
        avgDamage = new Object[][]{{hits, Dam, ratio}};
        return avgDamage;
    }

    public static Object[] avgDamColumnData() {
        return new Object[]{"Hits", "Damage Done", "Ratio"};
    }
}