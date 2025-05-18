package com.blueprinthell.model;

public enum ScenePath {
    MAIN_MENU("/com/blueprinthell/view/MainMenu.fxml"),
    LEVEL_SELECT("/com/blueprinthell/view/LevelSelectView.fxml"),
    SETTINGS("/com/blueprinthell/view/SettingsView.fxml"),
    GAME("/com/blueprinthell/view/GameView.fxml"),
    SHOP("/com/blueprinthell/view/ShopView.fxml"),
    GAME_OVER("/com/blueprinthell/view/GameOverView.fxml"),
    WIN("/com/blueprinthell/view/WinView.fxml");

    private final String path;

    ScenePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public java.net.URL getResourceURL() {
        return getClass().getResource(path);
    }

    public String getExternalForm() {
        java.net.URL url = getResourceURL();
        return url != null ? url.toExternalForm() : null;
    }
}

