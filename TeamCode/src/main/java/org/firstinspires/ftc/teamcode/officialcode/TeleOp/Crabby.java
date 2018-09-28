package org.firstinspires.ftc.teamcode.officialcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

// pineapple

@TeleOp(name = "CRABBY", group = "TeleOp")
public class Crabby extends LinearOpMode {
    private DcMotor LF, RF, LB, RB;

    boolean slowed = false;

    boolean tanked = false;
    String mode = "Crab";

    //INITIALIZATION:
    private void initializeMotors() {
        LF = hardwareMap.dcMotor.get("LF");
        LB = hardwareMap.dcMotor.get("LB");
        RF = hardwareMap.dcMotor.get("RF");
        RB = hardwareMap.dcMotor.get("RB");
    }

    //SET-UP AND LOOPS:
    public void runOpMode() {
        initializeMotors();
        waitForStart();

        while (opModeIsActive()) {

            if (!tanked) {
                mode = "Crab";

                moveCrab();
            } else {
                mode = "Tank";
                moveTank();

            }
        }



    }
    public void moveTank(){
        telemetry.addData("Movement Mode: ", mode);
        telemetry.addData("Slowed: ", slowed);
        telemetry.update();

        while (gamepad1.a)
            tanked = false;
        while (gamepad1.b)
            slowed = !slowed;

        double left;
        double right;

        if (!slowed) {
            left = -gamepad1.left_stick_y;
            telemetry.addData("Left Power", left);
            telemetry.update();
            right = gamepad1.right_stick_y;
        }
        else {
            left = -gamepad1.left_stick_y/2;
            telemetry.addData("Left Power", left);
            telemetry.update();
            right = (gamepad1.right_stick_y)/2;
        }

        LF.setPower(left);
        LB.setPower(left);
        RF.setPower(right);
        RB.setPower(right);

    }

    private void moveCrab () {

        while (gamepad1.a)
            tanked = true;
        while (gamepad1.b)
            slowed = !slowed;

        telemetry.addData("Movement Mode: ", mode);
        telemetry.addData("Slowed: ", slowed);
        telemetry.update();

        double RFpower;
        double RBpower;
        double LFpower;
        double LBpower;

        double stick1X;
        double stick1Y;
        double stick2X;

        if (!slowed) {
            stick1X = gamepad1.left_stick_x;
            stick1Y = gamepad1.left_stick_y;
            stick2X = gamepad1.right_stick_x;
        }
        else {
            stick1X = gamepad1.left_stick_x / 2;
            stick1Y = gamepad1.left_stick_y / 2;
            stick2X = gamepad1.right_stick_x / 2;
        }

        RFpower = (stick1Y + stick1X) / 2;
        RBpower = (stick1Y - stick1X) / 2;
        LFpower = -(stick1Y - stick1X) / 2;
        LBpower = -(stick1Y + stick1X) / 2;

        RFpower = (RFpower + stick2X) / 2;
        RBpower = (RBpower + stick2X) / 2;
        LFpower = (LFpower + stick2X) / 2;
        LBpower = (LBpower + stick2X) / 2;

        RFpower *= 4;
        RBpower *= 4;
        LFpower *= 4;
        LBpower *= 4;

        RF.setPower(RFpower);
        RB.setPower(RBpower);
        LF.setPower(LFpower);
        LB.setPower(LBpower);
        }

    }
