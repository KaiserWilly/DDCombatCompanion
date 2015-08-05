package GUIControlDialogs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by JD on 8/4/2015.
 */
public class FilingGUIControlDialogs {
    public static void openDialog(String button){
        SwingUtilities.invokeLater(() -> {

            try {
                GUIControlDialogsFrame.PaneFrameMain.createGUI(button);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    public Image getFrameIcon() {
        BufferedImage fIcon;
        try {
            fIcon = ImageIO.read(getClass().getResource("rsc/FIcon.png"));
        } catch (Exception e) {
            System.err.println("Can't load Frame Icon");
            return null;
        }
        return fIcon;
    }
}
