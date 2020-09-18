package controller;

import business.AppIconManager;

import java.awt.*;

public class AppIconController {
    private AppIconManager manager;

    public AppIconController() {
        manager = new AppIconManager();
    }

    public Image getAppIcon() {
        return manager.getAppIcon();
    }
}
