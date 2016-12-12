package org.firstinspires.ftc.teamcode.officialcode.lift;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */
public interface ILift {
    void ascend();
    void descend();
    void stop();
    void handleMessage() throws InterruptedException;
    Constants.LiftState getState();
    void setState(Constants.LiftState liftState);
}
