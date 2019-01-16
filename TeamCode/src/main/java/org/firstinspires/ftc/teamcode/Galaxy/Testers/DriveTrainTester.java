package org.firstinspires.ftc.teamcode.Galaxy.Testers;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Galaxy.MecanumWheelRobot.MecanumWheelRobot;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

@Autonomous(name = "Drive Train Tester", group = "Tester")
public class DriveTrainTester extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException{
        MecanumWheelRobot mwr = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        try{
            mwr.setMotorDirection(FORWARDS, REVERSE, REVERSE, FORWARDS);
            waitForStart();
            mwr.moveRobot(WEST, 6.0,  0, 0.8, 0.1, 2.0, telemetry);
            mwr.moveRobot(EAST, 6.0,  0, 0.8, 0.1, 2.0, telemetry);
            mwr.moveRobot(SOUTH, 6.0, 0, 0.8, 0.1, 2.0, telemetry);
            mwr.moveRobot(NORTH, 6.0, 0, 0.8, 0.1, 2.0, telemetry);
        }finally {
            mwr.stopRobot();
        }

    }
}
