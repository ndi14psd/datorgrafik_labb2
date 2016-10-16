package com.example.pontus.labb2;

import android.opengl.GLES20;

/**
 * Created by Pontus on 2016-10-16.
 */
public class Shaders {

    private Shaders() {}

    public final static String VERTEX_SHADER_CODE =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "attribute vec4 vPosition; \n" +
                    "attribute vec4 vColor; \n" +
                    "uniform mat4 uMVPMatrix;\n" +
                    "varying vec4 c; \n" +
                    "void main() { \n" +
                    "  c = vColor; \n" +
                    // the matrix must be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;\n" +
                    "}";

    public final static String FRAGMENT_SHADER_CODE =
            "precision mediump float;\n" +
                    "varying vec4 c;\n" +   //förr: varying vec4 c;
                    "void main() {\n" +
                    "  gl_FragColor = c;\n" +
                    "}";

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
