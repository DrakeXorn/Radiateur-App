package controller;

import business.KsosManager;
import dataAccess.KsosAccess;
import model.exceptions.FileException;

import javax.sound.sampled.Clip;
import java.awt.*;

public class KsosController {
    private KsosManager ksosManager;

    public KsosController() {
        ksosManager = new KsosManager();
    }

    public Image getKsosImg() throws FileException {
        return ksosManager.getKsosImg();
    }

    public Clip getKsosClip(int clipNbr) throws FileException {
        return KsosAccess.getKsosClip(clipNbr);
    }
}
