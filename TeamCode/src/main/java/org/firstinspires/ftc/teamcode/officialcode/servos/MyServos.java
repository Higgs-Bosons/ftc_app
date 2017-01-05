package org.firstinspires.ftc.teamcode.officialcode.servos;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */
public class MyServos {
    private BallGrabber ballGrabber;
    private CapGrabber capGrabber;
    private BallLoader ballLoader;
    private TouchEnablers touchers;

    public MyServos(BallGrabber ballGrabber, CapGrabber capGrabber, BallLoader ballLoader, TouchEnablers touchers){
        this.ballGrabber = ballGrabber;
        this.capGrabber = capGrabber;
        this.ballLoader = ballLoader;
        this.touchers = touchers;
    }

    public BallGrabber getBallGrabber(){
        return ballGrabber;
    }

    public CapGrabber getCapGrabber(){
        return capGrabber;
    }

    public BallLoader getBallLoader(){
        return ballLoader;
    }

    public TouchEnablers getTouchers(){
        return touchers;
    }
}
