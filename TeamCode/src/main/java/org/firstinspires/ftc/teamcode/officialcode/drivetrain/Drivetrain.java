package org.firstinspires.ftc.teamcode.officialcode.drivetrain;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

import java.security.PrivateKey;

/**
 * Created by Higgs Bosons on 10/22/2016.
 */
public class Drivetrain implements IDrivetrain {
    private Constants.DriveState state = Constants.DriveState.STOP;

    @Override
    public void moveDistance(int distance, double power) throws InterruptedException {

    }

    @Override
    public void rightAngleTurn(Constants.Turns direction) {

    }

    @Override
    public void joystickDrive() {

    }
}
