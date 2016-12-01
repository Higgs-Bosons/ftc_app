package org.firstinspires.ftc.teamcode.officialcode.teleop;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.launcher.ILauncher;
import org.firstinspires.ftc.teamcode.officialcode.lift.ILift;

/**
 * Created by Higgs Bosons on 11/30/2016.
 */
public class LauncherLiftController implements Runnable {
    ILauncher launcher;
    ILift lift;

    public LauncherLiftController(ILift lift, ILauncher launcher){
        this.launcher = launcher;
        this.lift = lift;
    }

    @Override
    public void run() {
        try{
            while (true){
                this.launcher.handleMessage();
                this.lift.handleMessage();
                Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
            }
        }catch (InterruptedException i){
            i.printStackTrace();
            System.exit(1);
        }
    }
}
