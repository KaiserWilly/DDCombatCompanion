/**
 * Created by J.D. Isenhart on 6/17/2015
 * 5:31 PM
 */
public class Values {
    public static String version = "0.7";
    public static String aboutText = "Dungeons & Dragons Combat Companion (3.5e)" + "\n" + "Created by JD Isenhart" + "\n" + "\n" + "Version " + version;

    //Table Headers
    public static Object[] overviewMainTableHeader = new Object[]{"PLAYER", "BR", "DAMAGE DONE", "KILLS", "HEALING DONE", "FRIENDLY FIRE", "HEALTH", "MAX HEALTH"};
    public static Object[] comStatsTableHeader = new Object[]{"DAMAGE DONE", "KILLS", "HEALING DONE"};
    public static Object[] initTableHeader = new Object[]{"PLAYER", "INITIATIVE ROLL"};
    public static Object[] lootTableHeader = new Object[]{"ITEM", "ITEM VALUE (GP)", "QUANTITY"};



    public Object[][] getDefaultXPLevels() {
        Object[][] levels = new Object[][]{
                {"2", 1000},
                {"3", 3000},
                {"4", 6000},
                {"5", 10000},
                {"6", 15000},
                {"7", 21000},
                {"8", 28000},
                {"9", 36000},
                {"10", 45000},
                {"11", 55000},
                {"12", 66000},
                {"13", 78000},
                {"14", 91000},
                {"15", 105000},
                {"16", 120000},
                {"17", 136000},
                {"18", 153000},
                {"19", 171000},
                {"20", 190000},
        };
        return levels;
    }
}
