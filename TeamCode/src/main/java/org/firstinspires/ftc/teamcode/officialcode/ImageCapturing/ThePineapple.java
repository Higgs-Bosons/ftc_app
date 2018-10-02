package org.firstinspires.ftc.teamcode.officialcode.ImageCapturing;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.*;
import android.hardware.camera2.*;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.*;
import android.support.annotation.NonNull;
import android.util.Size;
import android.view.*;


import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.util.Collections;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ThePineapple{
    private CameraManager cameraManager;
    private int cameraFacing;
    private String cameraId;
    private HandlerThread backgroundThread;
    private  Handler backgroundHandler;
    private Size previewSize;
    private  CameraDevice.StateCallback stateCallback;
    private CameraDevice cameraDevice;
    private  TextureView textureView;
    private  CaptureRequest.Builder captureRequestBuilder;
    private CaptureRequest captureRequest;
    private CameraCaptureSession cameraCaptureSession;

    public ThePineapple(){
        cameraManager = (CameraManager) AppUtil.getDefContext().getSystemService(Context.CAMERA_SERVICE);
        cameraFacing = CameraCharacteristics.LENS_FACING_BACK;

        TextureView.SurfaceTextureListener surfaceTextureListener = getSurfaceTextureListener();
        stateCallback          = getStateCallback();

        textureView = FtcRobotControllerActivity.getTextureView();
        textureView.setSurfaceTextureListener(surfaceTextureListener);

        openBackgroundThread();

        setUpCamera();
    }
    public void openThePineapple(){
        openCamera();
    }

    public void closeThePineapple(){
        closeBackgroundThread();
        closeCamera();
    }

    private TextureView.SurfaceTextureListener getSurfaceTextureListener(){
        return new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {

            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            }
        };
    }
    private CameraDevice.StateCallback getStateCallback(){
        return new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice cameraDevice) {
                ThePineapple.this.cameraDevice = cameraDevice;
                createPreviewSession();
            }

            @Override
            public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                cameraDevice.close();
                ThePineapple.this.cameraDevice = null;
            }

            @Override
            public void onError(@NonNull CameraDevice cameraDevice, int error) {
                cameraDevice.close();
                ThePineapple.this.cameraDevice = null;
            }
        };
    }




    private void setUpCamera() {
        try {
            for (String cameraId : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics =
                        cameraManager.getCameraCharacteristics(cameraId);
                if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == cameraFacing) {
                    StreamConfigurationMap streamConfigurationMap = cameraCharacteristics.get(
                            CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                    previewSize = streamConfigurationMap.getOutputSizes(SurfaceTexture.class)[0];
                    this.cameraId = cameraId;
                }
            }
        } catch (CameraAccessException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    private void openCamera() {
        try {
            cameraManager.openCamera(cameraId, stateCallback, backgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    private void openBackgroundThread() {
        Looper.prepare();
        backgroundThread = new HandlerThread("camera_background_thread");
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());
    }
    private void closeCamera() {
        if (cameraCaptureSession != null) {
            cameraCaptureSession.close();
            cameraCaptureSession = null;
        }

        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }
    private void closeBackgroundThread() {
        if (backgroundHandler != null) {
            backgroundThread.quitSafely();
            backgroundThread = null;
            backgroundHandler = null;
        }
    }
    private void createPreviewSession() {
        try {
            SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
            surfaceTexture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
            Surface previewSurface = new Surface(surfaceTexture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(previewSurface);

            cameraDevice.createCaptureSession(Collections.singletonList(previewSurface),
                    new CameraCaptureSession.StateCallback() {

                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                            if (cameraDevice == null) {
                                return;
                            }

                            try {
                                captureRequest = captureRequestBuilder.build();
                                ThePineapple.this.cameraCaptureSession = cameraCaptureSession;
                                ThePineapple.this.cameraCaptureSession.setRepeatingRequest(captureRequest,
                                        null, backgroundHandler);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                        }
                    }, backgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}
