package view.components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class EntityListPanel<T> extends JPanel {

    protected JTable table;
    protected DefaultTableModel tableModel;
    protected JButton addButton, editButton, deleteButton;

    public EntityListPanel(String[] columnNames) {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> onAdd());
        editButton.addActionListener(e -> onEdit(getSelectedEntity()));
        deleteButton.addActionListener(e -> onDelete(getSelectedEntity()));
    }

    protected abstract void refreshData(); // populates table with data
    protected abstract void onAdd();
    protected abstract void onEdit(T entity);
    protected abstract void onDelete(T entity);

    protected abstract T getSelectedEntity(); // get the item at selected row

    public void refresh() {
        tableModel.setRowCount(0);
        refreshData();
    }
}