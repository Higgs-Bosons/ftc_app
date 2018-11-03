package org.firstinspires.ftc.teamcode.Testers;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.ImageCapturing.EpicPineapple;
import org.firstinspires.ftc.teamcode.ImageCapturing.PineappleStrainer;

@Autonomous(name = "Camera Tester", group = "Tester")
public class CameraTester extends LinearOpMode {
    EpicPineapple thePineapple;
    Bitmap picture;
    PineappleStrainer pineappleStrainer;

    @Override
    public void runOpMode(){
        thePineapple = new EpicPineapple();

        waitForStart();

        picture = thePineapple.getWhatIAmSeeing();

        pineappleStrainer = new PineappleStrainer(picture, 80, 78, thePineapple);
        pineappleStrainer.findColoredObject(Color.rgb(250,200, 0));

        while (opModeIsActive()) {}
        thePineapple.closeEpicPineapple();
    }



}