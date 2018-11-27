package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;
import org.firstinspires.ftc.teamcode.Galaxy.Robot.MecanumWheelRobot;

@Autonomous(name = "DriveTrain Tester", group = "Tester")
public class DriveTrainTester extends LinearOpMode {
    public void runOpMode(){
        MecanumWheelRobot Robo = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Robo.setMotorDirection(FORWARDS,REVERSE,REVERSE,REVERSE);
        waitForStart();
        while (opModeIsActive()){
           Robo.moveDriveMotor(LEFT_BACK, 0.2);
        }
        Robo.stopRobot();
    }
}
