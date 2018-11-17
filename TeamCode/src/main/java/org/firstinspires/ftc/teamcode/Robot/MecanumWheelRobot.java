package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class MecanumWheelRobot {
    private Motors motors;
    private Servos servos;
    private Sensors sensors;
    private DriveTrain dt;


    public MecanumWheelRobot(HardwareMap hardwareMap, @MotorNameTypes String motorNameType) {
        this.motors = new Motors(hardwareMap);
        this.sensors = new Sensors(hardwareMap);
        this.servos = new Servos(hardwareMap);
        this.dt = new DriveTrain(this.motors.getAutoDriveTrain(motorNameType));
    }

    public DriveTrain useDriveTrain() {
        return this.dt;
    }
    public Motors useMotors(){
        return this.motors;
    }
    public Sensors useSensors(){
        return this.sensors;
    }
    public Servos useServos(){
        return this.servos;
    }

}
