package com.example.pontus.labb2;

import android.graphics.Color;
import android.opengl.GLES20;

import java.util.ArrayList;
import java.util.List;

import static android.opengl.GLES20.*;

/**
 * Created by ndi14psd on 2016-10-13.
 */
public class Cube {

    private final static List<Triangle> TRIANGLES = new ArrayList<>();

    public Cube() {
        int program = glCreateProgram();
        int vertexShader = Shaders.loadShader(GL_VERTEX_SHADER, Shaders.VERTEX_SHADER_CODE);
        int fragmentShader = Shaders.loadShader(GL_FRAGMENT_SHADER, Shaders.FRAGMENT_SHADER_CODE);
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);

        glLinkProgram(program);

        initializeTriangles(program);
    }

    private void initializeTriangles(final int program) {
        TRIANGLES.add(new Triangle(Colors.RED,
                new float[] {
                    0f, 0f, 0f, 1f,
                    0f, 1f, 0f, 1f,
                    1f, 0f, 0f, 1f
                }, program));
        TRIANGLES.add(new Triangle(Colors.DARK_GREEN,
                new float[] {
                        1f, 1f, 0f, 1f,
                        0f, 1f, 0f, 1f,
                        1f, 0f, 0f, 1f
                }, program));

        TRIANGLES.add(new Triangle(Colors.BLUE,
                new float[] {
                        0f, 0f, -1f, 1f,
                        0f, 1f, -1f, 1f,
                        1f, 0f, -1f, 1f,
                        1232f,
                }, program));
        TRIANGLES.add(new Triangle(Colors.PURPLE,
                new float[] {
                        1f, 1f, -1f, 1f,
                        0f, 1f, -1f, 1f,
                        1f, 0f, -1f, 1f
                }, program));

        TRIANGLES.add(new Triangle(Colors.YELLOW,
                new float[] {
                        0f, 0f, 0f, 1f,
                        0f, 0f, -1f, 1f,
                        1f, 0f, 0f, 1f
                }, program));
        TRIANGLES.add(new Triangle(Colors.DARK_GREEN,
                new float[] {
                        0f, 0f, -1f, 1f,
                        1f, 0f, -1f, 1f,
                        1f, 0f, 0f, 1f
                }, program));

        TRIANGLES.add(new Triangle(Colors.WHITE,
                new float[] {
                        0f, 1f, 0f, 1f,
                        0f, 1f, -1f, 1f,
                        1f, 1f, 0f, 1f
                }, program));
        TRIANGLES.add(new Triangle(Colors.BLUE,
                new float[] {
                        1f, 1f, -1f, 1f,
                        0f, 1f, -1f, 1f,
                        1f, 1f, 0f, 1f
                }, program));

        TRIANGLES.add(new Triangle(Colors.GREEN,
                new float[] {
                        1f, 0f, 0f, 1f,
                        1f, 0f, -1f, 1f,
                        1f, 1f, 0f, 1f
                },program));
        TRIANGLES.add(new Triangle(Colors.YELLOW,
                new float[] {
                        1f, 1f, -1f, 1f,
                        1f, 1f, 0f, 1f,
                        1f, 0f, -1f, 1f
                },program));

        TRIANGLES.add(new Triangle(Colors.LIGHT_GREEN,
                new float[] {
                        0f, 0f, 0f, 1f,
                        0f, 1f, 0f, 1f,
                        0f, 0f, -1f, 1f
                },program));
        TRIANGLES.add(new Triangle(Colors.RED,
                new float[] {
                        0f, 1f, -1f, 1f,
                        0f, 1f, 0f, 1f,
                        0f, 0f, -1f, 1f
                },program));
    }

    public void draw(float[] mvpMatrix) {
        for (Triangle t : TRIANGLES)
            t.draw(mvpMatrix);
    }


}

