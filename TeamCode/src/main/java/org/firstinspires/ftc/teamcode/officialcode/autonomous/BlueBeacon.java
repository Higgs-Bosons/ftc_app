package org.firstinspires.ftc.teamcode.officialcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Created by Higgs Bosons on 11/22/2016.
 */

@Autonomous(name = "Blue", group = "Beacon")
public class BlueBeacon extends BeaconAutonomous {
    public BlueBeacon(){
        super(Constants.Color.BLUE);
        System.out.println("After Auto");
    }
}
