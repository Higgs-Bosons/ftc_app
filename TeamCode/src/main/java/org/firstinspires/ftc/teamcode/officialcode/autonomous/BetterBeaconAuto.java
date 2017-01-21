package org.firstinspires.ftc.teamcode.officialcode.autonomous;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.drivetrain.DriveTrainFactory;
import org.firstinspires.ftc.teamcode.officialcode.drivetrain.IDrivetrain;
import org.firstinspires.ftc.teamcode.officialcode.launcher.ILauncher;
import org.firstinspires.ftc.teamcode.officialcode.launcher.LauncherFactory;
import org.firstinspires.ftc.teamcode.officialcode.pusher.IPusher;
import org.firstinspires.ftc.teamcode.officialcode.pusher.PusherFactory;
import org.firstinspires.ftc.teamcode.officialcode.sensors.Sensors;
import org.firstinspires.ftc.teamcode.officialcode.sensors.SensorsFactory;
import org.firstinspires.ftc.teamcode.officialcode.servos.MyServos;
import org.firstinspires.ftc.teamcode.officialcode.servos.ServosFactory;

import java.util.Stack;

/**
 * Beacon Autonomous 2.0 abstraction
 */
public abstract class BetterBeaconAuto extends Autonomous {
    //autonomous constant distances and powers
    private static final byte OFF_WALL_DIST = 35;
    private static final byte AWAY_BALL_DIST = -20;
    private static final byte TO_WALL_DIST = 60;
    private static final byte AWAY_WALL_DIST = -10;
    private static final byte SECOND_BEACON_DIST = -38;
    private static final double POWER_ONE = 0.7d;
    private static final double WHITE_LINE_PWER = 0.2d;

    //Declare components' variables
    private IDrivetrain dDrive;
    private ILauncher dLaunch;
    private IPusher dPusher;
    private MyServos dServos;
    private Sensors sensors;

    //declare abstract methods for getting color and turns
    protected abstract Stack<Integer> getTurns();
    protected abstract Constants.Color getColor();

    /**
     * Drive forward and make a basket
     * @throws InterruptedException
     */
    private void makeABasket() throws InterruptedException {
        //begin firing launcher
        this.dLaunch.fire();

        //move distance to vortex
        this.dDrive.moveDistance((int) (OFF_WALL_DIST), POWER_ONE);
        Thread.sleep(500);

        //open up doors to partial close position
        this.dServos.getBallGrabber().partialClose();
        Thread.sleep(500);

        //raise ball loader
        this.dServos.getBallLoader().raiseLoader();
        this.sleep(1000);

        //Cease launcher fire
        this.dLaunch.cease();
    }//makeABasket

    /**
     * align self to beacon wall
     * @throws InterruptedException
     */
    private void toWall() throws InterruptedException {
        System.out.println("Going to wall!");

        this.dDrive.moveDistance(AWAY_BALL_DIST, -POWER_ONE);

        this.dDrive.goToHeading(this.getTurns().pop());

        this.dDrive.moveDistance((int) (TO_WALL_DIST), POWER_ONE);

        this.dDrive.goToHeading(this.getTurns().pop());

//        this.dServos.getTouchers().activate();

        this.dDrive.wallAlign(5000);
    }

    private void beaconPrep() throws InterruptedException {
        this.dDrive.moveDistance(AWAY_WALL_DIST, -POWER_ONE);
        this.dDrive.goToHeading(this.getTurns().pop());
    }

    private void toBeacon(long finalWaitTime, double power) throws InterruptedException{
        //detects white line with sensors to align itself to the beacon
        boolean atBeacon = dDrive.stopAtWhiteLine(finalWaitTime, power);
        if(!atBeacon){
            throw new IllegalStateException("No Beacon");
        }

        this.dDrive.timedMove(-0.2d, 600);
    }

    private void secondBeacon() throws InterruptedException {
        dDrive.moveDistance((int) (SECOND_BEACON_DIST), -POWER_ONE);
    }


    private void activateBeacon(double pusherPower) throws InterruptedException {
        boolean foundBeacon = dDrive.stopAtBeacon(this.getColor(), 5000);
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
        this.dDrive = DriveTrainFactory.getInstance(this);
        this.dPusher = PusherFactory.getInstance(this);
        this.dServos = ServosFactory.getInstance(this);
        this.dLaunch = LauncherFactory.getInstance(this);

        this.dServos.getCapGrabber().closeGrabber();
        this.dServos.getBallGrabber().fullClose();
        this.dServos.getBallLoader().downLoader();
//        this.dServos.getTouchers().retract();

        this.sensors = SensorsFactory.getInstance(this);

        this.sensors.gyroCalibrate();

//        System.out.println("Initializing Gyro.");

        while(this.sensors.getGyro().isCalibrating()){
            Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
            idle();
        }

//        System.out.println("Initialize Done.");
    }

    @Override
    public void runAutonomous() throws InterruptedException {
        double pusherPower = (this.getColor() == Constants.Color.RED ? 0.3d : -0.3d);

        try {
            //System.out.println("Approaching Beacons");
            this.makeABasket();
            this.toWall();
            this.beaconPrep();
            this.toBeacon(5000, WHITE_LINE_PWER);
//            this.activateBeacon(pusherPower);
//            this.secondBeacon();
//            this.toBeacon(5000, -WHITE_LINE_PWER);
//            this.activateBeacon(pusherPower);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Something went wrong!");
        }
    }
}
