package org.dominikbrandon.renderEngine;

import org.dominikbrandon.models.RawModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader {

    public static RawModel loadObjModel(String fileName, Loader loader) throws IOException {
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        List<String[]> faces = new ArrayList<>();

        FileReader fileReader = new FileReader(new File("res/"+fileName+".obj"));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            String[] currentLine = line.split(" ");
            if (line.startsWith("v ")) {
                Vector3f vertex = new Vector3f(
                        Float.parseFloat(currentLine[1]),
                        Float.parseFloat(currentLine[2]),
                        Float.parseFloat(currentLine[3])
                );
                vertices.add(vertex);
            } else if (line.startsWith("vt ")) {
                Vector2f texture = new Vector2f(
                        Float.parseFloat(currentLine[1]),
                        Float.parseFloat(currentLine[2])
                );
                textures.add(texture);
            } else if (line.startsWith("vn ")) {
                Vector3f normal = new Vector3f(
                        Float.parseFloat(currentLine[1]),
                        Float.parseFloat(currentLine[2]),
                        Float.parseFloat(currentLine[3])
                );
                normals.add(normal);
            } else if (line.startsWith("f ")) {
                String[] face = {
                        currentLine[1],
                        currentLine[2],
                        currentLine[3]
                };
                faces.add(face);
            }
        }

        float[] verticesArray = new float[vertices.size() * 3];
        float[] normalsArray = new float[vertices.size() * 3];
        float[] textureArray = new float[vertices.size() * 2];

        for (String[] face: faces){
            String[] vertex1 = face[0].split("/");
            String[] vertex2 = face[1].split("/");
            String[] vertex3 = face[2].split("/");

            processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
            processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
            processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
        }
        bufferedReader.close();

        int vertexPointer = 0;
        for (Vector3f vertex: vertices) {
            verticesArray[vertexPointer++] = vertex.x;
            verticesArray[vertexPointer++] = vertex.y;
            verticesArray[vertexPointer++] = vertex.z;
        }

        int[] indicesArray = new int[indices.size()];
        for (int i = 0; i < indices.size(); i++) {
            indicesArray[i] = indices.get(i);
        }

        return loader.loadToVAO(verticesArray, textureArray, normalsArray, indicesArray);
    }

    private static void processVertex(
            String[] vertexData,
            List<Integer> indices,
            List<Vector2f> textures,
            List<Vector3f> normals,
            float[] textureArray,
            float[] normalsArray) {
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);

        if (!vertexData[1].equals("")) {
            Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
            textureArray[currentVertexPointer*2] = currentTex.x;
            textureArray[currentVertexPointer*2 + 1] = 1 - currentTex.y;
        }

        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArray[currentVertexPointer*3] = currentNorm.x;
        normalsArray[currentVertexPointer*3 + 1] = currentNorm.y;
        normalsArray[currentVertexPointer*3 + 2] = currentNorm.z;
    }
}
