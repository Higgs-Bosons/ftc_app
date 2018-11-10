package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.*;

public class Sensors {
    private ColorSensor colorSensor;
    private GyroSensor gyroSensor;
    private TouchSensor touchSensor;
    private UltrasonicSensor ultrasonicSensor;
    private OpticalDistanceSensor opticalDistanceSensor;
    private LightSensor lightSensor;
    private IrSeekerSensor irSeekerSensor;
    private AccelerationSensor accelerationSensor;
    private CompassSensor compassSensor;
    private VoltageSensor voltageSensor;

    private HardwareMap hardwareMap;

    public Sensors(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public void AddASensor(String SensorName, int SensorType) throws Exception{
        switch (SensorType) {
            case 0:
                if (this.colorSensor != null) {
                    throw new customErrors.DuplicateSensorException();
                }
                else {
                    this.colorSensor = hardwareMap.colorSensor.get(SensorName);
                }
                break;
            case 1:
                if (this.gyroSensor != null) {
                    throw new customErrors.DuplicateSensorException();
                }
                else {
                    this.gyroSensor = hardwareMap.gyroSensor.get(SensorName);
                }
                break;
            case 3:
                if (this.touchSensor != null) {
                    throw new customErrors.DuplicateSensorException();
                }
                else {
                    this.touchSensor = hardwareMap.touchSensor.get(SensorName);
                }
                break;
            case 4:
                if (this.ultrasonicSensor != null) {
                    throw new customErrors.DuplicateSensorException();
                }
                else {
                    this.ultrasonicSensor = hardwareMap.ultrasonicSensor.get(SensorName);
                }
                break;
            case 5:
                if (this.opticalDistanceSensor != null) {
                    throw new customErrors.DuplicateSensorException();
                }
                else {
                    this.opticalDistanceSensor = hardwareMap.opticalDistanceSensor.get(SensorName);
                }
                break;
            case 6:
                if (this.lightSensor != null) {
                    throw new customErrors.DuplicateSensorException();
                }
                else {
                    this.lightSensor = hardwareMap.lightSensor.get(SensorName);
                }
                break;
            case 7:
                if (this.irSeekerSensor != null) {
                    throw new customErrors.DuplicateSensorException();
                }
                else {
                    this.irSeekerSensor = hardwareMap.irSeekerSensor.get(SensorName);
                }
                break;
            case 8:
                if (this.accelerationSensor != null) {
                    throw new customErrors.DuplicateSensorException();
                }
                else {
                    this.accelerationSensor = hardwareMap.accelerationSensor.get(SensorName);
                }
                break;
            case 9:
                if (this.compassSensor != null) {
                    throw new customErrors.DuplicateSensorException();
                }
                else {
                    this.compassSensor = hardwareMap.compassSensor.get(SensorName);
                }
                break;
            case 10:
                if (this.voltageSensor != null) {
                    throw new customErrors.DuplicateSensorException();
                }
                else {
                    this.voltageSensor = hardwareMap.voltageSensor.get(SensorName);
                }
                break;
        }
    }
}
