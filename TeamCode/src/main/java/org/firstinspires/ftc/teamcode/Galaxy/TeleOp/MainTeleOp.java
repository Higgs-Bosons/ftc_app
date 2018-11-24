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
        BubbleTheRobo.setMotorDirection(FORWARDS,REVERSE,REVERSE,REVERSE);
        BubbleTheRobo.addAMotor("Grabby", NO_TAG);
        BubbleTheRobo.setBreakOrCoast(DcMotor.ZeroPowerBehavior.FLOAT);

        waitForStart();
        BubbleTheRobo.moveServo("Gate",.55);
        while (opModeIsActive()) {
            checkDriveMotors();
            checkButtons();
            checkGrabber();
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
        if(gamepad1.dpad_up)
            BubbleTheRobo.moveMotor("Grabby", 0.5);
        else if(gamepad1.dpad_down)
            BubbleTheRobo.moveMotor("Grabby", 0.5);
        else
            BubbleTheRobo.stopMotor("Grabby");

        if(gamepad2.a){
            gateOpen = !gateOpen;
            while (gamepad2.a){}
            if(gateOpen)
                BubbleTheRobo.moveServo("Gate",0.4);
            else
                BubbleTheRobo.moveServo("Gate", 0.55);

        }
    }
}
