package com.example.pontus.labb2;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
    }

}

class MyGLSurfaceView extends GLSurfaceView {

    private final CGRenderer mRenderer;

    private final float TOUCH_SCALE_FACTOR = 0.13f * 5;
    private float mPreviousX;
    private float mPreviousY;

    public MyGLSurfaceView(Context context){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new CGRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                mRenderer.setXAngle(mRenderer.getXAngle() + dx * TOUCH_SCALE_FACTOR);
                mRenderer.setYAngle(mRenderer.getYAngle() + dy * TOUCH_SCALE_FACTOR);
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }
}

class CGRenderer implements GLSurfaceView.Renderer {

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];
    private float[] CTM = new float[16];

    private Cube mCube;
    private CubeTriangleStrip cubeTriangleStrip;

    public volatile float xAngle;
    public volatile float yAngle;

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.8f, 0.8f, 0.8f, 1.0f);
        // initialize a triangle

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        mCube = new Cube();
        cubeTriangleStrip = new CubeTriangleStrip();
    }

    @Override
    public void onDrawFrame(GL10 unused) {

        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);

        Matrix.setIdentityM(mViewMatrix, 0);
        Matrix.translateM(mViewMatrix, 0, 0, 0, -1.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM (mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Calculate rotation matrix
        Matrix.translateM(CTM, 0, mMVPMatrix, 0, 0.5f, 0.5f, -0.5f);

        Matrix.setRotateM(mRotationMatrix, 0, xAngle, 0, 1.0f, 0);
        Matrix.multiplyMM(CTM, 0, CTM, 0, mRotationMatrix, 0);

        Matrix.setRotateM(mRotationMatrix, 0, yAngle, 1.0f, 0, 0);
        Matrix.multiplyMM(CTM, 0, CTM, 0, mRotationMatrix, 0);

        Matrix.translateM(CTM, 0, CTM, 0, -0.5f, -0.5f, 0.5f);

        // Draw shape
        //mCube.draw(CTM);
        cubeTriangleStrip.draw(CTM);

    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 0.5f, 10.0f);

    }

    public float getXAngle() {
        return xAngle;
    }

    public void setXAngle(float angle) {
        xAngle = angle;
    }

    public float getYAngle() {
        return yAngle;
    }

    public void setYAngle(float angle) {
        yAngle = angle;
    }
}