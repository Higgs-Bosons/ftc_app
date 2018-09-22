package org.firstinspires.ftc.teamcode.officialcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "CRABBY", group = "TeleOp")
public class Crabby extends LinearOpMode{
    private DcMotor LF,RF,LB,RB;

    boolean tanked = false;

    //INITIALIZATION:
    private void initializeMotors(){
        LF = hardwareMap.dcMotor.get("LF");
        LB = hardwareMap.dcMotor.get("LB");
        RF = hardwareMap.dcMotor.get("RF");
        RB = hardwareMap.dcMotor.get("RB");
    }

    //SET-UP AND LOOPS:
    public void runOpMode(){
        initializeMotors();
        waitForStart();
        while(opModeIsActive())
            telemetry.addData("Tanked: ", tanked);
            telemetry.update();
            
            if (!tanked)
                moveCrab();
            else
                moveTank();
    }

    public void moveTank() {
        if (gamepad1.a) {
            while (gamepad1.a)
                tanked = false;
        }
        else {
            double left = gamepad1.left_stick_y;
            double right = gamepad1.right_stick_y;

            LF.setPower(left);
            LB.setPower(left);
            RF.setPower(right);
            RB.setPower(right);
        }
    }

    private void moveCrab(){
        if (gamepad1.a) {
            while (gamepad1.a)
                tanked = true;
        }
        else {
            double RFpower;
            double RBpower;
            double LFpower;
            double LBpower;

            int count = 0;

            double stick1X = gamepad1.left_stick_x;
            double stick1Y = gamepad1.left_stick_y;
            double stick2X = gamepad1.right_stick_x;

            if (gamepad1.left_stick_y == 0) {
                count++;
            }
            if (gamepad1.left_stick_x == 0) {
                count++;
            }
            if (gamepad1.right_stick_x == 0) {
                count++;
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

}



