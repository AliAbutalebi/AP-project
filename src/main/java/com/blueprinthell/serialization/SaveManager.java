package com.blueprinthell.serialization;
import com.blueprinthell.model.GameState;

public interface SaveManager {
    void save(GameState state);
    GameState load();
}
