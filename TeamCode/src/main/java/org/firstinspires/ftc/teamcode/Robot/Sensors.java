package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.*;

import java.util.Map;

public class Sensors {
    private Map<String, ColorSensor> colorSensor;
    private GyroSensor gyroSensor;
    private TouchSensor touchSensor;
    private UltrasonicSensor ultrasonicSensor;
    private OpticalDistanceSensor opticalDistanceSensor;
    private LightSensor lightSensor;
    private IrSeekerSensor irSeekerSensor;
    private AccelerationSensor accelerationSensor;
    private CompassSensor compassSensor;
    private VoltageSensor voltageSensor;
    private BNO055IMU imu;

    private HardwareMap hardwareMap;

    public Sensors(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }


}
