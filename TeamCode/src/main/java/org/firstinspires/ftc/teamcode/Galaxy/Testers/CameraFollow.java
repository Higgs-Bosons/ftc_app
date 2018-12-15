package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.*;
import org.firstinspires.ftc.teamcode.Galaxy.MecanumWheelRobot.MecanumWheelRobot;
import org.firstinspires.ftc.teamcode.Galaxy.Tools;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

@Autonomous(name = "Camera Follow", group = "Tester")
public class CameraFollow extends LinearOpMode {
    //   UP AND DOWN:     0.5,  0.33    CENTER .47
    //   LEFT AND RIGHT:  0.4,  0.8     CENTER 0.6

    public void runOpMode(){
        double X, Y , XPos = .6, YPos = .47;

        MecanumWheelRobot Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Bubbles.addServo("X-Thing");
        Bubbles.addServo("Y-Thing");

        CanOfPineapple thePineappleCan = new CanOfPineapple();

        waitForStart();

        Bitmap picture;
        PineappleStrainer pineappleStrainer = new PineappleStrainer( thePineappleCan);
        PineappleChunks pineappleChunks;


        while (opModeIsActive()){
            picture = thePineappleCan.getBitmap();
            pineappleChunks = pineappleStrainer
                    .findShadedObject(80,80, picture, Color.rgb(250,200, 0), 130);

            telemetry.addData("Num of chunks", pineappleChunks.getNumberOfChunks());

            if(pineappleChunks.doesChunkExist()){

                telemetry.addData("X ",pineappleChunks.getChunk(0)[PineappleChunks.X]);
                telemetry.addData("Y ",pineappleChunks.getChunk(0)[PineappleChunks.Y]);
                telemetry.addData("SIZE ",pineappleChunks.getChunk(0)[PineappleChunks.SIZE]);

                X = pineappleChunks.getBiggestChunk()[PineappleChunks.Y];
                Y = pineappleChunks.getBiggestChunk()[PineappleChunks.X];

                X = (X - 50) / 700;
                Y = (Y - 50) / 700;

                XPos += X;
                YPos += Y;

                XPos = (XPos > 0.8) ? 0.8 : XPos;
                XPos = (XPos < 0.4) ? 0.4 : XPos;

                YPos = (YPos > 0.5) ? 0.5 : YPos;
                YPos = (YPos < 0.33) ? 0.33 : YPos;

                Bubbles.moveServo("X-Thing", XPos);
                Bubbles.moveServo("Y-Thing", YPos);

                Tools.wait(10);
            }
            telemetry.update();

        }

        thePineappleCan.closeCanOfPineapple();
    }

}