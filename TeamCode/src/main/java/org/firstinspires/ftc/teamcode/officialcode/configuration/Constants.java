package org.firstinspires.ftc.teamcode.officialcode.configuration;

/**
 * Created by Higgs Bosons on 10/5/2016.
 */
public class Constants {
    public enum RobotComponent{
        DRIVE_TRAIN, LIFT, PUSHER, LAUNCHER, B_GRABBER, C_GRABBER, LOADER
    }

    public enum RobotComponentAction{
        START, STOP
    }

    public enum Turns{
        LEFT_TURN, RIGHT_TURN
    }

    public enum Color{
        RED(2,256),BLUE(2,256);
        double lowThreshold;
        double highThreshold;

        Color(double lowThreshold, double highThreshold){
            this.lowThreshold = lowThreshold;
            this.highThreshold = highThreshold;
        }

        public double getLowThreshold(){
            return lowThreshold;
        }

        public double getHighThreshold() {
            return highThreshold;
        }
    }

    public enum DriveState{
        STOP,DRIVING
    }

    public enum LauncherState{
        STOPPED, FIRING
    }

    public enum LiftState{
        ASCENDING, DESCENDING, STOPPED
    }

    public enum PusherState{
        STOPPED, L_MOVING, R_MOVING
    }

    public enum BallGrabberState{
        OPEN, CLOSED
    }

    public enum TurnType{
        SWING,PIVOT
    }

    public enum DrivetrainPower{
        LEFT, RIGHT
    }

    public static final int ENCODER_CPR = 1120;
    public static final int THREAD_WAIT_TIME_MS = 20;
    public static final short EOPD_WHITE_THRESHOLD = 100;
    public static final byte COLOR_THRESH = 2;

    public static final String LF_MOTOR = "leftFront";
    public static final String LR_MOTOR = "leftRear";
    public static final String RF_MOTOR = "rightFront";
    public static final String RR_MOTOR = "rightRear";
    public static final String LAUNCHER_MOTOR = "launcher";
    public static final String PUSHER_MOTOR = "pusher";
    public static final String LIFT_MOTOR = "lift";

    public static final String L_BALL_SERVO = "leftBallGrabber";
    public static final String R_BALL_SERVO = "rightBallGrabber";
    public static final String T_CAP_SERVO = "topCapGrabber";
    public static final String B_CAP_SERVO = "bottomCapGrabber";
    public static final String LOADER_SERVO = "ballLoader";

    public static final String COLOR_SENSOR = "color";
    //public static final String GYRO_SENSOR = "gyro";
    public static final String LEFT_EOPD = "lEOPD";
    public static final String RIGHT_EOPD = "rEOPD";
}
