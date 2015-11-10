import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by J.D. Isenhart on 6/11/2015
 * 9:10 PM
 */
public class GUILoot implements ActionListener, DocumentListener {
    public static int dimX = 1366, dimY = 700;
    static Font tableData = new Font("Helvetica", Font.BOLD, 12);
    static Font tableHeading = new Font("Garamond", Font.BOLD, 14);
    public JPanel lootNote;
    public DefaultTableModel dataLoot;
    public JTable lootTable;
    public JScrollPane lootTablePane, notesPane;
    public JTextField lootItem, lootValue, lootQuantity, noteInput;
    public JTextArea noteField;
    public JButton updateLoot;
    public JLabel lootTab, lItem, lValue, lQty, noteF;
    public GroupLayout layLoot;
    public JMenuBar lootMenuBar;
    public JMenu lootFile, aboutMenu;
    public JMenuItem lootRemoveI, lootChangeV, noteRemove, lootChangeQ, about;

    public void setFonts() {
        tableHeading = FilingFonts.tableHeading;
        tableData = FilingFonts.tableDataS22;
    }

    public JMenuBar lootMenuBar() {
        lootMenuBar = new JMenuBar();
        lootFile = new JMenu("File");
        aboutMenu = new JMenu("About");
        aboutMenu.addActionListener(this);
        about = new JMenuItem("About the Program");
        about.addActionListener(this);
        aboutMenu.add(about);
        lootRemoveI = new JMenuItem("Remove Loot Item");
        lootRemoveI.addActionListener(this);
        lootChangeV = new JMenuItem("Change Loot Item Value");
        lootChangeV.addActionListener(this);
        lootChangeQ = new JMenuItem("Change Loot Item Qty.");
        lootChangeQ.addActionListener(this);
        noteRemove = new JMenuItem("Remove Note");
        noteRemove.addActionListener(this);
        lootFile.add(lootRemoveI);
        lootFile.add(lootChangeV);
        lootFile.add(lootChangeQ);
        lootFile.add(noteRemove);
        lootMenuBar.add(lootFile);
        lootMenuBar.add(aboutMenu);
        return lootMenuBar;
    }

    public JPanel lootPanel() {
        setFonts();
        lootNote = new JPanel();
        lootNote.setSize(new Dimension(dimX, dimY));
        lootNote.setBackground(Color.WHITE);


        dataLoot = new DefaultTableModel(FilingLoot.getLootTableData(), FilingLoot.getLootTableColumnHeaders()) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };
        lootTable = new JTable(dataLoot);
        DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
        CenterRenderer.setHorizontalAlignment(JLabel.CENTER);
        lootTable.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        lootTable.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        lootTable.getTableHeader().setFont(tableHeading);
        lootTable.setFont(tableData);
        lootTable.setBackground(Color.WHITE);
        lootTable.setRowHeight(22);
        lootTable.setMaximumSize(new Dimension(dimX, 200));
        lootTablePane = new JScrollPane(lootTable);
        lootTablePane.setSize(new Dimension(dimX, 200));
        lootNote.add(lootTablePane);

        lootItem = new JTextField();
        lootItem.setMaximumSize(new Dimension(250, 25));
        lootNote.add(lootItem);

        lootValue = new JTextField();
        lootValue.setMaximumSize(new Dimension(75, 25));
        lootNote.add(lootValue);

        lootQuantity = new JTextField();
        lootQuantity.setMaximumSize(new Dimension(75, 25));
        lootNote.add(lootQuantity);

        updateLoot = new JButton("Add Booty");
        updateLoot.addActionListener(this);
        lootNote.add(updateLoot);

        noteInput = new JTextField();
        noteInput.getDocument().addDocumentListener(this);
        noteInput.setColumns(50);
        noteInput.addActionListener(this);
        noteInput.setMaximumSize(new Dimension(dimX, 25));
        lootNote.add(noteInput);

        noteField = new JTextArea(25, 25);
        noteField.setText(FilingLoot.getLootNotes());
        noteField.setEditable(false);
        notesPane = new JScrollPane(noteField);
        notesPane.setSize(new Dimension(dimX, 125));
        lootNote.add(notesPane);

        lootTab = new JLabel("Party Loot:");
        lootNote.add(lootTab);

        lItem = new JLabel("Item:");
        lootNote.add(lItem);

