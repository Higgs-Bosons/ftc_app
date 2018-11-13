package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.AccelerationSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import static org.firstinspires.ftc.teamcode.Constants.*;

import java.util.Map;

public class SensorsWJK {
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

    public SensorsWJK(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }
    public void addSensor(String name, @SensorTypes int sensorType);

}
