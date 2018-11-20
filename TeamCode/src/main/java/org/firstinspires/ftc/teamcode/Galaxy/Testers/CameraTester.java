package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import android.graphics.*;
import com.qualcomm.robotcore.eventloop.opmode.*;
import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.*;

@Autonomous(name = "Camera Tester", group = "Tester")
public class CameraTester extends LinearOpMode {
    public void runOpMode(){
        CanOfPineapple thePineappleCan = new CanOfPineapple();
        waitForStart();
        Bitmap picture = thePineappleCan.getBitmap();
        PineappleStrainer pineappleStrainer = new PineappleStrainer( thePineappleCan);

        pineappleStrainer.findShadedObject(80,80, picture, Color.rgb(250,200, 0), 130);

        while (opModeIsActive()){}
        thePineappleCan.closeCanOfPineapple();
    }
}