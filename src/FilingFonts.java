import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by JD Isenhart on 11/8/2015.
 */
public class FilingFonts {
    public static Font inputField = new Font("SansSerif", Font.PLAIN, 16);
    public static Font moduleHeading = new Font("Trebuchet MS", Font.PLAIN, 20);
    public static Font inputHeading = new Font("Trebuchet MS", Font.PLAIN, 16);
    public static Font tableDataS12 = new Font("Helvetica", Font.BOLD, 12);
    public static Font tableDataS22 = new Font("Franklin Gothic Medium", Font.BOLD, 22);
    public static Font tableDataS24 = new Font("Franklin Gothic Medium", Font.BOLD, 24);
    public static Font tableHeading = new Font("Garamond", Font.BOLD, 14);
    public static Font playerSelection = new Font("Verdana", Font.PLAIN, 16);
    public static Font titleFont = new Font("Calibri", Font.BOLD, 20);


    public static void loadFonts() {
        try {
            Font tableHeader = Font.createFont(Font.TRUETYPE_FONT, new File("src/rsc/tableHeader.ttf")).deriveFont(14f);
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/rsc/tableHeader.ttf")));
            tableHeading = tableHeader;
        } catch (IOException | FontFormatException e) {
            System.out.println("Can't load Table Header Font!");
        }
    }
}
