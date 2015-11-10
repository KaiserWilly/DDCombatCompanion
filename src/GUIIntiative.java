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
    static Font tableData = new Font("Helvetica", Font.BOLD, 12);
    static Font tableHeading = new Font("Garamond", Font.BOLD, 14);
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

    public void setFonts() {
        tableHeading = FilingFonts.tableHeading;
        tableData = FilingFonts.tableDataS24;
    }

    public JPanel InitiativePanel() {
        setFonts();
        base = new JPanel();
        base.setBackground(Color.WHITE);
        base.setMaximumSize(new Dimension(200, 800));

        initData = new DefaultTableModel(FilingInitiative.InitRowData(), FilingInitiative.InitColumnHeaders()) {
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
        initTable.getTableHeader().setFont(tableHeading);
        initTable.setFont(tableData);
        initTable.setMinimumSize(new Dimension(dimX, 50));
        initTable.setRowHeight(30);
        initTable.setAutoCreateRowSorter(false);
        initTable.setRowSorter(FilingInitiative.RowSorter(initTable));
        initPane = new JScrollPane(initTable);
        initPane.setMaximumSize(new Dimension(dimX, 460));
        initPane.setLocation(0, 0);
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
        layInit.setAutoCreateContainerGaps(false);
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
        System.out.println("Done loading Initiative Widget!");
        return base;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == about) {
            JOptionPane.showMessageDialog(base, Values.aboutText, "About", JOptionPane.PLAIN_MESSAGE);
        }
        if (e.getSource() == genInitiative || e.getSource() == resetTable) {
            if (e.getSource() == resetTable) {
                FilingInitiative.resetInit();
            } else {
                FilingInitiative.genInit(initTable);
            }
            base.remove(initPane);
            initData = new DefaultTableModel(FilingInitiative.InitRowData(), FilingInitiative.InitColumnHeaders()) {
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
            initTable.getTableHeader().setFont(tableHeading);
            initTable.setFont(tableData);
            initTable.setMinimumSize(new Dimension(dimX, 50));
            initTable.setRowHeight(30);
            initTable.setAutoCreateRowSorter(false);
            initTable.setRowSorter(FilingInitiative.RowSorter(initTable));
            initPane = new JScrollPane(initTable);
            initPane.setMaximumSize(new Dimension(dimX, 460));
            initPane.setLocation(0, 0);
            base.add(initPane);

            layInit = new GroupLayout(base);
            base.setLayout(layInit);
            layInit.setAutoCreateGaps(true);
            layInit.setAutoCreateContainerGaps(false);
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
        System.out.println("Done updating Initiative Widget!");
    }
}
