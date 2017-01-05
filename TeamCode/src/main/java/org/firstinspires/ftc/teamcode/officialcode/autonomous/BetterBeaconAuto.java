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
 * Created by Higgs Bosons on 1/4/2017.
 */
public class BetterBeaconAuto extends Autonomous {
    private static final byte OFF_WALL_DIST = 15;
    private static final byte TO_WALL_DIST = 60;
    private static final byte SECOND_BEACON_DIST = 38;
    private static final double POWER_ONE = 0.7d;
    private static final int ANGLE_TURNS = 53;
    private static final double WHITE_LINE_PWER = 0.2d;
    private Constants.Color color;

    private Stack<Integer> turns;

    private IDrivetrain dDrive;
    private ILauncher dLaunch;
    private IPusher dPusher;
    private MyServos dServos;
    private Sensors sensors;

    public BetterBeaconAuto(Constants.Color color){
        this.color = color;
        turns = new Stack<>();

        if(this.color == Constants.Color.RED){
            turns.add(ANGLE_TURNS); turns.add(360-ANGLE_TURNS);
        }else{
            turns.add(360-ANGLE_TURNS); turns.add(ANGLE_TURNS);
        }
    }

    private void makeABasket() throws InterruptedException {
        this.dDrive.moveDistance((int) (OFF_WALL_DIST), POWER_ONE);

        this.dLaunch.fire();
        Thread.sleep(1000);
        this.dServos.getBallLoader().raiseLoader();
        this.sleep(500);
        this.dLaunch.cease();
    }

    private void toWall() throws InterruptedException {
        this.dDrive.goToHeading(turns.pop());

        this.dDrive.moveDistance((int) (TO_WALL_DIST), POWER_ONE);

        this.dDrive.goToHeading(turns.pop());

        this.dServos.getTouchers().activate();

        this.dDrive.wallAlign(5000);
    }

    private void beaconPrep() throws InterruptedException {
        this.dDrive.timedMove(-POWER_ONE, 1000);
        this.dDrive.goToHeading(turns.pop());
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
        this.dDrive = DriveTrainFactory.getInstance(this);
        this.dPusher = PusherFactory.getInstance(this);
        this.dServos = ServosFactory.getInstance(this);
        this.dLaunch = LauncherFactory.getInstance(this);

        this.dServos.getCapGrabber().closeGrabber();
        this.dServos.getBallGrabber().fullClose();
        this.dServos.getBallLoader().downLoader();
        this.dServos.getTouchers().retract();

        this.sensors = SensorsFactory.getInstance(this);

        this.sensors.gyroCalibrate();

        System.out.println("Initializing Gyro.");
//        Thread.sleep(2000);
        while(this.sensors.getGyro().isCalibrating()){
            Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
            idle();
        }
        System.out.println("Initialize Done.");
    }

    @Override
    public void runAutonomous() throws InterruptedException {
        double pusherPower = (this.color == Constants.Color.RED ? 0.3d : -0.3d);

        try {
            //System.out.println("Approaching Beacons");
            this.makeABasket();
            this.toWall();
            this.beaconPrep();
            this.toBeacon(5000, WHITE_LINE_PWER);
            this.activateBeacon(pusherPower);
            this.secondBeacon();
            this.toBeacon(5000, -WHITE_LINE_PWER);
            this.activateBeacon(pusherPower);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Something went wrong!");
        }
    }
}
