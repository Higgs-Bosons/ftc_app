package org.firstinspires.ftc.teamcode.officialcode.autonomous;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.drivetrain.DriveTrainFactory;
import org.firstinspires.ftc.teamcode.officialcode.drivetrain.IDrivetrain;
import org.firstinspires.ftc.teamcode.officialcode.pusher.IPusher;
import org.firstinspires.ftc.teamcode.officialcode.pusher.PusherFactory;
import org.firstinspires.ftc.teamcode.officialcode.sensors.Sensors;
import org.firstinspires.ftc.teamcode.officialcode.sensors.SensorsFactory;
import org.firstinspires.ftc.teamcode.officialcode.servos.IServos;
import org.firstinspires.ftc.teamcode.officialcode.servos.MyServos;
import org.firstinspires.ftc.teamcode.officialcode.servos.ServosFactory;

import java.util.Stack;

/**
 * The purpose of this class is to direct the robot from the wall to the wall where the beacons
 * are mounted. It then finds the white line to the first beacon, detects the correct color,
 * presses the beacon, moves to the next white line, detects the correct color and presses
 * the beacon. Following the it parks in the center goal while pushing the cap ball away.
 * Created by Higgs Bosons on 10/5/2016.
 */
public class BeaconAutonomous extends Autonomous {
    private static final byte OFF_WALL_DIST = 15;
    private static final byte TO_BEACON_DIST = 60;
    private static final byte SECOND_BEACON_DIST = 38;
    private static final double POWER_ONE = 0.7d;
    private static final int ANGLE_TURNS = 53;
    private static final double WHITE_LINE_PWER = 0.2d;
    private Constants.Color color;

    private Stack<Constants.Turns> turns;

    private IDrivetrain dDrive;
    private IPusher dPusher;
    private MyServos dServos;

    public BeaconAutonomous(Constants.Color color){
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
//        System.out.println("Moving Forward");
        dDrive.moveDistance((int) (OFF_WALL_DIST), POWER_ONE);
//        System.out.println("Turning");
//        //Turn 90 degrees towards the beacon wall
        dDrive.timedAngleTurn(turns.pop(), ANGLE_TURNS);
//        System.out.println("Done Turn");
//        /**
//         *  Move forward again so it can get into position again so it is can turn parallel to
//         *  beacon wall
//         */
//
        dDrive.moveDistance((int) (TO_BEACON_DIST), POWER_ONE);
//        //turn robot left again 90 degrees so it is facing the beacon wall
        dDrive.timedAngleTurn(turns.pop(), ANGLE_TURNS);
    }

    private void goToBeacon(long finalWaitTime, double power) throws InterruptedException{
        //detects white line with sensors to align itself to the beacon
        boolean atBeacon = dDrive.stopAtWhiteLine(finalWaitTime, power);
        if(!atBeacon){
            throw new IllegalStateException("No Beacon");
        }

        this.dDrive.timedMove(-0.2d, 600);
    }

    private void goToSecondBeacon() throws InterruptedException {
        dDrive.moveDistance((int) (SECOND_BEACON_DIST), POWER_ONE);
    }


    private void activateBeacon(double pusherPower) throws InterruptedException {
        boolean foundBeacon = dDrive.stopAtBeacon(this.color, 5000);
        if(!foundBeacon){
            throw new IllegalStateException("No Beacon");
        }
        this.pressButton(pusherPower);
    }

    private void pressButton(double power) throws InterruptedException {
        dPusher.pusherMovement(power);
        Thread.sleep(1500);
        dPusher.pusherMovement(-power);
        Thread.sleep(1000);
        dPusher.pusherMovement(0);
    }

    @Override
    public void initialize() throws InterruptedException {
       // System.out.println("Initialize Started.");
        this.dDrive = DriveTrainFactory.getInstance(this);
        this.dPusher = PusherFactory.getInstance(this);
        this.dServos = ServosFactory.getInstance(this);

        this.dServos.getCapGrabber().closeGrabber();
        this.dServos.getBallGrabber().fullClose();
        this.dServos.getBallLoader().downLoader();
//        this.sensors = SensorsFactory.getInstance(this);

//        this.sensors.gyroCalibrate();
//
//        System.out.println("Initializing Gyro.");
//        Thread.sleep(2000);
//        while(this.sensors.getGyro().isCalibrating()){
//            Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
//            idle();
//        }
//        System.out.println("Initialize Done.");
    }

    @Override
    public void runAutonomous() throws InterruptedException {
        double pusherPower = (this.color == Constants.Color.RED ? 0.3d : -0.3d);

        try {
            //System.out.println("Approaching Beacons");
            this.approachBeacons();
            this.goToBeacon(5000, -WHITE_LINE_PWER);
            this.activateBeacon(pusherPower);
            this.goToSecondBeacon();
            this.goToBeacon(5000, WHITE_LINE_PWER);
            this.activateBeacon(pusherPower);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Something went wrong!");
        }
    }
}
