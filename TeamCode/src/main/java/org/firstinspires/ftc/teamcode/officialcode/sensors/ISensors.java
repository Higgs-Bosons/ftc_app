package org.firstinspires.ftc.teamcode.officialcode.sensors;

/**
 * Created by Higgs Bosons on 10/12/2016.
 */
public interface ISensors {
    boolean detectWhiteLine();
    boolean detectBeacon(double upperThresh, double lowerThresh);
}
