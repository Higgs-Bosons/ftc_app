package org.firstinspires.ftc.teamcode.officialcode.Testers;
import android.graphics.Bitmap;

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
        pineappleStrainer = new PineappleStrainer();
        waitForStart();

        thePineapple.openEpicPineapple();


        long start =  System.currentTimeMillis();
        picture = thePineapple.getFrame(EpicPineapple.RECENT_FRAME);
        pineappleStrainer.findYellowCube(picture,start);

        while (opModeIsActive()){

        }
        thePineapple.closeEpicPineapple();
    }



}