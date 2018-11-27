package org.firstinspires.ftc.teamcode.Galaxy.Autonomus;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.*;
import org.firstinspires.ftc.teamcode.Galaxy.Robot.MecanumWheelRobot;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

@Autonomous(name = "Tester", group = "Main")
public class TestAutonomus extends LinearOpMode {

    @Override
    public void runOpMode(){
        MecanumWheelRobot BubbleTheRobo = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        BubbleTheRobo.setMotorDirection(FORWARDS,REVERSE,REVERSE,REVERSE);
        BubbleTheRobo.setBreakOrCoast(DcMotor.ZeroPowerBehavior.FLOAT);

        BubbleTheRobo.addSensor("IMU", IMU);

        CanOfPineapple thePineappleCan = new CanOfPineapple();
        waitForStart();
        Bitmap picture;
        PineappleStrainer pineappleStrainer = new PineappleStrainer( thePineappleCan);
        PineappleChunks pineappleChunks;

        while (opModeIsActive()){
            picture = thePineappleCan.getBitmap();
            pineappleChunks = pineappleStrainer
                    .findShadedObject(80,80, picture, Color.rgb(250,200, 0), 130);
            if (pineappleChunks.doesChunkExist()) {
                if (pineappleChunks.getChunk(0)[0] > 50) {
                    int degree = (int) BubbleTheRobo.ReadIMUGyro("IMU")[0] + 1;
                    BubbleTheRobo.turnGyro(degree, BubbleTheRobo.getIMU("IMU"));
                }
                if (pineappleChunks.getChunk(0)[0] < 50) {
                    int degree = (int) BubbleTheRobo.ReadIMUGyro("IMU")[0] - 1;
                    BubbleTheRobo.turnGyro(degree, BubbleTheRobo.getIMU("IMU"));
                }
            }

        }

    }
}
