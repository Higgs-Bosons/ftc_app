package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class MecanumWheelRobot extends Servos{
    public MecanumWheelRobot(HardwareMap hardwareMap, @MotorNameTypes String motorNameType) {
        super(hardwareMap);
        super.addMotors(getAutoDriveTrain(motorNameType));

    }
}
