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
        Bubbles.addSensor("Gyro", IMU);
        telemetry.addData("READY", "");
        waitForStart();
        Bubbles.resetIMUGyro("Gyro");
        while (opModeIsActive()){
            telemetry.addData("CM Right", Bubbles.readSensor(DistanceL, DISTANCE_IN_CENTIMETERS));
            telemetry.addData("CM Left", Bubbles.readSensor(DistanceR, DISTANCE_IN_CENTIMETERS));
            telemetry.addData("TouchyL", Bubbles.readSensor(TouchyL, TOUCH_VALUE));
            telemetry.addData("TouchyR", Bubbles.readSensor(TouchyR, TOUCH_VALUE));
            telemetry.addData("IMU Axis 0", Bubbles.readIMUGyro("Gyro")[0]);
            telemetry.addData("IMU Axis 1", Bubbles.readIMUGyro("Gyro")[1]);
            telemetry.addData("IMU Axis 2", Bubbles.readIMUGyro("Gyro")[2]);
            telemetry.update();

            if(this.gamepad1.a)
                Bubbles.resetIMUGyro("Gyro");
        }

    }
}
