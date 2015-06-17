import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

/**
 * Created by J.D. Isenhart on 6/10/2015
 * 9:01 PM
 */
public class GUIControl implements ActionListener, ItemListener {
    public static int dimX = 1366, dimY = 700;
    public static int KillV = 0;
    public static JPanel Stats;
    public static JTextField damDC, healDC, healthDC;
    public static JComboBox playerDam, playerHeal, playerHealth;
    public static JButton goDC, goHeal, updateHealth, goDice, goSword, goArrow, goSpell, goHit, goXP;
    public static JCheckBox KillQ;
    public static JLabel newDam, newHeal, HealthChange, Cstats, Pstats;
    public static JMenuBar statsMenuB;
    public static JMenu statsFile, aboutMenu;
    public static JMenuItem addPlayer, removePlayer, changeName, removeKill, setStat, about;
    public static GroupLayout layStats;
    public static DefaultTableModel modelDC, psMain;
    public static JTable dataDC, dataPsMain;
    public static JScrollPane dataPaneDC, dataPSPane;
    static Font CSStatsHeading = new Font("SansSerif", Font.PLAIN, 16);
    static Font sHeading = new Font("Trebuchet MS", Font.PLAIN, 20);
    static Font comStatHeading = new Font("Trebuchet MS", Font.PLAIN, 16);

    public JMenuBar getStatsMenuBar() {
        statsMenuB = new JMenuBar();
        statsFile = new JMenu("File");
        aboutMenu = new JMenu("About");
        aboutMenu.addActionListener(this);
        about = new JMenuItem("About the Program");
        about.addActionListener(this);
        aboutMenu.add(about);
        addPlayer = new JMenuItem("Add Player");
        addPlayer.addActionListener(this);
        removePlayer = new JMenuItem("Remove Player");
        removePlayer.addActionListener(this);
        changeName = new JMenuItem("Rename Player");
        changeName.addActionListener(this);
        removeKill = new JMenuItem("Remove Kill");
        removeKill.addActionListener(this);
        setStat = new JMenuItem("Set Party Stat");
        setStat.addActionListener(this);
        statsFile.add(addPlayer);
        statsFile.add(removePlayer);
        statsFile.add(changeName);
        statsFile.add(removeKill);
        statsFile.add(setStat);
        statsMenuB.add(statsFile);
        statsMenuB.add(aboutMenu);
        return statsMenuB;
    }

    public JPanel ModStatistics() {
        Stats = new JPanel();
        Stats.setBackground(Color.WHITE);
        Stats.setSize(dimX, dimY);


        modelDC = new DefaultTableModel(FilingCombat.rowDataAS(), FilingCombat.columnHeadersAS()) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return Integer.class;
                    case 3:
                        return Integer.class;
                    case 4:
                        return Integer.class;
                    case 5:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };
        dataDC = new JTable(modelDC);
        DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
        CenterRenderer.setHorizontalAlignment(JLabel.CENTER);
        dataDC.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        dataDC.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        dataDC.getColumnModel().getColumn(2).setCellRenderer(CenterRenderer);
        dataDC.getColumnModel().getColumn(3).setCellRenderer(CenterRenderer);
        dataDC.getColumnModel().getColumn(4).setCellRenderer(CenterRenderer);
        dataDC.getColumnModel().getColumn(5).setCellRenderer(CenterRenderer);
        dataDC.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        dataDC.setMaximumSize(new Dimension(dimX, dimY));
        dataDC.setFont((new Font("SansSerif", Font.BOLD, 12)));
        dataDC.setBackground(Color.WHITE);
        dataDC.setRowHeight(15);
        dataDC.setRowSorter(FilingCombat.BRSorter(dataDC));
        dataPaneDC = new JScrollPane(dataDC);
        dataPaneDC.setMaximumSize(new Dimension(dimX, 150));
        dataPaneDC.setLocation(25, 25);
        Stats.add(dataPaneDC);

        newDam = new JLabel("New Damage:");
        newDam.setFont(comStatHeading);
        Stats.add(newDam);
        playerDam = new JComboBox<>(FilingCombat.playerArray);
        playerDam.setFont(new Font("Verdana", Font.PLAIN, 16));
        playerDam.setMaximumSize(new Dimension(200, 25));
        Stats.add(playerDam);
        damDC = new JTextField();
        damDC.setMaximumSize(new Dimension(100, 25));
        damDC.setFont(CSStatsHeading);
        Stats.add(damDC);
        KillQ = new JCheckBox("Kill?");
        KillQ.setMnemonic(KeyEvent.VK_C);
        KillQ.setSelected(false);
        KillQ.setPreferredSize(new Dimension(75, 25));
        KillQ.setBackground(Color.WHITE);
        KillQ.addItemListener(this);
        Stats.add(KillQ);
        goDC = new JButton("Add Damage");
        goDC.addActionListener(this);
        Stats.add(goDC);

