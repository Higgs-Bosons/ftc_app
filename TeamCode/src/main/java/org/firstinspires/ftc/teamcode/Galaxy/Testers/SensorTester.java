package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;
import org.firstinspires.ftc.teamcode.Galaxy.MecanumWheelRobot.MecanumWheelRobot;

@Autonomous(name = "Sensor Tester", group = "Tester")
public class SensorTester extends LinearOpMode {

    public void runOpMode(){
        MecanumWheelRobot Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Bubbles.addSensor("distance", DISTANCE_SENSOR);

        waitForStart();
        while (opModeIsActive()){
            telemetry.addData("CM", Bubbles.readSensor("distance", DISTANCE_IN_CENTIMETERS));
            telemetry.addData("M", Bubbles.readSensor("distance", DISTANCE_IN_METERS));
            telemetry.addData("MM", Bubbles.readSensor("distance", DISTANCE_IN_MILLIMETERS));
            telemetry.addData("IN", Bubbles.readSensor("distance", DISTANCE_IN_INCHES));
            telemetry.update();
        }

    }
}
