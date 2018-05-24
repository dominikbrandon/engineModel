package org.dominikbrandon.engineTester;

import org.dominikbrandon.renderEngine.DisplayManager;
import org.dominikbrandon.renderEngine.Loader;
import org.dominikbrandon.renderEngine.RawModel;
import org.dominikbrandon.renderEngine.Renderer;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

public class MainGameLoop {

    public static void main(String[] args) throws LWJGLException {
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f
        };
        int[] indices = {
                0,1,3,
                3,1,2
        };
        RawModel model = loader.loadToVAO(vertices, indices);

        while(!Display.isCloseRequested()) {
            renderer.prepare();
            renderer.render(model);
            DisplayManager.updateDisplay();
        }
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
