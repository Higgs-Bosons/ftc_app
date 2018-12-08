package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;
import org.firstinspires.ftc.teamcode.Galaxy.MecanumWheelRobot.MecanumWheelRobot;

@Autonomous(name = "Sensor Tester", group = "Tester")
public class SensorTester extends LinearOpMode {

    public void runOpMode(){
        MecanumWheelRobot Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Bubbles.addSensor("TouchyL", TOUCH_SENSOR);
        Bubbles.addSensor("TouchyR", TOUCH_SENSOR);
        Bubbles.addSensor("imu", IMU);

        waitForStart();
        while (opModeIsActive()){
            float touchedL = Bubbles.readSensor("TouchyL",TOUCH_BOOLEAN);
            float touchedR = Bubbles.readSensor("TouchyR",TOUCH_BOOLEAN);
            float[] imuDegrees = Bubbles.ReadIMUGyro("imu");
            telemetry.addData("TouchyL", touchedL);
            telemetry.addData("TouchyR", touchedR);
            telemetry.addData("IMU Rotation", imuDegrees[0]);
            telemetry.addData("IMU Tilt 1", imuDegrees[1]);
            telemetry.addData("IMU Tilt 2", imuDegrees[2]);
            telemetry.update();
        }

    }
}
