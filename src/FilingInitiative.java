import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by J.D. Isenhart on 6/11/2015
 * 12:54 AM
 */
public class FilingInitiative {
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

    public static Object[][] InitRowData() {
        HashMap<String, HashMap> saveData = readSave();
        if (saveData.get("Party").containsKey("Init")) {
            Object[][] rowdata = (Object[][]) saveData.get("Party").get("Init");
            return rowdata;
        }
        Object[][] rowData = new Object[saveData.get("Players").size()][2];
        for (int i = 0; i < saveData.get("Players").size(); i++) {
            rowData[i][0] = saveData.get("Players").get(i);
            rowData[i][1] = 0;
        }
        return rowData;
    }

    public static Object[] InitColumnHeaders() {
        return new Object[]{"Player", "Initiative Roll"};
    }

    public static TableRowSorter RowSorter(JTable Table) {
        TableRowSorter sorter = new TableRowSorter(Table.getModel());
        List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
        int rowColumn = 1;
        sortKeys.add(new RowSorter.SortKey(rowColumn, SortOrder.DESCENDING));
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        return sorter;
    }

    public static void genInit(JTable table) {
        HashMap<String, HashMap> saveData = readSave();
        Object[][] tableData = getTableData(table);
        if (saveData.get("Party").containsKey("Init")) {
            saveData.get("Party").remove("Init");
            saveData.get("Party").put("Init", tableData);
        } else {
            saveData.get("Party").put("Init", tableData);
        }
        writeFile(saveData);

    }

    public static void resetInit() {
        HashMap<String, HashMap> saveData = readSave();
        if (saveData.get("Party").containsKey("Init")) {
            saveData.get("Party").remove("Init");
        }
        writeFile(saveData);
    }

    public static Object[][] getTableData(JTable table) {
        DefaultTableModel tableDTM = (DefaultTableModel) table.getModel();
        int nRow = tableDTM.getRowCount(), nCol = tableDTM.getColumnCount();
        Object[][] tableData = new Object[nRow][nCol];
        for (int i = 0; i < nRow; i++)
            for (int j = 0; j < nCol; j++)
                tableData[i][j] = tableDTM.getValueAt(i, j);
        return tableData;
    }
}
