package org.firstinspires.ftc.teamcode.officialcode.drivetrain;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.sensors.Sensors;
import org.firstinspires.ftc.teamcode.officialcode.teleop.MyMessageQueue;
import org.firstinspires.ftc.teamcode.officialcode.teleop.TeleopMessages;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Higgs Bosons on 10/22/2016.
 */
public class Drivetrain implements IDrivetrain {
    private DriveMotors dMotors;
    private Sensors dSense;
    private BlockingQueue<TeleopMessages> queue;

    private float powerLF;
    private float powerLR;
    private float powerRF;
    private float powerRR;

    private static final int TIMED_PER_DEGREE = 12;
    private static final double TURN_POWER = 0.2d;
    private static final double DISTANCE_PER_DEGREE = 0.3d;

    public Drivetrain(DriveMotors dMotors, Sensors dSense){
        this.dMotors = dMotors;
        this.dSense = dSense;
        this.queue = MyMessageQueue.getInstance();
    }

    private void setLeftMotors(double power){
        this.dMotors.getLeftFront().setPower(power);
        this.dMotors.getLeftBack().setPower(power);
    }

    private void setRightMotors(double power){
        this.dMotors.getRightFront().setPower(power);
        this.dMotors.getRightBack().setPower(power);
    }

    private void handleDrivetrainMessage(TeleopMessages message){
        HashMap<String, Object> metadata = message.getMetadata();

        for (String messageName : metadata.keySet()){
            Constants.DrivetrainPower dtPower = Constants.DrivetrainPower.valueOf(messageName);

            switch (dtPower){
                case LEFT:
                    this.setPowerLF((Float) metadata.get(messageName));
                    this.setPowerLR((Float) metadata.get(messageName));

                    break;
                case RIGHT:
                    this.setPowerRF((Float) metadata.get(messageName));
                    this.setPowerRR((Float) metadata.get(messageName));

                    break;
                default:
                    throw new IllegalStateException("Cannot Handle: " + messageName);
            }
        }
    }

    @Override
    public void moveDistance(int distance, double power) throws InterruptedException {
//        System.out.println("Moving Distance Drivetrain");
        this.dMotors.resetControllers();

        this.dMotors.encodeInitialize();

        int currentPositionL = this.dMotors.getLeftFront().getCurrentPosition();
        int currentPositionR = this.dMotors.getRightFront().getCurrentPosition();

//        System.out.println("Left Current Position: " + currentPositionL + ", Right Current Position: " + currentPositionR);

        int counts = this.dMotors.getCounts(distance);
        int targetPositionL = currentPositionL + counts;
        int targetPositionR = currentPositionR + counts;

//        System.out.println("Left Target Position: " + targetPositionL + ", Right Target Position: " + targetPositionR);

        this.dMotors.getLeftFront().setTargetPosition(targetPositionL);
        this.dMotors.getRightFront().setTargetPosition(targetPositionR);
        this.dMotors.moveControllers();

        for(double i = 0.0d; i <= power; i += 0.1d){
            this.setLeftMotors(i);
            this.setRightMotors(i);
            Thread.sleep(100);
        }

        boolean doneL = false;
        boolean doneR = false;

        while (!doneL || !doneR) {
            currentPositionL = this.dMotors.getLeftFront().getCurrentPosition();
            currentPositionR = this.dMotors.getRightFront().getCurrentPosition();

//            System.out.println("Left Current Position: " + currentPositionL + ", Right Current Position: " + currentPositionR);

            if (!doneL && currentPositionL >= targetPositionL) {
                this.setLeftMotors(0);
                doneL = true;
            }
            if (!doneR && currentPositionR >= targetPositionR) {
                this.setRightMotors(0);
                doneR = true;
            }

            Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
        }

        this.dMotors.resetControllers();
        this.dMotors.regularController();
    }


  //  @Override
    public void encoderAngleTurn(Constants.Turns direction, int angle, double power) throws InterruptedException {
//        System.out.println("Moving Distance Drivetrain");

        double leftPower;
        double rightPower;
        DcMotor targetMotor;

        boolean isLeft = Constants.Turns.LEFT_TURN.equals(direction);

        if(isLeft){
            leftPower = -power;
            rightPower = power;
            targetMotor = this.dMotors.getRightFront();
        }else{
            leftPower = power;
            rightPower = -power;
            targetMotor = this.dMotors.getRightFront();
        }

        this.dMotors.resetControllers();

        this.dMotors.encodeInitialize();

        int currentPosition = targetMotor.getCurrentPosition();

//        System.out.println("Left Current Position: " + currentPositionL + ", Right Current Position: " + currentPositionR);

        int counts = this.dMotors.getCounts(angle*DISTANCE_PER_DEGREE);
        int targetPosition = currentPosition + counts;

//        System.out.println("Left Target Position: " + targetPositionL + ", Right Target Position: " + targetPositionR);
        targetMotor.setTargetPosition(targetPosition);

        targetMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        this.setLeftMotors(leftPower);
        this.setRightMotors(rightPower);

        while (currentPosition < targetPosition) {
            currentPosition = targetMotor.getCurrentPosition();

//            System.out.println("Left Current Position: " + currentPositionL + ", Right Current Position: " + currentPositionR);

            Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
        }

        this.dMotors.resetControllers();
        this.dMotors.regularController();
    }

