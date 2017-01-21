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
//    private static final double DISTANCE_PER_DEGREE = 0.3d;
    private static final double MAX_TURN_POWER = 1.5;
    private static final double MIN_TURN_POWER = 0.1;

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

    private Constants.Turns getDirection(int finalHeading, int currentHeading){
        Constants.Turns direction;
        int headingDiff = finalHeading - currentHeading;

        if(headingDiff > 0){
            direction = headingDiff <= 180 ? Constants.Turns.RIGHT_TURN : Constants.Turns.LEFT_TURN;
        }else{
            direction = headingDiff <= -180 ? Constants.Turns.RIGHT_TURN : Constants.Turns.LEFT_TURN;
        }

        return direction;
    }

    private int getAngle(int finalHeading, int currentHeading, Constants.Turns direction) {
        int returnValue = 0;
        switch (direction) {
            case LEFT_TURN:
                if (finalHeading < currentHeading) {
                    returnValue = (360 - currentHeading) + finalHeading;
                } else {
                    returnValue = finalHeading - currentHeading;
                }
                break;
            case RIGHT_TURN:
                if (currentHeading < finalHeading) {
                    returnValue = (360 - finalHeading) + currentHeading;
                } else {
                    returnValue = currentHeading - finalHeading;
                }
                break;
            default:
                throw new IllegalStateException("No turns specified: " + direction);
        }
        return returnValue;
    }

    private void setTurnPower(Constants.Turns direction, int angle) {
        double currentPower = (MAX_TURN_POWER / 180d) * (double) angle;
        if (currentPower < MIN_TURN_POWER) {
            currentPower = MIN_TURN_POWER;
        }else if(currentPower > 0.2){
            currentPower = 0.2;
        }

        System.out.println("Turn power: " + currentPower);

        switch (direction) {
            case LEFT_TURN:
                this.setLeftMotors(-currentPower);
                this.setRightMotors(currentPower);

                System.out.println("********Turning Left at Power" + currentPower);
                break;
            case RIGHT_TURN:
                this.setLeftMotors(currentPower);
                this.setRightMotors(-currentPower);

                System.out.println("********Turning Right at Power" + currentPower);
                break;
            default:
                this.setLeftMotors(0.0f);
                this.setRightMotors(0.0f);
        }
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

    private void rampUp(double maxPower) throws InterruptedException {
        double multiFactor = maxPower > 0.0 ? 1.0d : -1.0d;

        for (double i = 0.0d; i <= Math.abs(maxPower); i += 0.1d) {
            this.setLeftMotors(i * multiFactor);
            this.setRightMotors(i * multiFactor);
            Thread.sleep(100);

            System.out.println("Ramping up to power: " + (i * multiFactor));
        }
    }

    @Override
    public void moveDistance(int distance, double power) throws InterruptedException {
        System.out.println("Moving Distance Drivetrain");
        this.dMotors.resetControllers();

        this.dMotors.encodeInitialize();

        int currentPositionL = this.dMotors.getLeftFront().getCurrentPosition();
        int currentPositionR = this.dMotors.getRightFront().getCurrentPosition();

        System.out.println("Left Current Position: " + currentPositionL + ", Right Current Position: " + currentPositionR);

        int counts = this.dMotors.getCounts(distance);
//        int targetPositionL = power > 0.0d ? currentPositionL + counts : currentPositionL - counts;
//        int targetPositionR = power > 0.0d ? currentPositionR + counts : currentPositionR - counts;
        int targetPositionL = currentPositionL + counts;
        int targetPositionR = currentPositionR + counts;

        System.out.println("Left Target Position: " + targetPositionL + ", Right Target Position: " + targetPositionR);

        this.dMotors.getLeftFront().setTargetPosition(targetPositionL);
        this.dMotors.getRightFront().setTargetPosition(targetPositionR);
        this.dMotors.moveControllers();

        this.rampUp(power);

        int absTargetL = Math.abs(targetPositionL);
        int absTargetR = Math.abs(targetPositionR);
        boolean slowDownL = false;
        boolean slowDownR = false;
        boolean doneL = false;
        boolean doneR = false;

        while (!doneL || !doneR) {
            currentPositionL = this.dMotors.getLeftFront().getCurrentPosition();
            currentPositionR = this.dMotors.getRightFront().getCurrentPosition();

            System.out.println("Left Current Position: " + currentPositionL + ", Right Current Position: " + currentPositionR);

            doneL = Math.abs(currentPositionL) >= absTargetL;
            doneR = Math.abs(currentPositionR) >= absTargetR;

            slowDownL = absTargetL - Math.abs(currentPositionL) < 1120;
            slowDownR = absTargetR - Math.abs(currentPositionR) < 1120;

            if (doneL) {
                this.setLeftMotors(0);
            }else if(slowDownL){
                double usePower = power * 0.5d;
                if(Math.abs(usePower) > 0.2d){
                    System.out.println("Slowing Left: " + usePower);
                    this.setLeftMotors(usePower);
                }
            }

            if (doneR) {
                this.setRightMotors(0);
            }else if(slowDownR){
                double usePower = power * 0.5d;
                if(Math.abs(usePower) > 0.2d){
                    System.out.println("Slowing Right: " + usePower);
                    this.setRightMotors(usePower);
                }
            }

            Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
        }

        this.dMotors.resetControllers();
        this.dMotors.regularController();
    }


//  //  @Override
//    public void encoderAngleTurn(Constants.Turns direction, int angle, double power) throws InterruptedException {
////        System.out.println("Moving Distance Drivetrain");
//
//        double leftPower;
//        double rightPower;
//        DcMotor targetMotor;
//
//        boolean isLeft = Constants.Turns.LEFT_TURN.equals(direction);
//
//        if(isLeft){
//            leftPower = -power;
//            rightPower = power;
//            targetMotor = this.dMotors.getRightFront();
//        }else{
//            leftPower = power;
//            rightPower = -power;
//            targetMotor = this.dMotors.getRightFront();
//        }
//
//        this.dMotors.resetControllers();
//
//        this.dMotors.encodeInitialize();
//
//        int currentPosition = targetMotor.getCurrentPosition();
//
////        System.out.println("Left Current Position: " + currentPositionL + ", Right Current Position: " + currentPositionR);
//
//        int counts = this.dMotors.getCounts(angle*DISTANCE_PER_DEGREE);
//        int targetPosition = currentPosition + counts;
//
////        System.out.println("Left Target Position: " + targetPositionL + ", Right Target Position: " + targetPositionR);
//        targetMotor.setTargetPosition(targetPosition);
//
//        targetMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        this.setLeftMotors(leftPower);
//        this.setRightMotors(rightPower);
//
//        while (currentPosition < targetPosition) {
//            currentPosition = targetMotor.getCurrentPosition();
//
////            System.out.println("Left Current Position: " + currentPositionL + ", Right Current Position: " + currentPositionR);
//
//            Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
//        }
//
//        this.dMotors.resetControllers();
//        this.dMotors.regularController();
//    }



    @Override
    public void timedMove(double power, long time) throws InterruptedException {
        this.setLeftMotors(power);
        this.setRightMotors(power);

        Thread.sleep(time);

        this.setLeftMotors(0.0d);
        this.setRightMotors(0.0d);
    }

    @Override
    public void goToHeading(int finalHeading) throws InterruptedException {
        int currentHeading;
        Constants.Turns direction;
        int angle;
        boolean done = false;

        while(!done){
            currentHeading = this.dSense.getHeading();
            System.out.println("***********Current Heading: " + currentHeading);

            if(Math.abs(currentHeading-finalHeading) < 2){
                done = true;
                this.setLeftMotors(0.0d);
                this.setRightMotors(0.0d);
            }else{
                direction = this.getDirection(finalHeading, currentHeading);
                angle = this.getAngle(finalHeading, currentHeading, direction);
                this.setTurnPower(direction, angle);
                Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
            }
        }
    }

    @Override
    public void timedAngleTurn(Constants.Turns direction, int angle) throws InterruptedException {
        if (direction == Constants.Turns.LEFT_TURN) {
            this.setLeftMotors(-TURN_POWER);
            this.setRightMotors(TURN_POWER);
        } else {
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
    public boolean wallAlign(long finalWaitTime) throws InterruptedException{
        long currentTime;

        long startTime = System.currentTimeMillis();
        boolean timeExhausted = false;
        boolean rFound = false;
        boolean lFound = false;

        this.setLeftMotors(0.2d);
        this.setRightMotors(0.2d);

        while(!(lFound && rFound) && !timeExhausted) {
            currentTime = System.currentTimeMillis();
            System.out.println("Left Touch Sensor Value:" + this.dSense.getlTouch().getValue());
            System.out.println("Right Touch Sensor Value:" + this.dSense.getrTouch().getValue());
            if (this.dSense.getlTouch().isPressed()) {
                System.out.println("Left Pressed");
                this.setLeftMotors(0.0f);
                lFound = true;
            } else if (this.dSense.getrTouch().isPressed()) {
                System.out.println("Right Pressed");
                this.setRightMotors(0.0f);
                rFound = true;
            }

            timeExhausted = currentTime - startTime > finalWaitTime;

            Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
        }
        System.out.println("Done Touching");

        this.setLeftMotors(0.0d);
        this.setRightMotors(0.0d);
        return (lFound && rFound);
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
