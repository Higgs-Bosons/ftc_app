package org.firstinspires.ftc.teamcode.officialcode.drivetrain;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Created by Higgs Bosons on 10/5/2016.
 */
public interface IDrivetrain {
    /**
     * Makes the robot move forward a certain distance
     * @param distance
     * @param power
     * @throws InterruptedException
     */
    void moveDistance(int distance, double power ) throws InterruptedException;

    /**
     * makes the robot turn a 90 degree angle in either the left or right direction
     * @param direction
     */
    void rightAngleTurn(Constants.Turns direction );



    void joystickDrive();
}
