import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by J.D. Isenhart on 6/10/2015
 * 7:44 PM
 */
public class FilingMain implements ActionListener {
    static String[] playerArray;
    static String[] playerArrayNE;
    static HashMap<String, HashMap> incomingSaveData = null;
    public JMenuItem about;
    public JMenuBar initMenu;
    public JMenu aboutMenu;

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

    public static String[] getPlayerArray() {
        String[] playerArray = new String[incomingSaveData.get("Players").size()];
        incomingSaveData = readSave();
        for (int i = 0; i < incomingSaveData.get("Players").size(); i++) {
            playerArray[i] = String.valueOf(incomingSaveData.get("Players").get(i));
        }
        return playerArray;

    }

    public static String[] getPlayerArrayNE() {
        playerArrayNE = new String[(incomingSaveData.get("Players").size())];
        for (int i = 0; i < incomingSaveData.get("Players").size(); i++) {
            if (!String.valueOf(incomingSaveData.get("Players").get(i)).equals("Enemy")) {
                playerArrayNE[i] = String.valueOf(incomingSaveData.get("Players").get(i));
            }

        }
        return playerArrayNE;
    }


    public static void runConversion() {
        incomingSaveData = readSave();
        Conversion.checkSaveMap(incomingSaveData);
    }

    public static void genPlayerArrays() {
        HashMap<String, HashMap> incomingSaveData = readSave();
        playerArray = new String[incomingSaveData.get("Players").size()];
        playerArrayNE = new String[(incomingSaveData.get("Players").size())];
        for (int i = 0; i < incomingSaveData.get("Players").size(); i++) {
            playerArray[i] = String.valueOf(incomingSaveData.get("Players").get(i));
            if (!String.valueOf(incomingSaveData.get("Players").get(i)).equals("Enemy")) {
                playerArrayNE[i] = String.valueOf(incomingSaveData.get("Players").get(i));
            }

        }
    }

    public static void createSave() {
        HashMap<String, HashMap> newMap = new HashMap<String, HashMap>();
        newMap.put("Players", new HashMap<Integer, String>());
        newMap.get("Players").put(0, "Enemy");
        newMap.put("Enemy", new HashMap<String, Integer>());
        newMap.get("Enemy").put("Damage", 0);
        newMap.get("Enemy").put("Kills", 0);
        newMap.get("Enemy").put("Healing", 0);
        HashMap partyStats = new HashMap();
        partyStats.put("Dice", 0);
        partyStats.put("Swords", 0);
        partyStats.put("Arrows", 0);
        partyStats.put("Spells", 0);
        partyStats.put("XP", 0);
        partyStats.put("Hits", 0);
        newMap.put("Party", partyStats);
        HashMap loot = new HashMap();
        loot.put("Data", new Object[][]{});
        loot.put("Notes", new String());
        newMap.put("Loot", loot);
        writeFile(newMap);
    }

    public static void addPlayer(String player) {
        List<String> PlayerOrder = new ArrayList<String>() {
        };
        incomingSaveData = readSave();
        for (int i = 0; i < incomingSaveData.get("Players").size(); i++) {
            assert PlayerOrder != null;
            PlayerOrder.add(String.valueOf(incomingSaveData.get("Players").get(i)));

        }
        PlayerOrder.add(player);
        PlayerOrder.remove("Enemy");
        java.util.Collections.sort(PlayerOrder);
        PlayerOrder.add(PlayerOrder.size(), "Enemy");
        incomingSaveData.remove("Players");
        incomingSaveData.put("Players", newPlayerMap(PlayerOrder));
        incomingSaveData.put(player, new HashMap());
        incomingSaveData.get(player).put("Damage", 0);
        incomingSaveData.get(player).put("Kills", 0);
        incomingSaveData.get(player).put("Healing", 0);
        incomingSaveData.get(player).put("Health", 0);
        incomingSaveData.get(player).put("BR", 0);
        HashMap casters = (HashMap) incomingSaveData.get("Spells").get("Casters");
        casters.put(player, new Object[][]{});
        incomingSaveData.get("Spells").remove("Casters");
        incomingSaveData.get("Spells").put("Casters", casters);
        FilingXP.addPlayer(player);
        writeFile(incomingSaveData);

        System.out.println("Done adding player to the Game!");
    }

    public static void removePlayer(String player) {
        List<String> PlayerOrder = new ArrayList<String>() {
        };
        incomingSaveData = readSave();
        System.out.println(incomingSaveData.get("Players").size());
        for (int i = 0; i < incomingSaveData.get("Players").size(); i++) {
            assert PlayerOrder != null;
            PlayerOrder.add(String.valueOf(incomingSaveData.get("Players").get(i)));

        }
        PlayerOrder.remove(player);
        PlayerOrder.remove("Enemy");
        java.util.Collections.sort(PlayerOrder);
        PlayerOrder.add(PlayerOrder.size(), "Enemy");
        incomingSaveData.remove("Players");
        incomingSaveData.put("Players", newPlayerMap(PlayerOrder));
        incomingSaveData.remove(player);
        HashMap casters = (HashMap) incomingSaveData.get("Spells").get("Casters");
        casters.remove(player);
        incomingSaveData.get("Spells").remove("Casters");
        incomingSaveData.get("Spells").put("Casters", casters);
        FilingXP.removePlayer(player);
        writeFile(incomingSaveData);
        System.out.println("Done removing player from the Game!");
    }

    public static HashMap newPlayerMap(List<String> names) {
        HashMap<Integer, String> blankMap = new HashMap<>();
        for (int i = 0; i < names.size(); i++) {
            blankMap.put(i, names.get(i));
        }
        return blankMap;
    }

    public JPanel showBlankPane() {
        int dimX = 1366, dimY = 700;
        JPanel base = new JPanel();
        base.setBackground(Color.WHITE);
        base.setSize(dimX, dimY);

        return base;
    }

    public JMenuBar defaultMenuBar() {
        initMenu = new JMenuBar();
        aboutMenu = new JMenu("About");
        aboutMenu.addActionListener(this);
        about = new JMenuItem("About the Program");
        about.addActionListener(this);
        aboutMenu.add(about);
        initMenu.add(aboutMenu);
        return initMenu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == about) {
            JOptionPane.showMessageDialog(about, Start.aboutText, "About", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
