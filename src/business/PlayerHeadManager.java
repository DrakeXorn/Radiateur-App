package business;

import dataAccess.utils.MinecraftDataRetrievingUtils;
import model.exceptions.MinecraftDataRetrieverException;

import java.awt.*;

public class PlayerHeadManager {
    public PlayerHeadManager() {}

    public Image getSkinHeadViaUsername(String username) throws MinecraftDataRetrieverException {
        return MinecraftDataRetrievingUtils.getSkinHeadViaUsername(username);
    }
}
