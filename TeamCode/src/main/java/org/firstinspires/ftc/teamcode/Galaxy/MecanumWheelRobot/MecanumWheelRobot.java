package org.firstinspires.ftc.teamcode.Galaxy.MecanumWheelRobot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

public class MecanumWheelRobot extends Servos{
    public MecanumWheelRobot(HardwareMap hardwareMap, @MotorNameTypes String motorNameType) {
        super(hardwareMap);
        super.addMotors(getAutoDriveTrain(motorNameType));

    }
}
