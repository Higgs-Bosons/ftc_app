package org.firstinspires.ftc.teamcode.Galaxy.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Galaxy.Robot.MecanumWheelRobot;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

@TeleOp(name = "Aayush's TeleOp", group = "TeleOp")
public class AayushTeleOp extends LinearOpMode{

    private boolean slowed = false;
    private boolean tanked = false;

    @Override
    public void runOpMode(){

        MecanumWheelRobot mwr = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);

        mwr.useDriveTrain().setMotorDirection(REVERSE,FORWARDS,FORWARDS,REVERSE);
        mwr.useDriveTrain().setBreakOrCoast(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {
            String mode;
            if (!tanked) {
                mode = "Crab";
                if (slowed)
                    mwr.useDriveTrain().driveByJoystick(gamepad1, 0.5);
                else
                    mwr.useDriveTrain().driveByJoystick(gamepad1, 1);
            } else {
                mode = "Tank";
                if (slowed)
                    mwr.useDriveTrain().driveByTank(gamepad1, 0.5);
                else
                    mwr.useDriveTrain().driveByTank(gamepad1, 1);
            }
            while (gamepad1.a)
                tanked = !tanked;
            while (gamepad1.b)
                slowed = !slowed;
            telemetry.addData("Movement Mode: ", mode);
            telemetry.addData("Slowed: ", slowed);
            telemetry.update();
        }

        mwr.useDriveTrain().stopRobot();
    }

}
