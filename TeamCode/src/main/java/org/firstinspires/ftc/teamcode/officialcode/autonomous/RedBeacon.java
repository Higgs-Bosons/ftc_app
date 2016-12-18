package org.firstinspires.ftc.teamcode.officialcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Created by Higgs Bosons on 11/22/2016.
 */

@Autonomous(name = "Red", group = "Beacon")
public class RedBeacon extends BeaconAutonomous{
    public RedBeacon(){
        super(Constants.Color.RED);
    }
}
