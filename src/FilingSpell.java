import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by J.D. Isenhart on 6/17/2015
 * 5:30 PM
 */
public class FilingSpell {
    public static int dimX = 1366, dimY = 700;

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

    public static void prepSpell(String player, String spellName, int quantity) {
        HashMap<String, HashMap> saveData = readSave();
        HashMap casters = (HashMap) saveData.get("Spells").get("Casters");
        if (!casters.containsKey(player)) {
            Object[][] spellData = new Object[][]{{spellName, quantity}};
            casters.put(player, spellData);
        } else {
            Object[][] oldSpellData = (Object[][]) casters.get(player);
            boolean prepCheck = false;
            int index = 0;
            for (int i = 0; i < oldSpellData.length; i++) {
                if (String.valueOf(oldSpellData[i][0]).equals(spellName)) {
                    prepCheck = true;
                    index = i;
                    i = oldSpellData.length;
                }
            }
            if (prepCheck) {
                int qty = quantity + Integer.parseInt(String.valueOf(oldSpellData[index][1]));
                oldSpellData[index][1] = qty;
                casters.remove(player);
                casters.put(player, oldSpellData);
                saveData.get("Spells").remove("Casters");
                saveData.get("Spells").put("Casters", casters);
            } else {
                Object[][] newSpellData = new Object[oldSpellData.length + 1][2];
                for (int i = 0; i < oldSpellData.length; i++) {
                    newSpellData[i][0] = oldSpellData[i][0];
                    newSpellData[i][1] = oldSpellData[i][1];
                }
                newSpellData[oldSpellData.length][0] = spellName;
                newSpellData[oldSpellData.length][1] = quantity;
                casters.remove(player);
                casters.put(player, newSpellData);
                saveData.get("Spells").remove("Casters");
                saveData.get("Spells").put("Casters", casters);
            }
        }
        writeFile(saveData);
    }

    public static void addSpell(String spellName) {
        HashMap<String, HashMap> saveData = readSave();
        ArrayList<String> sList = (ArrayList<String>) saveData.get("Spells").get("SpellList");
        sList.add(spellName);
        saveData.get("Spells").remove("SpellList");
        saveData.get("Spells").put("SpellList", sList);
        writeFile(saveData);
    }

    public static String[] getSpellArray() {
        HashMap<String, HashMap> saveData = readSave();
        ArrayList<String> sList = (ArrayList<String>) saveData.get("Spells").get("SpellList");
        Collections.sort(sList);
        String[] sArray = new String[sList.size()];
        for (int i = 0; i < sList.size(); i++) {
            sArray[i] = String.valueOf(sList.get(i));
        }
        return sArray;
    }

    public static HashMap checkSpells(HashMap casterMap) {
        FilingMain.genPlayerArrays();
        HashMap<String, HashMap> saveData = readSave();
        HashMap casters = new HashMap();
        ArrayList<String> spellList = (ArrayList<String>) saveData.get("Spells").get("SpellList");
        for (int i = 0; i < casterMap.size(); i++) {
            Object[][] spellData = (Object[][]) casterMap.get(FilingMain.playerArrayNE[i]);
            if (spellData == null) {
                spellData = new Object[][]{};
            }
            for (int j = 0; j < spellData.length; j++) {
                if (!spellList.contains(spellData[j][0])) {
                    Object[][] newSData = new Object[spellData.length - 1][2];
                    int aIndex = 0;
                    for (int k = 0; k < spellData.length; k++) {
                        if (String.valueOf(spellData[j][0]).equals(String.valueOf(spellData[k][0]))) {
                            aIndex = -1;
                        } else {
                            newSData[k + aIndex][0] = spellData[k][0];
                            newSData[k + aIndex][1] = spellData[k][1];
                        }

                    }
                    spellData = newSData;
                    j--;
                }
            }

            casters.put(FilingMain.playerArrayNE[i], spellData);
        }
        saveData.get("Spells").remove("Casters");
        saveData.get("Spells").put("Casters", casters);
        writeFile(saveData);
        return casters;
    }

    public static void setUse(String player, String spell, int quantity) {
        HashMap<String, HashMap> saveData = readSave();
        HashMap casters = (HashMap) saveData.get("Spells").get("Casters");
        if (!casters.containsKey(player)) {
            Object[][] spellData = new Object[][]{{spell, quantity}};
            casters.put(player, spellData);
        } else {
            Object[][] oldSpellData = (Object[][]) casters.get(player);
            boolean prepCheck = false;
            int index = 0;
            for (int i = 0; i < oldSpellData.length; i++) {
                if (String.valueOf(oldSpellData[i][0]).equals(spell)) {
                    prepCheck = true;
                    index = i;
                    i = oldSpellData.length;
                }
            }
            if (prepCheck) {
                oldSpellData[index][1] = quantity;
                casters.remove(player);
                casters.put(player, oldSpellData);
                saveData.get("Spells").remove("Casters");
                saveData.get("Spells").put("Casters", casters);
            } else {
                Object[][] newSpellData = new Object[oldSpellData.length + 1][2];
                for (int i = 0; i < oldSpellData.length; i++) {
                    newSpellData[i][0] = oldSpellData[i][0];
                    newSpellData[i][1] = oldSpellData[i][1];
                }
                newSpellData[oldSpellData.length][0] = spell;
                newSpellData[oldSpellData.length][1] = quantity;
                casters.remove(player);
                casters.put(player, newSpellData);
                saveData.get("Spells").remove("Casters");
                saveData.get("Spells").put("Casters", casters);
            }
        }
        writeFile(saveData);
    }

