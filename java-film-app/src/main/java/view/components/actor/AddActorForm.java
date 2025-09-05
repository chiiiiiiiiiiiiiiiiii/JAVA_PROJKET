package view.components.actor;

import controller.ActorController;
import model.Actor;
import view.components.AbstractEntityForm;

import javax.swing.*;

public class AddActorForm extends AbstractEntityForm<Actor> {

    private final JTextField firstNameField = new JTextField();
    private final JTextField lastNameField = new JTextField();
    private final ActorListView listView;

    public AddActorForm(ActorListView listView) {
        super("Add Actor", 300, 150);
        this.listView = listView;

        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
    }

    @Override
    protected boolean validateInput() {
        if (firstNameField.getText().trim().isEmpty() || lastNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both fields are required.");
            return false;
        }
        return true;
    }

    @Override
    protected void onSave() {
        try {
            Actor actor = new Actor(firstNameField.getText().trim(), lastNameField.getText().trim());
            new ActorController().addActor(actor);
            JOptionPane.showMessageDialog(this, "Actor added.");
            listView.refresh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
