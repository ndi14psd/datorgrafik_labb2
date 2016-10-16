package com.example.pontus.labb2;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

/**
 * Created by perjee on 10/6/16.
 */

public class Triangle {

    static final int VERTEX_POS_SIZE = 4;
    static final int COLOR_SIZE = 4;

    static final int VERTEX_ATTRIB_SIZE = VERTEX_POS_SIZE;
    static final int COLOR_ATTRIB_SIZE = COLOR_SIZE;

    private final int VERTEX_COUNT = VERTEX_POS_SIZE * 3 / VERTEX_ATTRIB_SIZE;

    private final FloatBuffer vertexDataBuffer;
    private final FloatBuffer colorDataBuffer;

    private final int mProgram;

    public Triangle(float[] color, float[] coordinates, int mProgram) {
        this.mProgram = mProgram;

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bbv = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                coordinates.length * 4);
        // use the device hardware's native byte order
        bbv.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexDataBuffer = bbv.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexDataBuffer.put(coordinates);
        // set the buffer to read the first coordinate
        vertexDataBuffer.position(0);

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bbc = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                color.length * 4);
        // use the device hardware's native byte order
        bbc.order(ByteOrder.nativeOrder());


        // create a floating point buffer from the ByteBuffer
        colorDataBuffer = bbc.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        colorDataBuffer.put(color);
        // set the buffer to read the first coordinate
        colorDataBuffer.position(0);
    }

    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL ES environment
        glUseProgram(mProgram);

        // get handle to shape's transformation matrix
        int mMVPMatrixHandle = glGetUniformLocation(mProgram, "uMVPMatrix");

        int positionHandle = glGetAttribLocation(mProgram, "vPosition");
        glEnableVertexAttribArray(positionHandle);

        // Prepare the triangle coordinate data
        glVertexAttribPointer(positionHandle, VERTEX_POS_SIZE,
                GL_FLOAT, false,
                VERTEX_ATTRIB_SIZE * 4, vertexDataBuffer);

        int colorHandle = glGetAttribLocation(mProgram, "vColor");
        glEnableVertexAttribArray(colorHandle);
        glVertexAttribPointer(colorHandle, COLOR_SIZE,
                GL_FLOAT, false,
                COLOR_ATTRIB_SIZE * 4, colorDataBuffer);

        // Pass the projection and view transformation to the shader
        glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // Draw the triangle
        glDrawArrays(GL_TRIANGLES, 0, VERTEX_COUNT);

        // Disable vertex array
        glDisableVertexAttribArray(positionHandle);
        glDisableVertexAttribArray(colorHandle);

    }
}
