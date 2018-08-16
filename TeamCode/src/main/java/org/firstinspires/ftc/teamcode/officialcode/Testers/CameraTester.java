package org.firstinspires.ftc.teamcode.officialcode.Testers;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.ui.UILocation;
import org.firstinspires.ftc.teamcode.officialcode.ImageCapturing.HiggsCamera;
@Autonomous(name = "Camera Tester", group = "Tester")
public class CameraTester extends LinearOpMode{
    HiggsCamera Camera = new HiggsCamera();
    @Override
    public void runOpMode() throws InterruptedException {
        Camera.open();
        waitForStart();
        Bitmap bitmap = Camera.getPicture();
        while(opModeIsActive()){
            if(Color.blue(bitmap.getPixel(1,1)) > Color.red(bitmap.getPixel(1,1))){
                Camera.turnOnCameraLight();
            }else{
                Camera.turnOffCameraLight();
            }
            bitmap = Camera.getPicture();
            Log.d("Running", "Working.. ------------------------------------------");
        }
        Camera.close();
    }
}
