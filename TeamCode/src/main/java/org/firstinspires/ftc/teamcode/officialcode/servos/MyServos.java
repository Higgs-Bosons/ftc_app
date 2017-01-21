package org.firstinspires.ftc.teamcode.officialcode.servos;

/**
 * Class for declaring, initializing, and providing getter methods for servos
 */
public class MyServos {
	//declare all servos
    private BallGrabber ballGrabber;
    private CapGrabber capGrabber;
    private BallLoader ballLoader;

	/**
	 * initialize servos
	 * @param ballGrabber
	 * @param capGrabber
	 * @param ballLoader
	 */	 
    public MyServos(BallGrabber ballGrabber, CapGrabber capGrabber, BallLoader ballLoader){
        this.ballGrabber = ballGrabber;
        this.capGrabber = capGrabber;
        this.ballLoader = ballLoader;
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
}//class
