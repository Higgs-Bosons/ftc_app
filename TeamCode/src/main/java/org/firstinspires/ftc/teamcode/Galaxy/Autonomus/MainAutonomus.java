package org.firstinspires.ftc.teamcode.Galaxy.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Galaxy.Robot.MecanumWheelRobot;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

@Autonomous(name = "Autonomus", group = "Autonomus")
public class MainAutonomus extends LinearOpMode{

    public void runOpMode(){
        MecanumWheelRobot Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Bubbles.addServo("Gate");
        Bubbles.setMotorDirection(FORWARDS,REVERSE,REVERSE,FORWARDS);
        AutonomousCode autonomousCode = new AutonomousCode(Bubbles);

        autonomousCode.addAction(DRIVE_ROBOT, NORTH, 720, 0.7f, 0.1f, 10);

        waitForStart();

        autonomousCode.runProgram();

        while (opModeIsActive()){}

        autonomousCode.stopProgram();
        Bubbles.stopRobot();
    }
}
