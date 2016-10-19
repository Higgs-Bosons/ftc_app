package org.firstinspires.ftc.teamcode.officialcode.sensors;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 *
 * Created by Higgs Bosons on 10/12/2016.
 */
public interface ISensors {
    /**
     * Detect a white line using 2 light sensonrs at the bottom of the robot
     * @return - true when line is detected, false otherwise
     */
    boolean detectWhiteLine();

    /**
     * double low threshold and double high threshold with red, blue and green values
     * @param color
     * @return
     */
    boolean detectBeacon(Constants.Color color);

}
