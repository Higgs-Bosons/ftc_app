package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import android.graphics.*;
import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.*;
import org.firstinspires.ftc.teamcode.Galaxy.Robot.MecanumWheelRobot;
import org.firstinspires.ftc.teamcode.Galaxy.Robot.Servos;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

@Autonomous(name = "Camera Follow", group = "Tester")
public class CameraFollow extends LinearOpMode {
    public void runOpMode(){
        //   UP AND DOWN:     0.5,  0.33    CENTER .47
        //   LEFT AND RIGHT:  0.4,  0.8     CENTER 0.6
        CanOfPineapple thePineappleCan = new CanOfPineapple();
        waitForStart();
        Bitmap picture;
        PineappleStrainer pineappleStrainer = new PineappleStrainer( thePineappleCan);
        PineappleChunks pineappleChunks;

        MecanumWheelRobot Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Bubbles.addServo("X-Thing");
        Bubbles.addServo("Y-Thing");
        Bubbles.getServo("X-Thing").scaleRange(0.4, 0.8);
        Bubbles.getServo("Y-Thing").scaleRange(0.33, 0.5);

        while (opModeIsActive()){
            picture = thePineappleCan.getBitmap();
            pineappleChunks = pineappleStrainer
                    .findShadedObject(80,80, picture, Color.rgb(250,200, 0), 130);
            if (pineappleChunks.doesChunkExist()) {
                Servo xthing = Bubbles.getServo("X-Thing");
                Servo ything = Bubbles.getServo("Y-Thing");
                int[] theChunk = pineappleChunks.getChunk(0);

                if (theChunk[1] < 40) {
                    Bubbles.moveServo("X-Thing", xthing.getPosition() - 0.05);
                } else if (theChunk[1] > 60) {
                    Bubbles.moveServo("X-Thing", xthing.getPosition() + 0.05);
                }

                if (theChunk[0] < 40) {
                    Bubbles.moveServo("Y-Thing", ything.getPosition() + 0.05);
                } else if (theChunk[0] > 60) {
                    Bubbles.moveServo("Y-Thing", ything.getPosition() - 0.05);
                }
            }

        }
        thePineappleCan.closeCanOfPineapple();
    }
}