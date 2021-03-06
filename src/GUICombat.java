import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by J.D. Isenhart on 6/6/2015
 * 11:57 PM
 */
public class GUICombat implements ActionListener {
    public static int dimX = 1366, dimY = 700;
    public static int KillV = 0;
    public static JPanel CStats;
    public static JTable dataDC, dataFCS, dataECS;
    public static JScrollPane dataPaneDC, dataPaneFCS, dataPaneECS;
    public static GroupLayout layCS;
    public static JLabel FCS, ECS, DamChamp, KillChamp, HealChamp;
    public static DefaultTableModel modelDC, modelFCS, modelECS;
    public static JMenuBar theMenuBar;
    public static JMenu aboutMenu;
    public static JMenuItem  about;
    static int RowHeight = 30;
    static Font CSPSHeading = new Font("Franklin Gothic Medium", Font.ITALIC, 20);
    static Font ChampTableContent = new Font("Trebuchet MS", Font.PLAIN, 20);
    static Font tableData = new Font("Helvetica", Font.BOLD, 12);
    static Font tableHeading = new Font("Garamond", Font.BOLD, 14);

    public void setFonts() {
        tableHeading = FilingFonts.tableHeading;
        tableData = FilingFonts.tableDataS24;
    }




    public JMenuBar getTheMenuBar() {
        theMenuBar = new JMenuBar();
        aboutMenu = new JMenu("About");
        aboutMenu.addActionListener(this);
        about = new JMenuItem("About the Program");
        about.addActionListener(this);
        aboutMenu.add(about);
        theMenuBar.add(aboutMenu);
        return theMenuBar;
    }

    public JPanel DCount() {
        setFonts();
        CStats = new JPanel();
        CStats.setSize(dimX, dimY);
        CStats.setBackground(Color.WHITE);
        modelDC = new DefaultTableModel(FilingCombat.rowDataAS(), Values.overviewMainTableHeader) {
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
                    default:
                        return Integer.class;
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
        dataDC.getColumnModel().getColumn(6).setCellRenderer(CenterRenderer);
        dataDC.getColumnModel().getColumn(7).setCellRenderer(CenterRenderer);
        dataDC.getTableHeader().setFont(tableHeading);
        dataDC.setSize(dimX, dimY);
        dataDC.setLocation(50, 50);
        dataDC.setFont(tableData);
        dataDC.setBackground(Color.WHITE);
        dataDC.setRowHeight(RowHeight);
        dataDC.setRowSorter(FilingCombat.BRSorter(dataDC));
        TableColumn column;
        for (int i = 0; i < dataDC.getColumnCount(); i++) {
            column = dataDC.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(300); //third column is bigger
            } else {
                column.setPreferredWidth(100);
            }
        }
        dataPaneDC = new JScrollPane(dataDC);
        dataPaneDC.setMaximumSize(new Dimension(dimX, 350));

        dataPaneDC.setLocation(25, 25);
        CStats.add(dataPaneDC);


        modelFCS = new DefaultTableModel(FilingCombat.FriendComStats, Values.comStatsTableHeader) {
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
        dataFCS = new JTable(modelFCS);
        dataFCS.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        dataFCS.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        dataFCS.getColumnModel().getColumn(2).setCellRenderer(CenterRenderer);
        dataFCS.getTableHeader().setFont(tableHeading);
        dataFCS.setFont(tableData);
        dataFCS.setMinimumSize(new Dimension(dimX, 150));
        dataFCS.setBackground(Color.WHITE);
        dataFCS.setRowHeight(40);
        dataFCS.setAutoCreateRowSorter(true);
        dataPaneFCS = new JScrollPane(dataFCS);
        dataPaneFCS.setLocation(25, 25);
        dataPaneFCS.setMaximumSize(new Dimension(dimX, 65));
        CStats.add(dataPaneFCS);

        modelECS = new DefaultTableModel((Object[][]) FilingCombat.EnemyComStats, Values.comStatsTableHeader) {
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
                    default:
                        return String.class;
                }
            }
        };
        dataECS = new JTable(modelECS);
        dataECS.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        dataECS.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        dataECS.getColumnModel().getColumn(2).setCellRenderer(CenterRenderer);
        dataECS.getTableHeader().setFont(tableHeading);
        dataECS.setFont(tableData);
        dataECS.setMinimumSize(new Dimension(dimX, 150));
        dataECS.setBackground(Color.WHITE);
        dataECS.setRowHeight(40);
        dataECS.setAutoCreateRowSorter(true);
        dataPaneECS = new JScrollPane(dataECS);
        dataPaneECS.setLocation(25, 25);
        dataPaneECS.setMaximumSize(new Dimension(dimX, 65));
        CStats.add(dataPaneECS);

