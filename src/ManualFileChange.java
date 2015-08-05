import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import javax.swing.*;
import java.io.IOException;
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
    static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        newSave = new JFileChooser();
        int returnVal = newSave.showDialog(base, "Load Save");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            Start.saveFilePath = Paths.get(String.valueOf(newSave.getSelectedFile()));
        }
        incomingSaveData = FilingMain.readSave();
        //End Loading File

//        HashMap XPdata = new HashMap();
//        Object[][] playerData = new Object[][]{
//                {"Brody", 2575, 2},
//                {"Chris", 2275, 2},
//                {"Daniel", 2575, 2},
//                {"Jared", 2500, 2},
//                {"JD", 2575, 2},
//                {"Ryan", 2575, 2},
//                {"Walker", 0, 1}
//
//        };
//        Object[][] levels = new Object[][]{
//                {"2", 1000},
//                {"3", 3000},
//                {"4", 6000},
//                {"5", 10000},
//                {"6", 15000},
//                {"7", 21000},
//                {"8", 28000},
//                {"9", 36000},
//                {"10", 45000},
//                {"11", 55000},
//                {"12", 66000},
//                {"13", 78000},
//                {"14", 91000},
//                {"15", 105000},
//                {"16", 120000},
//                {"17", 136000},
//                {"18", 153000},
//                {"19", 171000},
//                {"20", 190000},
//        };
//        XPdata.put("XP", playerData);
//        XPdata.put("Levels", levels);
//        incomingSaveData.put("XP",XPdata);
        System.out.println(Integer.parseInt(String.valueOf("0")));

        //Write File
        FilingMain.writeFile(incomingSaveData);
    }
}
