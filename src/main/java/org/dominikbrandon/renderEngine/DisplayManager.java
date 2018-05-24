package org.dominikbrandon.renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

public class DisplayManager {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int FPS_CAP = 120;

    public static void createDisplay() throws LWJGLException {
        ContextAttribs attribs =
                new ContextAttribs(3,2)
                .withForwardCompatible(true)
                .withProfileCore(true);
        Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
        Display.create(new PixelFormat(), attribs);
        Display.setTitle("Engine model");
        GL11.glViewport(0, 0, WIDTH, HEIGHT);
    }
    public static void updateDisplay() {
        Display.sync(FPS_CAP);
        Display.update();
    }
    public static void closeDisplay() {
        Display.destroy();
    }
}
