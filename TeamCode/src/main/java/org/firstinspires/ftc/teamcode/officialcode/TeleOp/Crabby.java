package org.firstinspires.ftc.teamcode.officialcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "CRABBY", group = "TeleOp")
public class Crabby extends LinearOpMode{
    private DcMotor LF,RF,LB,RB;

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
        while(opModeIsActive()){move();}
    }
    private void move(){
        double RFpower;
        double RBpower;
        double LFpower;
        double LBpower;

        RFpower = (gamepad1.left_stick_x+gamepad1.left_stick_y)/2;
        RBpower = (gamepad1.left_stick_x+gamepad1.left_stick_y)/2;
        LFpower = -(gamepad1.left_stick_x+gamepad1.left_stick_y)/2;
        LBpower = -(gamepad1.left_stick_x+gamepad1.left_stick_y)/2;

        RF.setPower(RFpower);
        RB.setPower(RBpower);
        LF.setPower(LFpower);
        LB.setPower(LBpower);


    }

}



