package org.firstinspires.ftc.teamcode.Galaxy.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Galaxy.Robot.MecanumWheelRobot;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

@TeleOp(name = "TeleOp", group = "TeleOp")
public class AayushTeleOp extends LinearOpMode{

    private boolean slowed = false;
    private boolean tanked = false;

    @Override
    public void runOpMode(){

        MecanumWheelRobot mwr = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);

        mwr.setMotorDirection(FORWARDS,REVERSE,REVERSE,REVERSE);
        mwr.addAMotor("Grabby", NO_TAG);
        mwr.setBreakOrCoast(DcMotor.ZeroPowerBehavior.FLOAT);

        waitForStart();

        while (opModeIsActive()) {
            String mode;
            if (!tanked) {
                mode = "Crab";
                if (slowed)
                    mwr.driveByJoystick(gamepad1, 0.5);
                else
                    mwr.driveByJoystick(gamepad1, 1);
            } else {
                mode = "Tank";
                if (slowed)
                    mwr.driveByTank(gamepad1, 0.5);
                else
                    mwr.driveByTank(gamepad1, 1);
            }
            while (gamepad1.a)
                tanked = !tanked;
            while (gamepad1.b)
                slowed = !slowed;
            while (gamepad1.dpad_down)
                mwr.moveMotorByName("Grabby", 0.5);
            telemetry.addData("Movement Mode: ", mode);
            telemetry.addData("Slowed: ", slowed);
            telemetry.update();
        }

        mwr.stopRobot();
    }

}
