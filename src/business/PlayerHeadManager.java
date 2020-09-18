package business;

import dataAccess.utils.SkinRetrievingUtils;
import model.exceptions.SkinRetrieverException;

import java.awt.*;

public class PlayerHeadManager {
    public PlayerHeadManager() {}

    public Image getSkinHeadViaUsername(String username) throws SkinRetrieverException {
        return SkinRetrievingUtils.getSkinHeadViaUsername(username);
    }
}
