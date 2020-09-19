package controller;

import business.PlayerHeadManager;
import model.exceptions.MinecraftDataRetrieverException;

import java.awt.*;

public class PlayerHeadController {
    private PlayerHeadManager manager;

    public PlayerHeadController() {
        manager = new PlayerHeadManager();
    }

    public Image getSkinHeadViaUsername(String username) throws MinecraftDataRetrieverException {
        return manager.getSkinHeadViaUsername(username);
    }
}
