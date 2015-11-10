import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.io.*;
import java.util.HashMap;
import java.util.List;

/**
 * Created by J.D. Isenhart on 7/11/2015
 * 9:54 AM
 */
public class FilingControl {
    static HashMap<String, HashMap<String, Object>> incomingSaveData = null;

    public static HashMap<String, HashMap<String, Object>> readSave() {
        HashMap<String, HashMap<String, Object>> SaveData = null;
        try {
            FileInputStream fileIn = new FileInputStream(String.valueOf(Start.saveFilePath));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            SaveData = (HashMap<String, HashMap<String, Object>>) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception i) {
            i.printStackTrace();
        }
        return SaveData;
    }

    public static void writeFile(HashMap<String, HashMap<String, Object>> saveData) {
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

    public static HashMap<Integer, String> newPlayerMap(List<String> names) {
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
        regenBRRanking(name);
        System.out.println("Done removing a kill from the player " + name + "!");
    }

    public static void removeDamage(String name, int d) {
        incomingSaveData = readSave();
        int newDamage;
        newDamage = Integer.parseInt(String.valueOf(FilingCombat.incomingSaveData.get(name).get("Damage"))) - d;
        if (newDamage < 0) {
            newDamage = 0;
        }
        incomingSaveData.get(name).remove("Damage");
        incomingSaveData.get(name).put("Damage", newDamage);
        writeFile(incomingSaveData);
        regenBRRanking(name);
        System.out.println("Done removing " + d + " damage from the player " + name + "!");
    }
    public static void setMaxHealth(String name, int mHealth) {
        incomingSaveData = readSave();
        incomingSaveData.get(name).remove("MaxHealth");
        incomingSaveData.get(name).put("MaxHealth", mHealth);
        writeFile(incomingSaveData);
        regenBRRanking(name);
        System.out.println("Done setting " + mHealth + " as the max health of the player " + name + "!");
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
        if (incomingSaveData.get("Party").containsKey("XP")) {
            incomingSaveData.get("Party").remove("XP");
        }

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

    public static void changeCS(int personVal, int damage, boolean killCondition, int healing, int health, boolean healthy) {
        int totalDam;
        int totalKill;
        int totalHealing;
        System.out.println(String.valueOf(incomingSaveData.get("Players").get(personVal)));
        incomingSaveData = readSave();
        totalDam = Integer.parseInt(String.valueOf(incomingSaveData.get(incomingSaveData.get("Players").get(personVal)).get("Damage"))) + damage;
        totalHealing = Integer.parseInt(String.valueOf(incomingSaveData.get(incomingSaveData.get("Players").get(personVal)).get("Healing"))) + healing;
        if (killCondition) {
            totalKill = Integer.parseInt(String.valueOf(incomingSaveData.get(incomingSaveData.get("Players").get(personVal)).get("Kills"))) + 1;
        } else {
            totalKill = Integer.parseInt(String.valueOf(incomingSaveData.get(incomingSaveData.get("Players").get(personVal)).get("Kills")));
        }
        incomingSaveData.get(incomingSaveData.get("Players").get(personVal)).remove("Damage");
        incomingSaveData.get(incomingSaveData.get("Players").get(personVal)).put("Damage", totalDam);
        incomingSaveData.get(incomingSaveData.get("Players").get(personVal)).remove("Kills");
        incomingSaveData.get(incomingSaveData.get("Players").get(personVal)).put("Kills", totalKill);
        incomingSaveData.get(incomingSaveData.get("Players").get(personVal)).remove("Healing");
        incomingSaveData.get(incomingSaveData.get("Players").get(personVal)).put("Healing", totalHealing);

        if (healthy) {
            incomingSaveData.get(incomingSaveData.get("Players").get(personVal)).remove("Health");
            incomingSaveData.get(incomingSaveData.get("Players").get(personVal)).put("Health", health);
        }
        writeFile(incomingSaveData);
        for (int i = 0; i < FilingMain.getPlayerArrayNE().length; i++) {
            regenBRRanking(String.valueOf(incomingSaveData.get("Players").get(i)));
        }
        System.out.println("Done updating Combat Statistics table!");
    }

    public static void regenBRRanking(String name) {
        incomingSaveData = readSave();
        int totalDam = Integer.parseInt(String.valueOf(incomingSaveData.get(name).get("Damage")));
        int totalKill = Integer.parseInt(String.valueOf(incomingSaveData.get(name).get("Kills")));
        int totalHealing = Integer.parseInt(String.valueOf(incomingSaveData.get(name).get("Healing")));
        int totalFF = Integer.parseInt(String.valueOf(incomingSaveData.get(name).get("FriendFire")));
        int totalBR = (int) (((double) totalDam * 2.25) + ((double) totalHealing * 2.5) + ((double) totalKill * 4.5) + ((double) totalFF * -3.25));
        incomingSaveData.get(name).remove("BR");
        incomingSaveData.get(name).put("BR", totalBR);
        writeFile(incomingSaveData);
    }

    public static void friendFire(String name, int statValue, boolean add) {
        incomingSaveData = readSave();
        int newFF;

        int totalFF = Integer.parseInt(String.valueOf(incomingSaveData.get(name).get("FriendFire")));
        if (add) {
            newFF = totalFF + statValue;
        } else {
            newFF = totalFF - statValue;
        }
        if (newFF < 0) {
            newFF = 0;
        }
        incomingSaveData.get(name).remove("FriendFire");
        incomingSaveData.get(name).put("FriendFire", newFF);
        writeFile(incomingSaveData);
        regenBRRanking(name);
    }
}
