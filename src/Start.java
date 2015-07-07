import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by J.D. Isenhart on 4/5/2015
 * 5:54 PM
 */
public class Start {

    static Path saveFilePath = Paths.get(System.getProperty("user.home"));//Example
    static String version = "1.4";
    static String aboutText = "Dungeons & Dragons Combat Companion" + "\n" + "Created by JD Isenhart" + "\n" + "Larkspur, Colorado" + "\n" + "Version " + version;

    public static void main(String[] args) {
        System.out.println("Isenhart D&D Combat Companion v" + version);
        System.out.println(saveFilePath);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUIMenu.PaneFrameMain.createGUI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void startMain() {
        System.out.println(String.valueOf(saveFilePath));
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                        if ("Windows Vista".equals(info.getName())) {
                            UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                } catch (Exception e) {
                    // If Nimbus is not available, you can set the GUI to another look and feel.
                }
                try {
                    GUIFrame.PaneFrameMain.createGUI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setSaveFilePath(Path newPath) {
        saveFilePath = newPath;
    }

}