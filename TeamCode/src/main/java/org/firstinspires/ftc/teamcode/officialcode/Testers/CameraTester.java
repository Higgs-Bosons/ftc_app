package org.firstinspires.ftc.teamcode.officialcode.Testers;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.officialcode.ImageCapturing.EpicPineapple;

@Autonomous(name = "Camera Tester", group = "Tester")
public class CameraTester extends LinearOpMode{
    EpicPineapple thePineapple;
    Bitmap picture;
    public void runOpMode() throws InterruptedException {
        thePineapple = new EpicPineapple();
        waitForStart();
        thePineapple.openEpicPineapple();
        while(opModeIsActive()){
           picture = thePineapple.getWhatIAmSeeing();
           telemetry.addData("Pixel Red", Color.red(picture.getPixel(1,1)));
           telemetry.update();
        }
        thePineapple.closeEpicPineapple();
   }
}
