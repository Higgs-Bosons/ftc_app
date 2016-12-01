package org.firstinspires.ftc.teamcode.officialcode.teleop;

import org.firstinspires.ftc.teamcode.officialcode.drivetrain.IDrivetrain;
import org.firstinspires.ftc.teamcode.officialcode.pusher.IPusher;
import org.firstinspires.ftc.teamcode.officialcode.servos.IServos;
import org.firstinspires.ftc.teamcode.officialcode.servos.MyServos;

/**
 * Created by Higgs Bosons on 11/30/2016.
 */
public class DrivetrainPusherServoController implements Runnable {
    IDrivetrain drivetrain;
    IPusher pusher;
    IServos ballGrabber;
    IServos ballLoader;
    IServos capGrabber;

    public DrivetrainPusherServoController(IDrivetrain drivetrain, IPusher pusher,
                                           MyServos ballGrabber, MyServos ballLoader,
                                           MyServos capGrabber){
        this.drivetrain = drivetrain;
        this.pusher = pusher;
        this.ballGrabber = ballGrabber.getBallGrabber();
        this.ballLoader = ballLoader.getBallLoader();
        this.capGrabber = capGrabber.getCapGrabber();
    }

    @Override
    public void run() {
        try{
            while (true){
                drivetrain.handleMessage();
                pusher.handleMessage();
                ballGrabber.handleMessage();
                ballLoader.handleMessage();
                capGrabber.handleMessage();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}
