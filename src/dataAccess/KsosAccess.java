package dataAccess;

import model.exceptions.FileException;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.io.IOException;

public class KsosAccess {
    public static Image ksosImg;

    public KsosAccess() {
    }

    public static Image getKsosImg() throws FileException {
        if (ksosImg == null) {
            try {
                ksosImg = ImageIO.read(ClassLoader.getSystemResource("ksosImg.png")).getScaledInstance(200, 200, 8);
            } catch (IOException exception) {
                throw new FileException("l'image ksos", exception.getMessage());
            }
        }

        return ksosImg;
    }

    public static Clip getKsosClip(int clibNbr) throws FileException {
        Clip clip;

        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(ClassLoader.getSystemResource("ksosAudio (" + clibNbr + ").wav"));

            clip = AudioSystem.getClip();
            clip.open(inputStream);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException exception) {
            throw new FileException("l'audio ksos", exception.getMessage());
        }
        return clip;
    }
}