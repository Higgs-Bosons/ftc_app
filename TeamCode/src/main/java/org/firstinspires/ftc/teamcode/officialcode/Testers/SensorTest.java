package org.firstinspires.ftc.teamcode.officialcode.Testers;

import com.qualcomm.hardware.bosch.*;
import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.teamcode.officialcode.Constants;
import org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot.OmniWheelRobot;
import static org.firstinspires.ftc.teamcode.officialcode.Constants.*;

@Autonomous(name = "Sensor Test", group = "Tester")
public class SensorTest  extends LinearOpMode {
    private OmniWheelRobot Omni;
    private void initialize(){
        Omni = new OmniWheelRobot();
        initializeSensors();
        initializeServos();
        initializeMotors();
    }
    private void initializeMotors(){
        DcMotor Left_Front = hardwareMap.dcMotor.get("LF");
        DcMotor Right_Front = hardwareMap.dcMotor.get("RF");
        DcMotor Left_Back = hardwareMap.dcMotor.get("LB");
        DcMotor Right_Back = hardwareMap.dcMotor.get("RB");

        Right_Front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Right_Back.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Left_Front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Left_Back.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);

        Omni.GiveDriveMotors(Left_Front, Right_Front, Left_Back, Right_Back);

        DcMotor ArmLifter = hardwareMap.dcMotor.get("ArmLifter");
        DcMotor SlideExtender = hardwareMap.dcMotor.get("LinearSlide");
        DcMotor SlideRetracter = hardwareMap.dcMotor.get("SlideRetractor");
        DcMotor Conveyor = hardwareMap.dcMotor.get("Conveyor");

        ArmLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SlideExtender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SlideRetracter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Conveyor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Omni.GiveAttachmentMotors(ArmLifter, Conveyor, SlideExtender, SlideRetracter);
    }
    private void initializeServos(){
        Servo FishTailLifter = hardwareMap.servo.get("FishTailLifter");
        Servo FishTailSwinger = hardwareMap.servo.get("FishTailSwinger");
        Servo GrabberOne = hardwareMap.servo.get("GrabberOne");
        Servo GrabberTwo = hardwareMap.servo.get("GrabberTwo");
        Servo GrabberSpinOne = hardwareMap.servo.get("Grabber1");
        Servo GrabberSpinTwo = hardwareMap.servo.get("Grabber2");
        Servo Clampy = hardwareMap.servo.get("Clampy");
        Servo RML = hardwareMap.servo.get("RML");
        Servo Lifter = hardwareMap.servo.get("Lifter");
        Omni.GiveServos(FishTailLifter, FishTailSwinger, GrabberOne, GrabberTwo, Clampy, RML, Lifter,GrabberSpinOne, GrabberSpinTwo);


    }
    private void initializeSensors(){
        ColorSensor SuperNitron9000 = hardwareMap.colorSensor.get("SuperNitron9000");
        OpticalDistanceSensor LIGHT = hardwareMap.opticalDistanceSensor.get("Light");
        BNO055IMU imu;
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "imu";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        Omni.GiveSensors(SuperNitron9000, imu, LIGHT);
    }
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("Red    ",Omni.sensors.ReadColor(RED));
            telemetry.addData("Blue   ",Omni.sensors.ReadColor(BLUE));
            telemetry.addData("Gyro   ",Omni.sensors.ReadGyro());
            telemetry.addData("Light  ",Omni.sensors.getReflectedLight());
            telemetry.addData("RF  ",Omni.driveMotors.GetMotor(Constants.RightFront).getCurrentPosition());
            telemetry.addData("RB  ",Omni.driveMotors.GetMotor(Constants.RightBack).getCurrentPosition());
            telemetry.addData("LF  ",Omni.driveMotors.GetMotor(Constants.LeftFront).getCurrentPosition());
            telemetry.addData("LB  ",Omni.driveMotors.GetMotor(Constants.LeftBack).getCurrentPosition());
            telemetry.update();
        }
    }
}
