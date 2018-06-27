package org.dominikbrandon.engineTester;

import org.dominikbrandon.entites.*;
import org.dominikbrandon.models.TexturedModel;
import org.dominikbrandon.renderEngine.*;
import org.dominikbrandon.models.RawModel;
import org.dominikbrandon.textures.ModelTexture;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainGameLoop {

    public static void main(String[] args) throws LWJGLException, IOException {
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        MasterRenderer renderer = new MasterRenderer();

        ModelTexture steelTexture = new ModelTexture(loader.loadTexture("steel"));
        steelTexture.setShineDamper(50);
        steelTexture.setReflectivity(10);

        RawModel crankshaftRawModel = OBJLoader.loadObjModel("crankshaft1", loader);
        RawModel pistonRodRawModel = OBJLoader.loadObjModel("pistonrod", loader);
        RawModel pistonHeadRawModel = OBJLoader.loadObjModel("pistonHead4", loader);
        RawModel valveRawModel = OBJLoader.loadObjModel("valve1", loader);
        RawModel camshaftRawModel = OBJLoader.loadObjModel("camshaft1", loader);
        TexturedModel crankshaftTexturedModel = new TexturedModel(crankshaftRawModel, steelTexture);
        TexturedModel pistonRodTexturedModel = new TexturedModel(pistonRodRawModel, steelTexture);
        TexturedModel pistonHeadTexturedModel = new TexturedModel(pistonHeadRawModel, steelTexture);
        TexturedModel valveTexturedModel = new TexturedModel(valveRawModel, steelTexture);
        TexturedModel camshaftTexturedModel = new TexturedModel(camshaftRawModel, steelTexture);

//        Entity crankshaftEntity = new Entity(crankshaftTexturedModel, new Vector3f(0,-3,-10),90,0,90,0.04f); // c2
        Entity crankshaftEntity = new Entity(crankshaftTexturedModel, new Vector3f(0,-3,-10),90,0,0,0.1f); // c1
        final float crankshaftRotationSpeed = 0.544f;

        final float pistonsNumber = 6;
        final float pistonPositionFirstX = -0.55f;
        final float pistonIntervalX = 4.10f;
        final float[] pistonStartingHeights = {3.2f,4.5f,2f,2f,3.2f,4.5f};
        final float[] pistonStartingRotations = {10f, -3f, -4f, 0f, -10f, 1f};
        final int[] pistonStartingStrokes = {2, 1, 1, 2, 1, 2};

        List<Piston> pistonEntities = new ArrayList<>();
        for (int i = 0; i < pistonsNumber; i++) {
            pistonEntities.add(new Piston(
                    pistonRodTexturedModel,
                    pistonHeadTexturedModel,
                    new Vector3f(
                            pistonPositionFirstX + i * pistonIntervalX,
                            pistonStartingHeights[i],
                            -10
                    ),
                    pistonStartingRotations[i],
                    pistonStartingStrokes[i]
            ));
        }

//        Entity pistonHeadEntity = new Entity(pistonHeadTexturedModel, new Vector3f(-3.25f,1,-10),0,90,0,0.05f);   // ph3
//        Entity pistonHeadEntity = new Entity(pistonHeadTexturedModel, new Vector3f(pistonPositionFirstX,pistonHighestPosition+1f,-10.5f),0,0,-90,0.2f);
        Entity valveEntity = new Entity(valveTexturedModel, new Vector3f(0,0,-10),0,0,0,0.04f);
        Entity camshaftEntity = new Entity(camshaftTexturedModel, new Vector3f(0,0,-10),0,0,0,0.053f);

        FocusPoint focusPoint = new FocusPoint(pistonHeadTexturedModel, new Vector3f(10,10,20),
                90,0,0,0.05f);
        Camera camera = new Camera(focusPoint);
        Light light = new Light(new Vector3f(20,30,100), new Vector3f(1,1,1));

        while(!Display.isCloseRequested()) {
            camera.move();
//            renderer.processEntity(focusPoint);   // ENABLE THIS TO SEE THE POINT

            crankshaftEntity.increaseRotation(crankshaftRotationSpeed,0,0);
            renderer.processEntity(crankshaftEntity);

            for (Piston piston: pistonEntities) {
                piston.move();
                renderer.processEntity(piston.getRod());
                renderer.processEntity(piston.getHead());
            }

//            renderer.processEntity(valveEntity);
//            renderer.processEntity(camshaftEntity);
            renderer.render(light, camera);

            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
