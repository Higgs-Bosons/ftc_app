package org.firstinspires.ftc.teamcode.officialcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

import java.util.Stack;

/**
 * Created by Higgs Bosons on 11/22/2016.
 */

@Autonomous(name = "Blue", group = "Beacon")
public class BlueBeacon extends BetterBeaconAuto {
    private Stack<Integer> turns = new Stack<>();

    public BlueBeacon(){
        super();
        turns.add(0);
        turns.add(90);
        turns.add(20);
    }

    @Override
    protected Stack<Integer> getTurns(){
        return turns;
    }

    @Override
    protected Constants.Color getColor(){
        return Constants.Color.RED;
    }
}
