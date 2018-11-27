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
        MecanumWheelRobot Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Bubbles.setMotorDirection(FORWARDS,REVERSE,REVERSE,REVERSE);
        Bubbles.setBreakOrCoast(DcMotor.ZeroPowerBehavior.FLOAT);

        Bubbles.addSensor("IMU", IMU);

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
                    int degree = (int) Bubbles.ReadIMUGyro("IMU")[0] + 1;
                    Bubbles.turnGyro(degree, Bubbles.getIMU("IMU"));
                }
                if (pineappleChunks.getChunk(0)[0] < 50) {
                    int degree = (int) Bubbles.ReadIMUGyro("IMU")[0] - 1;
                    Bubbles.turnGyro(degree, Bubbles.getIMU("IMU"));
                }
            }

            
        }

    }
}