        FCS = new JLabel("Friendly Combat Statistics:");
        FCS.setFont(CSPSHeading);
        FCS.setMinimumSize(new Dimension(300, 25));
        CStats.add(FCS);

        ECS = new JLabel("Enemy Combat Statistics:");
        ECS.setMinimumSize(new Dimension(300, 25));
        ECS.setFont(CSPSHeading);
        CStats.add(ECS);

        DamChamp = new JLabel("Damage Champion: " + FilingCombat.comStatsDamChamp());
        DamChamp.setFont(ChampTableContent);
        DamChamp.setMinimumSize(new Dimension(25, 200));
        CStats.add(DamChamp);

        KillChamp = new JLabel("     Kill Champion: " + FilingCombat.comStatsKillChamp());
        KillChamp.setFont(ChampTableContent);
        KillChamp.setMinimumSize(new Dimension(25, 200));
        CStats.add(KillChamp);

        HealChamp = new JLabel("     Healing Champion: " + FilingCombat.comStatsHealingChamp());
        HealChamp.setFont(ChampTableContent);
        HealChamp.setMinimumSize(new Dimension(25, 200));
        CStats.add(HealChamp);

        layCS = new GroupLayout(CStats);
        CStats.setLayout(layCS);
        layCS.setAutoCreateGaps(true);
        layCS.setAutoCreateContainerGaps(true);
        layCS.setHorizontalGroup(
                layCS.createSequentialGroup()
                        .addGroup(layCS.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(dataPaneDC)
                                        .addGroup(layCS.createSequentialGroup()
                                                .addComponent(FCS)
                                                .addComponent(dataPaneFCS))

                                        .addGroup(layCS.createSequentialGroup()
                                                .addComponent(ECS)
                                                .addComponent(dataPaneECS))
                                        .addGroup(layCS.createSequentialGroup()
                                                .addComponent(DamChamp)
                                                .addComponent(KillChamp)
                                                .addComponent(HealChamp))
                        )

        );
        layCS.setVerticalGroup(
                layCS.createSequentialGroup()
                        .addComponent(dataPaneDC)
                        .addGroup(layCS.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(FCS)
                                .addComponent(dataPaneFCS))
                        .addGroup(layCS.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(ECS)
                                .addComponent(dataPaneECS))
                        .addGroup(layCS.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(DamChamp)
                                .addComponent(KillChamp)
                                .addComponent(HealChamp))

        );
        System.out.println("Done loading Combat Statistics tab!");
        return CStats;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == about) {
            JOptionPane.showMessageDialog(CStats, Values.aboutText, "About", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public void updateStats() {
        KillV = 0;
        CStats.remove(dataPaneDC);
        CStats.remove(dataPaneFCS);
        CStats.remove(dataPaneECS);
        CStats.remove(DamChamp);
        CStats.remove(KillChamp);
        CStats.remove(HealChamp);
        CStats.setSize(dimX, dimY);
        CStats.setBackground(Color.WHITE);
        modelDC = new DefaultTableModel(FilingCombat.rowDataAS(), Values.overviewMainTableHeader) {
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
        dataDC.getColumnModel().getColumn(6).setCellRenderer(CenterRenderer);
        dataDC.getColumnModel().getColumn(7).setCellRenderer(CenterRenderer);
        dataDC.getTableHeader().setFont(tableHeading);
        dataDC.setSize(dimX, dimY);
        dataDC.setLocation(50, 50);
        dataDC.setFont(tableData);
        dataDC.setBackground(Color.WHITE);
        dataDC.setRowHeight(RowHeight);
        dataDC.setRowSorter(FilingCombat.BRSorter(dataDC));
        TableColumn column;
        for (int i = 0; i < 3; i++) {
            column = dataDC.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(300); //third column is bigger
            } else {
                column.setPreferredWidth(100);
            }
        }
        dataPaneDC = new JScrollPane(dataDC);
        dataPaneDC.setMaximumSize(new Dimension(dimX, 350));

        dataPaneDC.setLocation(25, 25);

        modelFCS = new DefaultTableModel(FilingCombat.getFriendComStats(), Values.comStatsTableHeader) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };
        dataFCS = new JTable(modelFCS);
        dataFCS.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        dataFCS.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        dataFCS.getColumnModel().getColumn(2).setCellRenderer(CenterRenderer);
        dataFCS.getTableHeader().setFont(tableHeading);
        dataFCS.setFont(tableData);
        dataFCS.setMinimumSize(new Dimension(dimX, 150));
        dataFCS.setBackground(Color.WHITE);
        dataFCS.setRowHeight(40);
        dataFCS.setAutoCreateRowSorter(true);
        dataPaneFCS = new JScrollPane(dataFCS);
        dataPaneFCS.setLocation(25, 25);
        dataPaneFCS.setMaximumSize(new Dimension(dimX, 65));
        CStats.add(dataPaneFCS);

        modelECS = new DefaultTableModel((Object[][]) FilingCombat.EnemyComStats, Values.comStatsTableHeader) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return Integer.class;
                    default:
                        return Integer.class;
                }
            }
        };
        dataECS = new JTable(modelECS);
        dataECS.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        dataECS.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        dataECS.getColumnModel().getColumn(2).setCellRenderer(CenterRenderer);
        dataECS.getTableHeader().setFont(tableHeading);
        dataECS.setFont(tableData);
        dataECS.setMinimumSize(new Dimension(dimX, 150));
        dataECS.setBackground(Color.WHITE);
        dataECS.setRowHeight(40);
        dataECS.setAutoCreateRowSorter(true);
        dataPaneECS = new JScrollPane(dataECS);
        dataPaneECS.setLocation(25, 25);
        dataPaneECS.setMaximumSize(new Dimension(dimX, 65));
        CStats.add(dataPaneECS);

        DamChamp = new JLabel("Damage Champion: " + FilingCombat.comStatsDamChamp());
        DamChamp.setFont(ChampTableContent);
        DamChamp.setMaximumSize(new Dimension(200, 25));
        CStats.add(DamChamp);

        KillChamp = new JLabel("     Kill Champion: " + FilingCombat.comStatsKillChamp());
        KillChamp.setFont(ChampTableContent);
        KillChamp.setMaximumSize(new Dimension(200, 25));
        CStats.add(KillChamp);

        HealChamp = new JLabel("     Healing Champion: " + FilingCombat.comStatsHealingChamp());
        HealChamp.setFont(ChampTableContent);
        HealChamp.setMaximumSize(new Dimension(200, 25));
        CStats.add(HealChamp);

        layCS = new GroupLayout(CStats);
        CStats.setLayout(layCS);
        layCS.setAutoCreateGaps(true);
        layCS.setAutoCreateContainerGaps(true);
        layCS.setHorizontalGroup(
                layCS.createSequentialGroup()
                        .addGroup(layCS.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(dataPaneDC)
                                        .addGroup(layCS.createSequentialGroup()
                                                .addComponent(FCS)
                                                .addComponent(dataPaneFCS))

                                        .addGroup(layCS.createSequentialGroup()
                                                .addComponent(ECS)
                                                .addComponent(dataPaneECS))
                                        .addGroup(layCS.createSequentialGroup()
                                                .addComponent(DamChamp)
                                                .addComponent(KillChamp)
                                                .addComponent(HealChamp))
                        )

        );
        layCS.setVerticalGroup(
                layCS.createSequentialGroup()
                        .addComponent(dataPaneDC)
                        .addGroup(layCS.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(FCS)
                                .addComponent(dataPaneFCS))
                        .addGroup(layCS.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(ECS)
                                .addComponent(dataPaneECS))
                        .addGroup(layCS.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(DamChamp)
                                .addComponent(KillChamp)
                                .addComponent(HealChamp))

        );
        CStats.add(dataPaneDC);
        CStats.revalidate();
        CStats.repaint();
        System.out.println("Done updating Combat Statistics tab!");
    }

}