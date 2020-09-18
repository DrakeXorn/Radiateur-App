package business;

import dataAccess.AppIconAccess;

import java.awt.*;

public class AppIconManager {
    public AppIconManager() {
    }

    public Image getAppIcon() {
        return AppIconAccess.getAppIcon();
    }
}
