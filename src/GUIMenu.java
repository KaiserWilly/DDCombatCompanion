import javax.imageio.ImageIO;
        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.*;
        import java.awt.image.BufferedImage;
        import java.io.IOException;
        import java.nio.file.Path;
        import java.nio.file.Paths;

/**
 * Created by J.D. Isenhart on 6/2/2015
 * 9:59 AM
 */
public class GUIMenu extends Component {

    public static JFrame frame;
    public static JPanel base;
    public static JButton openNew, openExisting;
    public static JLabel panelIcon;
    public static JFileChooser openSave, newSave;

    public static class PaneFrameMain implements ActionListener, ItemListener {

        public static void createGUI() throws IOException {
            GUIMenu icon = new GUIMenu();
            frame = new JFrame("Isenhart Combat Companion v." + Start.version);
            frame.setMinimumSize(new Dimension(400, 600));
            frame.setMaximumSize(new Dimension(400, 600));
            JFrame.setDefaultLookAndFeelDecorated(false);
            PaneFrameMain index = new PaneFrameMain();
            frame.getContentPane().add(index.paneContent());
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setBackground(new Color(140, 140, 140));
            frame.setIconImage(icon.getFrameIcon());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

        public JPanel paneContent() throws IOException {
            base = new JPanel();
            base.setBackground(new Color(140, 140, 140));
            base.setLayout(null);
            base.setSize(400, 600);

            BufferedImage pb = null;
            try {
                pb = ImageIO.read(getClass().getResource("rsc\\IsenhartCC.png"));
                panelIcon = new JLabel(new ImageIcon(pb));
                panelIcon.setLocation(100, 50);
                panelIcon.setSize(200, 150);
                panelIcon.setHorizontalAlignment(SwingConstants.CENTER);
                panelIcon.setVerticalAlignment(SwingConstants.CENTER);
                base.add(panelIcon);
            } catch (IOException e) {
                System.out.println("Can't load image");
            } catch (IllegalArgumentException u) {
                System.out.println("We can't find the picture! C");
            }
            assert pb != null;


            openExisting = new JButton("Open");
            openExisting.addActionListener(this);
            openExisting.setSize(200, 50);
            openExisting.setLocation(100, 225);
            openExisting.setSelected(false);
            openExisting.setBorder(BorderFactory.createLineBorder(new Color(92, 92, 92), 2));
            base.add(openExisting);

            openNew = new JButton("New");
            openNew.addActionListener(this);
            openNew.setSize(200, 50);
            openNew.setLocation(100, 325);
            openNew.setSelected(false);
            openNew.setBorder(BorderFactory.createLineBorder(new Color(92, 92, 92), 2));
            base.add(openNew);


            return base;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Path saveLocat = null;
            if (e.getSource() == openExisting) {
                openSave = new JFileChooser(Start.saveFilePath.toString());
                openSave.addChoosableFileFilter(new FilingCombat.AcceptFile());
                openSave.setAcceptAllFileFilterUsed(false);
                int returnVal = openSave.showDialog(base, "Open Existing Save");
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    saveLocat = Paths.get(String.valueOf(openSave.getSelectedFile()));
                }
                if (saveLocat != null) {
                    Start.setSaveFilePath(saveLocat);
                    Start.startMain();
                    frame.setVisible(false); //you can't see me!
                    frame.dispose(); //Destroy the JFrame object
                }

            } else {
                newSave = new JFileChooser(Start.saveFilePath.toString());
                newSave.addChoosableFileFilter(new FilingCombat.AcceptFile());
                newSave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                newSave.setAcceptAllFileFilterUsed(false);
                int returnVal = newSave.showDialog(base, "Create new Save");
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String name = (String) JOptionPane.showInputDialog(frame, "Enter the name of the New Game", "New Game", JOptionPane.PLAIN_MESSAGE, null, null, null);
                    if (name.length() != 0) {
                        saveLocat = Paths.get(String.valueOf(newSave.getSelectedFile()) + "\\" + name + ".ADDCC");
                    } else {
                        saveLocat = null;
                    }

                }
                if (saveLocat != null) {
                    Start.setSaveFilePath(saveLocat);
                    FilingMain.createSave();
                    Start.startMain();
                    frame.setVisible(false); //you can't see me!
                    frame.dispose(); //Destroy the JFrame object
                }
            }
        }

        @Override
        public void itemStateChanged(ItemEvent e) {

        }
    }

    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = GUIMenu.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public Image getFrameIcon() {
        BufferedImage fIcon;
        try {
            fIcon = ImageIO.read(getClass().getResource("rsc/Icon1616.png"));
        } catch (Exception e) {
            System.err.println("Can't load Frame Icon");
            return null;
        }
        return fIcon;
    }
}
