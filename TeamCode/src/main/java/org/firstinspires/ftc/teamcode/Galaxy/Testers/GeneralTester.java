package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Galaxy.MecanumWheelRobot.MecanumWheelRobot;
import org.firstinspires.ftc.teamcode.Galaxy.Tools;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

@Autonomous(name = "GeneralTester", group = "Tester")
public class GeneralTester extends LinearOpMode {


    public void runOpMode() {
        MecanumWheelRobot Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        // Bubbles.addSensor("Distance", DISTANCE_SENSOR);
        // Bubbles.readSensor("Distance", DISTANCE_IN_INCHES);
        waitForStart();
        Bubbles.driveAtHeader(50, 0.7);
        Tools.wait(100);
        Bubbles.stopRobot();
    }
}
