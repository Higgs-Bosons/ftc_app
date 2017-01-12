package org.firstinspires.ftc.teamcode.officialcode.launcher;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Interface for Launcher class
 */
public interface ILauncher {
    void fire();
    void cease();
    void handleMessage() throws InterruptedException;
    Constants.LauncherState getState();
    void setState(Constants.LauncherState launcherState);
}//interface
