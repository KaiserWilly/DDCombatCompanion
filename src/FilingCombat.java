import org.apache.commons.io.FilenameUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by J.D. Isenhart on 4/5/2015
 * 6:09 PM
 */
//Name: 1=Anna, 2=Brody, 3=Dan, 4=JD, 5=Jed, 6=Ryan
public class FilingCombat {
    static Object[][] FriendComStats;
    static Object[][] EnemyComStats;
    static List<String> playerList;
    static String[] playerArray;
    static String[] playerArrayNE;
    static HashMap<String, HashMap> incomingSaveData = null;
    static Object[][] columnData;

    public static Object[] columnHeadersAS() {
        return new Object[]{"Player", "BR", "Damage Done", "Kills", "Healing Done", "Health"};
    }

    public static Object[][] rowDataAS() { // Gather and piece together data to display on the main table
        EnemyComStats = new Object[][]{
                {0, 0, 0}
        };
        FriendComStats = new Object[][]{
                {0, 0, 0}
        };
        ObjectMapper mapper = new ObjectMapper();
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
        incomingSaveData = Conversion.checkSaveMap(incomingSaveData);
        playerArray = new String[incomingSaveData.get("Players").size()];
        playerArrayNE = new String[(incomingSaveData.get("Players").size())];
        for (int i = 0; i < incomingSaveData.get("Players").size(); i++) {
            playerArray[i] = String.valueOf(incomingSaveData.get("Players").get(i));
            if (!String.valueOf(incomingSaveData.get("Players").get(i)).equals("Enemy")) {
                playerArrayNE[i] = String.valueOf(incomingSaveData.get("Players").get(i));
            }

        }
        columnData = new Object[incomingSaveData.get("Players").size() - 1][6];
        for (int i = 0; i < incomingSaveData.get("Players").size(); i++) {
            if (String.valueOf(incomingSaveData.get("Players").get(i)).equals("Enemy")) {
                EnemyComStats[0][0] = incomingSaveData.get(incomingSaveData.get("Players").get(i)).get("Damage");
                EnemyComStats[0][1] = incomingSaveData.get(incomingSaveData.get("Players").get(i)).get("Kills");
                EnemyComStats[0][2] = incomingSaveData.get(incomingSaveData.get("Players").get(i)).get("Healing");
            } else {
                columnData[i][0] = String.valueOf(incomingSaveData.get("Players").get(i));
                columnData[i][2] = incomingSaveData.get(String.valueOf(columnData[i][0])).get("Damage");
                columnData[i][3] = incomingSaveData.get(String.valueOf(columnData[i][0])).get("Kills");
                columnData[i][4] = incomingSaveData.get(String.valueOf(columnData[i][0])).get("Healing");
                columnData[i][5] = incomingSaveData.get(String.valueOf(columnData[i][0])).get("Health");
                columnData[i][1] = incomingSaveData.get(String.valueOf(columnData[i][0])).get("BR");
            }
        }
        for (int i = 0; i < columnData.length; i++) {
            FriendComStats[0][0] = Integer.parseInt("0" + String.valueOf(FriendComStats[0][0])) + Integer.parseInt("0" + String.valueOf(columnData[i][2]));
            FriendComStats[0][1] = Integer.parseInt("0" + String.valueOf(FriendComStats[0][1])) + Integer.parseInt("0" + String.valueOf(columnData[i][3]));
            FriendComStats[0][2] = Integer.parseInt("0" + String.valueOf(FriendComStats[0][2])) + Integer.parseInt("0" + String.valueOf(columnData[i][4]));
        }
        FilingParty.Dam = Integer.parseInt("0" + String.valueOf(FriendComStats[0][0]));
        System.out.println("Done loading Combat Statistics Table!");
        return columnData;
    }

