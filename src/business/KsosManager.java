package business;

import dataAccess.KsosAccess;
import model.exceptions.FileException;

import javax.sound.sampled.Clip;
import java.awt.*;

public class KsosManager {
    public KsosManager() {}

    public Image getKsosImg() throws FileException {
        return KsosAccess.getKsosImg();
    }

    public Clip getKsosClip(int clipNbr) throws FileException {
        return KsosAccess.getKsosClip(clipNbr);
    }
}
