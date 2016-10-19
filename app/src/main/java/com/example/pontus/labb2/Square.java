package com.example.pontus.labb2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

class Square {

    private final FloatBuffer vertexDataBuffer;
    private final FloatBuffer colorDataBuffer;
    private final int program;

    private static final int VERTEX_POS_SIZE = 4;
    private static final int VERTEX_ATTRIB_SIZE = VERTEX_POS_SIZE;
    private static final int VERTEX_COUNT = 4;
    private static final int COLOR_SIZE = 4;
    private static final int COLOR_ATTRIB_SIZE = COLOR_SIZE;

    Square(int program, float[] colorData, float[] triangleStripData) {
        this.program = program;

        ByteBuffer bbv = ByteBuffer.allocateDirect(
                triangleStripData.length * 4);
        bbv.order(ByteOrder.nativeOrder());

        vertexDataBuffer = bbv.asFloatBuffer();
        vertexDataBuffer.put(triangleStripData);
        vertexDataBuffer.position(0);

        ByteBuffer bbc = ByteBuffer.allocateDirect(
                colorData.length * 4);
        bbc.order(ByteOrder.nativeOrder());


        colorDataBuffer = bbc.asFloatBuffer();
        colorDataBuffer.put(colorData);
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
