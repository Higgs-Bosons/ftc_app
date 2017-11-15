package org.firstinspires.ftc.teamcode.officialcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
//TODO We need to aline the Liner Servo (Arm) and adjust the Up/Down movement amounts. Arm.setPosition();
//                       all the way up : 0.27 all the way down : 0.47
@TeleOp(name = "TeleOp For Cam Wheels", group = "Beacon")
public class CAMrenCode extends LinearOpMode {
    private DcMotor Left_Front;
    private DcMotor Right_Front;
    private DcMotor Left_Back;
    private DcMotor Right_Back;
    private Servo Arm;
    private void initialize() throws InterruptedException {
        Left_Front = hardwareMap.dcMotor.get("LF");
        Right_Front = hardwareMap.dcMotor.get("RF");
        Left_Back = hardwareMap.dcMotor.get("LB");
        Right_Back = hardwareMap.dcMotor.get("RB");
        Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);
        Arm = hardwareMap.servo.get("Jack Smith");

    }
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        Right_Front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Left_Front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Right_Back.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Left_Back.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Arm.setPosition(0.27);
        while(opModeIsActive()){
            Check_D_Pad();
            Check_Slide();
            Check_Servo();
        }
    }
    private void Check_D_Pad(){
        Left_Front.setPower(-gamepad1.left_stick_y);
        Left_Back.setPower(-gamepad1.left_stick_y);
        Right_Front.setPower(-gamepad1.right_stick_y);
        Right_Back.setPower(-gamepad1.right_stick_y);
    }
    private void Check_Slide(){
        if(gamepad1.dpad_right){
            while(gamepad1.dpad_right){
                Left_Front.setPower(1.0);                Right_Front.setPower(-1.0);
                Left_Back.setPower(-1.0);                Right_Back.setPower(1.0);
            }
            Left_Front.setPower(0);
            Right_Front.setPower(0);
            Left_Back.setPower(0);
            Right_Back.setPower(0);
        }

        if(gamepad1.dpad_left){
            while(gamepad1.dpad_left){
                Left_Front.setPower(-1.0);                Right_Front.setPower(1.0);
                Left_Back.setPower(1.0);                Right_Back.setPower(-1.0);
            }

        Left_Front.setPower(0);
        Right_Front.setPower(0);
        Left_Back.setPower(0);
        Right_Back.setPower(0);
       }
    }
    private void Check_Servo(){
        if(gamepad1.left_trigger >= 0.1 || gamepad1.right_trigger >= 0.1){
            Arm.setPosition(0.37);
    }
       if(gamepad1.left_bumper || gamepad1.right_bumper){
            Arm.setPosition(0.27);
        }
    }
    private void Arm_Stuff(){
        if(gamepad2.left_trigger < 0.1){

        }
        if(gamepad2.right_trigger < 0.1){

        }
    }

}

