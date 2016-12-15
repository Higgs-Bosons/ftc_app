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
     *
     */
//    void rightAngleTurn(Constants.Turns direction ) throws InterruptedException;

    void timedAngleTurn(Constants.Turns direction, int angle) throws InterruptedException;

    void timedMove(double power, long time) throws InterruptedException;

    boolean stopAtWhiteLine(long finalWaitTime) throws InterruptedException;

    boolean stopAtBeacon(Constants.Color color, long finalWaitTime) throws InterruptedException;

    float getPowerLF();

    void setPowerLF(float powerLF);

    float getPowerLR();

    void setPowerLR(float powerLR);

    float getPowerRF();

    void setPowerRF(float powerRF);

    float getPowerRR();

    void setPowerRR(float powerRR);

    void handleMessage() throws InterruptedException;

    void joystickDrive();
}
