import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by J.D. Isenhart on 6/17/2015
 * 5:00 PM
 */
public class GUISpell implements ActionListener {
    public static int dimX = 1366, dimY = 700;
    public JPanel spellBase;
    public JScrollPane spellData;
    public JMenuBar theMenuBar;
    public JMenu aboutMenu, file;
    public JMenuItem about, prepSpell, addSpell, setSpell, removeSpell;
    public GroupLayout laySpells;

    public JMenuBar getTheMenuBar() {
        theMenuBar = new JMenuBar();
        aboutMenu = new JMenu("About");
        file = new JMenu("File");
        about = new JMenuItem("About the Program");
        about.addActionListener(this);
        aboutMenu.add(about);
        prepSpell = new JMenuItem("Prepare Spells");
        prepSpell.addActionListener(this);
        setSpell = new JMenuItem("Set Spell Uses");
        setSpell.addActionListener(this);
        addSpell = new JMenuItem("Add Spell");
        addSpell.addActionListener(this);
        removeSpell = new JMenuItem("Remove Spell");
        removeSpell.addActionListener(this);
        file.add(prepSpell);
        file.add(setSpell);
        file.add(addSpell);
        file.add(removeSpell);
        theMenuBar.add(file);
        theMenuBar.add(aboutMenu);
        return theMenuBar;
    }

    public JPanel SpellDisplay() {
        spellBase = new JPanel();
        spellBase.setPreferredSize(new Dimension(dimX, dimY));
        spellBase.setBackground(Color.WHITE);
        spellData = new JScrollPane(new FilingSpell().spellWidget());
        spellData.setPreferredSize(new Dimension(dimX, dimY));
        spellBase.add(spellData);

        laySpells = new GroupLayout(spellBase);
        spellBase.setLayout(laySpells);
        laySpells.setAutoCreateGaps(true);
        laySpells.setAutoCreateContainerGaps(true);
        laySpells.setHorizontalGroup(
                laySpells.createSequentialGroup()
                        .addComponent(spellData)


        );
        laySpells.setVerticalGroup(
                laySpells.createSequentialGroup()
                        .addComponent(spellData)

        );

        return spellBase;

    }

    public void updateStats() {
        spellBase.remove(spellData);
        spellData = new JScrollPane(new FilingSpell().spellWidget());
        spellData.setPreferredSize(new Dimension(dimX, dimY));
        spellBase.add(spellData);

        laySpells = new GroupLayout(spellBase);
        spellBase.setLayout(laySpells);
        laySpells.setAutoCreateGaps(true);
        laySpells.setAutoCreateContainerGaps(true);
        laySpells.setHorizontalGroup(
                laySpells.createSequentialGroup()
                        .addComponent(spellData)


        );
        laySpells.setVerticalGroup(
                laySpells.createSequentialGroup()
                        .addComponent(spellData)

        );
        spellBase.revalidate();
        spellBase.repaint();
        System.out.println("Updating Spell tab complete!");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == aboutMenu) {
            JOptionPane.showMessageDialog(about, Start.aboutText, "About", JOptionPane.PLAIN_MESSAGE);
        }
        if (e.getSource() == addSpell) {
            String name = (String) JOptionPane.showInputDialog(spellBase, "Enter the name of the Spell:", "Add a Spell", JOptionPane.PLAIN_MESSAGE, null, null, null);
            if (name.length() != 0) {
                FilingSpell.addSpell(name);
            }
        }
        if (e.getSource() == prepSpell) {
            String player = (String) JOptionPane.showInputDialog(spellBase, "Enter the name of the Player:", "Prep a Spell", JOptionPane.PLAIN_MESSAGE, null, FilingCombat.playerArrayNE, null);
            if (player.length() != 0) {
                String spell = (String) JOptionPane.showInputDialog(spellBase, "Enter the name of the Spell:", "Prep a Spell", JOptionPane.PLAIN_MESSAGE, null, FilingSpell.getSpellArray(), null);
                if (spell.length() != 0) {
                    String value = (String) JOptionPane.showInputDialog(spellBase, "Enter the quantity to add:", "Prep a Spell", JOptionPane.PLAIN_MESSAGE, null, null, null);
                    try {
                        int qty = Integer.parseInt(value);
                        FilingSpell.prepSpell(player, spell, qty);
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(spellBase, "Quantity must be an Integer (Whole Number)", "Value Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            }
        }
        if (e.getSource() == setSpell) {
            String player = (String) JOptionPane.showInputDialog(spellBase, "Enter the name of the Player:", "Set a Spell", JOptionPane.PLAIN_MESSAGE, null, FilingSpell.genSpellCasters(), null);
            if (player.length() != 0) {
                String spell = (String) JOptionPane.showInputDialog(spellBase, "Enter the name of the Spell:", "Set a Spell", JOptionPane.PLAIN_MESSAGE, null, FilingSpell.genPlayerSpellArray(player), null);
                if (spell.length() != 0) {
                    String value = (String) JOptionPane.showInputDialog(spellBase, "Enter the quantity to set tp:", "Set a Spell", JOptionPane.PLAIN_MESSAGE, null, null, null);
                    try {
                        int qty = Integer.parseInt(value);
                        FilingSpell.setUse(player, spell, qty);
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(spellBase, "Quantity must be an Integer (Whole Number)", "Value Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            }
        }
        if (e.getSource() == removeSpell) {
            String spell = (String) JOptionPane.showInputDialog(spellBase, "Enter the name of the Spell:", "Set a Spell", JOptionPane.PLAIN_MESSAGE, null, FilingSpell.getSpellArray(), null);
            if (spell.length() != 0) {
                FilingSpell.removeSpell(spell);
            }
        }
        updateStats();
    }
}
