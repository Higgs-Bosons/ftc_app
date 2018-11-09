package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Robots.DriveTrain;
import org.firstinspires.ftc.teamcode.Robots.Motors;
import org.firstinspires.ftc.teamcode.Robots.customErrors;

import static org.firstinspires.ftc.teamcode.Constants.*;

@TeleOp(name = "Aayush's TeleOp", group = "TeleOp")
public class AayushTeleOp extends LinearOpMode {

    private boolean slowed = false;
    private boolean tanked = false;

    @Override
    public void runOpMode(){


        Motors motor = new Motors(hardwareMap);
        DriveTrain dt = new DriveTrain(motor.getAutoDriveTrain(FIRST_LETTER_NO_SPACE_UPPERCASE));
        dt.setMotorDirection(REVERSE,FORWARDS,FORWARDS,REVERSE);
        dt.setBreakOrCoast(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {
            String mode;
            if (!tanked) {
                mode = "Crab";
                if (slowed)
                    dt.driveByJoystick(gamepad1, 0.5);
                else
                    dt.driveByJoystick(gamepad1, 1);
            } else {
                mode = "Tank";
                if (slowed)
                    dt.driveByTank(gamepad1, 0.5);
                else
                    dt.driveByTank(gamepad1, 1);
            }
            while (gamepad1.a)
                tanked = !tanked;
            while (gamepad1.b)
                slowed = !slowed;
            telemetry.addData("Movement Mode: ", mode);
            telemetry.addData("Slowed: ", slowed);
            telemetry.update();
        }

        dt.stopRobot();
    }

}
