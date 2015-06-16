
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * Created by J.D. Isenhart on 6/11/2015
 * 9:14 PM
 */
public class FilingLoot {

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
        Conversion.checkSaveMap(incomingSaveData);
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

    public static Object[][] getLootTableData() {
        HashMap<String, HashMap> saveData = readSave();
        return (Object[][]) saveData.get("Loot").get("Data");
    }

    public static Object[] getLootTableColumnHeaders() {
        return new Object[]{"Item", "Value (GP)","Qty."};
    }

    public static void updateLootTable(String item, String value,int qty) {
        HashMap<String, HashMap> saveData = readSave();
        Object[][] savedTable = (Object[][]) saveData.get("Loot").get("Data");
        Object[][] newTable = new Object[savedTable.length + 1][3];
        for (int i = 0; i < savedTable.length; i++) {
            newTable[i][0] = savedTable[i][0];
            newTable[i][1] = savedTable[i][1];
            newTable[i][2] = savedTable[i][2];
        }
        newTable[savedTable.length][0] = item;
        try {
            int valueI = Integer.parseInt(value);
            newTable[savedTable.length][1] = valueI;
        } catch (NumberFormatException e) {
            newTable[savedTable.length][1] = value;
        }
        newTable[savedTable.length][2] = qty;
        saveData.get("Loot").remove("Data");
        saveData.get("Loot").put("Data", newTable);
        writeFile(saveData);
        System.out.println("Done updating loot table!");
    }

    public static void updateLootItemValue(String item, String value) {
        HashMap<String, HashMap> saveData = readSave();
        Object[][] savedTable = (Object[][]) saveData.get("Loot").get("Data");
        int itemRowIndex = getItemRowIndex(item, savedTable);
        savedTable[itemRowIndex][1] = value;
        saveData.get("Loot").remove("Data");
        saveData.get("Loot").put("Data", savedTable);
        writeFile(saveData);
        System.out.println("Done updating loot item value!");
    }
    public static void updateLootItemQuantity(String item, int value) {
        HashMap<String, HashMap> saveData = readSave();
        Object[][] savedTable = (Object[][]) saveData.get("Loot").get("Data");
        int itemRowIndex = getItemRowIndex(item, savedTable);
        savedTable[itemRowIndex][2] = value;
        saveData.get("Loot").remove("Data");
        saveData.get("Loot").put("Data", savedTable);
        writeFile(saveData);
        System.out.println("Done updating loot item quantity!");
    }

    public static Object[] getLootItemArray() {
        HashMap<String, HashMap> saveData = readSave();
        Object[][] savedTable = (Object[][]) saveData.get("Loot").get("Data");
        Object[] itemArray = new Object[savedTable.length];
        for (int i = 0; i < savedTable.length; i++) {
            itemArray[i] = savedTable[i][0];
        }
        return itemArray;
    }

    public static int getItemRowIndex(String item, Object[][] lootTable) {
        for (int i = 0; i < lootTable.length; i++) {
            if (String.valueOf(lootTable[i][0]).equals(item)) {
                return i;
            }
        }
        return Integer.parseInt(null);
    }

    public static void removeLootTableItem(String item) {
        HashMap<String, HashMap> saveData = readSave();
        Object[][] savedTable = (Object[][]) saveData.get("Loot").get("Data");
        int itemRowIndex = getItemRowIndex(item, savedTable);
        if (savedTable.length == 0) {
            return;
        }
        Object[][] newTable = new Object[savedTable.length - 1][2];
        int rowModifier = 0;
        for (int i = 0; i < savedTable.length; i++) {
            if (i == itemRowIndex) {
                rowModifier = -1;
            } else {
                newTable[i + rowModifier][0] = savedTable[i][0];
                newTable[i + rowModifier][1] = savedTable[i][1];
            }
        }
        saveData.get("Loot").remove("Data");
        saveData.get("Loot").put("Data", newTable);
        writeFile(saveData);
        System.out.println("Done removing loot item!");
    }


    public static String getLootNotes() {
        HashMap<String, HashMap> saveData = readSave();
        return (String) saveData.get("Loot").get("Notes");
    }

    public static void updateLootNotes(String newNote) {
        HashMap<String, HashMap> saveData = readSave();
        String notes = (String) saveData.get("Loot").get("Notes");
        String newNotes = notes + newNote + "\n";
        saveData.get("Loot").remove("Notes");
        saveData.get("Loot").put("Notes", newNotes);
        writeFile(saveData);
        System.out.println("Done updating loot notes!");
    }

    public static String[] getNoteArray() {
        HashMap<String, HashMap> saveData = readSave();
        String notes = (String) saveData.get("Loot").get("Notes");
        String[] noteArray = notes.split("\n");
        return noteArray;
    }

    public static void removeNote(String note) {
        HashMap<String, HashMap> saveData = readSave();
        String notes = (String) saveData.get("Loot").get("Notes");
        String[] noteArray = notes.split("\n");
        List<String> noteList = new LinkedList<String>(Arrays.asList(noteArray));
        int index = noteList.indexOf(note);
        System.out.println(index);
        noteList.remove(index);
        Object[] newObjArray =  noteList.toArray();
        String[] newNoteArray = new String[newObjArray.length];
        for (int i=0;i<newObjArray.length;i++){
            newNoteArray[i] = String.valueOf(newObjArray[i]);
        }
        String newNotes = "";
        for (int i = 0; i < newNoteArray.length; i++) {
            newNotes = newNotes + newNoteArray[i] + "\n";
        }
        saveData.get("Loot").remove("Notes");
        saveData.get("Loot").put("Notes", newNotes);
        writeFile(saveData);
        System.out.println("Done removing loot note!");
    }

    public static TableRowSorter RowSorter(JTable Table) {
        TableRowSorter sorter = new TableRowSorter(Table.getModel());
        List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
        int rowColumn = 0;
        sortKeys.add(new RowSorter.SortKey(rowColumn, SortOrder.ASCENDING));
        sorter.setSortable(1, false);
        sorter.setSortKeys(sortKeys);
        return sorter;
    }
}
