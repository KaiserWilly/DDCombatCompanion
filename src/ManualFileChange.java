import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import javax.swing.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Created by J.D. Isenhart on 6/10/2015
 * 10:44 PM
 */
public class ManualFileChange { //Used to analyze and rewrite save file if corrupted while making changes. Used while making modifications only.
    static JFileChooser newSave;
    static JPanel base;
    static Path saveLocat;
    static HashMap<String, HashMap> incomingSaveData;

    public static void main(String[] args) throws IOException {
        newSave = new JFileChooser();
        int returnVal = newSave.showDialog(base, "Load Save");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            saveLocat = Paths.get(String.valueOf(newSave.getSelectedFile()));
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            FileInputStream fileIn = new FileInputStream(String.valueOf(saveLocat));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            incomingSaveData = (HashMap<String, HashMap>) in.readObject();
            in.close();
            fileIn.close();
            mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        } catch (Exception i) {
            i.printStackTrace();
        }
        HashMap lootData = incomingSaveData.get("Loot");
        Object[][] lootTable = (Object[][]) lootData.get("Data");
        Object[][]newLootTable = new Object[lootTable.length][3];
        for (int i = 0;i<lootTable.length;i++){
            newLootTable[i][0] = lootTable[i][0];
            newLootTable[i][1] = lootTable[i][1];
            newLootTable[i][2] = 1;
        }
        lootData.remove("Data");
        lootData.put("Data",newLootTable);
        incomingSaveData.remove("Loot");
        incomingSaveData.put("Loot", lootData);
        System.out.println(mapper.writeValueAsString(incomingSaveData));
        try {
            FileOutputStream fileOut = new FileOutputStream(String.valueOf(saveLocat));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(incomingSaveData);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
