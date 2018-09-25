package org.firstinspires.ftc.teamcode.officialcode.ImageCapturing;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.IOException;


@Autonomous(name = "Camera-Whitaker", group = "Tester")
public class Whitaker extends LinearOpMode {

    Camera camera;
    CameraPreview cameraPreview;
    public void runOpMode() throws InterruptedException {
       camera = getCameraInstance();
       cameraPreview = new CameraPreview(AppUtil.getDefContext(), camera);
       FrameLayout frameLayout = (new FtcRobotControllerActivity()).getFrameLayout();
       waitForStart();
       frameLayout.addView(cameraPreview);
       while (opModeIsActive()){}
    }
    public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder mHolder;
        private Camera mCamera;

        public CameraPreview(Context context, Camera camera) {
            super(context);
            mCamera = camera;
            mHolder = getHolder();
            mHolder.addCallback(this);
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        public void surfaceCreated(SurfaceHolder holder) {
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

            if (mHolder.getSurface() == null){
                return;
            }
            try {
                mCamera.stopPreview();
            } catch (Exception e){
            }
            try {
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();

            } catch (Exception e){
            }
        }
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

}
