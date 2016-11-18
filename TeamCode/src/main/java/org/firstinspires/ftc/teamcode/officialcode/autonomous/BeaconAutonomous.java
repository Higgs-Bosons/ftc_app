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
    private void approachBeacons() throws InterruptedException {
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
    }

    private void goToBeacon(long finalWaitTime) throws InterruptedException{
        //detects white line with sensors to align itself to the beacon
        boolean atBeacon = dDrive.stopAtWhiteLine(finalWaitTime);
        if(!atBeacon){
            throw new IllegalStateException("No Beacon");
        }
    }


    private void activateBeacon(double pusherPower) throws InterruptedException {
        boolean foundBeacon = dDrive.stopAtBeacon(this.color, 2000);
        if(!foundBeacon){
            throw new IllegalStateException("No Beacon");
        }
        this.pressButton(pusherPower);
    }

    private void pressButton(double power) throws InterruptedException {
        dPusher.pressButton(power);
    }

    @Override
    public void runAutonomous() throws InterruptedException {
        double pusherPower = (this.color == Constants.Color.RED ? 0.5d : -0.5d);

        try {
            this.approachBeacons();
            this.goToBeacon(5000);
            this.activateBeacon(pusherPower);
            this.goToBeacon(1000);
            this.activateBeacon(pusherPower);
        }catch(Exception e){
            System.out.println("Something went wrong!");
            System.exit(1);
        }
    }
}
