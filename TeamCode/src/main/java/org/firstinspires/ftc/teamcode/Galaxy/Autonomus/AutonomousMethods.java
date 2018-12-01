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
                    bubbles.moveDegrees((int) array[1], (int) array[2],(float) array[3], (float) array[4], (float) array[5]);
                    break;
                case MOVE_SERVO:
                    array = code.getAction(counter);
                    bubbles.moveServo((String) array[1], (int) array[2]);
                    break;

            }
        }

    }
}
