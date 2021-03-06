import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
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
    public static JLabel panelIcon, lineR, lineL;
    public static GroupLayout layFrame;
    static GUICombat comStats = new GUICombat();
    static GUIParty partyStats = new GUIParty();
    static GUIControl modStats = new GUIControl();
    static GUIIntiative init = new GUIIntiative();
    static GUILoot loot = new GUILoot();
    static FilingMain fMain = new FilingMain();
    static GUISpell spell = new GUISpell();
    static GUIXP xp = new GUIXP();

    public static class PaneFrameMain implements ChangeListener {


        public static void createGUI() throws IOException {
            GUIMenu icon = new GUIMenu();
            mainFrame = new JFrame("Isenhart D&D Combat Companion v" + Values.version);
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
            base.setBackground(new Color(245, 243, 238));
            cStatistics = comStats.DCount();
            pStatistics = partyStats.PartyStats();
            tPane = new JTabbedPane();
            tPane.addTab("Overview", null, modStats.controlPanel(), "Use this table to control stats during Combat");
            tPane.addTab("Combat Statistics", null, cStatistics, "Combat Statistics");
            tPane.addTab("Party Statistics", null, pStatistics, "Party Statistics");
            tPane.addTab("Loot", null, loot.lootPanel(), "Track your booty");
            tPane.addTab("Investments", null, new JPanel(), "Track Investments, property, and off-quest items");
//            tPane.addTab("Spells", null, fMain.showBlankPane()/*spell.SpellDisplay()*/, "Track your Spells!");
            tPane.addTab("XP", null, xp.XPpanel(), "XP and Leveling");
            tPane.addChangeListener(this);
            base.add(tPane);

            BufferedImage pb = null;
            try {
                pb = ImageIO.read(getClass().getResource("rsc/IsenhartCC.png"));
                panelIcon = new JLabel(new ImageIcon(pb));
                panelIcon.setHorizontalAlignment(SwingConstants.CENTER);
                panelIcon.setVerticalAlignment(SwingConstants.CENTER);
                base.add(panelIcon);
            } catch (IOException e) {
                System.out.println("Can't load image");
            } catch (IllegalArgumentException u) {
                System.out.println("We can't find the picture! Icon");
            }
            assert pb != null;

            try {
                pb = ImageIO.read(getClass().getResource("rsc/lineR.png"));
                lineR = new JLabel(new ImageIcon(pb));
                lineR.setHorizontalAlignment(SwingConstants.CENTER);
                lineR.setVerticalAlignment(SwingConstants.CENTER);
                base.add(lineR);
            } catch (IOException e) {
                System.out.println("Can't load image");
            } catch (IllegalArgumentException u) {
                System.out.println("We can't find the picture! LineR");
            }
            assert pb != null;

            try {
                pb = ImageIO.read(getClass().getResource("rsc/lineL.png"));
                lineL = new JLabel(new ImageIcon(pb));
                lineL.setHorizontalAlignment(SwingConstants.CENTER);
                lineL.setVerticalAlignment(SwingConstants.CENTER);
                base.add(lineL);
            } catch (IOException e) {
                System.out.println("Can't load image");
            } catch (IllegalArgumentException u) {
                System.out.println("We can't find the picture! LineL");
            }
            assert pb != null;


            layFrame = new GroupLayout(base);
            base.setLayout(layFrame);
            layFrame.setAutoCreateGaps(true);
            layFrame.setAutoCreateContainerGaps(true);
            layFrame.setHorizontalGroup(
                    layFrame.createSequentialGroup()
                            .addGroup(layFrame.createParallelGroup(GroupLayout.Alignment.CENTER)
                                            .addComponent(tPane)
                                            .addGroup(layFrame.createSequentialGroup()
                                                            .addComponent(lineL)
                                                            .addComponent(panelIcon)
                                                            .addComponent(lineR)
                                            )
                            )

            );
            layFrame.setVerticalGroup(
                    layFrame.createSequentialGroup()

                            .addComponent(tPane)
                            .addGroup(layFrame.createParallelGroup(GroupLayout.Alignment.CENTER)
                                            .addComponent(panelIcon)
                                            .addComponent(lineR)
                                            .addComponent(lineL)
                            )
            );
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
                    System.out.println(" (Overview)");
                    mainFrame.setJMenuBar(comStats.getTheMenuBar());
                    mainFrame.revalidate();
                    mainFrame.repaint();
                    comStats.updateStats();
                    break;
                case 2:
                    System.out.println(" (Party Statistics)");
                    mainFrame.setJMenuBar(partyStats.pMenuBar());
                    mainFrame.revalidate();
                    mainFrame.repaint();
                    partyStats.UpdateStats();
                    break;
                case 3:
                    System.out.println(" (Loot)");
                    mainFrame.setJMenuBar(loot.lootMenuBar());
                    mainFrame.revalidate();
                    mainFrame.repaint();
                    loot.updateStats();
                    break;
                case 4:
                    System.out.println(" (Investments)");
//                    mainFrame.setJMenuBar(spell.getTheMenuBar());
//                    mainFrame.revalidate();
//                    mainFrame.repaint();
//                    spell.updateStats();
                    break;
                case 5:
                    System.out.println(" (XP)");
                    mainFrame.setJMenuBar(xp.XPMenuBar());
                    mainFrame.revalidate();
                    mainFrame.repaint();
                    xp.updateStats();
                    break;
                default:
                    mainFrame.setJMenuBar(fMain.defaultMenuBar());
                    mainFrame.revalidate();
                    mainFrame.repaint();
            }
        }
    }
}