        newHeal = new JLabel("New Healing:");
        newHeal.setFont(comStatHeading);
        Stats.add(newHeal);
        playerHeal = new JComboBox<>(FilingCombat.playerArray);
        playerHeal.setFont(new Font("Verdana", Font.PLAIN, 16));
        playerHeal.setMaximumSize(new Dimension(200, 25));
        Stats.add(playerHeal);
        healDC = new JTextField();
        healDC.setFont(CSStatsHeading);
        healDC.setMaximumSize(new Dimension(100, 25));
        Stats.add(healDC);
        goHeal = new JButton("Add Healing");
        goHeal.addActionListener(this);
        Stats.add(healDC);

        HealthChange = new JLabel("Health Change:");
        HealthChange.setFont(comStatHeading);
        Stats.add(HealthChange);
        playerHealth = new JComboBox<>(FilingCombat.playerArray);
        playerHealth.setFont(new Font("Verdana", Font.PLAIN, 16));
        playerHealth.setMaximumSize(new Dimension(200, 25));
        Stats.add(playerHealth);
        healthDC = new JTextField();
        healthDC.setFont(CSStatsHeading);
        healthDC.setMaximumSize(new Dimension(100, 25));
        Stats.add(healthDC);
        updateHealth = new JButton("Change Health");
        updateHealth.addActionListener(this);
        Stats.add(updateHealth);

