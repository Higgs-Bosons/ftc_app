package org.firstinspires.ftc.teamcode.officialcode.autonomous;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.drivetrain.IDrivetrain;
import org.firstinspires.ftc.teamcode.officialcode.pusher.IPusher;

import java.util.Stack;

/**
 * The purpose of this class is to direct the robot from the wall to the wall where the beacons
 * are mounted. It then finds the white line to the first beacon, detects the correct color,
 * presses the beacon, moves to the next white line, detects the correct color and presses
 * the beacon. Following the it parks in the center goal while pushing the cap ball away.
 * Created by Higgs Bosons on 10/5/2016.
 */
public class BeaconAutonomous implements IAutonomous {
    private static final byte OFF_WALL_DIST = 0;
    private static final byte PREP_BEACON_DIST = 0;
    private static final byte TO_BEACON_DIST = 0;
    private static final double POWER_ONE = 0.5d;
    private Constants.Color color;

    private Stack<Constants.Turns> turns;

    private IDrivetrain dDrive;
    private IPusher dPusher;

    /**
     * Constructs a BeaconAutonomous
     * @param dDrive - An instance of a class implementing the IDrivetrain interface
     */
    public BeaconAutonomous(IPusher dPusher, IDrivetrain dDrive, Constants.Color color){
        this.dDrive = dDrive;
        this.dPusher = dPusher;
        this.color = color;
        turns = new Stack<>();

        if(this.color == Constants.Color.RED){
            turns.add(Constants.Turns.RIGHT_TURN); turns.add(Constants.Turns.LEFT_TURN);
        }else{
            turns.add(Constants.Turns.LEFT_TURN); turns.add(Constants.Turns.RIGHT_TURN);
        }
    }

    /**
     *The purpose of this class is to direct the robot from the wall to the wall where the beacons
     * are mounted. It then finds the white line to the first beacon, detects the correct color,
     * presses the beacon, moves to the next white line, detects the correct color and presses
     * the beacon. Following the it parks in the center goal while pushing the cap ball away.
     * Created by Higgs Bosons on 10/5/2016.
     * @throws InterruptedException
     */
    private void goToBeacon() throws InterruptedException{
        //Move forward to a position where the robot can turn towards the beacon wall
        dDrive.moveDistance((int) (OFF_WALL_DIST), POWER_ONE);
        //Turn 90 degrees towards the beacon wall
        dDrive.rightAngleTurn(turns.pop());
        /**
         *  Move forward again so it can get into position again so it is can turn parallel to
         *  beacon wall
         */

        dDrive.moveDistance((int) (TO_BEACON_DIST), POWER_ONE);
        //turn robot left again 90 degrees so it is facing the beacon wall
        dDrive.rightAngleTurn(turns.pop());
        //detects white line with sensors to align itself to the beacon

    }


    private void findBeacon() throws InterruptedException {
        dDrive.moveDistance((int) (PREP_BEACON_DIST), -POWER_ONE);

    }

    private void findSecondBeacon() {}

    private void pressButton(){
        dPusher.pressButton();
    }

    @Override
    public void runAutonomous() throws InterruptedException {
        this.goToBeacon();
        this.findBeacon();
        this.pressButton();
        this.findSecondBeacon();
        this.pressButton();
    }
}
