package org.firstinspires.ftc.teamcode.officialcode.configuration;

/**
 * Created by Higgs Bosons on 10/5/2016.
 */
public class Constants {
    public enum Turns{
        LEFT_TURN, RIGHT_TURN
    }

    public enum Color{
        RED(50.0,80.0),BLUE(50.0,80.0);
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

    public enum TurnType{
        SWING,PIVOT
    }

    public static final int ENCODER_CPR = 1120;
    public static final int THREAD_WAIT_TIME_MS = 20;
    public static final byte EOPD_WHITE_THRESHOLD_RAW_LOW = 39;

    public static final String LF_MOTOR = "leftFront";
    public static final String LR_MOTOR = "leftRear";
    public static final String RF_MOTOR = "rightFront";
    public static final String RR_MOTOR = "rightRear";
    public static final String LAUNCHER_MOTOR = "launcher";
    public static final String PUSHER_MOTOR = "pusher";
    public static final String LIFT_MOTOR = "lift";

    public static final String COLOR_SENSOR = "color";
    public static final String GYRO_SENSOR = "gyro";
    public static final String LEFT_EOPD = "lEOPD";
    public static final String RIGHT_EOPD = "rEOPD";
}
