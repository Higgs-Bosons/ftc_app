package org.firstinspires.ftc.teamcode.officialcode.pusher;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Created by Higgs Bosons on 10/19/2016.
 */
public interface IPusher {
    void pusherMovement(double power);
    void handleMessage() throws InterruptedException;
    Constants.PusherState getState();
    void setState(Constants.PusherState state);
}
