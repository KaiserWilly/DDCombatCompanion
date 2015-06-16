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
public class TestFile {
    static JFileChooser newSave;
    static JPanel base;
    static Path saveLocat;
    static HashMap<String,HashMap> incomingSaveData;

    public static void main(String[] args) throws IOException {
        newSave = new JFileChooser(Start.saveFilePath.toString());
        int returnVal = newSave.showDialog(base, "Create new Save");
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
        incomingSaveData.get("Loot").remove("Notes");
        incomingSaveData.get("Loot").put("Notes",new String(""));
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
