package controller;

import business.PlayerHeadManager;
import model.exceptions.SkinRetrieverException;

import java.awt.*;

public class PlayerHeadController {
    private PlayerHeadManager manager;

    public PlayerHeadController() {
        manager = new PlayerHeadManager();
    }

    public Image getSkinHeadViaUsername(String username) throws SkinRetrieverException {
        return manager.getSkinHeadViaUsername(username);
    }
}
