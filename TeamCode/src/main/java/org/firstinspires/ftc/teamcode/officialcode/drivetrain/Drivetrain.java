package org.firstinspires.ftc.teamcode.officialcode.drivetrain;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.sensors.Sensors;

/**
 * Created by Higgs Bosons on 10/22/2016.
 */
public class Drivetrain implements IDrivetrain, Runnable {
    private DriveMotors dMotors;
    private Sensors dSense;

    public Drivetrain(DriveMotors dMotors, Sensors dSense){
        this.dMotors = dMotors;
        this.dSense = dSense;
    }

    @Override
    public void moveDistance(int distance, double power) throws InterruptedException {
        this.dMotors.resetControllers();

        this.dMotors.encodeInitialize();

        int currentPositionL = this.dMotors.getLeftFront().getCurrentPosition();
        int currentPositionR = this.dMotors.getRightFront().getCurrentPosition();
        int counts = this.dMotors.getCounts(distance);
        int targetPositionL = currentPositionL + counts;
        int targetPositionR = currentPositionR + counts;

        this.dMotors.getLeftFront().setTargetPosition(targetPositionL);
        this.dMotors.getRightFront().setTargetPosition(targetPositionR);
        this.dMotors.moveControllers();
        this.setLeftMotors(power);
        this.setRightMotors(power);

        boolean doneL = false;
        boolean doneR = false;

        while (!doneL || !doneR) {
            Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
            currentPositionL = this.dMotors.getLeftFront().getCurrentPosition();
            currentPositionR = this.dMotors.getRightFront().getCurrentPosition();

            if (!doneL && currentPositionL >= targetPositionL) {
                this.setLeftMotors(0);
                doneL = true;
            }
            if (!doneR && currentPositionR >= targetPositionR) {
                this.setRightMotors(0);
                doneR = true;
            }
        }
    }

    @Override
    public void rightAngleTurn(Constants.Turns direction) throws InterruptedException {
        this.dSense.gyroCalibrate();

        while(this.dSense.getGyro().isCalibrating()){
            Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
        }

        int currentHeading = this.dSense.getHeading();
        int finalHeading;

        if(direction == Constants.Turns.LEFT_TURN){
            finalHeading = (359 - (89 - (currentHeading - 0)));

            this.setLeftMotors(-0.5);
            this.setRightMotors(0.5);

            while(currentHeading > finalHeading){
                Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
            }
        } else {
            finalHeading =  currentHeading + 90;

            this.setLeftMotors(0.5);
            this.setRightMotors(-0.5);

            while(currentHeading < finalHeading){
                Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
            }
        }

        this.setAllPower(0.0);
    }

    @Override
    public boolean stopAtWhiteLine(long finalWaitTime) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        long currentTime;
        boolean leftFound = false;
        boolean rightFound = false;
        boolean timeExhausted = false;

        this.setAllPower(0.5f);

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

        this.setAllPower(0.0f);
        return (leftFound && rightFound);
    }

    @Override
    public boolean stopAtBeacon(Constants.Color color, long finalWaitTime) throws InterruptedException {
        long currentTime;

        long startTime = System.currentTimeMillis();
        boolean timeExhausted = false;
        boolean beaconFound = false;

        this.setAllPower(0.5f);

        do{
            beaconFound = (this.dSense.getHue() > color.getLowThreshold() &&
                    this.dSense.getHue() < color.getHighThreshold());
            currentTime = System.currentTimeMillis();
            timeExhausted = currentTime - startTime > finalWaitTime;

            Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
        }while(!beaconFound && !timeExhausted);

        this.setAllPower(0.0f);
        return beaconFound;
    }

    @Override
    public void joystickDrive() {

    }

    @Override
    public void run() {

    }

    private void setLeftMotors(double power){
        this.dMotors.getLeftFront().setPower(power);
        this.dMotors.getLeftBack().setPower(power);
    }

    private void setRightMotors(double power){
        this.dMotors.getRightFront().setPower(power);
        this.dMotors.getRightBack().setPower(power);
    }

    private void setAllPower(double power){
        this.setLeftMotors(power);
        this.setRightMotors(power);
    }
}
