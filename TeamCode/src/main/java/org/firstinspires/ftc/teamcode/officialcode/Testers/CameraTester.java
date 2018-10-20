package org.firstinspires.ftc.teamcode.officialcode.Testers;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.officialcode.ImageCapturing.EpicPineapple;
import org.firstinspires.ftc.teamcode.officialcode.ImageCapturing.PineappleStrainer;

@Autonomous(name = "Camera Tester", group = "Tester")
public class CameraTester extends LinearOpMode {
    EpicPineapple thePineapple;
    Bitmap picture;
    PineappleStrainer pineappleStrainer;

    @Override
    public void runOpMode(){
        thePineapple = new EpicPineapple();

        waitForStart();

        thePineapple.openEpicPineapple();
        thePineapple.setPrecision(100);

        picture = thePineapple.getFrame(EpicPineapple.RECENT_FRAME);
<<<<<<< HEAD

        pineappleStrainer = new PineappleStrainer(picture, 95, 25);
        pineappleStrainer.findColoredObject(Color.rgb(250,200, 0));
=======
        pineappleStrainer.findYellowCube(picture, 100);
>>>>>>> adb00eb1df705bc227d9b10de1a7bb479b0d184c

        while (opModeIsActive()){

        }
        thePineapple.closeEpicPineapple();
    }



}