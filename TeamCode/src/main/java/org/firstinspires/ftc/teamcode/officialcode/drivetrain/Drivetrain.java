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
        int origHeading = this.dSense.getHeading();
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

            int currentHeading = this.dSense.getHeading();
            Constants.Turns direction = this.getDirection(origHeading, currentHeading);
            int angle = this.getAngle(origHeading, currentHeading, direction);

            if (angle > 1) {
                double newPower;
                switch (direction) {
                    case LEFT_TURN:
                        newPower = this.dMotors.getRightFront().getPower() + 0.1;
                        break;
                    case RIGHT_TURN:
                        newPower = this.dMotors.getRightFront().getPower() - 0.1;
                        break;
                    default:
                        throw new IllegalStateException("Unknown turn direction:" + direction);
                }
                if(newPower > 1.0d){
                    newPower = 1.0d;
                }else if(newPower < -1.0d){
                    newPower = -1.0d;
                }

                this.setRightMotors(newPower);
            } else {
                this.setRightMotors(power);
            }
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
            System.out.println("*************Current Counts: " + currentPositionL);
//            System.out.println("***********Heading While Moving: " + this.dSense.getHeading());
        }
    }

    @Override
    public void rightAngleTurn(Constants.Turns direction) {

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

    private Constants.Turns getDirection(int finalHeading, int currentHeading) {
//        return finalHeading-currentHeading > 0 || finalHeading-currentHeading <= -180 ?
//                Constants.Turns.LEFT_TURN : Constants.Turns.RIGHT_TURN;
        Constants.Turns direction;
        int headingDiff = finalHeading - currentHeading;

        if (headingDiff > 0) {
            direction = headingDiff <= 180 ? Constants.Turns.LEFT_TURN : Constants.Turns.RIGHT_TURN;
        } else {
            direction = headingDiff <= -180 ? Constants.Turns.LEFT_TURN : Constants.Turns.RIGHT_TURN;
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
                //returnValue = 0;
        }
        return returnValue;
    }
}
