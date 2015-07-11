import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by J.D. Isenhart on 7/11/2015
 * 11:26 AM
 */
public class FilingXP {
    static HashMap<String, HashMap> incomingSaveData = null;
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

    public static Object[][] retLevelTableData() {
        incomingSaveData = readSave();
        HashMap XPData = incomingSaveData.get("XP");
        Object[][] XPTabData = (Object[][]) XPData.get("Levels");
        DecimalFormat formatter = new DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale.getDefault()));
        for (int i = 0; i < XPTabData.length; i++) {
            XPTabData[i][1] = formatter.format(Integer.parseInt(String.valueOf(XPTabData[i][1])));
        }
        return XPTabData;
    }

    public static Object[] retLevelColumnHeaders() {
        return new Object[]{"Level", "XP Required"};
    }

    public static Object[] retXPColumnHeaders() {
        return new Object[]{"Player", "XP", "Level"};
    }

    public static Object[][] retXPTabData() {
        incomingSaveData = readSave();
        HashMap XPData = incomingSaveData.get("XP");
        Object[][] levelTabDataRaw = (Object[][]) XPData.get("XP");
        Object[][] levelTabData = new Object[levelTabDataRaw.length][3];
        DecimalFormat formatter = new DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale.getDefault()));
        for (int i = 0; i < levelTabDataRaw.length; i++) {
            levelTabData[i][0] = levelTabDataRaw[i][0];
            levelTabData[i][1] = formatter.format(Integer.parseInt(String.valueOf(levelTabDataRaw[i][1])));
            levelTabData[i][2] = calcLevelFromXP(String.valueOf(levelTabData[i][0]), Integer.parseInt(String.valueOf(levelTabDataRaw[i][1])));
        }
        return levelTabData;
    }

    public static int calcLevelFromXP(String player, int XP) {
        incomingSaveData = readSave();
        HashMap XPData = incomingSaveData.get("XP");
        Object[][] playerXPdata = (Object[][]) XPData.get("Levels");
        boolean below = false, above = false;
        if (XP < Integer.parseInt(String.valueOf(playerXPdata[0][1]))) {
            return 1;
        } else {
            for (int i = 1; i < playerXPdata.length; i++) {
                if (XP > Integer.parseInt(String.valueOf(playerXPdata[i - 1][1])) && XP < Integer.parseInt(String.valueOf(playerXPdata[i][1]))) {
                    return i + 1;

                }
            }
        }
        return 0;
    }

    public static boolean checkupdateValidity(String level, int XP) {
        incomingSaveData = readSave();
        boolean below = false, above = false;
        HashMap XPData = incomingSaveData.get("XP");
        Object[][] levelTabData = (Object[][]) XPData.get("Levels");
        for (int i = 0; i < levelTabData.length; i++) {
            if (String.valueOf(levelTabData[i][0]).equals(level)) {
                try {
                    if (Integer.parseInt(String.valueOf(levelTabData[i - 1][1])) < XP) {
                        below = true;
                    }
                } catch (IndexOutOfBoundsException e) {
                    if (XP > 0) {
                        below = true;
                    }
                }
                try {
                    if (Integer.parseInt(String.valueOf(levelTabData[i + 1][1])) > XP) {
                        above = true;
                    }
                } catch (IndexOutOfBoundsException e) {
                    if (XP > 0) {
                        above = true;
                    }
                }
                if (below && above) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }

    public static void updateLevel(String lev, int XP) {
        String level = lev.replaceAll("[^0-9.]", "");
        if (checkupdateValidity(level, XP)) {
            incomingSaveData = readSave();
            HashMap XPData = incomingSaveData.get("XP");
            Object[][] levelTabData = (Object[][]) XPData.get("Levels");
            for (int i = 0; i < levelTabData.length; i++) {
                if (String.valueOf(levelTabData[i][0]).equals(level)) {
                    levelTabData[i][1] = XP;
                    break;
                }


            }
            XPData.remove("Levels");
            XPData.put("Levels", levelTabData);
            incomingSaveData.remove("XP");
            incomingSaveData.put("XP", XPData);
            writeFile(incomingSaveData);
        }
    }

    public static void updateXP(String player, int XP) {
        incomingSaveData = readSave();
        HashMap XPData = incomingSaveData.get("XP");
        Object[][] playerXPdata = (Object[][]) XPData.get("XP");
        for (int i = 0; i < playerXPdata.length; i++) {
            if (String.valueOf(playerXPdata[i][0]).equals(player)) {
                playerXPdata[i][1] = XP;
                break;
            }
        }
        XPData.remove("XP");
        XPData.put("XP", playerXPdata);
        incomingSaveData.remove("XP");
        incomingSaveData.put("XP", XPData);
        writeFile(incomingSaveData);
    }

    public static void addXPAll(int XP) {
        incomingSaveData = readSave();
        HashMap XPData = incomingSaveData.get("XP");
        Object[][] playerXPdata = (Object[][]) XPData.get("XP");
        for (int i = 0; i < playerXPdata.length; i++) {
            playerXPdata[i][1] = Integer.parseInt(String.valueOf(playerXPdata[i][1])) + XP;
        }
        XPData.remove("XP");
        XPData.put("XP", playerXPdata);
        incomingSaveData.remove("XP");
        incomingSaveData.put("XP", XPData);
        writeFile(incomingSaveData);
    }

    public static void addPlayer(String player) {
        incomingSaveData = readSave();
        HashMap XPData = incomingSaveData.get("XP");
        Object[][] oldPlayerXPdata = (Object[][]) XPData.get("XP");
        Object[][] nwPlayerXPdata = new Object[oldPlayerXPdata.length + 1][3];
        int nameConditioner = 0;
        for (int i = 0; i < nwPlayerXPdata.length; i++) {
            nwPlayerXPdata[i + nameConditioner][0] = oldPlayerXPdata[i + nameConditioner][0];
            nwPlayerXPdata[i + nameConditioner][1] = oldPlayerXPdata[i + nameConditioner][1];
            nwPlayerXPdata[i + nameConditioner][2] = oldPlayerXPdata[i + nameConditioner][2];
        }
        nwPlayerXPdata[oldPlayerXPdata.length][0] = oldPlayerXPdata[oldPlayerXPdata.length][0];
        nwPlayerXPdata[oldPlayerXPdata.length][1] = oldPlayerXPdata[oldPlayerXPdata.length][1];
        nwPlayerXPdata[oldPlayerXPdata.length][2] = oldPlayerXPdata[oldPlayerXPdata.length][2];
        XPData.remove("XP");
        XPData.put("XP", nwPlayerXPdata);
        incomingSaveData.remove("XP");
        incomingSaveData.put("XP", XPData);
        writeFile(incomingSaveData);
    }

    public static void removePlayer(String player) {
        incomingSaveData = readSave();
        HashMap XPData = incomingSaveData.get("XP");
        Object[][] oldPlayerXPdata = (Object[][]) XPData.get("XP");
        Object[][] nwPlayerXPdata = new Object[oldPlayerXPdata.length - 1][3];
        int nameConditioner = 0;
        for (int i = 0; i < nwPlayerXPdata.length; i++) {
            if (String.valueOf(oldPlayerXPdata[i][0]).equals(player)) {
                nameConditioner = -1;
            } else {
                nwPlayerXPdata[i + nameConditioner][0] = oldPlayerXPdata[i + nameConditioner][0];
                nwPlayerXPdata[i + nameConditioner][1] = oldPlayerXPdata[i + nameConditioner][1];
                nwPlayerXPdata[i + nameConditioner][2] = oldPlayerXPdata[i + nameConditioner][2];
            }
        }
        XPData.remove("XP");
        XPData.put("XP", nwPlayerXPdata);
        incomingSaveData.remove("XP");
        incomingSaveData.put("XP", XPData);
        writeFile(incomingSaveData);
    }
}
