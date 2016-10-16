package com.example.pontus.labb2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

/**
 * Created by Pontus on 2016-10-16.
 */
public class CubeTriangleStrip {

    private final FloatBuffer vertexDataBuffer;
    private final FloatBuffer colorDataBuffer;
    private final int program;

    private final static float[] TRIANGLE_STRIP = new float[] {
            0.0f,  0.0f,   0.0f,     1.0f,
            1.0f,  0.0f,  -1.0f,     1.0f,
            1.0f,  0.0f,   0.0f,     1.0f,
            0.0f,  0.0f,  -1.0f,     1.0f,
            0.0f,  1.0f,   0.0f,     1.0f,
            1.0f,  1.0f,   0.0f,     1.0f,
            1.0f,  1.0f,  -1.0f,     1.0f,
            0.0f,  1.0f,  -1.0f,     1.0f
    };

    private static final float[] color = {   // in counterclockwise order:
            1.0f, 0.0f, 0.0f, 1.0f, // Red
            0.0f, 1.0f, 0.0f, 1.0f, // Green
            0.0f, 0.0f, 1.0f, 1.0f// Blue
    };

    private static final int VERTEX_POS_SIZE = 4;
    private static final int VERTEX_ATTRIB_SIZE = VERTEX_POS_SIZE;
    private static final int VERTEX_COUNT = TRIANGLE_STRIP.length / VERTEX_ATTRIB_SIZE;
    private static final int COLOR_SIZE = 4;
    private static final int COLOR_ATTRIB_SIZE = COLOR_SIZE;

    public CubeTriangleStrip() {
        program = glCreateProgram();
        int vertexShader = Shaders.loadShader(GL_VERTEX_SHADER, Shaders.VERTEX_SHADER_CODE);
        int fragmentShader = Shaders.loadShader(GL_FRAGMENT_SHADER, Shaders.FRAGMENT_SHADER_CODE);
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);

        glLinkProgram(program);

        ByteBuffer bbv = ByteBuffer.allocateDirect(
                TRIANGLE_STRIP.length * 4);
        bbv.order(ByteOrder.nativeOrder());

        vertexDataBuffer = bbv.asFloatBuffer();
        vertexDataBuffer.put(TRIANGLE_STRIP);
        vertexDataBuffer.position(0);

        ByteBuffer bbc = ByteBuffer.allocateDirect(
                color.length * 4);
        bbc.order(ByteOrder.nativeOrder());


        colorDataBuffer = bbc.asFloatBuffer();
        colorDataBuffer.put(color);
        colorDataBuffer.position(0);
    }

    public void draw(float[] mvpMatrix) {
        glUseProgram(program);

        // get handle to shape's transformation matrix
        int mMVPMatrixHandle = glGetUniformLocation(program, "uMVPMatrix");

        int positionHandle = glGetAttribLocation(program, "vPosition");
        glEnableVertexAttribArray(positionHandle);

        glVertexAttribPointer(positionHandle, VERTEX_POS_SIZE,
                GL_FLOAT, false,
                VERTEX_ATTRIB_SIZE * 4, vertexDataBuffer);

        int colorHandle = glGetAttribLocation(program, "vColor");
        glEnableVertexAttribArray(colorHandle);
        glVertexAttribPointer(colorHandle, COLOR_SIZE,
                GL_FLOAT, false,
                COLOR_ATTRIB_SIZE * 4, colorDataBuffer);

        glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        glDrawArrays(GL_TRIANGLE_STRIP, 0, VERTEX_COUNT);

        glDisableVertexAttribArray(positionHandle);
        glDisableVertexAttribArray(colorHandle);
    }
}



























