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
    public static boolean killCondition = false;
    public static JPanel statisticsModule, initiativeModule;
    public static JTextField damageInput, healInput, healthInput;
    public static JComboBox<String> playerDamSel;
    public static JComboBox<String> playerHealSel;
    public static JComboBox<String> playerHealthSel;
    public static JButton
            damAction, healAction, healthAction,
            diceAction, swordAction, arrowAction,
            spellAction, hitAction, xpAction, setMaxHealth, healAll;
    public static JCheckBox killAction;
    public static JLabel damageLab, healingLab, healthLab, comStatsLab, parStatsLab, InitiativeLab;
    public static JMenuBar menuBar;
    public static JMenu fileMenu, aboutMenu, friendFire;
    public static JMenuItem addPlayer, removePlayer, changeName, removeKill, removeDamage, setStat, removeFF, addFF, about;
    public static GroupLayout controlLayout;
    public static DefaultTableModel statisticsTableModel, partyStatsTableModel;
    public static JTable mainStatsTable, parStatsTable;
    public static JScrollPane dataPaneDC, dataPSPane;
    static Font inputField = new Font("SansSerif", Font.PLAIN, 16);
    static Font moduleHeading = new Font("Trebuchet MS", Font.PLAIN, 20);
    static Font inputHeading = new Font("Trebuchet MS", Font.PLAIN, 16);
    static Font playerSelection = new Font("Verdana", Font.PLAIN, 16);
    static Font tableData = new Font("Helvetica", Font.BOLD, 12);
    static Font tableHeading = new Font("Garamond", Font.BOLD, 14);

    public void setFonts() {
        tableHeading = FilingFonts.tableHeading;
        tableData = FilingFonts.tableDataS12;
        inputField = FilingFonts.inputField;
        playerSelection = FilingFonts.playerSelection;
        moduleHeading = FilingFonts.moduleHeading;
        inputHeading = FilingFonts.inputHeading;
    }

    public JMenuBar getStatsMenuBar() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        aboutMenu = new JMenu("About");
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
        removeDamage = new JMenuItem("Remove Damage");
        removeDamage.addActionListener(this);
        friendFire = new JMenu("Friendly Fire");
        removeFF = new JMenuItem("Remove Friendly Fire");
        removeFF.addActionListener(this);
        addFF = new JMenuItem("Add Friendly Fire");
        addFF.addActionListener(this);
        setStat = new JMenuItem("Set Party Stat");
        setStat.addActionListener(this);
        fileMenu.add(addPlayer);
        fileMenu.add(removePlayer);
        fileMenu.add(changeName);
        fileMenu.add(removeKill);
        fileMenu.add(removeDamage);
        friendFire.add(removeFF);
        friendFire.add(addFF);
        fileMenu.add(friendFire);
        fileMenu.add(setStat);
        menuBar.add(fileMenu);
        menuBar.add(aboutMenu);
        return menuBar;
    }

    public JPanel controlPanel() {
        setFonts();
        statisticsModule = new JPanel();
        statisticsModule.setBackground(Color.WHITE);
        statisticsModule.setSize(dimX, 900);


        statisticsTableModel = new DefaultTableModel(FilingCombat.rowDataAS(), Values.overviewMainTableHeader) {
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
        mainStatsTable = new JTable(statisticsTableModel);
        DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
        CenterRenderer.setHorizontalAlignment(JLabel.CENTER);
        mainStatsTable.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        mainStatsTable.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        mainStatsTable.getColumnModel().getColumn(2).setCellRenderer(CenterRenderer);
        mainStatsTable.getColumnModel().getColumn(3).setCellRenderer(CenterRenderer);
        mainStatsTable.getColumnModel().getColumn(4).setCellRenderer(CenterRenderer);
        mainStatsTable.getColumnModel().getColumn(5).setCellRenderer(CenterRenderer);
        mainStatsTable.getColumnModel().getColumn(6).setCellRenderer(CenterRenderer);
        mainStatsTable.getColumnModel().getColumn(7).setCellRenderer(CenterRenderer);
        mainStatsTable.getTableHeader().setFont(tableHeading);
        mainStatsTable.setMaximumSize(new Dimension(dimX, dimY));
        mainStatsTable.setFont(tableData);
        mainStatsTable.setBackground(Color.WHITE);
        mainStatsTable.setRowHeight(15);
        mainStatsTable.setRowSorter(FilingCombat.BRSorter(mainStatsTable));
        dataPaneDC = new JScrollPane(mainStatsTable);
        dataPaneDC.setMaximumSize(new Dimension(dimX, 175));
        dataPaneDC.setLocation(25, 25);
        statisticsModule.add(dataPaneDC);

        damageLab = new JLabel("New Damage:");
        damageLab.setFont(inputHeading);
        statisticsModule.add(damageLab);
        playerDamSel = new JComboBox<>(FilingMain.getPlayerArray());
        playerDamSel.setFont(playerSelection);
        playerDamSel.setMaximumSize(new Dimension(200, 25));
        statisticsModule.add(playerDamSel);
        damageInput = new JTextField();
        damageInput.setMaximumSize(new Dimension(100, 25));
        damageInput.setFont(inputField);
        statisticsModule.add(damageInput);
        killAction = new JCheckBox("Kill?");
        killAction.setMnemonic(KeyEvent.VK_C);
        killAction.setSelected(false);
        killAction.setPreferredSize(new Dimension(75, 25));
        killAction.setBackground(Color.WHITE);
        killAction.addItemListener(this);
        statisticsModule.add(killAction);
        damAction = new JButton("Add Damage");
        damAction.addActionListener(this);
        statisticsModule.add(damAction);

        healingLab = new JLabel("New Healing:");
        healingLab.setFont(inputHeading);
        statisticsModule.add(healingLab);
        playerHealSel = new JComboBox<>(FilingMain.getPlayerArray());
        playerHealSel.setFont(playerSelection);
        playerHealSel.setMaximumSize(new Dimension(200, 25));
        statisticsModule.add(playerHealSel);
        healInput = new JTextField();
        healInput.setFont(inputField);
        healInput.setMaximumSize(new Dimension(100, 25));
        statisticsModule.add(healInput);
        healAction = new JButton("Add Healing");
        healAction.addActionListener(this);
        statisticsModule.add(healInput);

        healthLab = new JLabel("Change Health:");
        healthLab.setFont(inputHeading);
        statisticsModule.add(healthLab);
        playerHealthSel = new JComboBox<>(FilingMain.getPlayerArrayNE());
        playerHealthSel.setFont(playerSelection);
        playerHealthSel.setMaximumSize(new Dimension(200, 25));
        statisticsModule.add(playerHealthSel);
        healthInput = new JTextField();
        healthInput.setFont(inputField);
        healthInput.setMaximumSize(new Dimension(100, 25));
        statisticsModule.add(healthInput);
        healthAction = new JButton("Change Health");
        healthAction.addActionListener(this);
        statisticsModule.add(healthAction);

        setMaxHealth = new JButton("Set Max Health");
        setMaxHealth.addActionListener(this);
        statisticsModule.add(setMaxHealth);

        healAll = new JButton("Heal all to Max Health");
        healAll.addActionListener(this);
        statisticsModule.add(healAll);

        partyStatsTableModel = new DefaultTableModel(FilingParty.rowDataPSTable(), FilingParty.columnDataPSTable()) {
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
        parStatsTable = new JTable(partyStatsTableModel);
        parStatsTable.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        parStatsTable.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        parStatsTable.setRowHeight(15);
        parStatsTable.setFont(tableData);
        parStatsTable.getTableHeader().setFont(tableHeading);
        dataPSPane = new JScrollPane(parStatsTable);
        dataPSPane.setMaximumSize(new Dimension(dimX, 125));
        statisticsModule.add(dataPSPane);


        diceAction = new JButton("Add Dice Roll");
        diceAction.addActionListener(this);
        statisticsModule.add(diceAction);

        swordAction = new JButton("Add Weapon Swing");
        swordAction.addActionListener(this);
        statisticsModule.add(swordAction);

        arrowAction = new JButton("Add Arrow Shot");
        arrowAction.addActionListener(this);
        statisticsModule.add(arrowAction);

        spellAction = new JButton("Add Spell Cast");
        spellAction.addActionListener(this);
        statisticsModule.add(spellAction);

        hitAction = new JButton("Add Hit");
        hitAction.addActionListener(this);
        statisticsModule.add(hitAction);

        xpAction = new JButton("Add XP");
        xpAction.addActionListener(this);
        statisticsModule.add(xpAction);

        comStatsLab = new JLabel("-Combat Statistics-");
        comStatsLab.setFont(moduleHeading);
        statisticsModule.add(comStatsLab);

        parStatsLab = new JLabel("-Party Statistics-");
        parStatsLab.setFont(moduleHeading);
        statisticsModule.add(parStatsLab);

        initiativeModule = new GUIIntiative().InitiativePanel();
        statisticsModule.add(initiativeModule);

        InitiativeLab = new JLabel("-Initiative-");
        InitiativeLab.setFont(moduleHeading);
        statisticsModule.add(InitiativeLab);

        controlLayout = new GroupLayout(statisticsModule);
        statisticsModule.setLayout(controlLayout);
        controlLayout.setAutoCreateGaps(true);
        controlLayout.setAutoCreateContainerGaps(true);
        controlLayout.setHorizontalGroup(
                controlLayout.createSequentialGroup()
                        .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(comStatsLab)
                                .addComponent(dataPaneDC)
                                .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addGroup(controlLayout.createSequentialGroup()
                                                .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                        .addComponent(damageLab)
                                                        .addComponent(playerDamSel)
                                                        .addGroup(controlLayout.createSequentialGroup()
                                                                .addComponent(damageInput)
                                                                .addComponent(killAction)
                                                        )
                                                        .addComponent(damAction))
                                                .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                        .addComponent(healingLab)
                                                        .addComponent(playerHealSel)
                                                        .addComponent(healInput)
                                                        .addComponent(healAction))
                                                .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                        .addComponent(healthLab)
                                                        .addComponent(playerHealthSel)
                                                        .addComponent(healthInput)
                                                        .addComponent(healthAction))
                                                .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                        .addComponent(setMaxHealth)
                                                        .addComponent(healAll)
                                                )
                                        )
                                        .addComponent(parStatsLab)
                                        .addComponent(dataPSPane)
                                        .addGroup(controlLayout.createSequentialGroup()
                                                .addComponent(diceAction)
                                                .addComponent(swordAction)
                                                .addComponent(arrowAction)
                                                .addComponent(spellAction)
                                                .addComponent(hitAction)
                                                .addComponent(xpAction))
                                )
                        )
                        .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(InitiativeLab)
                                .addComponent(initiativeModule))

        );
        controlLayout.setVerticalGroup(
                controlLayout.createSequentialGroup()
                        .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(comStatsLab)
                                .addComponent(InitiativeLab)
                        )
                        .addGroup(controlLayout.createParallelGroup()
                                .addGroup(controlLayout.createSequentialGroup()
                                        .addComponent(dataPaneDC)
                                        .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(damageLab)
                                                .addComponent(healingLab)
                                                .addComponent(healthLab)
                                        )
                                        .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(playerDamSel)
                                                .addComponent(playerHealSel)
                                                .addComponent(playerHealthSel)
                                                .addComponent(setMaxHealth)
                                        )
                                        .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(damageInput)
                                                .addComponent(killAction)
                                                .addComponent(healInput)
                                                .addComponent(healthInput)
                                                .addComponent(healAll)
                                        )
                                        .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(damAction)
                                                .addComponent(healAction)
                                                .addComponent(healthAction)
                                        )
                                        .addComponent(parStatsLab)
                                        .addComponent(dataPSPane)
                                        .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(diceAction)
                                                .addComponent(swordAction)
                                                .addComponent(arrowAction)
                                                .addComponent(spellAction)
                                                .addComponent(hitAction)
                                                .addComponent(xpAction))
                                )
                                .addComponent(initiativeModule)
                        )
        );
        System.out.println("Done loading Combat Control tab!");
        return statisticsModule;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == about) {
            JOptionPane.showMessageDialog(about, Values.aboutText, "About", JOptionPane.PLAIN_MESSAGE);
        }
        if (e.getSource() == addFF || e.getSource() == removeFF) {
            if (e.getSource() == addFF) {
                String name = (String) JOptionPane.showInputDialog(statisticsModule, "Enter the name of the Player:", "Add Friendly Fire", JOptionPane.PLAIN_MESSAGE, null, FilingMain.getPlayerArrayNE(), null);
                if (name.length() != 0) {
                    String numInt = (String) JOptionPane.showInputDialog(statisticsModule, "Enter the damage to add:", "Add Friendly Fire", JOptionPane.PLAIN_MESSAGE, null, null, null);
                    int value = 0;
                    try {
                        value = Integer.parseInt(numInt);
                        if (value <= 0) {
                            JOptionPane.showMessageDialog(statisticsModule, "Must ADD Friendly Fire", "Value Error", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(statisticsModule, "Statistic must be an Integer (Whole Number)", "Value Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    FilingControl.friendFire(name, value, true);
                }
            } else {
                String name = (String) JOptionPane.showInputDialog(statisticsModule, "Enter the name of the Player:", "Remove Friendly Fire", JOptionPane.PLAIN_MESSAGE, null, FilingMain.getPlayerArrayNE(), null);
                if (name.length() != 0) {
                    String numInt = (String) JOptionPane.showInputDialog(statisticsModule, "Enter the damage to remove:", "Remove Friendly Fire", JOptionPane.PLAIN_MESSAGE, null, null, null);
                    int value;
                    try {
                        value = Integer.parseInt(numInt);
                        if (value <= 0) {
                            JOptionPane.showMessageDialog(statisticsModule, "Must REMOVE Friendly Fire", "Value Error", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(statisticsModule, "Statistic must be an Integer (Whole Number)", "Value Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    FilingControl.friendFire(name, value, false);
                }
            }
        }
        if (e.getSource() == removeKill || e.getSource() == removeDamage) {
            if (e.getSource() == removeKill) {
                String name = (String) JOptionPane.showInputDialog(statisticsModule, "Enter the name of the Player:", "Remove a Kill", JOptionPane.PLAIN_MESSAGE, null, FilingMain.getPlayerArray(), null);
                if (name.length() != 0) {
                    FilingControl.removeKill(name);
                }
            }
            if (e.getSource() == removeDamage) {
                String name = (String) JOptionPane.showInputDialog(statisticsModule, "Enter the name of the Player:", "Remove Damage", JOptionPane.PLAIN_MESSAGE, null, FilingMain.getPlayerArray(), null);
                if (name.length() != 0) {
                    String numInt = (String) JOptionPane.showInputDialog(statisticsModule, "Enter the damage to remove:", "Remove Damage", JOptionPane.PLAIN_MESSAGE, null, null, null);
                    int value = 0;
                    try {
                        value = Integer.parseInt(numInt);
                        if (value <= 0) {
                            JOptionPane.showMessageDialog(statisticsModule, "Must REMOVE damage", "Value Error", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(statisticsModule, "Statistic must be an Integer (Whole Number)", "Value Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    FilingControl.removeDamage(name, value);
                }
            }
        }
        if (e.getSource() == setStat) {
            String[] statsToSet = new String[]{"Dice Rolled", "Weapons Swung", "Arrows Shot", "Spells Cast", "Enemies Hit", "XP Gained"};
            String name = (String) JOptionPane.showInputDialog(statisticsModule, "Enter the name of the Statistic to Set:", "Set a Statistic", JOptionPane.PLAIN_MESSAGE, null, statsToSet, null);
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
                String nameInt = (String) JOptionPane.showInputDialog(statisticsModule, "Enter the value of the Statistic:", "Set a Statistic", JOptionPane.PLAIN_MESSAGE, null, null, null);
                int value = -1;
                try {
                    value = Integer.parseInt(nameInt);
                    if (value <= -1) {
                        JOptionPane.showMessageDialog(statisticsModule, "Statistic must be 0 or greater", "Value Error", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(statisticsModule, "Statistic must be an Integer (Whole Number)", "Value Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (nameInt.length() != 0 && value > -1) {
                    FilingControl.SetPartyStat(name, value);
                }
            }
        }
        if (e.getSource() == setMaxHealth) {
            String name = (String) JOptionPane.showInputDialog(statisticsModule, "Enter the name of the Player:", "Set Max Health", JOptionPane.PLAIN_MESSAGE, null, FilingMain.getPlayerArrayNE(), null);
            if (name.length() != 0) {
                String numInt = (String) JOptionPane.showInputDialog(statisticsModule, "Enter the player's maximum health:", "Set Max Health", JOptionPane.PLAIN_MESSAGE, null, null, null);
                int value;
                try {
                    value = Integer.parseInt(numInt);
                    if (value <= 0) {
                        JOptionPane.showMessageDialog(statisticsModule, "Must have positive max health", "Value Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(statisticsModule, "Health must be an Integer (Whole Number)", "Value Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                FilingControl.setMaxHealth(name, value);
            }
        }
        if (e.getSource() == healAll) {
            Object[] options = {"Yes",
                    "No"};
            int n = JOptionPane.showOptionDialog(statisticsModule, "Are you sure you want to heal ALL characters?", "Heal All Characters", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (n == 0) {
                FilingControl.healAlltoMax();
            }
        }
        if (e.getSource() == healAction || e.getSource() == healthAction || e.getSource() == damAction) {
            System.out.println("Updating Data");
            int damage = 0;
            int healing = 0;
            int health = 0;
            int per;
            boolean healthy = false;
            try {
                if (e.getSource() == healAction) {
//                    FilingGUIControlDialogs.openDialog("Healing");
                    healing = Integer.parseInt(healInput.getText());
                    killAction.setSelected(false);
                    killCondition = false;
                    per = playerHealSel.getSelectedIndex();

                    FilingControl.writePartyUpdate(1, 0, 0, 0, 0, 0);
                } else if (e.getSource() == healthAction) {
                    per = playerHealthSel.getSelectedIndex();
                    health = Integer.parseInt(healthInput.getText());
                    healthy = true;
                } else {
//                    FilingGUIControlDialogs.openDialog("Damage");
                    damage = Integer.parseInt(damageInput.getText());
                    FilingControl.writePartyUpdate(1, 0, 0, 0, 0, 0);
                    per = playerDamSel.getSelectedIndex();
                }
                FilingControl.changeCS(per, damage, killCondition, healing, health, healthy);
            } catch (NumberFormatException e1) {
                System.out.println("Invalid Input data");
            }
        }
        if (e.getSource() == addPlayer || e.getSource() == removePlayer || e.getSource() == changeName) {
            if (e.getSource() == addPlayer) {
                String name = (String) JOptionPane.showInputDialog(statisticsModule, "Enter the name of the Player:", "Add a Player", JOptionPane.PLAIN_MESSAGE, null, null, null);
                String[] nameArray = FilingMain.getPlayerArray();
                if (name.length() == 0) {
                    JOptionPane.showMessageDialog(statisticsModule, "Names can not be blank", "Name Error", JOptionPane.WARNING_MESSAGE);
                } else if (name.length() > 8) {
                    JOptionPane.showMessageDialog(statisticsModule, "Names can not be more than 8 Characters", "Name Error", JOptionPane.WARNING_MESSAGE);
                } else if (Arrays.asList(nameArray).contains(name)) {
                    JOptionPane.showMessageDialog(statisticsModule, "Names can not be duplicate of an already present name", "Name Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    FilingMain.addPlayer(name);
                }
            }
            if (e.getSource() == removePlayer) {
                String name = (String) JOptionPane.showInputDialog(statisticsModule, "Enter the name of the Player:", "Remove a Player", JOptionPane.PLAIN_MESSAGE, null, FilingMain.getPlayerArrayNE(), null);
                if (name.length() != 0) {
                    FilingMain.removePlayer(name);
                }
            }
            if (e.getSource() == changeName) {
                String namePre = (String) JOptionPane.showInputDialog(statisticsModule, "Enter the name of the Player you want to rename:", "Rename a Player", JOptionPane.PLAIN_MESSAGE, null, FilingMain.getPlayerArrayNE(), null);
                if (namePre.length() != 0) {
                    String namePro = (String) JOptionPane.showInputDialog(statisticsModule, "Enter the new name of the player:", "Rename a Player", JOptionPane.PLAIN_MESSAGE, null, null, null);
                    if (namePro.length() == 0) {
                        JOptionPane.showMessageDialog(statisticsModule, "Names can not be blank", "Name Error", JOptionPane.WARNING_MESSAGE);
                    } else if (namePro.length() > 8) {
                        JOptionPane.showMessageDialog(statisticsModule, "Names can not be more than 8 Characters", "Name Error", JOptionPane.WARNING_MESSAGE);
                    } else if (Arrays.asList(FilingMain.getPlayerArray()).contains(namePro)) {
                        JOptionPane.showMessageDialog(statisticsModule, "Names can not be duplicate of an already present name", "Name Error", JOptionPane.WARNING_MESSAGE);
                    } else {
                        FilingMain.renamePlayer(namePre, namePro);
                    }
                }
            }
        }
        if (e.getSource() == diceAction || e.getSource() == swordAction || e.getSource() == arrowAction || e.getSource() == spellAction || e.getSource() == hitAction || e.getSource() == xpAction) {
            int dice = 0, sword = 0, arrow = 0, spell = 0, hit = 0, xp = 0;
            if (e.getSource() == xpAction) {
                String name = (String) JOptionPane.showInputDialog(statisticsModule, "Enter XP to be added:", "Add XP", JOptionPane.PLAIN_MESSAGE, null, null, null);
                if (name.length() != 0) {
                    try {
                        xp = Integer.parseInt(name);
                        FilingXP.addXPAll(xp);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(statisticsModule, "Invalid XP Amount", "XP Error", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else if (e.getSource() == diceAction) {
                dice = 1;
                FilingControl.writePartyUpdate(dice, sword, arrow, spell, hit, xp);
            } else if (e.getSource() == swordAction) {
                dice = 1;
                sword = 1;
                FilingControl.writePartyUpdate(dice, sword, arrow, spell, hit, xp);
            } else if (e.getSource() == arrowAction) {
                dice = 1;
                arrow = 1;
                FilingControl.writePartyUpdate(dice, sword, arrow, spell, hit, xp);
            } else if (e.getSource() == spellAction) {
                String name = (String) JOptionPane.showInputDialog(statisticsModule, "How many Dice are rolled through the spell cast?:", "Cast a Spell", JOptionPane.PLAIN_MESSAGE, null, new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}, null);
                if (name.length() != 0) {
                    spell = 1;
                    dice = Integer.parseInt(name);
                    FilingControl.writePartyUpdate(dice, sword, arrow, spell, hit, xp);

                }
            } else if (e.getSource() == hitAction) {
                hit = 1;
                FilingControl.writePartyUpdate(dice, sword, arrow, spell, hit, xp);
            }
        }
        updateStats();

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == killAction) {
            killCondition = e.getStateChange() != ItemEvent.DESELECTED;
        }
    }

    public void updateStats() {
        statisticsModule.remove(dataPaneDC);
        statisticsModule.remove(dataPSPane);
        statisticsModule.remove(playerDamSel);
        statisticsModule.remove(playerHealSel);
        statisticsModule.remove(playerHealthSel);
        statisticsModule.remove(initiativeModule);
        damageInput.setText("");
        healInput.setText("");
        healthInput.setText("");
        killAction.setSelected(false);
        killCondition = false;
        statisticsTableModel = new DefaultTableModel(FilingCombat.rowDataAS(), Values.overviewMainTableHeader) {
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
        mainStatsTable = new JTable(statisticsTableModel);
        DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
        CenterRenderer.setHorizontalAlignment(JLabel.CENTER);
        mainStatsTable.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        mainStatsTable.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        mainStatsTable.getColumnModel().getColumn(2).setCellRenderer(CenterRenderer);
        mainStatsTable.getColumnModel().getColumn(3).setCellRenderer(CenterRenderer);
        mainStatsTable.getColumnModel().getColumn(4).setCellRenderer(CenterRenderer);
        mainStatsTable.getColumnModel().getColumn(5).setCellRenderer(CenterRenderer);
        mainStatsTable.getColumnModel().getColumn(6).setCellRenderer(CenterRenderer);
        mainStatsTable.getColumnModel().getColumn(7).setCellRenderer(CenterRenderer);
        mainStatsTable.getTableHeader().setFont(tableHeading);
        mainStatsTable.setMaximumSize(new Dimension(dimX, dimY));
        mainStatsTable.setFont(tableData);
        mainStatsTable.setBackground(Color.WHITE);
        mainStatsTable.setRowHeight(15);
        mainStatsTable.setRowSorter(FilingCombat.BRSorter(mainStatsTable));
        dataPaneDC = new JScrollPane(mainStatsTable);
        dataPaneDC.setMaximumSize(new Dimension(dimX, 175));
        dataPaneDC.setLocation(25, 25);
        statisticsModule.add(dataPaneDC);

        partyStatsTableModel = new DefaultTableModel(FilingParty.rowDataPSTable(), FilingParty.columnDataPSTable()) {
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
        parStatsTable = new JTable(partyStatsTableModel);
        parStatsTable.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        parStatsTable.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        parStatsTable.setRowHeight(15);
        parStatsTable.setFont(tableData);
        parStatsTable.getTableHeader().setFont(tableHeading);
        dataPSPane = new JScrollPane(parStatsTable);
        dataPSPane.setMaximumSize(new Dimension(dimX, 125));
        statisticsModule.add(dataPSPane);

        playerHealSel = new JComboBox<>(FilingMain.getPlayerArray());
        playerHealSel.setFont(playerSelection);
        playerHealSel.setMaximumSize(new Dimension(200, 25));
        statisticsModule.add(playerHealSel);

        playerHealthSel = new JComboBox<>(FilingMain.getPlayerArrayNE());
        playerHealthSel.setFont(playerSelection);
        playerHealthSel.setMaximumSize(new Dimension(200, 25));
        statisticsModule.add(playerHealthSel);

        playerDamSel = new JComboBox<>(FilingMain.getPlayerArray());
        playerDamSel.setFont(playerSelection);
        playerDamSel.setMaximumSize(new Dimension(200, 25));
        statisticsModule.add(playerDamSel);

        initiativeModule = new GUIIntiative().InitiativePanel();
        statisticsModule.add(initiativeModule);

        controlLayout = new GroupLayout(statisticsModule);
        statisticsModule.setLayout(controlLayout);
        controlLayout.setAutoCreateGaps(true);
        controlLayout.setAutoCreateContainerGaps(true);
        controlLayout.setHorizontalGroup(
                controlLayout.createSequentialGroup()
                        .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(comStatsLab)
                                .addComponent(dataPaneDC)
                                .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addGroup(controlLayout.createSequentialGroup()
                                                .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                        .addComponent(damageLab)
                                                        .addComponent(playerDamSel)
                                                        .addGroup(controlLayout.createSequentialGroup()
                                                                .addComponent(damageInput)
                                                                .addComponent(killAction)
                                                        )
                                                        .addComponent(damAction))
                                                .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                        .addComponent(healingLab)
                                                        .addComponent(playerHealSel)
                                                        .addComponent(healInput)
                                                        .addComponent(healAction))
                                                .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                        .addComponent(healthLab)
                                                        .addComponent(playerHealthSel)
                                                        .addComponent(healthInput)
                                                        .addComponent(healthAction))
                                                .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                        .addComponent(setMaxHealth)
                                                        .addComponent(healAll)
                                                )
                                        )
                                        .addComponent(parStatsLab)
                                        .addComponent(dataPSPane)
                                        .addGroup(controlLayout.createSequentialGroup()
                                                .addComponent(diceAction)
                                                .addComponent(swordAction)
                                                .addComponent(arrowAction)
                                                .addComponent(spellAction)
                                                .addComponent(hitAction)
                                                .addComponent(xpAction))
                                )
                        )
                        .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(InitiativeLab)
                                .addComponent(initiativeModule))

        );
        controlLayout.setVerticalGroup(
                controlLayout.createSequentialGroup()
                        .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(comStatsLab)
                                .addComponent(InitiativeLab)
                        )
                        .addGroup(controlLayout.createParallelGroup()
                                .addGroup(controlLayout.createSequentialGroup()
                                        .addComponent(dataPaneDC)
                                        .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(damageLab)
                                                .addComponent(healingLab)
                                                .addComponent(healthLab)
                                        )
                                        .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(playerDamSel)
                                                .addComponent(playerHealSel)
                                                .addComponent(playerHealthSel)
                                                .addComponent(setMaxHealth)
                                        )
                                        .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(damageInput)
                                                .addComponent(killAction)
                                                .addComponent(healInput)
                                                .addComponent(healthInput)
                                                .addComponent(healAll)
                                        )
                                        .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(damAction)
                                                .addComponent(healAction)
                                                .addComponent(healthAction)
                                        )
                                        .addComponent(parStatsLab)
                                        .addComponent(dataPSPane)
                                        .addGroup(controlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(diceAction)
                                                .addComponent(swordAction)
                                                .addComponent(arrowAction)
                                                .addComponent(spellAction)
                                                .addComponent(hitAction)
                                                .addComponent(xpAction))
                                )
                                .addComponent(initiativeModule)
                        )
        );

        statisticsModule.revalidate();
        statisticsModule.repaint();
        System.out.println("Done updating the Combat Control Tab!");
    }
}