        lValue = new JLabel("Value: (GP)");
        lootNote.add(lValue);

        lQty = new JLabel("Qty:");
        lootNote.add(lQty);

        noteF = new JLabel("Notes on Loot:");
        lootNote.add(noteF);

        layLoot = new GroupLayout(lootNote);
        lootNote.setLayout(layLoot);
        layLoot.setAutoCreateGaps(true);
        layLoot.setAutoCreateContainerGaps(true);
        layLoot.setHorizontalGroup(
                layLoot.createSequentialGroup()
                        .addGroup(layLoot.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(lootTab)
                                        .addGroup(layLoot.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                        .addComponent(lootTablePane)
                                                        .addGroup(layLoot.createSequentialGroup()
                                                                .addGroup(layLoot.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                                        .addComponent(lItem)
                                                                        .addComponent(lootItem))
                                                                .addGroup(layLoot.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                                        .addComponent(lValue)
                                                                        .addComponent(lootValue))
                                                                .addGroup(layLoot.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                                        .addComponent(lQty)
                                                                        .addComponent(lootQuantity))
                                                                .addComponent(updateLoot))
                                        )
                                        .addComponent(noteF)
                                        .addGroup(layLoot.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                        .addComponent(noteInput)
                                                        .addComponent(notesPane)

                                        )
                        )
        );
        layLoot.setVerticalGroup(
                layLoot.createSequentialGroup()
                        .addComponent(lootTab)
                        .addComponent(lootTablePane)
                        .addGroup(layLoot.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lItem)
                                .addComponent(lValue)
                                .addComponent(lQty))
                        .addGroup(layLoot.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lootItem)
                                        .addComponent(lootValue)
                                        .addComponent(lootQuantity)
                                        .addComponent(updateLoot)
                        )
                        .addComponent(noteF)
                        .addComponent(noteInput)
                        .addComponent(notesPane)

        );

        System.out.println("Done loading loot Tab!");
        return lootNote;

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == about) {
            JOptionPane.showMessageDialog(lootNote, Values.aboutText, "About", JOptionPane.PLAIN_MESSAGE);
        }
        if (e.getSource() == lootRemoveI) {
            String name = (String) JOptionPane.showInputDialog(lootNote, "Enter the name of the Item to Remove:", "Remove an Item", JOptionPane.PLAIN_MESSAGE, null, FilingLoot.getLootItemArray(), null);
            if (name.length() != 0) {
                FilingLoot.removeLootTableItem(name);
            }
        }
        if (e.getSource() == updateLoot) {
            String item = lootItem.getText();
            String value = lootValue.getText();
            int quantity;
            try {
                quantity = Integer.parseInt(lootQuantity.getText());
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(lootNote, "Quantity must be a positive number", "Value Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    lootItem.setText("");
                    lootValue.setText("");
                    lootQuantity.setText("");
                }
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(lootNote, "The Quantity must be an Integer", "Quantity Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (item.length() != 0 && value.length() != 0 && quantity > 0) {
                FilingLoot.updateLootTable(item, value, quantity);
            } else if (item.length() != 0 && value.length() == 0) {
                JOptionPane.showMessageDialog(lootNote, "All Objects must have a Value (Can be ?)", "Value Error", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (e.getSource() == noteInput) {
            String newNote = noteInput.getText();
            noteInput.setText("");
            FilingLoot.updateLootNotes(newNote);
        }
        if (e.getSource() == noteRemove) {
            String name = (String) JOptionPane.showInputDialog(lootNote, "Select Note to Remove:", "Remove a Note", JOptionPane.PLAIN_MESSAGE, null, FilingLoot.getNoteArray(), null);
            if (name.length() != 0 || name == null) {
                FilingLoot.removeNote(name);
            }
        }
        if (e.getSource() == lootChangeV) {
            String item = (String) JOptionPane.showInputDialog(lootNote, "Select an Item to Change:", "Change a Value", JOptionPane.PLAIN_MESSAGE, null, FilingLoot.getLootItemArray(), null);
            if (item.length() != 0) {
                String value = (String) JOptionPane.showInputDialog(lootNote, "Specify a Value:", "Change a Value", JOptionPane.PLAIN_MESSAGE, null, null, null);
                if (value.length() != 0) {
                    FilingLoot.updateLootItemValue(item, value);
                }
            }
        }
        if (e.getSource() == lootChangeQ) {
            String item = (String) JOptionPane.showInputDialog(lootNote, "Select an Item to Change:", "Change a Quantity", JOptionPane.PLAIN_MESSAGE, null, FilingLoot.getLootItemArray(), null);
            if (item.length() != 0) {
                String qty = (String) JOptionPane.showInputDialog(lootNote, "Select a Quantity:", "Change a Quantity", JOptionPane.PLAIN_MESSAGE, null, null, null);
                int quantity;
                try {
                    quantity = Integer.parseInt(qty);
                    if (quantity > 0) {
                        FilingLoot.updateLootItemQuantity(item, quantity);
                    } else {
                        JOptionPane.showMessageDialog(lootNote, "Quantity must be a positive number", "Value Error", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(lootNote, "The Quantity must be an Integer", "Quantity Error", JOptionPane.WARNING_MESSAGE);
                }

            }
        }
        updateStats();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    public void updateStats() {
        lootNote.remove(lootTablePane);
        lootNote.remove(notesPane);
        dataLoot = new DefaultTableModel(FilingLoot.getLootTableData(), FilingLoot.getLootTableColumnHeaders()) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };
        lootTable = new JTable(dataLoot) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        DefaultTableCellRenderer CenterRenderer = new DefaultTableCellRenderer();
        CenterRenderer.setHorizontalAlignment(JLabel.CENTER);
        lootTable.getColumnModel().getColumn(0).setCellRenderer(CenterRenderer);
        lootTable.getColumnModel().getColumn(1).setCellRenderer(CenterRenderer);
        lootTable.getColumnModel().getColumn(2).setCellRenderer(CenterRenderer);
        lootTable.getTableHeader().setFont(tableHeading);
        lootTable.setFont(tableData);
        lootTable.setBackground(Color.WHITE);
        lootTable.setRowHeight(22);
        lootTable.setAutoCreateRowSorter(false);
        lootTable.setRowSorter(FilingLoot.RowSorter(lootTable));
        TableColumn column;
        for (int i = 0; i < lootTable.getColumnCount(); i++) {
            column = lootTable.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(400); //third column is bigger
            } else {
                column.setPreferredWidth(100);
            }
        }
        lootTablePane = new JScrollPane(lootTable);
        lootTablePane.setMaximumSize(new Dimension(dimX, 200));
        lootNote.add(lootTablePane);

        noteField = new JTextArea(25, 25);
        noteField.setText(FilingLoot.getLootNotes());
        noteField.setEditable(false);
        notesPane = new JScrollPane(noteField);
        notesPane.setMaximumSize(new Dimension(dimX, 200));
        lootNote.add(notesPane);

        layLoot = new GroupLayout(lootNote);
        lootNote.setLayout(layLoot);
        layLoot.setAutoCreateGaps(true);
        layLoot.setAutoCreateContainerGaps(true);
        layLoot.setHorizontalGroup(
                layLoot.createSequentialGroup()
                        .addGroup(layLoot.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(lootTab)
                                        .addGroup(layLoot.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                        .addComponent(lootTablePane)
                                                        .addGroup(layLoot.createSequentialGroup()
                                                                .addGroup(layLoot.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                                        .addComponent(lItem)
                                                                        .addComponent(lootItem))
                                                                .addGroup(layLoot.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                                        .addComponent(lValue)
                                                                        .addComponent(lootValue))
                                                                .addGroup(layLoot.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                                        .addComponent(lQty)
                                                                        .addComponent(lootQuantity))
                                                                .addComponent(updateLoot))
                                        )
                                        .addComponent(noteF)
                                        .addGroup(layLoot.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                        .addComponent(noteInput)
                                                        .addComponent(notesPane)

                                        )
                        )
        );
        layLoot.setVerticalGroup(
                layLoot.createSequentialGroup()
                        .addComponent(lootTab)
                        .addComponent(lootTablePane)
                        .addGroup(layLoot.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lItem)
                                .addComponent(lValue)
                                .addComponent(lQty))
                        .addGroup(layLoot.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lootItem)
                                        .addComponent(lootValue)
                                        .addComponent(lootQuantity)
                                        .addComponent(updateLoot)
                        )
                        .addComponent(noteF)
                        .addComponent(noteInput)
                        .addComponent(notesPane)

        );

        lootNote.revalidate();
        lootNote.repaint();
        System.out.println("Done updating loot Tab!");
    }
}
