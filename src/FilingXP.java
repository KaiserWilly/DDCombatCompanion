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
    static ObjectMapper mapper = new ObjectMapper();

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
        return new Object[]{"LEVEL", "XP REQUIRED"};
    }

    public static Object[] retXPColumnHeaders() {
        return new Object[]{"PLAYER", "XP", "LEVEL"};
    }

    public static Object[][] retXPTabData() {
        incomingSaveData = readSave();
        HashMap XPData = incomingSaveData.get("XP");
        Object[][] xpTabDataRaw = (Object[][]) XPData.get("XP");
        Object[][] xpTabData = new Object[xpTabDataRaw.length][3];
        DecimalFormat formatter = new DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale.getDefault()));
        for (int i = 0; i < xpTabDataRaw.length; i++) {
            xpTabData[i][0] = xpTabDataRaw[i][0];
            xpTabData[i][1] = formatter.format(Integer.parseInt(String.valueOf(xpTabDataRaw[i][1])));
            xpTabData[i][2] = calcLevelFromXP(String.valueOf(xpTabData[i][0]), Integer.parseInt(String.valueOf(xpTabDataRaw[i][1])));
        }
        return xpTabData;
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
                if (XP >= Integer.parseInt(String.valueOf(playerXPdata[i - 1][1])) && XP < Integer.parseInt(String.valueOf(playerXPdata[i][1]))) {
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
                return below && above;
            }
        }

        return false;
    }

    public static boolean updateLevel(String lev, int XP) {
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
            return true;
        }
        return false;
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
        for (int i = 0; i < oldPlayerXPdata.length; i++) {
            nwPlayerXPdata[i + nameConditioner][0] = oldPlayerXPdata[i + nameConditioner][0];
            nwPlayerXPdata[i + nameConditioner][1] = oldPlayerXPdata[i + nameConditioner][1];
            nwPlayerXPdata[i + nameConditioner][2] = oldPlayerXPdata[i + nameConditioner][2];
        }
        nwPlayerXPdata[oldPlayerXPdata.length][0] = player;
        nwPlayerXPdata[oldPlayerXPdata.length][1] = 0;
        nwPlayerXPdata[oldPlayerXPdata.length][2] = 1;
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

    public static void renamePlayer(String playerOld, String playerNew) {
        incomingSaveData = readSave();
        HashMap XPData = incomingSaveData.get("XP");
        Object[][] playerXPData = (Object[][]) XPData.get("XP");
        int nameConditioner = 0;
        for (int i = 0; i < playerXPData.length; i++) {
            if (String.valueOf(playerXPData[i][0]).equals(playerOld)) {
                playerXPData[i][0] = playerNew;
            } else {
                playerXPData[i + nameConditioner][0] = playerXPData[i + nameConditioner][0];
            }
            playerXPData[i + nameConditioner][1] = playerXPData[i + nameConditioner][1];
            playerXPData[i + nameConditioner][2] = playerXPData[i + nameConditioner][2];

        }
        XPData.remove("XP");
        XPData.put("XP", playerXPData);
        incomingSaveData.remove("XP");
        incomingSaveData.put("XP", XPData);
        writeFile(incomingSaveData);

    }
}
