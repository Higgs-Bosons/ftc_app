package org.firstinspires.ftc.teamcode.officialcode.teleop;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.launcher.ILauncher;
import org.firstinspires.ftc.teamcode.officialcode.lift.ILift;
import org.firstinspires.ftc.teamcode.officialcode.servos.IServos;
import org.firstinspires.ftc.teamcode.officialcode.servos.MyServos;

/**
 * Created by Higgs Bosons on 11/30/2016.
 */
public class LauncherLiftController implements Runnable {
    private ILauncher launcher;
    private ILift lift;
    private IServos ballGrabber;
    private IServos ballLoader;
    private IServos capGrabber;


    public LauncherLiftController(ILift lift, ILauncher launcher, MyServos ballGrabber,
                                  MyServos ballLoader, MyServos capGrabber){
        this.launcher = launcher;
        this.lift = lift;
        this.ballGrabber = ballGrabber.getBallGrabber();
        this.ballLoader = ballLoader.getBallLoader();
        this.capGrabber = capGrabber.getCapGrabber();
    }

    @Override
    public void run() {
        try{
            while (true){
                this.launcher.handleMessage();
                this.lift.handleMessage();
                this.ballGrabber.handleMessage();
                this.ballLoader.handleMessage();
                this.capGrabber.handleMessage();
                Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
            }
        }catch (InterruptedException i){
            i.printStackTrace();
        }
    }
}