        psMain = new DefaultTableModel(FilingParty.rowDataPSTable(), FilingParty.columnDataPSTable()) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return Integer.class;
                    case 3:
                        return Integer.class;
                    case 4:
                        return Integer.class;
                    case 5:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };
        dataPsMain = new JTable(psMain);
        dataPsMain.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        dataPsMain.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        dataPsMain.setRowHeight(15);
        dataPsMain.setFont((new Font("SansSerif", Font.BOLD, 12)));
        dataPsMain.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        dataPSPane = new JScrollPane(dataPsMain);
        dataPSPane.setMaximumSize(new Dimension(dimX, 125));
        Stats.add(dataPSPane);


        goDice = new JButton("Add Dice Roll");
        goDice.addActionListener(this);
        Stats.add(goDice);

        goSword = new JButton("Add Weapon Swing");
        goSword.addActionListener(this);
        Stats.add(goSword);

        goArrow = new JButton("Add Arrow Shot");
        goArrow.addActionListener(this);
        Stats.add(goArrow);

        goSpell = new JButton("Add Spell Cast");
        goSpell.addActionListener(this);
        Stats.add(goSpell);

        goHit = new JButton("Add Hit");
        goHit.addActionListener(this);
        Stats.add(goHit);

        goXP = new JButton("Add XP");
        goXP.addActionListener(this);
        Stats.add(goXP);

        Cstats = new JLabel("-Combat Statistics-");
        Cstats.setFont(sHeading);
        Stats.add(Cstats);

        Pstats = new JLabel("-Party Statistics-");
        Pstats.setFont(sHeading);
        Stats.add(Pstats);

        layStats = new GroupLayout(Stats);
        Stats.setLayout(layStats);
        layStats.setAutoCreateGaps(true);
        layStats.setAutoCreateContainerGaps(true);
        layStats.setHorizontalGroup(
                layStats.createSequentialGroup()
                        .addGroup(layStats.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(Cstats)
                                        .addComponent(dataPaneDC)
                                        .addGroup(layStats.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                        .addGroup(layStats.createSequentialGroup()
                                                                        .addGroup(layStats.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                                                .addComponent(newDam)
                                                                                .addComponent(playerDam)
                                                                                .addGroup(layStats.createSequentialGroup()
                                                                                        .addComponent(damDC)
                                                                                        .addComponent(KillQ))
                                                                                .addComponent(goDC))
                                                                        .addGroup(layStats.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                                                .addComponent(newHeal)
                                                                                .addComponent(playerHeal)
                                                                                .addComponent(healDC)
                                                                                .addComponent(goHeal))
                                                                        .addGroup(layStats.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                                                .addComponent(HealthChange)
                                                                                .addComponent(playerHealth)
                                                                                .addComponent(healthDC)
                                                                                .addComponent(updateHealth))
                                                        )
                                                        .addComponent(Pstats)
                                                        .addComponent(dataPSPane)
                                                        .addGroup(layStats.createSequentialGroup()
                                                                .addComponent(goDice)
                                                                .addComponent(goSword)
                                                                .addComponent(goArrow)
                                                                .addComponent(goSpell)
                                                                .addComponent(goHit)
                                                                .addComponent(goXP))
                                        )
                        )
        );
        layStats.setVerticalGroup(
                layStats.createSequentialGroup()
                        .addComponent(Cstats)
                        .addComponent(dataPaneDC)
                        .addGroup(layStats.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(newDam)
                                .addComponent(newHeal)
                                .addComponent(HealthChange))
                        .addGroup(layStats.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(playerDam)
                                .addComponent(playerHeal)
                                .addComponent(playerHealth))
                        .addGroup(layStats.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(damDC)
                                .addComponent(KillQ)
                                .addComponent(healDC)
                                .addComponent(healthDC))

                        .addGroup(layStats.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(goDC)
                                .addComponent(goHeal)
                                .addComponent(updateHealth))
                        .addComponent(Pstats)
                        .addComponent(dataPSPane)
                        .addGroup(layStats.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(goDice)
                                .addComponent(goSword)
                                .addComponent(goArrow)
                                .addComponent(goSpell)
                                .addComponent(goHit)
                                .addComponent(goXP))
        );

        return Stats;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == aboutMenu) {
            JOptionPane.showMessageDialog(about, Start.aboutText, "About", JOptionPane.PLAIN_MESSAGE);
        }
        if (e.getSource() == removeKill) {
            String name = (String) JOptionPane.showInputDialog(Stats, "Enter the name of the Player:", "Remove a Kill", JOptionPane.PLAIN_MESSAGE, null, FilingCombat.playerArray, null);
            if (name.length() != 0) {
                FilingCombat.removeKill(name);
            }
        }
        if (e.getSource() == setStat) {
            String[] statsToSet = new String[]{"Dice Rolled", "Weapons Swung", "Arrows Shot", "Spells Cast", "Enemies Hit", "XP Gained"};
            String name = (String) JOptionPane.showInputDialog(Stats, "Enter the name of the Statistic to Set:", "Set a Statistic", JOptionPane.PLAIN_MESSAGE, null, statsToSet, null);
            switch (name) {
                case "Dice Rolled":
                    name = "Dice";
                    break;
                case "Weapons Swung":
                    name = "Swords";
                    break;
                case "Arrows Shot":
                    name = "Arrows";
                    break;
                case "Spells Cast":
                    name = "Spells";
                    break;
                case "Enemies hit":
                    name = "Hits";
                    break;
                case "XP Gained":
                    name = "XP";
                    break;
                default:
                    name = null;
            }
            if (name != null) {
                String nameInt = (String) JOptionPane.showInputDialog(Stats, "Enter the value of the Statistic:", "Set a Statistic", JOptionPane.PLAIN_MESSAGE, null, null, null);
                int value = -1;
                try {
                    value = Integer.parseInt(nameInt);
                    if (value <= -1) {
                        JOptionPane.showMessageDialog(Stats, "Statistic must be 0 or greater", "Value Error", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(Stats, "Statistic must be an Integer (Whole Number)", "Value Error", JOptionPane.WARNING_MESSAGE);
                }
                if (nameInt.length() != 0 && value > -1) {
                    FilingParty.SetPartyStat(name, value);
                }
            }
        }
        if (e.getSource() == goHeal || e.getSource() == updateHealth || e.getSource() == goDC) {
            System.out.println("Updating Data");
            int damage = 0;
            int healing = 0;
            int health = 0;
            int per;
            boolean healthy = false;
            try {
                if (e.getSource() == goHeal) {
                    healing = Integer.parseInt(healDC.getText());
                    KillQ.setSelected(false);
                    KillV = 0;
                    per = playerHeal.getSelectedIndex();
                } else if (e.getSource() == updateHealth) {
                    per = playerHealth.getSelectedIndex();
                    health = Integer.parseInt(healthDC.getText());
                    healthy = true;
                } else {
                    damage = Integer.parseInt(damDC.getText());
                    per = playerDam.getSelectedIndex();
                }

                FilingCombat.changeCS(per, damage, KillV, healing, health, healthy);
            } catch (
                    NumberFormatException e1) {
                System.out.println("Invalid Input data");
            }
        }
        if (e.getSource() == addPlayer || e.getSource() == removePlayer || e.getSource() == changeName) {
            if (e.getSource() == addPlayer) {
                String name = (String) JOptionPane.showInputDialog(Stats, "Enter the name of the Player:", "Add a Player", JOptionPane.PLAIN_MESSAGE, null, null, null);
                String[] nameArray = FilingCombat.playerArray;
                if (name.length() == 0) {
                    JOptionPane.showMessageDialog(Stats, "Names can not be blank", "Name Error", JOptionPane.WARNING_MESSAGE);
                } else if (name.length() > 8) {
                    JOptionPane.showMessageDialog(Stats, "Names can not be more than 8 Characters", "Name Error", JOptionPane.WARNING_MESSAGE);
                } else if (Arrays.asList(nameArray).contains(name)) {
                    JOptionPane.showMessageDialog(Stats, "Names can not be duplicate of an already present name", "Name Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    FilingCombat.addPlayer(name);
                }
            }
            if (e.getSource() == removePlayer) {
                String name = (String) JOptionPane.showInputDialog(Stats, "Enter the name of the Player:", "Remove a Player", JOptionPane.PLAIN_MESSAGE, null, FilingCombat.playerArray, null);
                if (name.length() != 0) {
                    FilingCombat.removePlayer(name);
                }
            }
            if (e.getSource() == changeName) {
                String namePre = (String) JOptionPane.showInputDialog(Stats, "Enter the name of the Player you want to rename:", "Rename a Player", JOptionPane.PLAIN_MESSAGE, null, FilingCombat.playerArrayNE, null);
                if (namePre.length() != 0) {
                    String namePro = (String) JOptionPane.showInputDialog(Stats, "Enter the new name of the player:", "Rename a Player", JOptionPane.PLAIN_MESSAGE, null, null, null);
                    if (namePro.length() == 0) {
                        JOptionPane.showMessageDialog(Stats, "Names can not be blank", "Name Error", JOptionPane.WARNING_MESSAGE);
                    } else if (namePro.length() > 8) {
                        JOptionPane.showMessageDialog(Stats, "Names can not be more than 8 Characters", "Name Error", JOptionPane.WARNING_MESSAGE);
                    } else if (Arrays.asList(FilingCombat.playerArray).contains(namePro)) {
                        JOptionPane.showMessageDialog(Stats, "Names can not be duplicate of an already present name", "Name Error", JOptionPane.WARNING_MESSAGE);
                    } else {
                        FilingCombat.renamePlayer(namePre, namePro);
                    }
                }
            }
        }
        if (e.getSource() == goDice || e.getSource() == goSword || e.getSource() == goArrow || e.getSource() == goSpell || e.getSource() == goHit || e.getSource() == goXP) {
            int dice = 0, sword = 0, arrow = 0, spell = 0, hit = 0, xp = 0;
            if (e.getSource() == goXP) {
                String name = (String) JOptionPane.showInputDialog(Stats, "Enter XP to be added:", "Add XP", JOptionPane.PLAIN_MESSAGE, null, null, null);
                if (name.length() != 0) {
                    try {
                        xp = Integer.parseInt(name);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(Stats, "Invalid XP Amount", "XP Error", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else if (e.getSource() == goDice) {
                dice = 1;
            } else if (e.getSource() == goSword) {
                sword = 1;
            } else if (e.getSource() == goArrow) {
                arrow = 1;
            } else if (e.getSource() == goSpell) {
                spell = 1;
            } else if (e.getSource() == goHit) {
                hit = 1;
            }
            FilingParty.writePartyUpdate(dice, sword, arrow, spell, hit, xp);
        }
        updateStats();

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == KillQ) {
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                KillV = 0;
            } else {
                KillV = 1;
            }
        }
    }

    public void updateStats() {
        Stats.remove(dataPaneDC);
        Stats.remove(dataPSPane);
        Stats.remove(playerDam);
        Stats.remove(playerHeal);
        Stats.remove(playerHealth);
        damDC.setText("");
        healDC.setText("");
        healthDC.setText("");
        KillQ.setSelected(false);
        KillV = 0;
        modelDC = new DefaultTableModel(FilingCombat.rowDataAS(), FilingCombat.columnHeadersAS()) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return Integer.class;
                    case 3:
                        return Integer.class;
                    case 4:
                        return Integer.class;
                    case 5:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };
        dataDC = new JTable(modelDC);
        DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
        CenterRenderer.setHorizontalAlignment(JLabel.CENTER);
        dataDC.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        dataDC.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        dataDC.getColumnModel().getColumn(2).setCellRenderer(CenterRenderer);
        dataDC.getColumnModel().getColumn(3).setCellRenderer(CenterRenderer);
        dataDC.getColumnModel().getColumn(4).setCellRenderer(CenterRenderer);
        dataDC.getColumnModel().getColumn(5).setCellRenderer(CenterRenderer);
        dataDC.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        dataDC.setMaximumSize(new Dimension(dimX, dimY));
        dataDC.setFont((new Font("SansSerif", Font.BOLD, 12)));
        dataDC.setBackground(Color.WHITE);
        dataDC.setRowHeight(15);
        dataDC.setRowSorter(FilingCombat.BRSorter(dataDC));
        dataPaneDC = new JScrollPane(dataDC);
        dataPaneDC.setMaximumSize(new Dimension(dimX, 150));
        dataPaneDC.setLocation(25, 25);
        Stats.add(dataPaneDC);

        psMain = new DefaultTableModel(FilingParty.rowDataPSTable(), FilingParty.columnDataPSTable()) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };
        dataPsMain = new JTable(psMain);
        dataPsMain.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        dataPsMain.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        dataPsMain.setRowHeight(15);
        dataPsMain.setFont((new Font("SansSerif", Font.BOLD, 12)));
        dataPsMain.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        dataPSPane = new JScrollPane(dataPsMain);
        dataPSPane.setMaximumSize(new Dimension(dimX, 125));
        Stats.add(dataPSPane);

        playerHeal = new JComboBox<>(FilingCombat.playerArray);
        playerHeal.setFont(new Font("Verdana", Font.PLAIN, 16));
        playerHeal.setMaximumSize(new Dimension(200, 25));
        Stats.add(playerHeal);

        playerHealth = new JComboBox<>(FilingCombat.playerArray);
        playerHealth.setFont(new Font("Verdana", Font.PLAIN, 16));
        playerHealth.setMaximumSize(new Dimension(200, 25));
        Stats.add(playerHealth);

        playerDam = new JComboBox<>(FilingCombat.playerArray);
        playerDam.setFont(new Font("Verdana", Font.PLAIN, 16));
        playerDam.setMaximumSize(new Dimension(200, 25));
        Stats.add(playerDam);

        layStats = new GroupLayout(Stats);
        Stats.setLayout(layStats);
        layStats.setAutoCreateGaps(true);
        layStats.setAutoCreateContainerGaps(true);
        layStats.setHorizontalGroup(
                layStats.createSequentialGroup()
                        .addGroup(layStats.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(Cstats)
                                        .addComponent(dataPaneDC)
                                        .addGroup(layStats.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                        .addGroup(layStats.createSequentialGroup()
                                                                        .addGroup(layStats.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                                                .addComponent(newDam)
                                                                                .addComponent(playerDam)
                                                                                .addGroup(layStats.createSequentialGroup()
                                                                                        .addComponent(damDC)
                                                                                        .addComponent(KillQ))
                                                                                .addComponent(goDC))
                                                                        .addGroup(layStats.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                                                .addComponent(newHeal)
                                                                                .addComponent(playerHeal)
                                                                                .addComponent(healDC)
                                                                                .addComponent(goHeal))
                                                                        .addGroup(layStats.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                                                .addComponent(HealthChange)
                                                                                .addComponent(playerHealth)
                                                                                .addComponent(healthDC)
                                                                                .addComponent(updateHealth))
                                                        )
                                                        .addComponent(Pstats)
                                                        .addComponent(dataPSPane)
                                                        .addGroup(layStats.createSequentialGroup()
                                                                .addComponent(goDice)
                                                                .addComponent(goSword)
                                                                .addComponent(goArrow)
                                                                .addComponent(goSpell)
                                                                .addComponent(goHit)
                                                                .addComponent(goXP))
                                        )
                        )
        );
        layStats.setVerticalGroup(
                layStats.createSequentialGroup()
                        .addComponent(Cstats)
                        .addComponent(dataPaneDC)
                        .addGroup(layStats.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(newDam)
                                .addComponent(newHeal)
                                .addComponent(HealthChange))
                        .addGroup(layStats.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(playerDam)
                                .addComponent(playerHeal)
                                .addComponent(playerHealth))
                        .addGroup(layStats.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(damDC)
                                .addComponent(KillQ)
                                .addComponent(healDC)
                                .addComponent(healthDC))

                        .addGroup(layStats.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(goDC)
                                .addComponent(goHeal)
                                .addComponent(updateHealth))
                        .addComponent(Pstats)
                        .addComponent(dataPSPane)
                        .addGroup(layStats.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(goDice)
                                .addComponent(goSword)
                                .addComponent(goArrow)
                                .addComponent(goSpell)
                                .addComponent(goHit)
                                .addComponent(goXP))
        );

        Stats.revalidate();
        Stats.repaint();
        System.out.println("Done updating the Combat Control Tab!");
    }
}