package org.firstinspires.ftc.teamcode.Galaxy.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.CanOfPineapple;
import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.PineappleChunks;
import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.PineappleStrainer;
import org.firstinspires.ftc.teamcode.Galaxy.MecanumWheelRobot.MecanumWheelRobot;
import org.firstinspires.ftc.teamcode.Galaxy.Tools;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.*;

@Autonomous(name = "New Autonomous", group = "Autonomous")
public class MainAutonomous extends LinearOpMode {
    private MecanumWheelRobot Bubbles;
    private CanOfPineapple canOfPineapple;
    private PineappleStrainer pineappleStrainer;
    private PineappleChunks pineappleChunks;

    public void runOpMode() {
        initializeRobot();
        //getMenuChoices();

        waitForStart();

        dropFromLander();
        /*
        turnAndSample();
        if (left) {driveToDepoFromLeft();}
        else {driveToDepoFromRight();}
        dropOffStuffAndDriveToCrater();
        */
    }

    private void initializeRobot(){
        Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Bubbles.resetEncoders();

        canOfPineapple = new CanOfPineapple(UPSIDE_DOWN);
        pineappleStrainer = new PineappleStrainer(canOfPineapple);

        Bubbles.addAMotor(PowerUp, NO_TAG);
        Bubbles.addAMotor(PowerDown, NO_TAG);

        Bubbles.addServo(XThing);
        Bubbles.addServo(YThing);

        Bubbles.setBreakOrCoast(DcMotor.ZeroPowerBehavior.BRAKE);
        Bubbles.setMotorDirection(FORWARDS,REVERSE,REVERSE,FORWARDS);
    }
    private void dropFromLander(){
        Bubbles.moveMotor(PowerUp, -1.0);
        Bubbles.moveMotor(PowerDown, -1.0);

        Tools.wait(1000);

        Bubbles.stopMotor(PowerDown);
        Bubbles.stopMotor(PowerUp);
    }
    private void turnAndSample() {
        //move away from hook


    }
}
