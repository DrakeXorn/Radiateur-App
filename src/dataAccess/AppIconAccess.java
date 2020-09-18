package dataAccess;

import javax.swing.*;
import java.awt.*;

public class AppIconAccess {
    private static ImageIcon appIcon;

    public AppIconAccess() {
    }

    public static Image getAppIcon() {
        if (appIcon == null) {
            ImageIcon radiatorIcon = new ImageIcon(ClassLoader.getSystemResource("icon.png"));
            Image radiatorImage = radiatorIcon.getImage().getScaledInstance(20, 20, 4);

            appIcon = new ImageIcon(radiatorImage);
        }

        return appIcon.getImage();
    }
}
