package org.firstinspires.ftc.teamcode.Galaxy.Testers;

/*
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.CanOfPineapple;
import org.firstinspires.ftc.teamcode.Galaxy.MecanumWheelRobot.MecanumWheelRobot;
import org.firstinspires.ftc.teamcode.Galaxy.Tools;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.FIRST_LETTER_NO_SPACE_UPPERCASE;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.FORWARDS;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.IMU;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.NORTH;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.NO_TAG;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.REVERSE;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.TOUCH_SENSOR;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.Dumper;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.Gate;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.Grabby;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.Imu;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.Lifter;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.TouchyLB;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.TouchyLF;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.TouchyRB;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.TouchyRF;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.XThing;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.YThing;

@Disabled
@Autonomous(name = "Crater Park Tester", group = "Tester")
public class CraterStopOnTester extends LinearOpMode {
    private MecanumWheelRobot Bubbles;

    public void runOpMode(){
        initializeTheRobot();
        telemetry.addData("READY ","-)");
        telemetry.update();
        waitForStart();

        Bubbles.driveAtHeader(NORTH, 1, -0.1);
        Tools.wait(500);
        Bubbles.ResetIMUGyro(Imu);

        float[] imuDegrees = Bubbles.ReadIMUGyro(Imu);
        while (!((imuDegrees[1] > 3  && imuDegrees[1] < 357) ||
                 (imuDegrees[2] > 3  && imuDegrees[2] < 357) ||
                 (imuDegrees[0] > 30 && imuDegrees[0] < 330))) {
            imuDegrees = Bubbles.ReadIMUGyro(Imu);

            telemetry.addData("imuDegrees[0] ",imuDegrees[0]);
            telemetry.addData("imuDegrees[1] ",imuDegrees[1]);
            telemetry.addData("imuDegrees[2] ",imuDegrees[2]);
            telemetry.update();
        }

        Bubbles.stopRobot();

        telemetry.addData("imuDegrees[0] ",imuDegrees[0]);
        telemetry.addData("imuDegrees[1] ",imuDegrees[1]);
        telemetry.addData("imuDegrees[2] ",imuDegrees[2]);
        telemetry.update();
        while (opModeIsActive());
    }
    private void initializeTheRobot(){
        telemetry.addData("INITIALIZING","ROBOT"); telemetry.update();
        Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Bubbles.resetEncoders();

        telemetry.addData("INITIALIZING","SERVOS"); telemetry.update();
        Bubbles.addServo(Gate);
        Bubbles.addServo(Dumper);
        Bubbles.addServo(XThing);
        Bubbles.addServo(YThing);

        Bubbles.moveServo(XThing, 0.64);
        Bubbles.moveServo(YThing, 0.27);

        Bubbles.moveServo(Gate,0.55);
        Bubbles.moveServo(Dumper, 0);

        telemetry.addData("INITIALIZING","MOTORS"); telemetry.update();
        Bubbles.setBreakOrCoast(DcMotor.ZeroPowerBehavior.BRAKE);
        Bubbles.setMotorDirection(FORWARDS,REVERSE,REVERSE,FORWARDS);
        Bubbles.addAMotor(Grabby, NO_TAG);
        Bubbles.addAMotor(Lifter, NO_TAG);

        telemetry.addData("INITIALIZING","SENSORS"); telemetry.update();
        Bubbles.addSensor(TouchyLF,TOUCH_SENSOR);
        Bubbles.addSensor(TouchyRF,TOUCH_SENSOR);
        Bubbles.addSensor(TouchyLB,TOUCH_SENSOR);
        Bubbles.addSensor(TouchyRB,TOUCH_SENSOR);
        Bubbles.addSensor(Imu, IMU);
        Bubbles.ResetIMUGyro(Imu);
    }
}*/

