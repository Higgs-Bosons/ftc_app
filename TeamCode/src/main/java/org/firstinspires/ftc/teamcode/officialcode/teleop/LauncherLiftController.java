package org.firstinspires.ftc.teamcode.officialcode.teleop;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.launcher.ILauncher;
import org.firstinspires.ftc.teamcode.officialcode.lift.ILift;
import org.firstinspires.ftc.teamcode.officialcode.servos.IServos;
import org.firstinspires.ftc.teamcode.officialcode.servos.MyServos;

/**
 * Class for running launcher and lift in a thread
 */
public class LauncherLiftController implements Runnable {
    //declare launcher, lift, and their servos
    private ILauncher launcher;
    private ILift lift;
    private IServos ballGrabber;
    private IServos ballLoader;
    private IServos capGrabber;

    /**
     * initialize variables
     * @param lift
     * @param launcher
     * @param ballGrabber
     * @param ballLoader
     * @param capGrabber
     */
    public LauncherLiftController(ILift lift, ILauncher launcher, MyServos ballGrabber,
                                  MyServos ballLoader, MyServos capGrabber){
        this.launcher = launcher;
        this.lift = lift;
        this.ballGrabber = ballGrabber.getBallGrabber();
        this.ballLoader = ballLoader.getBallLoader();
        this.capGrabber = capGrabber.getCapGrabber();
    }//constructor

    /**
     * Runnable's run method
     */
    @Override
    public void run() {
        //run components until interruption
        try{
            while (true){
                this.launcher.handleMessage();
                this.lift.handleMessage();
                this.ballGrabber.handleMessage();
                this.ballLoader.handleMessage();
                this.capGrabber.handleMessage();
                Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
            }//while
        }catch (InterruptedException i){
            i.printStackTrace();
        }//try-catch
    }//run
}//class
