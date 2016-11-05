package org.firstinspires.ftc.teamcode.officialcode.configuration;

/**
 * Created by Higgs Bosons on 10/5/2016.
 */
public class Constants {
    public enum Turns{
        LEFT_TURN, RIGHT_TURN
    }

    public enum Shade{
        WHITE,BLACK
    }

    public enum Color{
        RED(50.0,80.0),BLUE(50.0,80.0);
        double lowThreshold;
        double highThreshold;

        Color(double lowThreshold, double highThreshold){
            this.lowThreshold=lowThreshold;
            this.highThreshold=highThreshold;
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

    public static final int ENCODER_CPR = 1120;
    public static final int THREAD_WAIT_TIME_MS = 20;
}
