package org.firstinspires.ftc.teamcode.Galaxy.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Galaxy.MecanumWheelRobot.MecanumWheelRobot;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.FIRST_LETTER_NO_SPACE_UPPERCASE;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.FORWARDS;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.NO_TAG;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.REVERSE;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.ArmY;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.BucketDumper;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.Grabby;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.Gyro;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.HSlide;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.Holder;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.PowerDown;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.PowerUp;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.Rampy;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.Spinny;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.VSlide;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.WeightLifter;


@TeleOp(name = "TeleOp", group = "TeleOp")
public class ScreenTeleOp extends LinearOpMode{

    private boolean isArmUp = true;
    private boolean tanked = false;
    private MecanumWheelRobot BubbleTheRobo;
    private boolean aiControls = true;
    private int speed = 80;
    private int rampPosition = 2;
    private int bucketPosition = 1;
    private int counterOfLiftingRobot = 0;
    private int screenPos = 1;
    private String mode = "JOYSTICK DRIVE";
    

    public void runOpMode(){
        initializeTheRobot();

        telemetry.setCaptionValueSeparator("");
        telemetry.addData("Ready to Rumble!", "");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            checkDriveMotors();
            checkLifter();
            checkDumper();
            checkSettings();
            checkGatherer();

            updateTelemetry();
        }

