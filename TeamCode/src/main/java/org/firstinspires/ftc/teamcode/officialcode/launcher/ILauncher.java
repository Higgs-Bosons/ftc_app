package org.firstinspires.ftc.teamcode.officialcode.launcher;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */
public interface ILauncher {
    void fire();
    void cease();
    void handleMessage() throws InterruptedException;
    Constants.LauncherState getState();
    void setState(Constants.LauncherState launcherState);
}
