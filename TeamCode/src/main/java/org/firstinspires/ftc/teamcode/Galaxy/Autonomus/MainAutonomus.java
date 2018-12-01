package org.firstinspires.ftc.teamcode.Galaxy.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Galaxy.Robot.MecanumWheelRobot;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

@Autonomous(name = "Autonomus", group = "Autonomus")
public class MainAutonomus extends LinearOpMode{

    public void runOpMode(){
        MecanumWheelRobot Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Bubbles.addServo("Gate");
        Bubbles.setMotorDirection(FORWARDS,REVERSE,REVERSE,FORWARDS);
        Bubbles.addAMotor("Grabby", NO_TAG);
        Bubbles.addAMotor("Lifter", NO_TAG);
        Bubbles.addServo("X-Thing");
        Bubbles.addServo("Y-Thing");
        Bubbles.getServo("X-Thing").scaleRange(0.4, 0.8);
        Bubbles.setBreakOrCoast(DcMotor.ZeroPowerBehavior.FLOAT);

        waitForStart();
        Bubbles.moveServo("X-Thing", 0.5);

    }
}
