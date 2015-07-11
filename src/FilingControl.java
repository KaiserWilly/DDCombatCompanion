import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by J.D. Isenhart on 7/11/2015
 * 9:54 AM
 */
public class FilingControl {
    static HashMap<String, HashMap> incomingSaveData = null;

    public static HashMap readSave() {
        HashMap SaveData = null;
        try {
            FileInputStream fileIn = new FileInputStream(String.valueOf(Start.saveFilePath));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            SaveData = (HashMap<String, HashMap>) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception i) {
            i.printStackTrace();
        }
        return SaveData;
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

    public static void renamePlayer(String playerPre, String playerPro) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        incomingSaveData = readSave();
        List<String> playerOrder = new ArrayList<>();
        HashMap data = incomingSaveData.get(playerPre);
        for (int i = 0; i < incomingSaveData.get("Players").size(); i++) {
            playerOrder.add(String.valueOf(incomingSaveData.get("Players").get(i)));

        }
        playerOrder.remove(playerPre);
        playerOrder.remove("Enemy");
        playerOrder.add(playerPro);
        java.util.Collections.sort(playerOrder);
        playerOrder.add(playerOrder.size(), "Enemy");
        incomingSaveData.remove("Players");
        incomingSaveData.put("Players", newPlayerMap(playerOrder));
        try {
            System.out.println(mapper.writeValueAsString(incomingSaveData.get("Players")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        incomingSaveData.remove(playerPre);
        incomingSaveData.put(playerPro, data);
        writeFile(incomingSaveData);
        System.out.println("Done renaming player!");
    }

    public static HashMap newPlayerMap(List<String> names) {
        HashMap<Integer, String> blankMap = new HashMap<>();
        for (int i = 0; i < names.size(); i++) {
            blankMap.put(i, names.get(i));
        }
        return blankMap;
    }

    public static void removeKill(String name) {
        incomingSaveData = readSave();
        int totalKill;
        totalKill = Integer.parseInt(String.valueOf(FilingCombat.incomingSaveData.get(name).get("Kills"))) - 1;
        incomingSaveData.get(name).remove("Kills");
        incomingSaveData.get(name).put("Kills", totalKill);
        writeFile(incomingSaveData);
        System.out.println("Done removing a kill from the player " + name + "!");
    }

    public static void writePartyUpdate(int dice, int sword, int arrow, int spell, int hit, int XP) {
        incomingSaveData = readSave();
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

        writeFile(incomingSaveData);
        System.out.println("Done updating party Statistics!");
    }

    public static void SetPartyStat(String Stat, int StatValue) {
        incomingSaveData = readSave();

        try {
            incomingSaveData.get("Party").remove(Stat);
            incomingSaveData.get("Party").put(Stat, StatValue);
            writeFile(incomingSaveData);
        } catch (Exception e) {
            System.err.println("Failure to Set Statistic, PartyFiling.SetPartyStat");
            e.printStackTrace();
        }
        System.out.println("Done setting party Statistic!");
    }

    public static void changeCS(int Person, int Dam, int Kill, int Healing, int health, boolean healthy) {
        HashMap<String, HashMap> saveData = readSave();
        int totalDam;
        int totalKill;
        int totalHealing, totalBR;
        System.out.println(String.valueOf(saveData.get("Players").get(Person)));
        incomingSaveData = readSave();
        totalDam = Integer.parseInt(String.valueOf(incomingSaveData.get(saveData.get("Players").get(Person)).get("Damage"))) + Dam;
        totalKill = Integer.parseInt(String.valueOf(incomingSaveData.get(saveData.get("Players").get(Person)).get("Kills"))) + Kill;
        totalHealing = Integer.parseInt(String.valueOf(incomingSaveData.get(saveData.get("Players").get(Person)).get("Healing"))) + Healing;
        totalBR = (int) (Integer.parseInt(String.valueOf(incomingSaveData.get(saveData.get("Players").get(Person)).get("BR"))) + (Dam * 2) + ((double) Healing * 2.1) + (Kill * 6)) + 0;
        incomingSaveData.get(saveData.get("Players").get(Person)).remove("Damage");
        incomingSaveData.get(saveData.get("Players").get(Person)).put("Damage", totalDam);
        incomingSaveData.get(saveData.get("Players").get(Person)).remove("Kills");
        incomingSaveData.get(saveData.get("Players").get(Person)).put("Kills", totalKill);
        incomingSaveData.get(saveData.get("Players").get(Person)).remove("Healing");
        incomingSaveData.get(saveData.get("Players").get(Person)).put("Healing", totalHealing);
        incomingSaveData.get(saveData.get("Players").get(Person)).remove("BR");
        incomingSaveData.get(saveData.get("Players").get(Person)).put("BR", totalBR);

        if (healthy) {
            incomingSaveData.get(incomingSaveData.get("Players").get(Person)).remove("Health");
            incomingSaveData.get(incomingSaveData.get("Players").get(Person)).put("Health", health);
        }
        writeFile(incomingSaveData);
        System.out.println("Done updating Combat Statistics table!");
    }
}