        BubbleTheRobo.stopRobot();
    }
    private void initializeTheRobot(){
        BubbleTheRobo = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        BubbleTheRobo.addAMotor(PowerUp, NO_TAG);
        BubbleTheRobo.addAMotor(PowerDown, NO_TAG);
        BubbleTheRobo.addAMotor(VSlide, NO_TAG);
        BubbleTheRobo.addAMotor(HSlide, NO_TAG);

        BubbleTheRobo.addServo(Holder);
        BubbleTheRobo.addServo(Grabby);
        BubbleTheRobo.addServo(WeightLifter);
        BubbleTheRobo.addServo(Spinny);
        BubbleTheRobo.addServo(ArmY);
        BubbleTheRobo.addServo(Rampy);
        BubbleTheRobo.addServo(BucketDumper);

        BubbleTheRobo.setMotorZeroPowerMode(PowerUp, DcMotor.ZeroPowerBehavior.BRAKE);
        BubbleTheRobo.setMotorZeroPowerMode(PowerDown, DcMotor.ZeroPowerBehavior.BRAKE);
        BubbleTheRobo.setMotorZeroPowerMode(VSlide, DcMotor.ZeroPowerBehavior.BRAKE);
        BubbleTheRobo.setMotorZeroPowerMode(HSlide, DcMotor.ZeroPowerBehavior.BRAKE);


        BubbleTheRobo.setMotorDirection(FORWARDS, REVERSE, REVERSE, FORWARDS);
        BubbleTheRobo.setBreakOrCoast(DcMotor.ZeroPowerBehavior.FLOAT);

        BubbleTheRobo.moveServo(Grabby, 0.6);
        BubbleTheRobo.moveServo(WeightLifter, 0.7);
        BubbleTheRobo.moveServo(BucketDumper, 0);
        BubbleTheRobo.getMotorByName(PowerDown).setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BubbleTheRobo.getMotorByName(PowerUp).setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void updateTelemetry(){
        if (gamepad1.left_bumper) {
            screenPos = (screenPos == 1) ? 3 : --screenPos;
            while (gamepad1.left_bumper);
        }
        if (gamepad1.right_bumper) {
            screenPos = (screenPos == 3) ? 1 : ++screenPos;
            while (gamepad1.right_bumper);
        }

        switch (screenPos) {
            case 1:
                telemetry.addData("Screen:", "1: Controls");
                break;
            case 2:
                telemetry.addData("Screen:", "2: Sensors");
                break;
        }

        telemetry.addData("--------------------", "");
        telemetry.addData("Movement Mode : ", mode);
        telemetry.addData("Speed : ", speed + "/100");
        telemetry.update();
    }

    private void checkDriveMotors(){
        if (!tanked) 
             BubbleTheRobo.driveByJoystick(gamepad1, (speed/100.0));
         else
             BubbleTheRobo.driveByTank(gamepad1, (speed/100.0));
    }
    private void checkLifter(){
        if(!gamepad1.a){
            if(gamepad1.dpad_down){
                BubbleTheRobo.moveMotor(PowerUp, -1.0);
                BubbleTheRobo.moveMotor(PowerDown, -1.0);
            }else if(gamepad1.dpad_up){
                BubbleTheRobo.moveMotor(PowerUp, 1.0);
                BubbleTheRobo.moveMotor(PowerDown, 1.0);
                counterOfLiftingRobot ++;
                if(counterOfLiftingRobot == 20){
                    BubbleTheRobo.moveServo(Holder, 0);
                }
            }else{
                counterOfLiftingRobot = 0;
                BubbleTheRobo.stopMotor(PowerUp);
                BubbleTheRobo.stopMotor(PowerDown);
            }
        }
        if (gamepad1.x) {
            isArmUp = !isArmUp;
            BubbleTheRobo.moveServo(Holder, (isArmUp) ? 1 : 0);
            while (gamepad1.x);
        }
    }
    private void checkSettings(){
        if(gamepad1.a){

            if(gamepad1.dpad_up){
                long startTime = System.currentTimeMillis(), currentTime;
                speed += 5;
                while (gamepad1.dpad_up){
                    updateTelemetry();
                    currentTime = System.currentTimeMillis();
                    if(startTime < currentTime - 1000){
                        speed += 5;
                        speed = (speed > 100) ? 100 : speed;
                        startTime += 250L;
                    }
                }

            }else if(gamepad1.dpad_down){
                long startTime = System.currentTimeMillis(), currentTime;
                speed -= 5;
                while (gamepad1.dpad_up){
                    updateTelemetry();
                    currentTime = System.currentTimeMillis();
                    if(startTime < currentTime - 1000){
                        speed -= 5;
                        speed = (speed > 100) ? 100 : speed;
                        startTime += 250L;
                    }
                }
            }

        }
        if(gamepad1.b){
            mode = (mode.equals("JOYSTICK DRIVE")) ? "TANK DRIVE" : "JOYSTICK DRIVE";
            tanked = !tanked;
            updateTelemetry();
            while (gamepad1.b);
        }

        if(gamepad1.y || gamepad2.y){
            aiControls = !aiControls;
            updateTelemetry();
            while(gamepad1.y || gamepad2.y);
        }

    }

    private void checkAIControls(){
        if(aiControls){
            if(!gamepad1.a){
                counterOfLiftingRobot ++;
                if(counterOfLiftingRobot == 20){
                    BubbleTheRobo.moveServo(Holder, 0);
                }
            }else counterOfLiftingRobot = 0;

            
        }
    }
    private void checkDumper(){
        if(gamepad2.dpad_up){
            if (BubbleTheRobo.getMotorTickCount(VSlide) >= -1400)
                BubbleTheRobo.moveMotor(VSlide, -0.7);
            else
                BubbleTheRobo.stopMotor(VSlide);
        }else if(gamepad2.dpad_down){
            BubbleTheRobo.moveMotor(VSlide, 0.7);
        }else{
            BubbleTheRobo.stopMotor(VSlide);
        }

        if (gamepad2.x) {
            bucketPosition = (bucketPosition == 3) ? 1 : ++bucketPosition;
            switch (bucketPosition) {
                case 1: BubbleTheRobo.moveServo(BucketDumper, 0); break;
                case 2: BubbleTheRobo.moveServo(BucketDumper, 0.45); break;
                case 3: BubbleTheRobo.moveServo(BucketDumper, 1); break;
            }

            while (gamepad2.x);
        }
    }
    private void checkGatherer(){
        BubbleTheRobo.moveServo(Spinny, ((-gamepad2.left_stick_y + 1) / 2.0));

        if(gamepad2.right_stick_y > 0.1){
            BubbleTheRobo.moveServo(ArmY, 0.26);
        }else if(gamepad2.right_stick_y < -0.1){
            BubbleTheRobo.moveServo(ArmY, 0.745);
        }else {
            BubbleTheRobo.moveServo(ArmY, 0.5);
        }


        if(gamepad2.dpad_up){
            if (BubbleTheRobo.getMotorTickCount(VSlide) >= -1400)
                BubbleTheRobo.moveMotor(VSlide, -0.7);
            else
                BubbleTheRobo.stopMotor(VSlide);
        }else if(gamepad2.dpad_down){
            BubbleTheRobo.moveMotor(VSlide, 0.7);
        }else{
            BubbleTheRobo.stopMotor(VSlide);
        }

        BubbleTheRobo.moveMotor(HSlide, -gamepad2.left_stick_x);


        if(gamepad2.a){
            rampPosition++;
            rampPosition = (rampPosition == 4) ? 1 : rampPosition;

            if(rampPosition == 1){
                BubbleTheRobo.moveServo(Rampy, .425);
            }else if(rampPosition == 2){
                BubbleTheRobo.moveServo(Rampy, .495);
            }else{
                BubbleTheRobo.moveServo(Rampy, .51);
            }

            while (gamepad2.a);
        }
    }
}