    @Override
    public void timedMove(double power, long time) throws InterruptedException {
        this.setLeftMotors(power);
        this.setRightMotors(power);

        Thread.sleep(time);

        this.setLeftMotors(0.0d);
        this.setRightMotors(0.0d);
    }

    @Override
    public void timedAngleTurn(Constants.Turns direction, int angle) throws InterruptedException {
        if(direction == Constants.Turns.LEFT_TURN){
            this.setLeftMotors(-TURN_POWER);
            this.setRightMotors(TURN_POWER);
        }else{
            this.setLeftMotors(TURN_POWER);
            this.setRightMotors(-TURN_POWER);
        }

        Thread.sleep(angle * TIMED_PER_DEGREE);
       // Thread.sleep(2000);

        this.setLeftMotors(0.0d);
        this.setLeftMotors(0.0d);
    }

    @Override
    public boolean stopAtWhiteLine(long finalWaitTime, double power) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        long currentTime;
        boolean leftFound = false;
        boolean rightFound = false;
        boolean timeExhausted = false;

        this.setLeftMotors(power);
        this.setRightMotors(power);

        double leftEOPDReading;
        double rightEOPDReading;

        while (!(leftFound && rightFound) && !timeExhausted) {
            currentTime = System.currentTimeMillis();

            leftEOPDReading = this.dSense.getLeftEOPD().getRawLightDetected() * 100;
            rightEOPDReading = this.dSense.getRightEOPD().getRawLightDetected() * 100;

            System.out.println("Left EOPD: " + leftEOPDReading + ", Right EOPD: " + rightEOPDReading);

            if(leftEOPDReading > Constants.EOPD_WHITE_THRESHOLD){
                this.setLeftMotors(0.0f);
                leftFound = true;
            }else if(rightEOPDReading > Constants.EOPD_WHITE_THRESHOLD){
                this.setRightMotors(0.0f);
                rightFound = true;
            }

            timeExhausted = currentTime - startTime > finalWaitTime;

            Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
        }

        this.setLeftMotors(0.0f);
        this.setRightMotors(0.0f);
        return (leftFound && rightFound);
    }

    @Override
    public boolean stopAtBeacon(Constants.Color color, long finalWaitTime) throws InterruptedException {
        long currentTime;

        long startTime = System.currentTimeMillis();
        boolean timeExhausted = false;
        boolean beaconFound = false;

        this.setLeftMotors(0.2d);
        this.setRightMotors(0.2d);

        do{
            if(color == Constants.Color.BLUE) {
//                System.out.println("Blue val: " + dSense.getColoring().red());
                beaconFound = (this.dSense.getColoring().blue() > color.getLowThreshold());
            }else{
//                System.out.println("Red val: " + dSense.getColoring().red());
                beaconFound = (this.dSense.getColoring().red() >= color.getLowThreshold());
            }
            currentTime = System.currentTimeMillis();
            timeExhausted = currentTime - startTime > finalWaitTime;

            Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
        }while(!beaconFound && !timeExhausted);

        this.setLeftMotors(0.0d);
        this.setRightMotors(0.0d);
        return beaconFound;
    }

    @Override
    public synchronized float getPowerLF() {
        return powerLF;
    }

    @Override
    public synchronized void setPowerLF(float powerLF) {
        this.powerLF = powerLF;
    }

    @Override
    public synchronized float getPowerLR() {
        return powerLR;
    }

    @Override
    public synchronized void setPowerLR(float powerLR) {
        this.powerLR = powerLR;
    }

    @Override
    public synchronized float getPowerRF() {
        return powerRF;
    }

    @Override
    public synchronized void setPowerRF(float powerRF) {
        this.powerRF = powerRF;
    }

    @Override
    public synchronized float getPowerRR() {
        return powerRR;
    }

    @Override
    public synchronized void setPowerRR(float powerRR) {
        this.powerRR = powerRR;
    }

    @Override
    public void handleMessage() throws InterruptedException{
        TeleopMessages msg = this.queue.peek();

        if (msg != null && Constants.RobotComponent.DRIVE_TRAIN.equals(msg.getRobotComponent())){
            msg = this.queue.take();

            this.handleDrivetrainMessage(msg);
            this.joystickDrive();
        }
    }

    @Override
    public void joystickDrive() {
        this.dMotors.getLeftFront().setPower(this.getPowerLF());
        this.dMotors.getLeftBack().setPower(this.getPowerLR());
        this.dMotors.getRightFront().setPower(-this.getPowerRF());
        this.dMotors.getRightBack().setPower(-this.getPowerRR());
    }
}