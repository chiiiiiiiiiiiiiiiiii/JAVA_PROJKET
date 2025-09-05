package view.components.actor;

import controller.ActorController;
import model.Actor;
import view.components.EntityListPanel;

import javax.swing.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.sql.SQLException;
import java.util.List;

public class ActorListView extends EntityListPanel<Actor> {

    private List<Actor> actorList;

    public ActorListView() {
        super(new String[]{"First Name", "Last Name"});
        table.setDragEnabled(true);
        table.setDropMode(DropMode.ON); // oznaci (plavo) glumca kojeg trenutno dropas
        table.setTransferHandler(new TransferHandler("selectedRow"));
        refresh();
    }

    @Override
    protected void refreshData() {
        tableModel.setRowCount(0);
        try {
            actorList = new ActorController().getAllActors();
            for (Actor a : actorList) {
                tableModel.addRow(new Object[]{a.getFirstName(), a.getLastName()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load actors: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() {
        new AddActorForm(this).setVisible(true);
    }

    @Override
    protected void onEdit(Actor actor) {
        if (actor == null) return;
        new EditActorForm(this, actor).setVisible(true);
    }

    @Override
    protected void onDelete(Actor actor) {
        if (actor == null) return;
        int confirm = JOptionPane.showConfirmDialog(this, "Delete actor: " + actor + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                new ActorController().deleteActor(actor.getId());
                refresh();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Failed to delete actor: " + e.getMessage());
            }
        }
    }

    @Override
    protected Actor getSelectedEntity() {
        int selected = table.getSelectedRow();
        if (selected >= 0 && selected < actorList.size()) {
            return actorList.get(selected);
        }
        return null;
    }

    // Drag and drop support
    @Override
    public void addNotify() {
        super.addNotify();
        table.setTransferHandler(new TransferHandler() {
            @Override
            protected Transferable createTransferable(JComponent c) {
                int row = table.getSelectedRow();
                if (row >= 0 && row < actorList.size()) {
                    Actor actor = actorList.get(row);
                    int actorId = actor.getId();
                    String name = actor.getFirstName() + " " + actor.getLastName();
                    return new StringSelection("actor:" + actorId + ":" + name);
                }
                return null;
            }

            @Override
            public int getSourceActions(JComponent c) {
                return COPY;
            }
        });
    }

}
