package org.firstinspires.ftc.teamcode.officialcode.autonomous;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.drivetrain.IDrivetrain;
import org.firstinspires.ftc.teamcode.officialcode.sensors.ISensors;

/**
 * Created by Higgs Bosons on 10/5/2016.
 */
public class BeaconAutonomous implements IAutonomous {
    private static final byte OFF_WALL_DIST = 0;
    private static final byte TO_BEACON_DIST = 0;
    private static final double POWER_ONE = 0.5d;
    private ISensors dSense;
    private IDrivetrain dDrive;

    public BeaconAutonomous(ISensors dSense, IDrivetrain dDrive){
        this.dSense = dSense;
        this.dDrive = dDrive;

    }

    private void goToBeacon() throws InterruptedException{
        dDrive.moveDistance((int) (OFF_WALL_DIST), POWER_ONE);
        dDrive.rightAngleTurn(Constants.Turns.lEFT_TURN);
        dDrive.moveDistance((int) (TO_BEACON_DIST), POWER_ONE);
        dDrive.rightAngleTurn(Constants.Turns.lEFT_TURN);
        dDrive.rightAngleTurn(Constants.Turns.lEFT_TURN);
        dSense.detectWhiteLine();
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
