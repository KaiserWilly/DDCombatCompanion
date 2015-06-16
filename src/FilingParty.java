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
    static HashMap<String, HashMap> incomingSaveData;
    static ObjectMapper mapper = new ObjectMapper();
    static Object[][] partyStatsRowData;
    public static Object[][] hitPercentage, avgDamage;
    public static int Dam = 0;

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
                {"XP Gained", incomingSaveData.get("Party").get("XP")}
        };
        return partyStatsRowData;
    }

    public static void writePartyUpdate(int dice, int sword, int arrow, int spell, int hit, int XP) {
        int totalDice, totalSwords, totalArrows, totalSpells, totalHits, totalXP;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        totalDice = Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("Dice"))) + dice;
        totalSwords = Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("Swords"))) + sword;
        totalArrows = Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("Arrows"))) + arrow;
        totalSpells = Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("Spells"))) + spell;
        totalHits = Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("Hits"))) + hit;
        totalXP = Integer.parseInt(String.valueOf(incomingSaveData.get("Party").get("XP"))) + XP;
        incomingSaveData.get("Party").remove("Dice");
        incomingSaveData.get("Party").put("Dice", totalDice);
        incomingSaveData.get("Party").remove("Swords");
        incomingSaveData.get("Party").put("Swords", totalSwords);
        incomingSaveData.get("Party").remove("Arrows");
        incomingSaveData.get("Party").put("Arrows", totalArrows);
        incomingSaveData.get("Party").remove("Spells");
        incomingSaveData.get("Party").put("Spells", totalSpells);
        incomingSaveData.get("Party").remove("Hits");
        incomingSaveData.get("Party").put("Hits", totalHits);
        incomingSaveData.get("Party").remove("XP");
        incomingSaveData.get("Party").put("XP", totalXP);

        try {
            FileOutputStream fileOut = new FileOutputStream(String.valueOf(Start.saveFilePath));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(incomingSaveData);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done updating party Statistics!");
    }

    public static void SetPartyStat(String Stat, int StatValue) {

        try {
            incomingSaveData.get("Party").remove(Stat);
            incomingSaveData.get("Party").put(Stat, StatValue);
            FileOutputStream fileOut = new FileOutputStream(String.valueOf(Start.saveFilePath));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(incomingSaveData);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            System.err.println("Failure to Set Statistic, PartyFiling.SetPartyStat");
            e.printStackTrace();
        }
        System.out.println("Done setting party Statistic!");
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