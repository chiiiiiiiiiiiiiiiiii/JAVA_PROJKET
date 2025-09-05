package view.components.actor;

import controller.ActorController;
import model.Actor;
import view.components.AbstractEntityForm;

import javax.swing.*;

public class EditActorForm extends AbstractEntityForm<Actor> {

    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final ActorListView listView;
    private final Actor actor;

    public EditActorForm(ActorListView listView, Actor actor) {
        super("Edit Actor", 300, 150);
        this.listView = listView;
        this.actor = actor;

        firstNameField = new JTextField(actor.getFirstName());
        lastNameField = new JTextField(actor.getLastName());

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
            actor.setFirstName(firstNameField.getText().trim());
            actor.setLastName(lastNameField.getText().trim());
            new ActorController().updateActor(actor);
            JOptionPane.showMessageDialog(this, "Actor updated.");
            listView.refresh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
