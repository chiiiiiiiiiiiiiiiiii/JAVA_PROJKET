package view.components;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractEntityForm<T> extends JFrame {

    protected final JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
    protected final JButton saveButton = new JButton("Save");

    public AbstractEntityForm(String title, int width, int height) {
        setTitle(title);
        setSize(width, height);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(formPanel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> {
            if (validateInput()) {
                onSave();
                dispose();
            }
        });
    }

    protected abstract boolean validateInput();
    protected abstract void onSave();
}
