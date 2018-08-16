package org.firstinspires.ftc.teamcode.officialcode.ImageCapturing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.provider.Contacts;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.ui.UILocation;
import org.firstinspires.ftc.teamcode.officialcode.Tools;

import java.io.IOException;
import java.util.List;

public class HiggsCamera{
    // Variables:
    private byte[] pictureByte;              //The Camera return a byte[] that we convert to a Bitmap
    private Bitmap pictureBitmap;            //This is the image variable that we received from the byte[]

    private CameraPreview CameraPreview;     //Preview of what the camera sees.
    private android.hardware.Camera camera;  //The Camera
    private FrameLayout previewHolder;       //The display of the CameraPreview
    private Camera.PictureCallback mPicture = new Camera.PictureCallback(){
        public void onPictureTaken(byte[] data, Camera camera){
            Log.d("TOOK","IT");pictureByte = data;tookIt = true;Ready=true;}
    }; //What get called when you capture a image.

    private boolean tookIt = false;          //Boolean for whether or not the camera is finished taking the picture.
    private volatile boolean Ready = false;  //Boolean for whether or not the camera is initialized.

    public void open(){
        AppUtil.getInstance().getActivity().runOnUiThread(new Runnable() {
            public void run(){
                while(camera == null){camera = openFrontFacingCamera();}
                CameraPreview = new CameraPreview(AppUtil.getDefContext(), camera);
                previewHolder = FtcRobotControllerActivity.getCameraPreview();
                previewHolder.setVisibility(View.VISIBLE);
                previewHolder.addView(CameraPreview);
                Camera.Parameters params= camera.getParameters();

                List<Camera.Size> sizes = params.getSupportedPictureSizes();
                Camera.Size size = sizes.get(0);
                for(int i=0;i<sizes.size();i++){if(sizes.get(i).width > size.width)size = sizes.get(i);}
                params.setPictureSize(size.width, size.height);
                params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                params.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
                params.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
                params.setExposureCompensation(0);
                params.setPictureFormat(ImageFormat.JPEG);
                params.setJpegQuality(5);
                params.setRotation(90);


                camera.setParameters(params);
                camera.startPreview();
                Ready = true;
            }
        });
    }
    public void close(){
        AppUtil.getInstance().getActivity().runOnUiThread(new Runnable() {
            public void run() {
                releaseCameraAndPreview();
            }
        });
    }
    public Bitmap getPicture(){
        if(!Ready){
            AppUtil.getInstance().showToast(UILocation.BOTH, "Camera not ready.");
        }else{
            try{
                Ready = false;
                Log.d("STARTED ","IT");
                camera.takePicture(null, null, mPicture);
            }catch (Exception ignore){
                Tools.showToast("Crashed");}
            while(!tookIt){Log.d("Camera","Waiting for Camera to take picture.");}
            pictureBitmap = BitmapFactory.decodeByteArray(pictureByte,0,pictureByte.length);
        }
        return pictureBitmap;
    }
    public void setZoom(int amount){
        if(amount > 100 || amount < 0){
            AppUtil.getInstance().showToast(UILocation.BOTH, "Zoom amount incorrect. (Outside of 0-100)");
        }else{
            camera.startSmoothZoom((int)(amount*0.6));
        }
    }
    public void turnOnCameraLight(){
        Ready = false;
        Camera.Parameters p = camera.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(p);
        camera.startPreview();
        Ready = true;
    }
    public void turnOffCameraLight(){
        Ready = false;
        Camera.Parameters p = camera.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(p);
        camera.startPreview();
        Ready = true;
    }


    private Camera openFrontFacingCamera() {
        Camera cam = null;
        try {
            cam = Camera.open(0);
        } catch (Exception ignore){}
        return cam;
    }
    private void releaseCameraAndPreview() {
        CameraPreview = new CameraPreview(AppUtil.getDefContext(), null);
        if (camera != null) {
            camera.release();
            camera = null;
        }
        previewHolder.removeAllViews();
        previewHolder.setVisibility(View.INVISIBLE);
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
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            if (mHolder.getSurface() == null){return;}
            try{mCamera.stopPreview();}catch(Exception e){e.printStackTrace();}
            try{
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
