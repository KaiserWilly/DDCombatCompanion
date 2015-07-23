import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by J.D. Isenhart on 7/7/2015
 * 2:00 PM
 */
public class GUIXP implements ActionListener {
    public static int dimX = 1366, dimY = 700, XPTRH = 35, levelTRH = 25;
    public static Font XPCellContent = new Font("Franklin Gothic Medium", Font.BOLD, 25);
    static JPanel baseXP;
    static DefaultTableModel levelTM, XPTM;
    static JTable levelT, XPT;
    static JScrollPane levelTPane, XPTPane;
    static GroupLayout layXP;
    public JMenuBar XPmenuB;
    public JMenu xpFile, xpAbout;
    public JMenuItem setXP, setLevel, about;
    public JLabel xpCount, xpReq;
    public Font heading = new Font("Trebuchet MS", Font.PLAIN, 20);

    public JMenuBar XPMenuBar() {
        XPmenuB = new JMenuBar();
        xpFile = new JMenu("File");
        setXP = new JMenuItem("Set Player XP");
        setLevel = new JMenuItem("Set Lvl Req XP");
        setXP.addActionListener(this);
        setLevel.addActionListener(this);
        xpFile.add(setXP);
        xpFile.add(setLevel);
        xpAbout = new JMenu("About");
        about = new JMenuItem("About the Program");
        about.addActionListener(this);
        xpAbout.add(about);
        XPmenuB.add(xpFile);
        XPmenuB.add(xpAbout);
        return XPmenuB;
    }

    public JPanel XPpanel() {
        baseXP = new JPanel();
        baseXP.setSize(dimX, dimY);
        baseXP.setBackground(Color.WHITE);

        XPTM = new DefaultTableModel(FilingXP.retXPTabData(), FilingXP.retXPColumnHeaders()) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return Integer.class;
                    default:
                        return Integer.class;
                }
            }
        };
        XPT = new JTable(XPTM);
        DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
        CenterRenderer.setHorizontalAlignment(JLabel.CENTER);
        XPT.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        XPT.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        XPT.getColumnModel().getColumn(2).setCellRenderer(CenterRenderer);
        XPT.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        XPT.setLocation(50, 50);
        XPT.setFont(XPCellContent);
        XPT.setBackground(Color.WHITE);
        XPT.setRowHeight(XPTRH);
        XPT.setAutoCreateRowSorter(true);
        TableColumn column;
//        for (int i = 0; i < XPT.getColumnCount(); i++) {
//            column = XPT.getColumnModel().getColumn(i);
//            if (i == 1) {
//                column.setPreferredWidth(300); //third column is bigger
//            } else {
//                column.setPreferredWidth(100);
//            }
//        }
        XPTPane = new JScrollPane(XPT);
        baseXP.add(XPTPane);

        levelTM = new DefaultTableModel(FilingXP.retLevelTableData(), FilingXP.retLevelColumnHeaders()) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return Integer.class;
                    default:
                        return Integer.class;
                }
            }
        };
        levelT = new JTable(levelTM);
        CenterRenderer.setHorizontalAlignment(JLabel.CENTER);
        levelT.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        levelT.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        levelT.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        levelT.setFont(XPCellContent);
        levelT.setBackground(Color.WHITE);
        levelT.setRowHeight(levelTRH);
        levelT.setAutoCreateRowSorter(true);
        for (int i = 0; i < levelT.getColumnCount(); i++) {
            column = levelT.getColumnModel().getColumn(i);
            if (i == 1) {
                column.setPreferredWidth(300); //third column is bigger
            } else {
                column.setPreferredWidth(100);
            }
        }
        levelTPane = new JScrollPane(levelT);
        baseXP.add(levelTPane);

        xpCount = new JLabel("-XP Gained-");
        xpCount.setFont(heading);
        baseXP.add(xpCount);

        xpReq = new JLabel("-XP Required-");
        xpReq.setFont(heading);
        baseXP.add(xpReq);


        layXP = new GroupLayout(baseXP);
        baseXP.setLayout(layXP);
        layXP.setAutoCreateGaps(true);
        layXP.setAutoCreateContainerGaps(true);
        layXP.setHorizontalGroup(
                layXP.createSequentialGroup()
                        .addGroup(layXP.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(xpCount)
                                        .addComponent(XPTPane)
                        )
                        .addGroup(layXP.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(xpReq)
                                        .addComponent(levelTPane)
                        )
        );
        layXP.setVerticalGroup(
                layXP.createSequentialGroup()
                        .addGroup(layXP.createParallelGroup()
                                        .addComponent(xpCount)
                                        .addComponent(xpReq)

                        )
                        .addGroup(layXP.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(XPTPane)
                                        .addComponent(levelTPane)
                        )
        );
        baseXP.add(XPTPane);
        System.out.println("Done loading XP Tab!");
        return baseXP;
    }

    public void updateStats() {
        baseXP.remove(XPTPane);
        baseXP.remove(levelTPane);
        XPTM = new DefaultTableModel(FilingXP.retXPTabData(), FilingXP.retXPColumnHeaders()) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return Integer.class;
                    default:
                        return Integer.class;
                }
            }
        };
        XPT = new JTable(XPTM);
        DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
        CenterRenderer.setHorizontalAlignment(JLabel.CENTER);
        XPT.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        XPT.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        XPT.getColumnModel().getColumn(2).setCellRenderer(CenterRenderer);
        XPT.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        XPT.setLocation(50, 50);
        XPT.setFont(XPCellContent);
        XPT.setBackground(Color.WHITE);
        XPT.setRowHeight(XPTRH);
        XPT.setAutoCreateRowSorter(true);
        TableColumn column;
