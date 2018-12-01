package org.firstinspires.ftc.teamcode.Galaxy.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Galaxy.Robot.MecanumWheelRobot;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

@TeleOp(name = "TeleOp", group = "TeleOp")
public class MainTeleOp extends LinearOpMode{

    private boolean slowed = false;
    private boolean tanked = false;
    private boolean gateOpen = false;
    private MecanumWheelRobot BubbleTheRobo;
    private String mode = "JOYSTICK DRIVE";
    
    @Override
    public void runOpMode(){
        BubbleTheRobo = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        BubbleTheRobo.addServo("Gate");
        BubbleTheRobo.setMotorDirection(FORWARDS,REVERSE,REVERSE,FORWARDS);
        BubbleTheRobo.addAMotor("Grabby", NO_TAG);
        BubbleTheRobo.addAMotor("Lifter", NO_TAG);
        BubbleTheRobo.addServo("X-Thing");
        BubbleTheRobo.addServo("Y-Thing");
        BubbleTheRobo.setBreakOrCoast(DcMotor.ZeroPowerBehavior.FLOAT);

        waitForStart();

        BubbleTheRobo.moveServo("X-Thing", 0.6);
        BubbleTheRobo.moveServo("Y-Thing", 0.47);
        BubbleTheRobo.moveServo("Gate",.55);
        while (opModeIsActive()) {
            checkDriveMotors();
            checkButtons();
            checkGrabber();
            checkLifter();
            telemetry.addData("Movement Mode: ", mode);
            telemetry.addData("Slowed: ", slowed);
            telemetry.update();
        }

        BubbleTheRobo.stopRobot();
    }
    
    private void checkDriveMotors(){
        double speed = (slowed) ? 0.5 : 1;
        if (!tanked) 
             BubbleTheRobo.driveByJoystick(gamepad1, speed);
         else
             BubbleTheRobo.driveByTank(gamepad1, speed);
    } 
    private void checkButtons(){
        if(gamepad1.a) {
            while (gamepad1.a){}
            tanked = !tanked;
            mode = (tanked) ? "TANK DRIVE" : "JOYSTICK DRIVE";
        }
        if(gamepad1.b) {
            while (gamepad1.b){}
            slowed = !slowed;
        }
    }
    private void checkGrabber(){
        BubbleTheRobo.moveMotor("Grabby", gamepad2.left_stick_y);
        if(gamepad2.a)
            BubbleTheRobo.moveServo("Gate",0.4);
        else
            BubbleTheRobo.moveServo("Gate", 0.55);
    }
    private void checkLifter(){
        if(gamepad2.dpad_up)
            BubbleTheRobo.moveMotor("Lifter",1);
        else if(gamepad2.dpad_down)
            BubbleTheRobo.moveMotor("Lifter",-1);
        else
            BubbleTheRobo.stopMotor("Lifter");
    }
}
