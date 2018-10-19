package org.firstinspires.ftc.teamcode.officialcode.ImageCapturing;

import android.graphics.Bitmap;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import android.view.TextureView;

public class EpicPineappleObjects {
    public static final int RECENT_FRAME = 0;

    CameraManager cameraManager;
    String cameraId;
    HandlerThread backgroundThread;
    Handler backgroundHandler;
    Size previewSize;
     CameraDevice.StateCallback stateCallback;
    CameraDevice cameraDevice;
    TextureView textureView;
     CaptureRequest.Builder captureRequestBuilder;
    CaptureRequest captureRequest;
    CameraCaptureSession cameraCaptureSession;
    Bitmap[] frames;

    int cameraFacing;
    boolean PineappleIsActive;
    boolean youHaveMostRecentFrame;
    int precision = 4;
}
