package com.example.pontus.labb2;

import java.util.ArrayList;
import java.util.List;

import static android.opengl.GLES20.*;

public class CubeTriangleStrip {

    private final List<Square> squares;
    private final int program;

    private final static float[] COLOR_DATA = {
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
    };

    public CubeTriangleStrip() {
        squares = new ArrayList<>();
        this.program = prepareProgram();
        initSquares(squares, program);
    }

    private int prepareProgram() {
        int program = glCreateProgram();
        int vertexShader = Shaders.loadShader(GL_VERTEX_SHADER, Shaders.VERTEX_SHADER_CODE);
        int fragmentShader = Shaders.loadShader(GL_FRAGMENT_SHADER, Shaders.FRAGMENT_SHADER_CODE);
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);
        glLinkProgram(program);
        return program;
    }

    private void initSquares(List<Square> squares, int program) {
        squares.add( new Square(program, COLOR_DATA, new float[] {
                0,  0,   0,    1,
                0,  1,   0,    1,
                1,  0,   0,    1,
                1,  1,   0,    1,
        }));
        squares.add(new Square(program, COLOR_DATA, new float[] {
                0,  0,  -1,    1,
                0,  1,  -1,    1,
                1,  0,  -1,    1,
                1,  1,  -1,    1,
        }));
        squares.add(new Square(program, COLOR_DATA, new float[] {
                0,  0,   0,    1,
                0,  0,  -1,    1,
                1,  0,   0,    1,
                1,  0,  -1,    1,
        }));
        squares.add(new Square(program, COLOR_DATA, new float[] {
                0,  1,   0,    1,
                0,  1,  -1,    1,
                1,  1,   0,    1,
                1,  1,  -1,    1,
        }));
        squares.add(new Square(program, COLOR_DATA, new float[] {
                1,  0,   0,    1,
                1,  0,  -1,    1,
                1,  1,   0,    1,
                1,  1,  -1,    1,
        }));
        squares.add(new Square(program, COLOR_DATA, new float[] {
                0,  0,   0,    1,
                0,  1,   0,    1,
                0,  0,  -1,    1,
                0,  1,  -1,    1,
        }));
    }

    public void draw(float[] mvpMatrix) {
        for (Square sq : squares)
            sq.draw(mvpMatrix);
    }


}



























