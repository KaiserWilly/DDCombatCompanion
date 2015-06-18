import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.IOException;

/**
 * Created by J.D. Isenhart on 4/5/2015
 * 5:56 PM
 */
public class GUIFrame {
    public static int dimX = 1366, dimY = 700;
    public static JPanel pStatistics, cStatistics, base;
    public static JFrame mainFrame;
    public static JTabbedPane tPane;
    static GUICombat comStats = new GUICombat();
    static GUIParty partyStats = new GUIParty();
    static GUIControl modStats = new GUIControl();
    static GUIIntiative init = new GUIIntiative();
    static GUILoot loot = new GUILoot();
    static GUISpell spell = new GUISpell();

    public static class PaneFrameMain implements ChangeListener {


        public static void createGUI() throws IOException {
            GUIMenu icon = new GUIMenu();
            mainFrame = new JFrame("Isenhart D&D Combat Companion v" + Start.version);
            mainFrame.setJMenuBar(modStats.getStatsMenuBar());
            mainFrame.setMaximumSize(new Dimension(1366, 700));
            JFrame.setDefaultLookAndFeelDecorated(false);
            PaneFrameMain index = new PaneFrameMain();
            mainFrame.setIconImage(icon.getFrameIcon());
            mainFrame.getContentPane().add(index.paneContent());
            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainFrame.pack();
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);

        }


        public JPanel paneContent() throws IOException {
            base = new JPanel();
            base.setPreferredSize(new Dimension(dimX, dimY));
            base.setMinimumSize(new Dimension(dimX, dimY));
            cStatistics = comStats.DCount();
            pStatistics = partyStats.PartyStats();
            tPane = new JTabbedPane();
            tPane.addTab("Combat Control", null, modStats.ModStatistics(), "Use this table to control stats during Combat");
            tPane.addTab("Combat Statistics", null, cStatistics, "Combat Statistics");
            tPane.addTab("Initiative", null, init.InitiativePanel(), "Generate your Initiative lists!");
            tPane.addTab("Party Statistics", null, pStatistics, "Party Statistics");
            tPane.addTab("Loot", null, loot.lootPanel(), "Track your loot!");
            tPane.addTab("Spells", null, spell.SpellDisplay(), "Track your Spells!");
            tPane.setSize(1366, 700);
            tPane.addChangeListener(this);
            base.add(tPane);
            return base;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            int tabIndex = tPane.getSelectedIndex();
            System.out.print("Selected Tab: " + tPane.getSelectedIndex());
            switch (tabIndex) {
                case 0:
                    System.out.println(" (Modify Statistics)");
                    mainFrame.setJMenuBar(modStats.getStatsMenuBar());
                    mainFrame.revalidate();
                    mainFrame.repaint();
                    modStats.updateStats();
                    break;
                case 1:
                    System.out.println(" (Combat Statistics)");
                    mainFrame.setJMenuBar(comStats.getTheMenuBar());
                    mainFrame.revalidate();
                    mainFrame.repaint();
                    comStats.updateStats();
                    break;
                case 2:
                    System.out.println(" (Initiative)");
                    mainFrame.setJMenuBar(init.initMenuBar());
                    mainFrame.revalidate();
                    mainFrame.repaint();
                    init.loadInitTable();
                case 3:
                    System.out.println(" (Party Statistics)");
                    mainFrame.setJMenuBar(partyStats.pMenuBar());
                    mainFrame.revalidate();
                    mainFrame.repaint();
                    partyStats.UpdateStats();
                    break;
                case 4:
                    System.out.println(" (Loot)");
                    mainFrame.setJMenuBar(loot.lootMenuBar());
                    mainFrame.revalidate();
                    mainFrame.repaint();
                    loot.updateStats();
                    break;
                case 5:
                    System.out.println(" (Spells)");
                    mainFrame.setJMenuBar(loot.lootMenuBar());
                    mainFrame.revalidate();
                    mainFrame.repaint();
                    spell.updateStats();
                    break;

            }
        }
    }
}
