package org.dominikbrandon.engineTester;

import org.dominikbrandon.renderEngine.DisplayManager;
import org.dominikbrandon.renderEngine.Loader;
import org.dominikbrandon.renderEngine.RawModel;
import org.dominikbrandon.renderEngine.Renderer;
import org.dominikbrandon.shaders.StaticShader;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import java.io.IOException;

public class MainGameLoop {

    public static void main(String[] args) throws LWJGLException, IOException {
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

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
            shader.start();
            renderer.render(model);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
