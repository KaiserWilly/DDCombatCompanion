import org.codehaus.jackson.map.ObjectMapper;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by J.D. Isenhart on 4/5/2015
 * 6:09 PM
 */
//Name: 1=Anna, 2=Brody, 3=Dan, 4=JD, 5=Jed, 6=Ryan
public class FilingCombat {
    static Object[][] FriendComStats;
    static Object[][] EnemyComStats;
    static HashMap<String, HashMap> incomingSaveData = null;
    static Object[][] columnData;
    ObjectMapper mapper = new ObjectMapper();

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

    public static Object[] columnHeadersAS() {
        return new Object[]{"Player", "BR", "Damage Done", "Kills", "Healing Done", "Friendly Fire", "Health"};
    }

    public static Object[][] rowDataAS() { // Gather and piece together data to display on the main table
        EnemyComStats = new Object[][]{{0, 0, 0}};
        FriendComStats = new Object[][]{{0, 0, 0}};
        incomingSaveData = readSave();
        columnData = new Object[incomingSaveData.get("Players").size() - 1][7];
        for (int i = 0; i < incomingSaveData.get("Players").size(); i++) {
            if (String.valueOf(incomingSaveData.get("Players").get(i)).equals("Enemy")) {
                EnemyComStats[0][0] = incomingSaveData.get(incomingSaveData.get("Players").get(i)).get("Damage");
                EnemyComStats[0][1] = incomingSaveData.get(incomingSaveData.get("Players").get(i)).get("Kills");
                EnemyComStats[0][2] = incomingSaveData.get(incomingSaveData.get("Players").get(i)).get("Healing");
            } else {
                columnData[i][0] = String.valueOf(incomingSaveData.get("Players").get(i));
                columnData[i][1] = incomingSaveData.get(String.valueOf(columnData[i][0])).get("BR");
                columnData[i][2] = incomingSaveData.get(String.valueOf(columnData[i][0])).get("Damage");
                columnData[i][3] = incomingSaveData.get(String.valueOf(columnData[i][0])).get("Kills");
                columnData[i][4] = incomingSaveData.get(String.valueOf(columnData[i][0])).get("Healing");
                columnData[i][5] = incomingSaveData.get(String.valueOf(columnData[i][0])).get("FriendFire");
                columnData[i][6] = incomingSaveData.get(String.valueOf(columnData[i][0])).get("Health");

            }
        }
        for (int i = 0; i < columnData.length; i++) {
            if (String.valueOf(columnData[i][2]).equals(null)) {
                FriendComStats[0][0] = Integer.parseInt(String.valueOf(FriendComStats[0][0]));
            } else {
                FriendComStats[0][0] = Integer.parseInt(String.valueOf(FriendComStats[0][0])) + Integer.parseInt(String.valueOf(columnData[i][2]));
            }
            if (String.valueOf(columnData[i][3]).equals(null)) {
                FriendComStats[0][1] = Integer.parseInt(String.valueOf(FriendComStats[0][1]));
            } else {
                FriendComStats[0][1] = Integer.parseInt(String.valueOf(FriendComStats[0][1])) + Integer.parseInt(String.valueOf(columnData[i][3]));
            }
            if (String.valueOf(columnData[i][4]).equals(null)) {
                FriendComStats[0][2] = Integer.parseInt(String.valueOf(FriendComStats[0][2]));
            } else {
                FriendComStats[0][2] = Integer.parseInt(String.valueOf(FriendComStats[0][2])) + Integer.parseInt(String.valueOf(columnData[i][4]));
            }
        }
        HashMap saveParty = incomingSaveData.get("Party");
        try {
            saveParty.remove("FriendCom");
        } catch (Exception e) {
        }
        saveParty.put("FriendCom", FriendComStats);
        incomingSaveData.remove("Party");
        incomingSaveData.put("Party", saveParty);
        writeFile(incomingSaveData);

        return columnData;
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
            if (Champs.length() > 20) {
                return "Many";
            }
        } catch (Exception e) {
            return "Error: Kill Champ";
        }
        return Champs;
    }

    public static Object[][] getFriendComStats() {
        incomingSaveData = readSave();
        HashMap partyData = incomingSaveData.get("Party");
        Object[][] friendStats = (Object[][]) partyData.get("FriendCom");
        DecimalFormat formatter = new DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale.getDefault()));
        for (int i = 0; i < friendStats.length; i++) {
            friendStats[i][0] =  formatter.format(Integer.parseInt(String.valueOf(friendStats[i][0])));
        }
        return friendStats;
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
}

