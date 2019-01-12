package org.firstinspires.ftc.teamcode.Galaxy.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Galaxy.MecanumWheelRobot.MecanumWheelRobot;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

@TeleOp(name = "TeleOp", group = "TeleOp")
public class MainTeleOp extends LinearOpMode{

    private boolean tanked = false;
    private MecanumWheelRobot BubbleTheRobo;
    private int speed = 80;
    private String mode = "JOYSTICK DRIVE";
    
    @Override
    public void runOpMode(){
        initializeTheRobot();

        waitForStart();

        while (opModeIsActive()) {
            checkDriveMotors();
            checkLifter();
            checkSlides();
            checkSettings();
            telemetry.addData("Movement Mode ", mode);
            telemetry.addData("Speed", speed + "/100");
            telemetry.addData("Lifter Tick Count", BubbleTheRobo.getMotorTickCount("PowerUp"));
            telemetry.addData("VSlide Tick Count", BubbleTheRobo.getMotorTickCount("VSlide"));
            telemetry.update();
        }

        BubbleTheRobo.stopRobot();
    }
    private void initializeTheRobot(){
        BubbleTheRobo = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        BubbleTheRobo.addAMotor("PowerUp", NO_TAG);
        BubbleTheRobo.addAMotor("PowerDown", NO_TAG);
        BubbleTheRobo.addAMotor("VSlide", NO_TAG);
        BubbleTheRobo.setMotorZeroPowerMode("PowerUp", DcMotor.ZeroPowerBehavior.BRAKE);
        BubbleTheRobo.setMotorZeroPowerMode("PowerDown", DcMotor.ZeroPowerBehavior.BRAKE);
        BubbleTheRobo.setMotorZeroPowerMode("VSlide", DcMotor.ZeroPowerBehavior.BRAKE);


        BubbleTheRobo.setMotorDirection(REVERSE, FORWARDS, FORWARDS, REVERSE);
        BubbleTheRobo.setBreakOrCoast(DcMotor.ZeroPowerBehavior.FLOAT);
    }
    
    private void checkDriveMotors(){
        if (!tanked) 
             BubbleTheRobo.driveByJoystick(gamepad1, (speed/100.0));
         else
             BubbleTheRobo.driveByTank(gamepad1, (speed/100.0));
    }
    private void checkLifter(){
        if(!gamepad1.a){
            if(gamepad1.dpad_up){
                BubbleTheRobo.moveMotor("PowerUp", -1.0);
                BubbleTheRobo.moveMotor("PowerDown", -1.0);
            }else if(gamepad1.dpad_down){
                BubbleTheRobo.moveMotor("PowerUp", 1.0);
                BubbleTheRobo.moveMotor("PowerDown", 1.0);
            }else{
                BubbleTheRobo.stopMotor("PowerUp");
                BubbleTheRobo.stopMotor("PowerDown");
            }
        }
    }
    private void checkSettings(){
        if(gamepad1.a){
            if(gamepad1.dpad_up){
                speed += 5;
            }else if(gamepad1.dpad_down){
                speed -= 5;
            }
            speed = (speed > 100) ? 100 : speed;
            speed = (speed < 0)   ? 0   : speed;
        }
        if(gamepad1.b){
            while (gamepad1.b);
            mode = (mode.equals("JOYSTICK DRIVE")) ? "TANK DRIVE" : "JOYSTICK DRIVE";
            tanked = !tanked;

        }

    }
    private void checkSlides(){
        if(gamepad2.dpad_up){
            BubbleTheRobo.moveMotor("VSlide", 0.7);
        }else if(gamepad2.dpad_down){
            BubbleTheRobo.moveMotor("VSlide", -0.7);
        }else{
            BubbleTheRobo.stopMotor("VSlide");
        }
    }
}
