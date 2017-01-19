package org.firstinspires.ftc.teamcode.officialcode.pusher;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Interface for button pusher
 */
public interface IPusher {
    void pusherMovement(double power);
    void handleMessage() throws InterruptedException;
    Constants.PusherState getState();
    void setState(Constants.PusherState state);
}//interface
