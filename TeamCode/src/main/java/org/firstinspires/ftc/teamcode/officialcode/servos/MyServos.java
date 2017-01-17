package org.firstinspires.ftc.teamcode.officialcode.servos;

/**
 * Class for declaring, initializing, and providing getter methods for servos
 */
public class MyServos {
	//declare all servos
    private BallGrabber ballGrabber;
    private CapGrabber capGrabber;
    private BallLoader ballLoader;
    private TouchEnablers touchers;

	/**
	 * initialize servos
	 * @param ballGrabber
	 * @param capGrabber
	 * @param ballLoader
	 * @param touchers
	 */	 
    public MyServos(BallGrabber ballGrabber, CapGrabber capGrabber, BallLoader ballLoader, TouchEnablers touchers){
        this.ballGrabber = ballGrabber;
        this.capGrabber = capGrabber;
        this.ballLoader = ballLoader;
        this.touchers = touchers;
    }//constructors

    public BallGrabber getBallGrabber(){
        return ballGrabber;
    }//getBallGrabber

    public CapGrabber getCapGrabber(){
        return capGrabber;
    }//getCapGrabber

    public BallLoader getBallLoader(){
        return ballLoader;
    }//getBallLoader

    public TouchEnablers getTouchers(){
        return touchers;
    }//getTouchers
}//class
