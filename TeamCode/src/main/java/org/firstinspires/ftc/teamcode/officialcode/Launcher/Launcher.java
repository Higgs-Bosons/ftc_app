package org.firstinspires.ftc.teamcode.officialcode.Launcher;


import org.firstinspires.ftc.teamcode.officialcode.teleop.MyMessageQueue;
import org.firstinspires.ftc.teamcode.officialcode.teleop.TeleopMessages;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */
public class Launcher implements ILauncher, Runnable {
    private LauncherMotor launcher;
    private BlockingQueue<TeleopMessages> queue;

    public Launcher(LauncherMotor launcher){
        this.launcher = launcher;
        this.queue = MyMessageQueue.getInstance();
    }

    @Override
    public void run() {

    }
}
