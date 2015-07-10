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
import java.util.Map;

/**
 * Created by J.D. Isenhart on 6/10/2015
 * 7:44 PM
 */
public class FilingMain implements ActionListener {
    static String[] playerArray;
    static String[] playerArrayNE;
    public JMenuItem about;
    public JMenuBar initMenu;
    public JMenu aboutMenu;

    public static void genPlayerArrays() {
        HashMap<String, HashMap> incomingSaveData = FilingLoot.readSave();
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
        Map<String, HashMap> newMap = new HashMap<String, HashMap>();
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
        try {
            System.out.println("Save Path: " + String.valueOf(Start.saveFilePath));
            FileOutputStream fileOut = new FileOutputStream(String.valueOf(Start.saveFilePath));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(newMap);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            System.out.println("Error in File Creation; New Save; Filing.createSave");
            e.printStackTrace();
        }
    }

    public static void addPlayer(String player) {
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
            System.out.println("Error in File Reading; Filing.addPlayer");
            e.printStackTrace();
        }
        for (int i = 0; i < playerData.get("Players").size(); i++) {
            assert PlayerOrder != null;
            PlayerOrder.add(String.valueOf(playerData.get("Players").get(i)));

        }
        PlayerOrder.add(player);
        PlayerOrder.remove("Enemy");
        java.util.Collections.sort(PlayerOrder);
        PlayerOrder.add(PlayerOrder.size(), "Enemy");
        playerData.remove("Players");
        playerData.put("Players", newPlayerMap(PlayerOrder));
        playerData.put(player, new HashMap());
        playerData.get(player).put("Damage", 0);
        playerData.get(player).put("Kills", 0);
        playerData.get(player).put("Healing", 0);
        playerData.get(player).put("Health", 0);
        playerData.get(player).put("BR", 0);
        HashMap casters = (HashMap) playerData.get("Spells").get("Casters");
        casters.put(player, new Object[][]{});
        playerData.get("Spells").remove("Casters");
        playerData.get("Spells").put("Casters", casters);
        try {
            FileOutputStream fileOut = new FileOutputStream(String.valueOf(Start.saveFilePath));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(playerData);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            System.out.println("Error in File Creation; New Save; Filing.addPlayer");
            e.printStackTrace();
        }

        System.out.println("Done adding player to the Game!");
    }

    public static void removePlayer(String player) {
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
            System.out.println("Error in File Reading; Filing.addPlayer");
            e.printStackTrace();
        }
        System.out.println(playerData.get("Players").size());
        for (int i = 0; i < playerData.get("Players").size(); i++) {
            //  System.out.println("264" + String.valueOf(playerData.get("Players").get(i)));
            assert PlayerOrder != null;
            //  System.out.println("266" + String.valueOf(playerData.get("Players").get(i)));
            PlayerOrder.add(String.valueOf(playerData.get("Players").get(i)));

        }
        PlayerOrder.remove(player);
        PlayerOrder.remove("Enemy");
        java.util.Collections.sort(PlayerOrder);
        PlayerOrder.add(PlayerOrder.size(), "Enemy");
        playerData.remove("Players");
        playerData.put("Players", newPlayerMap(PlayerOrder));
        playerData.remove(player);
        HashMap casters = (HashMap) playerData.get("Spells").get("Casters");
        casters.remove(player);
        playerData.get("Spells").remove("Casters");
        playerData.get("Spells").put("Casters", casters);
        try {
            System.out.println("Save Path: " + String.valueOf(Start.saveFilePath));
            FileOutputStream fileOut = new FileOutputStream(String.valueOf(Start.saveFilePath));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(playerData);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            System.out.println("Error in File Creation; New Save; Filing.removeSave");
            e.printStackTrace();
        }
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
