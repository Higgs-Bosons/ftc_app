package org.firstinspires.ftc.teamcode.officialcode.autonomous;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.drivetrain.IDrivetrain;
import org.firstinspires.ftc.teamcode.officialcode.sensors.ISensors;

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

    private ISensors dSense;
    private IDrivetrain dDrive;


    /**
     * Constructs a BeaconAutonomous
     * @param dSense - An instance of a class implementing the ISensors interface
     * @param dDrive - An instance of a class implementing the IDrivetrain interface
     */
    public BeaconAutonomous(ISensors dSense, IDrivetrain dDrive, Constants.Color color){
        this.dSense = dSense;
        this.dDrive = dDrive;
        this.color = color;
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
        dDrive.rightAngleTurn(Constants.Turns.lEFT_TURN);
        /**
         *  Move forward again so it can get into position again so it is can turn parallel to
         *  beacon wall
         */

        dDrive.moveDistance((int) (TO_BEACON_DIST), POWER_ONE);
        //turn robot left 90 degrees so it parallel to the beacon wall
        dDrive.rightAngleTurn(Constants.Turns.lEFT_TURN);
        //turn robot left again 90 degrees so it is facing the beacon wall
        dDrive.rightAngleTurn(Constants.Turns.lEFT_TURN);
        //detects white line with sensors to align itself to the beacon
        dSense.detectWhiteLine();
        dSense.detectBeacon(this.color);
    }


    private void findBeacon(){

    }

    private void findSecondBeacon(){

    }

    private void pressButton(){

    }

    @Override
    public void runAutonomous() throws InterruptedException {

    }
}
