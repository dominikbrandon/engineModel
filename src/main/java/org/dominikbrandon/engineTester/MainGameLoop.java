package org.dominikbrandon.engineTester;

import org.dominikbrandon.entites.Camera;
import org.dominikbrandon.entites.Entity;
import org.dominikbrandon.models.TexturedModel;
import org.dominikbrandon.renderEngine.DisplayManager;
import org.dominikbrandon.renderEngine.Loader;
import org.dominikbrandon.models.RawModel;
import org.dominikbrandon.renderEngine.OBJLoader;
import org.dominikbrandon.renderEngine.Renderer;
import org.dominikbrandon.shaders.StaticShader;
import org.dominikbrandon.textures.ModelTexture;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;

public class MainGameLoop {

    public static void main(String[] args) throws LWJGLException, IOException {
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        RawModel model = OBJLoader.loadObjModel("stall", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("stall"));
        TexturedModel staticModel = new TexturedModel(model, texture);
        Entity entity = new Entity(staticModel, new Vector3f(0,0,-10),0,0,0,1);
        Camera camera = new Camera();

        while(!Display.isCloseRequested()) {
//            entity.increasePosition(0, 0, -0.01f);
            entity.increaseRotation(1,1,0);
            camera.move();

            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
