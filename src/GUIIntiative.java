import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by J.D. Isenhart on 6/11/2015
 * 12:48 AM
 */
public class GUIIntiative implements ActionListener {
    public static Font initCellContent = new Font("Franklin Gothic Medium", Font.BOLD, 30);
    public int dimX = 1366, dimY = 700;
    public JPanel base;
    public JButton genInitiative, resetTable;
    public DefaultTableModel initData;
    public JTable initTable;
    public JScrollPane initPane;
    public GroupLayout layInit;
    public JMenuItem about;
    public JMenuBar initMenu;
    public JMenu aboutMenu;

    public JMenuBar initMenuBar() {

        initMenu = new JMenuBar();
        aboutMenu = new JMenu("About");
        aboutMenu.addActionListener(this);
        about = new JMenuItem("About the Program");
        about.addActionListener(this);
        aboutMenu.add(about);
        initMenu.add(aboutMenu);
        return initMenu;
    }

    public JPanel InitiativePanel() {
        base = new JPanel();
        base.setBackground(Color.WHITE);
        base.setSize(dimX, dimY);

        initData = new DefaultTableModel(FilingInit.InitRowData(), FilingInit.InitColumnHeaders()) {
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
        initTable = new JTable(initData);
        DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
        CenterRenderer.setHorizontalAlignment(JLabel.CENTER);
        initTable.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        initTable.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        initTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        initTable.setFont(initCellContent);
        initTable.setMinimumSize(new Dimension(dimX, 50));
        initTable.setAutoCreateRowSorter(true);
        initTable.setRowHeight(35);
        initPane = new JScrollPane(initTable);
        initPane.setMaximumSize(new Dimension(dimX, 500));
        base.add(initPane);

        genInitiative = new JButton("Generate Initiative");
        genInitiative.setMaximumSize(new Dimension(100, 25));
        genInitiative.addActionListener(this);
        base.add(genInitiative);

        resetTable = new JButton("Reset Initiative");
        resetTable.setMaximumSize(new Dimension(100, 25));
        resetTable.addActionListener(this);
        base.add(resetTable);

        layInit = new GroupLayout(base);
        base.setLayout(layInit);
        layInit.setAutoCreateGaps(true);
        layInit.setAutoCreateContainerGaps(true);
        layInit.setHorizontalGroup(
                layInit.createSequentialGroup()
                        .addGroup(layInit.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(initPane)
                                        .addGroup(layInit.createSequentialGroup()
                                                .addComponent(genInitiative)
                                                .addComponent(resetTable))

                        )


        );
        layInit.setVerticalGroup(
                layInit.createSequentialGroup()

                        .addComponent(initPane)
                        .addGroup(layInit.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(genInitiative)
                                .addComponent(resetTable))

        );
        System.out.println("Done loading Initiative tab!");
        return base;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == about) {
            JOptionPane.showMessageDialog(base, Start.aboutText, "About", JOptionPane.PLAIN_MESSAGE);
        }
        if (e.getSource() == genInitiative) {
            FilingInit.genInit(initTable);
            Object[][] tableData = new Object[initData.getRowCount()][2];
            for (int count = 0; count < initData.getRowCount(); count++) {
                tableData[count][1] = Integer.parseInt(initData.getValueAt(count, 1).toString());
                tableData[count][0] = initData.getValueAt(count, 0).toString();
            }
            base.remove(initPane);
            initData = new DefaultTableModel(FilingInit.InitRowData(), FilingInit.InitColumnHeaders()) {
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
            initTable = new JTable(initData) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
            CenterRenderer.setHorizontalAlignment(JLabel.CENTER);
            initTable.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
            initTable.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
            initTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
            initTable.setFont(initCellContent);
            initTable.setMinimumSize(new Dimension(dimX, 50));
            initTable.setAutoCreateRowSorter(false);
            initTable.setRowSorter(FilingInit.RowSorter(initTable));
            initTable.setRowHeight(30);
            initPane = new JScrollPane(initTable);
            initPane.setMaximumSize(new Dimension(dimX, 500));
            base.add(initPane);

            layInit = new GroupLayout(base);
            base.setLayout(layInit);
            layInit.setAutoCreateGaps(true);
            layInit.setAutoCreateContainerGaps(true);
            layInit.setHorizontalGroup(
                    layInit.createSequentialGroup()
                            .addGroup(layInit.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(initPane)
                                            .addGroup(layInit.createSequentialGroup()
                                                    .addComponent(genInitiative)
                                                    .addComponent(resetTable))

                            )


            );
            layInit.setVerticalGroup(
                    layInit.createSequentialGroup()

                            .addComponent(initPane)
                            .addGroup(layInit.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(genInitiative)
                                    .addComponent(resetTable))

            );
            base.revalidate();
            base.repaint();

        }
        if (e.getSource() == resetTable) {
            FilingInit.resetInit();
            base.remove(initPane);
            initData = new DefaultTableModel(FilingInit.InitRowData(), FilingInit.InitColumnHeaders()) {
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
            initTable = new JTable(initData);
            DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
            CenterRenderer.setHorizontalAlignment(JLabel.CENTER);
            initTable.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
            initTable.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
            initTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
            initTable.setFont(initCellContent);
            initTable.setMinimumSize(new Dimension(dimX, 50));
            initTable.setAutoCreateRowSorter(true);
            initTable.setRowHeight(30);
            initPane = new JScrollPane(initTable);
            initPane.setMaximumSize(new Dimension(dimX, 500));
            base.add(initPane);

            layInit = new GroupLayout(base);
            base.setLayout(layInit);
            layInit.setAutoCreateGaps(true);
            layInit.setAutoCreateContainerGaps(true);
            layInit.setHorizontalGroup(
                    layInit.createSequentialGroup()
                            .addGroup(layInit.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(initPane)
                                            .addGroup(layInit.createSequentialGroup()
                                                    .addComponent(genInitiative)
                                                    .addComponent(resetTable))

                            )


            );
            layInit.setVerticalGroup(
                    layInit.createSequentialGroup()

                            .addComponent(initPane)
                            .addGroup(layInit.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(genInitiative)
                                    .addComponent(resetTable))

            );
            base.revalidate();
            base.repaint();
        }
        System.out.println("Done updating Init Tab!");
    }

    public void loadInitTable() {
        base.remove(initPane);
        initData = new DefaultTableModel(FilingInit.InitRowData(), FilingInit.InitColumnHeaders()) {
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
        if (FilingInit.checkInitExist()) {
            initTable = new JTable(initData) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            initTable.setRowSorter(FilingInit.RowSorter(initTable));
        } else {
            initTable = new JTable(initData);
            initTable.setAutoCreateRowSorter(true);
        }
        DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
        CenterRenderer.setHorizontalAlignment(JLabel.CENTER);
        initTable.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        initTable.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        initTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        initTable.setFont(initCellContent);
        initTable.setMinimumSize(new Dimension(dimX, 50));
        initTable.setRowHeight(30);
        initPane = new JScrollPane(initTable);
        initPane.setMaximumSize(new Dimension(dimX, 500));
        base.add(initPane);

        layInit = new GroupLayout(base);
        base.setLayout(layInit);
        layInit.setAutoCreateGaps(true);
        layInit.setAutoCreateContainerGaps(true);
        layInit.setHorizontalGroup(
                layInit.createSequentialGroup()
                        .addGroup(layInit.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(initPane)
                                        .addGroup(layInit.createSequentialGroup()
                                                .addComponent(genInitiative)
                                                .addComponent(resetTable))

                        )


        );
        layInit.setVerticalGroup(
                layInit.createSequentialGroup()

                        .addComponent(initPane)
                        .addGroup(layInit.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(genInitiative)
                                .addComponent(resetTable))

        );
        base.revalidate();
        base.repaint();
        System.out.println("Done loading Init Tab!");
    }
}
