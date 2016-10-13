package org.firstinspires.ftc.teamcode.officialcode.drivetrain;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Created by Higgs Bosons on 10/5/2016.
 */
public interface IDrivetrain {
    void moveDistance(int distance, double power ) throws InterruptedException;
    void rightAngleTurn(Constants.Turns direction );
    void joystickDrive();
}
