package org.firstinspires.ftc.teamcode.Galaxy.Autonomus;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.CanOfPineapple;
import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.PineappleChunks;
import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.PineappleStrainer;
import org.firstinspires.ftc.teamcode.Galaxy.Robot.MecanumWheelRobot;
import org.firstinspires.ftc.teamcode.Galaxy.Tools;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.CRATER_ON_THE_LEFT;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.CRATER_ON_THE_RIGHT;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.FIRST_LETTER_NO_SPACE_UPPERCASE;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.FORWARDS;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.IMU;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.LEFT_SIDE_OF_THE_LANDER;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.NORTH;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.NO_TAG;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.REVERSE;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.RIGHT_SIDE_OF_THE_LANDER;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.SOUTH;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.TOUCH_SENSOR;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.TOUCH_VALUE;

@Autonomous(name = "Autonomous", group = "Autonomous")
public class MenuAutonomous extends LinearOpMode {
    private MecanumWheelRobot Bubbles;
    private CanOfPineapple canOfPineapple;
    private int cubePosition;
    private String craterToGoTo =  "  NULL";
    private String sideOfTheLander =  "  NULL";

    private int getMenuChoices(){
        while(!gamepad1.y && sideOfTheLander.equals("  NULL") && craterToGoTo.equals("  NULL")){
            telemetry.addData("WHICH CRATER TO GO TO    ", craterToGoTo);
            telemetry.addData("WHICH SIDE OF THE LANDER ", sideOfTheLander);
            telemetry.addLine("         PRESS Y TO CONTINUE  ");
            telemetry.update();
            if(gamepad1.dpad_left)
                sideOfTheLander = LEFT_SIDE_OF_THE_LANDER;
            else if(gamepad1.dpad_right)
                sideOfTheLander = RIGHT_SIDE_OF_THE_LANDER;

            if(gamepad1.x)
                craterToGoTo = CRATER_ON_THE_LEFT;
            else if(gamepad1.b)
                craterToGoTo = CRATER_ON_THE_RIGHT;
        }
    }
}


