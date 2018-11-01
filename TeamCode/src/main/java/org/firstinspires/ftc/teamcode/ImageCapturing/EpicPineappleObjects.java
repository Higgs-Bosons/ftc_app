package org.firstinspires.ftc.teamcode.ImageCapturing;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import android.view.TextureView;

public class EpicPineappleObjects {

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

    int cameraFacing;
    boolean PineappleIsActive;
}
