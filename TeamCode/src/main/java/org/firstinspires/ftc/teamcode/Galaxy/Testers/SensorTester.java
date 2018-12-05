package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;
import org.firstinspires.ftc.teamcode.Galaxy.Robot.MecanumWheelRobot;

@Autonomous(name = "Sensor Tester", group = "Tester")
public class SensorTester extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        MecanumWheelRobot Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Bubbles.addSensor("touchy",TOUCH_SENSOR);

        waitForStart();
        while (opModeIsActive()){
            float touched = Bubbles.readSensor("touchy",TOUCH_VALUE);
            telemetry.addData("touchy", touched);
            telemetry.update();
        }

    }
}
