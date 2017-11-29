package org.firstinspires.ftc.teamcode.officialcode.Autonomus;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.internal.*;
import org.firstinspires.ftc.teamcode.officialcode.Robot.*;

import static org.firstinspires.ftc.teamcode.officialcode.Constants.*;

@Autonomous(name = "Autonomous", group = "Program")
public class AutonomousCode extends LinearOpMode{
    public static CrabbingRobot Crabby;
    private String COLOR ="NULL";
    private String PositionOnField = "NULL";
    private int[][] PROGRAM;

    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        Runnable runProgram = new runProgram();
        Thread billy = new Thread(runProgram);
        billy.start();
        while(opModeIsActive()){
            telemetry.addData("ENCODER RF", Crabby.driveMotors.getRF().getCurrentPosition());
            telemetry.addData("ENCODER LF", Crabby.driveMotors.getLF().getCurrentPosition());
            telemetry.addData("ENCODER RB", Crabby.driveMotors.getRB().getCurrentPosition());
            telemetry.addData("ENCODER LB", Crabby.driveMotors.getLB().getCurrentPosition());
            telemetry.addData("GYRO",Crabby.sensors.ReadGyro());
            telemetry.update();
        }
        billy.interrupt();

    }

    public void initialize(){
        GatherInfo();

        DcMotor lf = hardwareMap.dcMotor.get("LF");
        DcMotor lb = hardwareMap.dcMotor.get("LB");
        DcMotor rf = hardwareMap.dcMotor.get("RF");
        DcMotor rb = hardwareMap.dcMotor.get("RB");

        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        rb.setDirection(DcMotorSimple.Direction.REVERSE);
        Servo FishTail = hardwareMap.servo.get("FishTail");
        Servo Jack = hardwareMap.servo.get("Jack Smith");Jack.setPosition(0.2);

        ColorSensor SuperNitron9000 = hardwareMap.colorSensor.get("SuperNitron9000");
        BNO055IMU imu;
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "imu";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        Crabby = new CrabbingRobot();
        Crabby.GiveServos(FishTail, null);
        Crabby.GiveDriveMotors(lf,rf,lb,rb);
        Crabby.GiveSensors(SuperNitron9000, imu);
        Crabby.GiveAttachmentMotors();

        WriteProgram();
        AppUtil.getInstance().showToast(UILocation.BOTH,"Ready To Run! :-)");
    }
    private void GatherInfo(){
        telemetry.addData("Alliance Color:",COLOR);
        telemetry.addData("POSITION ON FIELD:", PositionOnField);telemetry.update();
        boolean HasPosition = false;
        boolean HasColor = false;

        while(!HasPosition || !HasColor){
            if(gamepad1.x){
                COLOR = BLUE;
                HasColor = true;
            }else if(gamepad1.b){
                COLOR = RED;
                HasColor = true;
            }

            if(gamepad1.dpad_right){
                PositionOnField = "RIGHT";
                HasPosition = true;
            }else if(gamepad1.dpad_left){
                PositionOnField = "LEFT";
                HasPosition = true;
            }
            telemetry.addData("Alliance Color:",COLOR);
            telemetry.addData("POSITION ON FIELD:", PositionOnField);telemetry.update();
        }
    }
    private void WriteProgram(){
        if(PositionOnField.equals("LEFT")){
            if(COLOR.equals(RED)){
                PROGRAM = new int[][]{{RobotActions.KnockOffJewel, RobotActions.NULL},
                        {RobotActions.Turn, 340},
                        {RobotActions.Move_Forward, 30}};
            }else{
                PROGRAM = new int[][]{{RobotActions.KnockOffJewel, RobotActions.NULL},
                        {RobotActions.Turn, 20},
                        {RobotActions.Move_Backwards, 30}};
            }
        }
        if(PositionOnField.equals("RIGHT")){
            if(COLOR.equals(RED)){
                PROGRAM = new int[][]{{RobotActions.KnockOffJewel, RobotActions.NULL},
                        {RobotActions.Turn, 25},
                        {RobotActions.Move_Forward, 30}};
            }else{
                PROGRAM = new int[][]{{RobotActions.KnockOffJewel, RobotActions.NULL},
                        {RobotActions.Turn, 340},
                        {RobotActions.Move_Backwards, 30}};
            }
        }
    }

    private class runProgram implements Runnable{
        public void run(){
            ReadProgram();
            requestOpModeStop();
        }
    }
    private void ReadProgram(){
        for(int LineInProgram = 0; LineInProgram <= PROGRAM.length-1;LineInProgram++){
            if(PROGRAM[LineInProgram][0] == RobotActions.KnockOffJewel){
               Crabby.KnockOffJewel(COLOR);
            }else if(PROGRAM[LineInProgram][0] == RobotActions.Move_Forward){
                Crabby.driveMotors.Move(Forwards, PROGRAM[LineInProgram][1], 0.8);
            }else if(PROGRAM[LineInProgram][0] == RobotActions.Move_Backwards){
                Crabby.driveMotors.Move(Backwards, PROGRAM[LineInProgram][1], 0.8);
            }
            else if(PROGRAM[LineInProgram][0] == RobotActions.Turn){
                Crabby.driveMotors.Turn(PROGRAM[LineInProgram][1]);
            }
        }
    }
}
