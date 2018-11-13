package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.*;

import java.util.Map;

public class Sensors {
    private Map<String, ColorSensor> colorSensor;
    private Map<String, GyroSensor> gyroSensor;
    private Map<String, TouchSensor> touchSensor;
    private Map<String, UltrasonicSensor> ultrasonicSensor;
    private Map<String, OpticalDistanceSensor> opticalDistanceSensor;
    private Map<String, LightSensor> lightSensor;
    private Map<String, IrSeekerSensor> irSeekerSensor;
    private Map<String, AccelerationSensor> accelerationSensor;
    private Map<String, CompassSensor> compassSensor;
    private Map<String, VoltageSensor> voltageSensor;
    private Map<String, BNO055IMU> imu;

    private HardwareMap hardwareMap;

    public Sensors(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }


}
