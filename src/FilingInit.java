import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by J.D. Isenhart on 6/11/2015
 * 12:54 AM
 */
public class FilingInit {

    public static Object[][] InitRowData() {
        Object[][] rowData = new Object[FilingCombat.playerArray.length][2];
        for (int i = 0; i < FilingCombat.playerArray.length; i++) {
            rowData[i][0] = FilingCombat.playerArray[i];
            rowData[i][1] = 0;
        }
        return rowData;
    }

    public static Object[] InitColumnHeaders() {
        return new Object[]{"Player", "Initiative Roll"};
    }

    public static TableRowSorter RowSorter(JTable Table) {
        TableRowSorter sorter = new TableRowSorter(Table.getModel());
        List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
        int rowColumn = 1;
        sortKeys.add(new RowSorter.SortKey(rowColumn, SortOrder.DESCENDING));
        sorter.setSortable(0, false);
        sorter.setSortKeys(sortKeys);
        return sorter;
    }
}
