import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by J.D. Isenhart on 4/5/2015
 * 5:54 PM
 */
public class Start {

    static Path saveFilePath = Paths.get(System.getProperty("user.home"));//Example

    public static void main(String[] args) {
        FilingFonts.loadFonts();
        System.out.println("Isenhart D&D Combat Companion v" + Values.version);
        System.out.println(saveFilePath);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUIMenu.PaneFrameMain.createGUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void startMain() {
        System.out.println(String.valueOf(saveFilePath));
        FilingMain.runConversion();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                try {
                    GUIFrame.PaneFrameMain.createGUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setSaveFilePath(Path newPath) {
        saveFilePath = newPath;
    }

}