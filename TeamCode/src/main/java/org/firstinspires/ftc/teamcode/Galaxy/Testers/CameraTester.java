package org.firstinspires.ftc.teamcode.Galaxy.Testers;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.CanOfPineapple;
import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.PineappleStrainer;

@Autonomous(name = "Camera Tester", group = "Tester")
public class CameraTester extends LinearOpMode {
    CanOfPineapple thePineappleCan;
    Bitmap picture;
    PineappleStrainer pineappleStrainer;

    @Override
    public void runOpMode(){


        thePineappleCan = new CanOfPineapple();

        waitForStart();

        picture = thePineappleCan.getWhatIAmSeeing();

        pineappleStrainer = new PineappleStrainer(picture, 80, 80, thePineappleCan);
        pineappleStrainer.findColoredObject(Color.rgb(250,200, 0), 130);

        while (opModeIsActive()) {}
        thePineappleCan.closeCanOfPineapple();
    }



}