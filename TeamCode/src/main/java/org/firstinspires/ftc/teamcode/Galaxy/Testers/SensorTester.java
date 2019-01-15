package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.*;
import org.firstinspires.ftc.teamcode.Galaxy.MecanumWheelRobot.MecanumWheelRobot;

@Autonomous(name = "Sensor Tester", group = "Tester")
public class SensorTester extends LinearOpMode {

    public void runOpMode(){
        MecanumWheelRobot Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Bubbles.addSensor(DistanceL, DISTANCE_SENSOR);
        Bubbles.addSensor(DistanceR, DISTANCE_SENSOR);
        Bubbles.addSensor(TouchyL, TOUCH_SENSOR);
        Bubbles.addSensor(TouchyR, TOUCH_SENSOR);

        waitForStart();
        while (opModeIsActive()){
            telemetry.addData("CM Right", Bubbles.readSensor(DistanceL, DISTANCE_IN_CENTIMETERS));
            telemetry.addData("CM Left", Bubbles.readSensor(DistanceR, DISTANCE_IN_CENTIMETERS));
            telemetry.addData("TouchyL", Bubbles.readSensor(TouchyL, TOUCH_VALUE));
            telemetry.addData("TouchyR", Bubbles.readSensor(TouchyR, TOUCH_VALUE));
            telemetry.update();
        }

    }
}
