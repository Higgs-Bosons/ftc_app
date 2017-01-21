package org.firstinspires.ftc.teamcode.officialcode.configuration;

/**
 * Class for universal constants
 */
public class Constants {
    /**
     * components of robot enum
     */
    public enum RobotComponent{
        DRIVE_TRAIN, LIFT, PUSHER, LAUNCHER, B_GRABBER, C_GRABBER, LOADER
    }//RobotComponent

    /**
     * actions of components enum
     */
    public enum RobotComponentAction{
        START, STOP
    }//RobotComponentAction

    /**
     * turns enum
     */
    public enum Turns{
        LEFT_TURN, RIGHT_TURN
    }//Turns

    /**
     * Color enum
     */
    public enum Color{
        RED(2,256),BLUE(2,256);
        double lowThreshold;
        double highThreshold;

        /**
         * initialize low and high threshold
         * @param lowThreshold
         * @param highThreshold
         */
        Color(double lowThreshold, double highThreshold){
            this.lowThreshold = lowThreshold;
            this.highThreshold = highThreshold;
        }//Color

        /**
         * get low threshold of color
         * @return
         */
        public double getLowThreshold(){
            return lowThreshold;
        }//getLowThreshold

        /**
         * get high threshold of color
         */
        public double getHighThreshold() {
            return highThreshold;
        }//getHighThreshold
    }//Color

    /**
     * state of launcher enum
     */
    public enum LauncherState{
        STOPPED, FIRING
    }//LauncherState

    /**
     * state of lift enum
     */
    public enum LiftState{
        ASCENDING, DESCENDING, STOPPED
    }//LiftState

    /**
     * state of pusher enum
     */
    public enum PusherState{
        STOPPED, L_MOVING, R_MOVING
    }//PusherState

    /**
     * state of ball grabber
     */
    public enum BallGrabberState{
        OPEN, CLOSED
    }//BallGrabberState

    /**
     * state of ball loader
     */
    public enum BallLoaderState{
        UP, DOWN
    }//BallLoaderState

    /**
     * state of cap ball grabber
     */
    public enum CapGrabberState{
        CLOSED, READY, HOLDING
    }//CapGrabberState

    /**
     * state of touch sensors
     */
    public enum TouchersState{
        RETRACTED, READY
    }//TouchersState

    /**
     * power of drivetrain
     */
    public enum DrivetrainPower{
        LEFT, RIGHT
    }//DrivetrainPower

    //Misc. constants
    public static final int ENCODER_CPR = 1120;
    //SA: Change this to 5 ms, if that does not work change to 10 ms
    public static final int THREAD_WAIT_TIME_MS = 20;
    public static final int DIRE_THREAD_WAIT_MS = 5;
    public static final short EOPD_WHITE_THRESHOLD = 100;

    //motor hardware mapping names
    public static final String LF_MOTOR = "leftFront";
    public static final String LR_MOTOR = "leftRear";
    public static final String RF_MOTOR = "rightFront";
    public static final String RR_MOTOR = "rightRear";
    public static final String LAUNCHER_MOTOR = "launcher";
    public static final String PUSHER_MOTOR = "pusher";
    public static final String LEFT_LIFT_MOTOR = "lLift";
    public static final String RIGHT_LIFT_MOTOR = "rLift";

    //servo hardware mapping names
    public static final String L_BALL_SERVO = "lBGrabber";
    public static final String R_BALL_SERVO = "rBGrabber";
    public static final String T_CAP_SERVO = "tCGrabber";
    public static final String BL_CAP_SERVO = "lCGrabber";
    public static final String BR_CAP_SERVO = "rCGrabber";
    public static final String LOADER_SERVO = "bLoader";
    public static final String RIGHT_TOUCHER_SERVO = "rTouch";
    public static final String LEFT_TOUCHER_SERVO = "lTouch";

    //sensor hardware mapping names
    public static final String COLOR_SENSOR = "color";
    public static final String GYRO_SENSOR = "gyro";
    public static final String LEFT_EOPD = "lEOPD";
    public static final String RIGHT_EOPD = "rEOPD";
    public static final String RIGHT_TOUCHER = "rTouchSense";
    public static final String LEFT_TOUCHER = "lTouchSense";
}//class