//        for (int i = 0; i < XPT.getColumnCount(); i++) {
//            column = XPT.getColumnModel().getColumn(i);
//            if (i == 1) {
//                column.setPreferredWidth(300); //third column is bigger
//            } else {
//                column.setPreferredWidth(100);
//            }
//        }
        XPTPane = new JScrollPane(XPT);
        baseXP.add(XPTPane);

        levelTM = new DefaultTableModel(FilingXP.retLevelTableData(), FilingXP.retLevelColumnHeaders()) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return Integer.class;
                    default:
                        return Integer.class;
                }
            }
        };
        levelT = new JTable(levelTM);
        CenterRenderer.setHorizontalAlignment(JLabel.CENTER);
        levelT.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        levelT.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        levelT.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        levelT.setFont(XPCellContent);
        levelT.setBackground(Color.WHITE);
        levelT.setRowHeight(levelTRH);
        levelT.setAutoCreateRowSorter(true);
        for (int i = 0; i < levelT.getColumnCount(); i++) {
            column = levelT.getColumnModel().getColumn(i);
            if (i == 1) {
                column.setPreferredWidth(300); //third column is bigger
            } else {
                column.setPreferredWidth(100);
            }
        }
        levelTPane = new JScrollPane(levelT);
        baseXP.add(levelTPane);

        layXP.setAutoCreateGaps(true);
        layXP.setAutoCreateContainerGaps(true);
        layXP.setHorizontalGroup(
                layXP.createSequentialGroup()
                        .addGroup(layXP.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(xpCount)
                                        .addComponent(XPTPane)
                        )
                        .addGroup(layXP.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(xpReq)
                                        .addComponent(levelTPane)
                        )
        );
        layXP.setVerticalGroup(
                layXP.createSequentialGroup()
                        .addGroup(layXP.createParallelGroup()
                                        .addComponent(xpCount)
                                        .addComponent(xpReq)

                        )
                        .addGroup(layXP.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(XPTPane)
                                        .addComponent(levelTPane)
                        )
        );
        baseXP.revalidate();
        baseXP.repaint();
        System.out.println("Done updating XP tab!");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == about) {
            JOptionPane.showMessageDialog(about, Start.aboutText, "About", JOptionPane.PLAIN_MESSAGE);
        }
        if (e.getSource() == setXP) {
            String name = (String) JOptionPane.showInputDialog(baseXP, "Enter the name of the Player:", "Set Player's XP", JOptionPane.PLAIN_MESSAGE, null, FilingMain.getPlayerArrayNE(), null);
            if (name.length() != 0) {
                String xp = (String) JOptionPane.showInputDialog(baseXP, "Enter the XP Amount:", "Set Player's XP", JOptionPane.PLAIN_MESSAGE, null, null, null);
                xp = xp.replaceAll("[^0-9]", "");
                int XP;
                try {
                    XP = Integer.parseInt(xp);
                    FilingXP.updateXP(name, XP);
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(baseXP, "XP must be an Integer (Whole Number)", "Value Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        if (e.getSource() == setLevel) {
            String[] levels = new String[]{"Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7", "Level 8", "Level 9", "Level 10", "Level 11", "Level 12", "Level 13", "Level 14", "Level 15", "Level 16", "Level 17", "Level 18", "Level 19", "Level 20",};
            String level = (String) JOptionPane.showInputDialog(baseXP, "Enter the name of the Level:", "Set Level's Required XP", JOptionPane.PLAIN_MESSAGE, null, levels, null);
            if (level.length() != 0) {
                String xp = (String) JOptionPane.showInputDialog(baseXP, "Enter the XP required to get to the level:", "Set Level's Required XP", JOptionPane.PLAIN_MESSAGE, null, null, null);
                xp = xp.replaceAll("[^0-9]", "");
                int XP;
                try {
                    XP = Integer.parseInt(xp);
                    if (!FilingXP.updateLevel(level, XP)) {
                        JOptionPane.showMessageDialog(baseXP, "XP Requirements must be in numerical order ( Level 2 < Level 3 < Level 4, etc.)", "Value Error", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(baseXP, "XP must be an Integer (Whole Number)", "Value Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        updateStats();
    }
}
