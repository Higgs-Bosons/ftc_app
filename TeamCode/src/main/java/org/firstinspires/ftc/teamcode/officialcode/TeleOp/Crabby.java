package org.firstinspires.ftc.teamcode.officialcode.TeleOp;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot.OmniWheelRobot;
import org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot.Servos;

import static org.firstinspires.ftc.teamcode.officialcode.Constants.ArmLifter;
import static org.firstinspires.ftc.teamcode.officialcode.Constants.Conveyor;

@TeleOp(name = "CRABBY", group = "TeleOp")
public class Crabby extends LinearOpMode{
    public DcMotor LF,RF,LB,RB;

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
    public void move(){
        if(Math.abs(gamepad1.right_stick_y)>=0.1){
            RF.setPower(gamepad1.right_stick_y*0.8);
            RB.setPower(gamepad1.right_stick_y*0.8);
        }else{
            RF.setPower(0);
            RB.setPower(0);
        }
        if(Math.abs(gamepad1.left_stick_y)>=0.1){
            LF.setPower(gamepad1.left_stick_y*0.8);
            LB.setPower(gamepad1.left_stick_y*0.8);
        }else{
            LF.setPower(0);
            LB.setPower(0);
        }
    }

}