    public static void changeCS(int Person, int Dam, int Kill, int Healing, int health, boolean healthy) { //Append new damage to file
        int totalDam;
        int totalKill;
        int totalHealing, totalBR;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
            System.out.println("Person Mapping: (Combat Statistics)");
            System.out.println(mapper.writeValueAsString(incomingSaveData.get(incomingSaveData.get("Players").get(Person))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        totalDam = Integer.parseInt(String.valueOf(incomingSaveData.get(incomingSaveData.get("Players").get(Person)).get("Damage"))) + Dam;
        totalKill = Integer.parseInt(String.valueOf(incomingSaveData.get(incomingSaveData.get("Players").get(Person)).get("Kills"))) + Kill;
        totalHealing = Integer.parseInt(String.valueOf(incomingSaveData.get(incomingSaveData.get("Players").get(Person)).get("Healing"))) + Healing;
        totalBR = (int) (Integer.parseInt(String.valueOf(incomingSaveData.get(incomingSaveData.get("Players").get(Person)).get("BR"))) + (Dam * 2) + ((double) Healing * 2.1) + (Kill * 6));
        incomingSaveData.get(incomingSaveData.get("Players").get(Person)).remove("Damage");
        incomingSaveData.get(incomingSaveData.get("Players").get(Person)).put("Damage", totalDam);
        incomingSaveData.get(incomingSaveData.get("Players").get(Person)).remove("Kills");
        incomingSaveData.get(incomingSaveData.get("Players").get(Person)).put("Kills", totalKill);
        incomingSaveData.get(incomingSaveData.get("Players").get(Person)).remove("Healing");
        incomingSaveData.get(incomingSaveData.get("Players").get(Person)).put("Healing", totalHealing);
        incomingSaveData.get(incomingSaveData.get("Players").get(Person)).remove("BR");
        incomingSaveData.get(incomingSaveData.get("Players").get(Person)).put("BR", totalBR);

        if (healthy) {
            incomingSaveData.get(incomingSaveData.get("Players").get(Person)).remove("Health");
            incomingSaveData.get(incomingSaveData.get("Players").get(Person)).put("Health", health);
        }
        try {
            FileOutputStream fileOut = new FileOutputStream(String.valueOf(Start.saveFilePath));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(incomingSaveData);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done updating Combat Statistics table!");
    }

    public static Object[] totalStatsRowData() {
        return new Object[]{"Damage Done", "Kills", "Healing Done"};
    }

    //Computes the Scoring Champions for each category
    public static String comStatsDamChamp() {
        int Champ = -1;
        String Champs = "No one";
        try {
            if (columnData.length != 0) {
                for (int i = 0; i < columnData.length; i++) {
                    if (Integer.parseInt(columnData[i][2].toString()) > Champ) {
                        Champ = Integer.parseInt(columnData[i][2].toString());
                        Champs = columnData[i][0].toString();
                    } else if (Integer.parseInt(columnData[i][2].toString()) == Champ) {
                        Champs = Champs + " & " + columnData[i][0].toString();
                    }
                }
                System.out.println("Done loading Damage Champion!");
                if (Champs.length() > 20) {
                    return "Many";
                }
            }
        } catch (Exception e) {
            return "Error: Healing Champ";

        }

        return Champs;
    }

    public static String comStatsHealingChamp() {
        int Champ = -1;
        String Champs = "No one";
        try {
            for (int i = 0; i < columnData.length; i++) {
                if (Integer.parseInt(columnData[i][4].toString()) > Champ) {
                    Champ = Integer.parseInt(columnData[i][4].toString());
                    Champs = columnData[i][0].toString();
                } else if (Integer.parseInt(columnData[i][4].toString()) == Champ) {
                    Champs = Champs + " & " + columnData[i][0].toString();
                }
            }
            System.out.println("Done loading healing champion!");
            if (Champs.length() > 20) {
                return "Many";
            }
        } catch (Exception e) {
            return "Error: Healing Champ";
        }
        return Champs;
    }

    public static String comStatsKillChamp() {
        int Champ = -1;
        String Champs = "No one";
        try {
            for (int i = 0; i < columnData.length; i++) {
                if (Integer.parseInt(columnData[i][3].toString()) > Champ) {
                    Champ = Integer.parseInt(columnData[i][3].toString());
                    Champs = columnData[i][0].toString();
                } else if (Integer.parseInt(columnData[i][3].toString()) == Champ) {
                    Champs = Champs + " & " + columnData[i][0].toString();
                }
            }
            System.out.println("Done loading Kill champion!");
            if (Champs.length() > 20) {
                return "Many";
            }
        } catch (Exception e) {
            return "Error: Kill Champ";
        }
        return Champs;
    }

    public static void renamePlayer(String playerPre, String playerPro) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        List<String> PlayerOrder = new ArrayList<String>() {
        };
        HashMap<String, HashMap> playerData = null;
        try {
            FileInputStream fileIn = new FileInputStream(String.valueOf(Start.saveFilePath));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            playerData = (HashMap<String, HashMap>) in.readObject();
            in.close();
            fileIn.close();

        } catch (Exception e) {
            System.out.println("Error in File Reading; Filing.renamePlayer");
            e.printStackTrace();
        }
        List<String> playerOrder = new ArrayList<String>();
        HashMap data = playerData.get(playerPre);
        for (int i = 0; i < playerData.get("Players").size(); i++) {
            playerOrder.add(String.valueOf(playerData.get("Players").get(i)));

        }
        playerOrder.remove(playerPre);
        playerOrder.remove("Enemy");
        playerOrder.add(playerPro);
        java.util.Collections.sort(playerOrder);
        playerOrder.add(playerOrder.size(), "Enemy");
        playerData.remove("Players");
        playerData.put("Players", newPlayerMap(playerOrder));
        try {
            System.out.println(mapper.writeValueAsString(playerData.get("Players")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        playerData.remove(playerPre);
        playerData.put(playerPro, data);
        try {
            System.out.println("Save Path: " + String.valueOf(Start.saveFilePath));
            FileOutputStream fileOut = new FileOutputStream(String.valueOf(Start.saveFilePath));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(playerData);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            System.out.println("Error in File Creation; New Save; Filing.renamePlayer");
            e.printStackTrace();
        }
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
        int totalKill;
        totalKill = Integer.parseInt(String.valueOf(incomingSaveData.get(name).get("Kills"))) - 1;
        incomingSaveData.get(name).remove("Kills");
        incomingSaveData.get(name).put("Kills", totalKill);
        try {
            FileOutputStream fileOut = new FileOutputStream(String.valueOf(Start.saveFilePath));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(incomingSaveData);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done removing a kill from the player " + name + "!");
    }

    public static TableRowSorter BRSorter(JTable Table) {
        TableRowSorter sorter = new TableRowSorter(Table.getModel());
        List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
        int BRColumn = 1;
        int KillColumn = 3;
        int HealingColumn = 4;
        int damageColumn = 2;
        int HealthColumn = 5;
        sortKeys.add(new RowSorter.SortKey(BRColumn, SortOrder.DESCENDING));
        sortKeys.add(new RowSorter.SortKey(KillColumn, SortOrder.DESCENDING));
        sortKeys.add(new RowSorter.SortKey(HealingColumn, SortOrder.DESCENDING));
        sortKeys.add(new RowSorter.SortKey(damageColumn, SortOrder.DESCENDING));
        sortKeys.add(new RowSorter.SortKey(HealthColumn, SortOrder.DESCENDING));
        sorter.setSortable(0, false);
        sorter.setSortKeys(sortKeys);
        return sorter;
    }

    public static class AcceptFile extends javax.swing.filechooser.FileFilter {

        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String extension = FilenameUtils.getExtension(f.getAbsolutePath());
            if (extension != null) {
                if (extension.equals("ADDCC")) {
                    return true;
                } else {
                    return false;
                }
            }

            return false;
        }

        @Override
        public String getDescription() {
            return null;
        }
    }
}

