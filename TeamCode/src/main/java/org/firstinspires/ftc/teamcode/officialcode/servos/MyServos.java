package org.firstinspires.ftc.teamcode.officialcode.servos;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */
public class MyServos {
    private BallGrabber ballGrabber;
    private CapGrabber capGrabber;
    private BallLoader ballLoader;

    public MyServos(BallGrabber ballGrabber, CapGrabber capGrabber, BallLoader ballLoader){
        this.ballGrabber = ballGrabber;
        this.capGrabber = capGrabber;
        this.ballLoader = ballLoader;
    }

    public BallGrabber getBallGrabber(){
        return ballGrabber;
    }

    public CapGrabber getCapGrabber(){
        return capGrabber;
    }

    public BallLoader ballLoader(){
        return ballLoader;
    }
}
