import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by J.D. Isenhart on 6/8/2015
 * 12:41 AM
 */
public class GUIParty implements ActionListener {
    public static int dimX = 1366, dimY = 700;
    static Font PSPartyContent = new Font("Trebuchet MS", Font.PLAIN, 20);
    public JPanel PStats;
    public JMenuBar pStatsMenuBar;
    public JMenu aboutMenu;
    public JMenuItem about;
    public DefaultTableModel psMain, psHits, psAvg;
    public JTable dataPsMain, dataPSHits, dataPSAvg;
    public JScrollPane dataPSPane, psHitsPane, psAvgPane;
    public GroupLayout layPS;
    public JLabel hitPercentage, avgDam;
    public Font PSCellContent = new Font("Franklin Gothic Medium", Font.BOLD, 30);

    public JMenuBar pMenuBar() {
        pStatsMenuBar = new JMenuBar();
        aboutMenu = new JMenu("About");
        about = new JMenuItem("About the Program");
        about.addActionListener(this);
        aboutMenu.add(about);
        pStatsMenuBar.add(aboutMenu);
        return pStatsMenuBar;
    }

    public JPanel PartyStats() {
        PStats = new JPanel();
        PStats.setSize(dimX, dimY);
        PStats.setBackground(Color.WHITE);

        hitPercentage = new JLabel("Hit Percentage:   ");
        hitPercentage.setFont(PSPartyContent);
        PStats.add(hitPercentage);

        avgDam = new JLabel("Average Damage:");
        avgDam.setFont(PSPartyContent);
        PStats.add(avgDam);

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
        DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
        CenterRenderer.setHorizontalAlignment(JLabel.CENTER);
        dataPsMain.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        dataPsMain.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        dataPsMain.setRowHeight(35);
        dataPsMain.setFont(PSCellContent);
        dataPsMain.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        dataPSPane = new JScrollPane(dataPsMain);
        dataPSPane.setMaximumSize(new Dimension(dimX, 250));
        PStats.add(dataPSPane);


        psHits = new DefaultTableModel(FilingParty.hitPerRowData(), FilingParty.hitPerColumnData());
        dataPSHits = new JTable(psHits);
        dataPSHits.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        dataPSHits.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        dataPSHits.getColumnModel().getColumn(2).setCellRenderer(CenterRenderer);
        dataPSHits.setRowHeight(50);
        dataPSHits.setFont(PSCellContent);
        dataPSHits.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        psHitsPane = new JScrollPane(dataPSHits);
        psHitsPane.setMaximumSize(new Dimension(500, 75));
        PStats.add(psHitsPane);

        psAvg = new DefaultTableModel(FilingParty.avgDamRowData(), FilingParty.avgDamColumnData());
        dataPSAvg = new JTable(psAvg);
        dataPSAvg.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        dataPSAvg.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        dataPSAvg.getColumnModel().getColumn(2).setCellRenderer(CenterRenderer);
        dataPSAvg.setRowHeight(50);
        dataPSAvg.setFont(PSCellContent);
        dataPSAvg.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        psAvgPane = new JScrollPane(dataPSAvg);
        psAvgPane.setMaximumSize(new Dimension(500, 75));
        PStats.add(psAvgPane);


        layPS = new GroupLayout(PStats);
        PStats.setLayout(layPS);
        layPS.setAutoCreateGaps(true);
        layPS.setAutoCreateContainerGaps(true);
        layPS.setHorizontalGroup(
                layPS.createSequentialGroup()
                        .addGroup(layPS.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(dataPSPane)
                                        .addGroup(layPS.createSequentialGroup()
                                                .addComponent(hitPercentage)
                                                .addComponent(psHitsPane))
                                        .addGroup(layPS.createSequentialGroup()
                                                .addComponent(avgDam)
                                                .addComponent(psAvgPane))
                        )

        );
        layPS.setVerticalGroup(
                layPS.createSequentialGroup()

                        .addComponent(dataPSPane)
                        .addGroup(layPS.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(hitPercentage)
                                .addComponent(psHitsPane))
                        .addGroup(layPS.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(avgDam)
                                .addComponent(psAvgPane))
        );
        System.out.println("Done loading Party Statistics Tab!");
        return PStats;
    }

    public void UpdateStats() {
        PStats.remove(dataPSPane);
        PStats.remove(psHitsPane);
        PStats.remove(psAvgPane);

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
        DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
        CenterRenderer.setHorizontalAlignment(JLabel.CENTER);
        dataPsMain.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        dataPsMain.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        dataPsMain.setRowHeight(35);
        dataPsMain.setFont(PSCellContent);
        dataPsMain.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        dataPSPane = new JScrollPane(dataPsMain);
        dataPSPane.setMaximumSize(new Dimension(dimX, 250));
        PStats.add(dataPSPane);


        psHits = new DefaultTableModel(FilingParty.hitPerRowData(), FilingParty.hitPerColumnData());
        dataPSHits = new JTable(psHits);
        dataPSHits.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        dataPSHits.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        dataPSHits.getColumnModel().getColumn(2).setCellRenderer(CenterRenderer);
        dataPSHits.setRowHeight(50);
        dataPSHits.setFont(PSCellContent);
        dataPSHits.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        psHitsPane = new JScrollPane(dataPSHits);
        psHitsPane.setMaximumSize(new Dimension(500, 75));
        PStats.add(psHitsPane);

        psAvg = new DefaultTableModel(FilingParty.avgDamRowData(), FilingParty.avgDamColumnData());
        dataPSAvg = new JTable(psAvg);
        dataPSAvg.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        dataPSAvg.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        dataPSAvg.getColumnModel().getColumn(2).setCellRenderer(CenterRenderer);
        dataPSAvg.setRowHeight(50);
        dataPSAvg.setFont(PSCellContent);
        dataPSAvg.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        psAvgPane = new JScrollPane(dataPSAvg);
        psAvgPane.setMaximumSize(new Dimension(500, 75));
        PStats.add(psAvgPane);


        layPS = new GroupLayout(PStats);
        PStats.setLayout(layPS);
        layPS.setAutoCreateGaps(true);
        layPS.setAutoCreateContainerGaps(true);
        layPS.setHorizontalGroup(
                layPS.createSequentialGroup()
                        .addGroup(layPS.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(dataPSPane)
                                        .addGroup(layPS.createSequentialGroup()
                                                .addComponent(hitPercentage)
                                                .addComponent(psHitsPane))
                                        .addGroup(layPS.createSequentialGroup()
                                                .addComponent(avgDam)
                                                .addComponent(psAvgPane))
                        )

        );
        layPS.setVerticalGroup(
                layPS.createSequentialGroup()

                        .addComponent(dataPSPane)
                        .addGroup(layPS.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(hitPercentage)
                                .addComponent(psHitsPane))
                        .addGroup(layPS.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(avgDam)
                                .addComponent(psAvgPane))
        );
        PStats.revalidate();
        PStats.repaint();
        System.out.println("Done updating Party Statistics Tab!");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == about){
            JOptionPane.showMessageDialog(PStats, Start.aboutText, "About", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
