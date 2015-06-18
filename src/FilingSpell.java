import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by J.D. Isenhart on 6/17/2015
 * 5:30 PM
 */
public class FilingSpell implements ActionListener {
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
            Object[][] newSpellData = new Object[oldSpellData.length][2];
            for (int i = 0; i < oldSpellData.length; i++) {
                newSpellData[i][0] = oldSpellData[i][0];
                newSpellData[i][1] = oldSpellData[i][1];
            }
            newSpellData[oldSpellData.length][0] = spellName;
            newSpellData[oldSpellData.length][1] = spellName;
            casters.remove(player);
            casters.put(player, newSpellData);
            saveData.get("Spells").remove("Casters");
            saveData.get("Spells").put("Casters", casters);
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

    public JPanel spellWidget() {
        HashMap<String, HashMap> saveData = readSave();
        HashMap casters = (HashMap) saveData.get("Spells").get("Casters");
        String[] playerArray = FilingCombat.playerArrayNE;
        JPanel base = new JPanel();
        base.setLayout(null);
        int panelIndex = 0;
        base.setSize(dimX, dimY);
        base.setBackground(Color.WHITE);
        for (int playerIndex = 0; playerIndex < playerArray.length; playerIndex++) {
            if (casters.containsKey(playerArray[playerIndex])) {
                Object[][] spellData = (Object[][]) casters.get(playerArray[playerIndex]);
                if (spellData.length != 0) {
                    JPanel namePanel = new JPanel();
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
                        spellPanel.setSize(dimX, 30);
                        JLabel spellName = new JLabel(String.valueOf(spellData[i][0]));
                        spellName.setSize(100, 20);
                        JLabel spellQty = new JLabel("Uses: " + String.valueOf(spellData[i][1]));
                        spellQty.setSize(75, 20);
                        spellName.setLocation(5, 5);
                        spellQty.setLocation(110, 5);
                        spellPanel.add(spellName);
                        spellPanel.add(spellQty);
                        JButton use = new JButton("Cast");
                        use.addActionListener(new buttonClass(playerArray[playerIndex], spellData[i][0].toString()));
                        use.setSize(75, 20);
                        use.setLocation(dimX - 80, 5);
                        spellPanel.add(use);
                        spellPanel.setLocation(0, panelIndex * 30);
                        base.add(spellPanel);
                    }
                }
            }
        }
        return base;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Identify Button (Relates to person and spell)
        //Subtract quantity
        //If Spell now has zero quantity, remove from 2D Array
        //Write Changes to File
    }

    public class buttonClass implements ActionListener {
        String player, spell;

        buttonClass(String player, String spell) {
            this.player = player;
            this.spell = spell;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Player: " + player);
            System.out.println("Spell: " + spell);
        }
    }
}

