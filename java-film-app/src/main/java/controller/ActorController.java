package controller;

import model.Actor;
import repository.ActorRepository;

import java.sql.SQLException;
import java.util.List;

public class ActorController {

    private final ActorRepository repo = new ActorRepository();

    public void addActor(Actor actor) throws SQLException {
        repo.insertActor(actor);
    }

    public void updateActor(Actor actor) throws SQLException {
        repo.updateActor(actor);
    }

    public void deleteActor(int actorId) throws SQLException {
        repo.deleteActor(actorId);
    }

    public List<Actor> getAllActors() throws SQLException {
        return repo.getAllActors();
    }

    public Actor getActorById(int id) throws SQLException {
        return repo.getActorById(id);
    }
}
