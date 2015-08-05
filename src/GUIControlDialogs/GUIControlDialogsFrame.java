package GUIControlDialogs;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by JD on 8/4/2015.
 */
public class GUIControlDialogsFrame {
    static FilingGUIControlDialogs icon = new FilingGUIControlDialogs();
    static JFrame dialogFrame;
    static JPanel base;
    static String Cbutton;

    public static class PaneFrameMain {
        public static void createGUI(String button) throws IOException {
            Cbutton = button;

            dialogFrame = new JFrame("Isenhart D&D Combat Companion");
            dialogFrame.setMaximumSize(new Dimension(1366, 700));
            JFrame.setDefaultLookAndFeelDecorated(false);
            PaneFrameMain index = new PaneFrameMain();
            dialogFrame.setIconImage(icon.getFrameIcon());
            dialogFrame.getContentPane().add(index.paneContent());
            dialogFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            dialogFrame.pack();
            dialogFrame.setLocationRelativeTo(null);
            dialogFrame.setVisible(true);

        }

        public JPanel paneContent() throws IOException {
            switch (Cbutton) {
                case "Damage":
                    base = GUIControlDialogDamage.damageDialog();
                case "Healing":
                    GUIControlDialogHealing.damageDialog();
            }
            return base;
        }
    }

}
