package org.firstinspires.ftc.teamcode.officialcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

import java.util.Stack;

/**
 * Created by Higgs Bosons on 11/22/2016.
 */

@Autonomous(name = "Red", group = "Beacon")
public class RedBeacon extends BetterBeaconAuto{

    private Stack<Integer> turns = new Stack<>();

    public RedBeacon(){
        super();
        turns.add(0);
        turns.add(270);
        turns.add(315);
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
