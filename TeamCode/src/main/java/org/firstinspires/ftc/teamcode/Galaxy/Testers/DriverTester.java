package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Galaxy.MecanumWheelRobot.MecanumWheelRobot;

@Autonomous(name = "Driver Tester", group = "Tester")
public class DriverTester extends LinearOpMode {

    private boolean tanked = false;
    private String mode = "JOYSTICK DRIVE";

    public void runOpMode() {

        MecanumWheelRobot Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Bubbles.setMotorDirection(FORWARDS, FORWARDS, FORWARDS, FORWARDS);
        Bubbles.setBreakOrCoast(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                while (gamepad1.a){}
                tanked = !tanked;
                mode = (tanked) ? "TANK DRIVE" : "JOYSTICK DRIVE";
            }
            if (!tanked)
                Bubbles.driveByJoystick(gamepad1, 1);
            else
                Bubbles.driveByTank(gamepad1, 1);

            telemetry.addData("Mode:", mode);
            telemetry.update();
        }

    }
}
