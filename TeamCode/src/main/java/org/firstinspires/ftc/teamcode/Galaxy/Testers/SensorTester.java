package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;
import org.firstinspires.ftc.teamcode.Galaxy.MecanumWheelRobot.MecanumWheelRobot;

@Autonomous(name = "Sensor Tester", group = "Tester")
public class SensorTester extends LinearOpMode {

    public void runOpMode(){
        MecanumWheelRobot Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Bubbles.addSensor("TouchyLF", TOUCH_SENSOR);
        Bubbles.addSensor("TouchyRF", TOUCH_SENSOR);
        Bubbles.addSensor("TouchyLB", TOUCH_SENSOR);
        Bubbles.addSensor("TouchyRB", TOUCH_SENSOR);
        Bubbles.addSensor("imu", IMU);

        waitForStart();
        while (opModeIsActive()){
            float touchedLF = Bubbles.readSensor("TouchyLF",TOUCH_BOOLEAN);
            float touchedRF = Bubbles.readSensor("TouchyRF",TOUCH_BOOLEAN);
            float touchedLB = Bubbles.readSensor("TouchyLB",TOUCH_BOOLEAN);
            float touchedRB = Bubbles.readSensor("TouchyRB",TOUCH_BOOLEAN);
            float[] imuDegrees = Bubbles.ReadIMUGyro("imu");
            telemetry.addData("TouchyLF", touchedLF);
            telemetry.addData("TouchyRF", touchedRF);
            telemetry.addData("TouchyLB", touchedLB);
            telemetry.addData("TouchyRB", touchedRB);
            telemetry.addData("IMU Rotation", imuDegrees[0]);
            telemetry.addData("IMU Tilt 1", imuDegrees[1]);
            telemetry.addData("IMU Tilt 2", imuDegrees[2]);
            telemetry.update();
        }

    }
}