    public static void castSpell(String player, String spell) {
        HashMap<String, HashMap> saveData = readSave();
        HashMap casters = (HashMap) saveData.get("Spells").get("Casters");
        Object[][] playerData = (Object[][]) casters.get(player);
        int index = 0;
        for (int i = 0; i < playerData.length; i++) {
            if (String.valueOf(playerData[i][0]).equals(spell)) {
                index = i;
                i = playerData.length;
            }
        }
        int qtyLft = Integer.parseInt(String.valueOf(playerData[index][1])) - 1;
        Object[][] newPData = new Object[playerData.length - 1][2];
        int aIndex = 0;
        if (qtyLft == 0) {
            for (int i = 0; i < newPData.length; i++) {
                if (String.valueOf(playerData[i][0]).equals(spell)) {
                    aIndex = -1;
                } else {
                    newPData[i + aIndex][0] = playerData[i][0];
                    newPData[i + aIndex][1] = playerData[i][1];
                }
            }
            casters.remove(player);
            casters.put(player, newPData);
        } else {
            playerData[index][1] = qtyLft;
            casters.remove(player);
            casters.put(player, playerData);
        }
        writeFile(saveData);
    }

    public static void removeSpell(String spell) {
        HashMap<String, HashMap> saveData = readSave();
        ArrayList<String> sList = (ArrayList<String>) saveData.get("Spells").get("SpellList");
        sList.remove(spell);
        saveData.get("Spells").remove("SpellList");
        saveData.get("Spells").put("SpellList", sList);
        writeFile(saveData);
    }

    public static String[] genPlayerSpellArray(String player) {
        HashMap<String, HashMap> saveData = readSave();
        HashMap casters = (HashMap) saveData.get("Spells").get("Casters");
        Object[][] spellData = (Object[][]) casters.get(player);
        String[] spellArray = new String[spellData.length];
        for (int i = 0; i < spellData.length; i++) {
            spellArray[i] = String.valueOf(spellData[i][0]);
        }
        return spellArray;
    }

    public static String[] genSpellCasters() {
        HashMap<String, HashMap> saveData = readSave();
        HashMap casters = (HashMap) saveData.get("Spells").get("Casters");
        FilingMain.genPlayerArrays();
        java.util.List players = new ArrayList<>();
        for (int i = 0; i < FilingMain.playerArrayNE.length; i++) {
            Object[][] spellData = (Object[][]) casters.get(String.valueOf(FilingMain.playerArrayNE[i]));
            if (spellData == null) {
                spellData = new Object[][]{};
            }
            if (spellData.length != 0) {
                players.add(FilingMain.playerArrayNE[i]);
            }
        }
        String[] playerA = new String[players.size()];
        for (int i = 0; i < players.size(); i++) {
            playerA[i] = String.valueOf(players.get(i));
        }
        return playerA;
    }

    public JPanel spellWidget() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        HashMap<String, HashMap> saveData = readSave();
        HashMap casters = (HashMap) saveData.get("Spells").get("Casters");
        casters = checkSpells(casters);
        try {
            System.out.println(mapper.writeValueAsString(casters));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] playerArray = FilingCombat.playerArrayNE;
        JPanel base = new JPanel();
        base.setBackground(Color.GREEN);
        base.setLayout(null);
        int panelIndex = 0;
        base.setSize(dimX, dimY);
        base.setBackground(Color.WHITE);
        for (int playerIndex = 0; playerIndex < playerArray.length; playerIndex++) {
            if (casters.containsKey(playerArray[playerIndex])) {
                Object[][] spellData = (Object[][]) casters.get(playerArray[playerIndex]);
                if (spellData.length != 0) {
                    JPanel namePanel = new JPanel();
                    namePanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                    namePanel.setLayout(null);
                    namePanel.setSize(dimX, 30);
                    JLabel name = new JLabel(playerArray[playerIndex]);
                    name.setSize(dimX, 20);
                    name.setLocation(5, 5);
                    namePanel.add(name);
                    namePanel.setLocation(0, 30 * panelIndex);
                    base.add(namePanel);
                    panelIndex++;
                    for (int i = 0; i < spellData.length; i++) {
                        JPanel spellPanel = new JPanel();
                        spellPanel.setBorder(BorderFactory.createMatteBorder(1, 10, 1, 1, Color.black));
                        spellPanel.setLayout(null);
                        spellPanel.setSize(dimX, 30);
                        JLabel spellName = new JLabel(String.valueOf(spellData[i][0]));
                        spellName.setSize(100, 20);
                        JLabel spellQty = new JLabel("Uses: " + String.valueOf(spellData[i][1]));
                        spellQty.setSize(100, 20);
                        spellName.setLocation(25, 5);
                        spellQty.setLocation(130, 5);
                        spellPanel.add(spellName);
                        spellPanel.add(spellQty);
                        JButton use = new JButton("Cast");
                        use.addActionListener(new buttonClass(playerArray[playerIndex], spellData[i][0].toString()));
                        use.setSize(75, 20);
                        use.setLocation(dimX - 160, 5);
                        spellPanel.add(use);
                        spellPanel.setLocation(0, panelIndex * 30);
                        panelIndex++;
                        base.add(spellPanel);
                    }
                }

            }

        }
        return base;
    }

    public class buttonClass implements ActionListener {
        String player, spell;

        buttonClass(String player, String spell) {
            this.player = player;
            this.spell = spell;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            castSpell(this.player, this.spell);
        }
    }
}

