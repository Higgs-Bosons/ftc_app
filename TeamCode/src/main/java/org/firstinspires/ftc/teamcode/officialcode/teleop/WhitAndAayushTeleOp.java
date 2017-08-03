package org.firstinspires.ftc.teamcode.officialcode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

@TeleOp(name="Crabby", group="TeleOp")
public class WhitAndAayushTeleOp extends LinearOpMode {
//-----{ MOTORS }-----------------------------------------------------------------------------------
    private DcMotor north;
    private DcMotor east;
    private DcMotor south;
    private DcMotor west;
    private GyroSensor bob;
    private void initialize() throws InterruptedException {

    }//initialize
    public void runOpMode() throws InterruptedException {
        //initialization
        this.initialize();

        //wait for play button
        waitForStart();

        //try to start threads
        try {
            play();
        } catch (Exception e) {
            e.printStackTrace();
        }//try-catch

        //sleep while op mode is running and interrupt threads when stopping it
        try {
            while (opModeIsActive()) {
                Thread.sleep(5000);
                idle();
            }
        } catch (InterruptedException e) {
            //System.out.println("Interrupting Threads");
        }//try-catch
    }//runOpMode
//-----{ MOVEMENT PART }----------------------------------------------------------------------------
    private void play() throws InterruptedException {
        waitForStart();
        north = hardwareMap.dcMotor.get("MOTORNW");
        east = hardwareMap.dcMotor.get("MOTORSW");
        south = hardwareMap.dcMotor.get("MOTORNE");
        west = hardwareMap.dcMotor.get("MOTORSE");

        north.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        east.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        south.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        west.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        if (north == null) {
            System.exit(0);
        }
        north.setDirection(DcMotor.Direction.REVERSE);
        east.setDirection(DcMotor.Direction.REVERSE);


        while (opModeIsActive()) {

            float motor1Power = this.gamepad1.left_stick_y;
            float motor2Power = this.gamepad1.left_stick_x;

            north.setPower(motor1Power);
            south.setPower(motor1Power);
            east.setPower(motor2Power);
            west.setPower(motor2Power);

            if (gamepad1.right_stick_x >= 0.1){
                while(gamepad1.right_stick_x >= 0.1){
                    north.setPower(-gamepad1.right_stick_x);
                    south.setPower(gamepad1.right_stick_x);
                    east.setPower(-gamepad1.right_stick_x);
                    west.setPower(gamepad1.right_stick_x);
                }
                north.setPower(0);
                south.setPower(0);
                east.setPower(0);
                west.setPower(0);

            }else if(gamepad1.right_stick_x <= -0.1){
                while(gamepad1.right_stick_x <= -0.1){
                    north.setPower(-gamepad1.right_stick_x);
                    south.setPower(gamepad1.right_stick_x);
                    east.setPower(-gamepad1.right_stick_x);
                    west.setPower(gamepad1.right_stick_x);
                }
                north.setPower(0);
                south.setPower(0);
                east.setPower(0);
                west.setPower(0);
            }
            bump();
            pad();
            idle();
        }
    }
    private void bump() {
        if (this.gamepad1.right_bumper) {
            while (this.gamepad1.right_bumper) { //hello whit, what's fucking gooooooood
                north.setPower(-0.5);
                south.setPower(0.5);
                east.setPower(-0.5);
                west.setPower(0.5);
            }
            north.setPower(0);
            south.setPower(0);
            east.setPower(0);
            west.setPower(0);
            return;
        } else if (this.gamepad1.left_bumper) {
            while (this.gamepad1.left_bumper) {
                north.setPower(0.5);
                south.setPower(-0.5);
                east.setPower(0.5);
                west.setPower(-0.5);
            }
            north.setPower(0);
            south.setPower(0);
            east.setPower(0);
            west.setPower(0);
            return;
        }
        else if (this.gamepad1.right_trigger >0) {
            while (this.gamepad1.right_trigger > 0) {
                north.setPower(1);
                south.setPower(-1);
                east.setPower(1);
                west.setPower(-1);
            }
            north.setPower(0);
            south.setPower(0);
            east.setPower(0);
            west.setPower(0);
            return;
        }
        else if (this.gamepad1.left_trigger >0) {
                while (this.gamepad1.left_trigger>0) {
                north.setPower(-1);
                south.setPower(1);
                east.setPower(-1);
                west.setPower(1);
            }
            north.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            north.setPower(0);
            south.setPower(0);
            east.setPower(0);
            west.setPower(0);
            return;
        }
        north.setPower(0);
        south.setPower(0);
        east.setPower(0);
        west.setPower(0);
    }
    private void pad(){

        if (this.gamepad1.dpad_down) {
        while (this.gamepad1.dpad_down) {
            double Power = getPower();
            north.setPower(Power);
            south.setPower(Power);
            east.setPower(Power);
            west.setPower(Power);
        }
        north.setPower(0);
        south.setPower(0);
        east.setPower(0);
        west.setPower(0);
            return;
    } else if (this.gamepad1.dpad_up) {
        while (this.gamepad1.dpad_up) {
            double Power = getPower();
            north.setPower(-Power);
            south.setPower(-Power);
            east.setPower(-Power);
            west.setPower(-Power);
        }
        north.setPower(0);
        south.setPower(0);
        east.setPower(0);
        west.setPower(0);
            return;
    }
    else if (this.gamepad1.dpad_right) {
        while (this.gamepad1.dpad_right) {
            double Power = getPower();
            north.setPower(-Power);
            south.setPower(-Power);
            east.setPower(Power);
            west.setPower(Power);
        }
        north.setPower(0);
        south.setPower(0);
        east.setPower(0);
        west.setPower(0);
            return;
    } else if (this.gamepad1.dpad_left) {
            while (this.gamepad1.dpad_left) {
                double Power = getPower();
                north.setPower(Power);
                south.setPower(Power);
                east.setPower(-Power);
                west.setPower(-Power);
            }
            north.setPower(0);
            south.setPower(0);
            east.setPower(0);
            west.setPower(0);
            return;
        }
        north.setPower(0);
        south.setPower(0);
        east.setPower(0);
        west.setPower(0);

    }
    private double getPower(){
        if(gamepad1.x){
            return 0.3;
        }else{return 1;}
    }

}
