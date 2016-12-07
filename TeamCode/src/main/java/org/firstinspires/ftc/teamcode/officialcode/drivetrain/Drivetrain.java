package org.firstinspires.ftc.teamcode.officialcode.drivetrain;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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
        System.out.println("Moving Distance Drivetrain");
        this.dMotors.resetControllers();

        this.dMotors.encodeInitialize();

        int currentPositionL = this.dMotors.getLeftFront().getCurrentPosition();
        int currentPositionR = this.dMotors.getRightFront().getCurrentPosition();

        System.out.println("Left Current Position: " + currentPositionL + ", Right Current Position: " + currentPositionR);

        int counts = this.dMotors.getCounts(distance);
        int targetPositionL = currentPositionL + counts;
        int targetPositionR = currentPositionR + counts;

        System.out.println("Left Target Position: " + targetPositionL + ", Right Target Position: " + targetPositionR);

        this.dMotors.getLeftFront().setTargetPosition(targetPositionL);
        this.dMotors.getRightFront().setTargetPosition(targetPositionR);
        this.dMotors.moveControllers();
        this.setLeftMotors(power);
        this.setRightMotors(power);

        boolean doneL = false;
        boolean doneR = false;

        while (!doneL || !doneR) {
            currentPositionL = this.dMotors.getLeftFront().getCurrentPosition();
            currentPositionR = this.dMotors.getRightFront().getCurrentPosition();

            System.out.println("Left Current Position: " + currentPositionL + ", Right Current Position: " + currentPositionR);

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
    }

    @Override
    public void rightAngleTurn(Constants.Turns direction) throws InterruptedException {
        System.out.println("Starting Right Angle");
        this.dSense.gyroCalibrate();

        System.out.println("Starting Calibrate");
//        while(this.dSense.getGyro().isCalibrating()){
//            //Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
//        }
        Thread.sleep(2000);
        System.out.println("Ending Calibrate");

        int currentHeading = this.dSense.getHeading();
        int finalHeading;

        if(direction == Constants.Turns.LEFT_TURN){
            finalHeading = (359 - (89 - (currentHeading - 0)));

            System.out.println("Current Heading: " + currentHeading + ", Final Heading: " + finalHeading);

            this.setLeftMotors(-0.2);
            this.setRightMotors(0.2);

            while(currentHeading > finalHeading){
                currentHeading = this.dSense.getHeading();
                System.out.println("Current Heading: " + currentHeading + ", Final Heading: " + finalHeading);
                Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
            }
        } else {
            finalHeading =  currentHeading + 90;

            System.out.println("Current Heading: " + currentHeading + ", Final Heading: " + finalHeading);

            this.setLeftMotors(0.2);
            this.setRightMotors(-0.2);

            while(currentHeading < finalHeading){
                currentHeading = this.dSense.getHeading();
                System.out.println("Current Heading: " + currentHeading + ", Final Heading: " + finalHeading);
                Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
            }
        }

        this.dMotors.setPowerAll(0.0);
    }

    @Override
    public boolean stopAtWhiteLine(long finalWaitTime) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        long currentTime;
        boolean leftFound = false;
        boolean rightFound = false;
        boolean timeExhausted = false;

        this.dMotors.setPowerAll(0.0);

        while (!(leftFound && rightFound) && !timeExhausted) {
            currentTime = System.currentTimeMillis();

            if(this.dSense.getLeftEOPD().getRawLightDetected() > Constants.EOPD_WHITE_THRESHOLD_RAW_LOW){
                this.setLeftMotors(0.0f);
                leftFound = true;
            }else if(this.dSense.getRightEOPD().getRawLightDetected() > Constants.EOPD_WHITE_THRESHOLD_RAW_LOW){
                this.setRightMotors(0.0f);
                rightFound = true;
            }

            timeExhausted = currentTime - startTime > finalWaitTime;

            Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
        }

        this.dMotors.setPowerAll(0.0);
        return (leftFound && rightFound);
    }

    @Override
    public boolean stopAtBeacon(Constants.Color color, long finalWaitTime) throws InterruptedException {
        long currentTime;

        long startTime = System.currentTimeMillis();
        boolean timeExhausted = false;
        boolean beaconFound = false;

        this.dMotors.setPowerAll(0.0);

        do{
            beaconFound = (this.dSense.getHue() > color.getLowThreshold() &&
                    this.dSense.getHue() < color.getHighThreshold());
            currentTime = System.currentTimeMillis();
            timeExhausted = currentTime - startTime > finalWaitTime;

            Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
        }while(!beaconFound && !timeExhausted);

        this.dMotors.setPowerAll(0.0);
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
