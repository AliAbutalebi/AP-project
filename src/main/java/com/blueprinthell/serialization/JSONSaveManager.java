package com.blueprinthell.serialization;

import com.blueprinthell.model.GameState;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class JSONSaveManager implements SaveManager {
    private File data = new File("game-save.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void save(GameState state) {
        try (Writer writer = new FileWriter(data)) {
            gson.toJson(state, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GameState load() {
        try (Reader reader = new FileReader(data)) {
            return gson.fromJson(reader, GameState.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
