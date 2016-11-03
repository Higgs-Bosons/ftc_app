package org.firstinspires.ftc.teamcode.officialcode.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

import java.security.PrivateKey;

/**
 * Created by Higgs Bosons on 10/22/2016.
 */
public class Drivetrain implements IDrivetrain, Runnable {
    private DriveMotors dMotors;

    public Drivetrain(DriveMotors dMotors){
        this.dMotors = dMotors;
        this.dMotors.getLeftFront().setDirection(DcMotor.Direction.REVERSE);
        this.dMotors.getLeftBack().setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void moveDistance(int distance, double power) throws InterruptedException {

    }

    @Override
    public void rightAngleTurn(Constants.Turns direction) {

    }

    @Override
    public void joystickDrive() {

    }

    @Override
    public void run() {

    }
}
