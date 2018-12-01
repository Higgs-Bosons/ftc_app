package org.firstinspires.ftc.teamcode.Galaxy.Autonomus;

import org.firstinspires.ftc.teamcode.Galaxy.Robot.MecanumWheelRobot;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

class AutonomousMethods {
    static void runProgram(AutonomousCode code){
        String actionName;
        Object[] array;
        MecanumWheelRobot bubbles = code.Bubbles;

        for(int counter = 0; counter < code.numOfActions; counter++){
            actionName = code.getActionName(counter);
            switch(actionName){
                case DRIVE_ROBOT:
                    array = code.getAction(counter);
                    bubbles.moveDegrees((double) array[1], (int) array[2],(double) array[3], (double) array[4], (double) array[5]);
                    break;
                case MOVE_SERVO:
                    array = code.getAction(counter);
                    bubbles.moveServo((String) array[1], (int) array[2]);
                    break;

            }
        }

    }
}
