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
    private MecanumWheelRobot BubbleTheRobo;
    private String mode;
    
    @Override
    public void runOpMode(){
        BubbleTheRobo = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);

        BubbleTheRobo.setMotorDirection(FORWARDS,REVERSE,REVERSE,REVERSE);
        BubbleTheRobo.addAMotor("Grabby", NO_TAG);
        BubbleTheRobo.setBreakOrCoast(DcMotor.ZeroPowerBehavior.FLOAT);

        waitForStart();

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
        if(gamepad2.left_stick_y > 0)
            BubbleTheRobo.moveMotor("Grabby", 1);
        else if(gamepad2.left_stick_y < 0)
            BubbleTheRobo.moveMotor("Grabby", -1);
        else
            BubbleTheRobo.stopMotor("Grabby");
    }
}
