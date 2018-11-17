package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.hardware.bosch.*;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.external.navigation.*;

import java.util.Hashtable;
import java.util.Map;

import org.firstinspires.ftc.teamcode.Constants;
import static org.firstinspires.ftc.teamcode.Constants.*;

public class Sensors {
    private Map<String, String> sensorNameAndTypes;
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

    public Sensors(){}
    public Sensors(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        this.colorSensor = new Hashtable<>();
        this.gyroSensor = new Hashtable<>();
        this.touchSensor = new Hashtable<>();
        this.ultrasonicSensor = new Hashtable<>();
        this.opticalDistanceSensor = new Hashtable<>();
        this.lightSensor = new Hashtable<>();
        this.irSeekerSensor = new Hashtable<>();
        this.accelerationSensor = new Hashtable<>();
        this.compassSensor = new Hashtable<>();
        this.voltageSensor = new Hashtable<>();
        this.imu = new Hashtable<>();
    }

    public void addSensor(String name, @Constants.SensorTypes int sensorType) throws RuntimeException {
        sensorNameAndTypes.put(name, sensorType+"");
        switch (sensorType) {
            case COLOR_SENSOR:
                if (this.colorSensor.containsKey(name)) {
                    throw new customErrors.DuplicateNameException();
                }
                this.colorSensor.put(name, hardwareMap.colorSensor.get(name));
                break;
            case GYRO_SENSOR:
                if (this.gyroSensor.containsKey(name)) {
                    throw new customErrors.DuplicateNameException();
                }
                this.gyroSensor.put(name, hardwareMap.gyroSensor.get(name));
                break;
            case TOUCH_SENSOR:
                if (this.touchSensor.containsKey(name)) {
                    throw new customErrors.DuplicateNameException();
                }
                this.touchSensor.put(name, hardwareMap.touchSensor.get(name));
                break;
            case ULTRASONIC_SENSOR:
                if (this.ultrasonicSensor.containsKey(name)) {
                    throw new customErrors.DuplicateNameException();
                }
                this.ultrasonicSensor.put(name, hardwareMap.ultrasonicSensor.get(name));
                break;
            case OPTICAL_DISTANCE_SENSOR:
                if (this.opticalDistanceSensor.containsKey(name)) {
                    throw new customErrors.DuplicateNameException();
                }
                this.opticalDistanceSensor.put(name, hardwareMap.opticalDistanceSensor.get(name));
                break;
            case LIGHT_SENSOR:
                if (this.lightSensor.containsKey(name)) {
                    throw new customErrors.DuplicateNameException();
                }
                this.lightSensor.put(name, hardwareMap.lightSensor.get(name));
                break;
            case IR_SEEKER_SENSOR:
                if (this.irSeekerSensor.containsKey(name)) {
                    throw new customErrors.DuplicateNameException();
                }
                this.irSeekerSensor.put(name, hardwareMap.irSeekerSensor.get(name));
                break;
            case ACCELERATION_SENSOR:
                if (this.accelerationSensor.containsKey(name)) {
                    throw new customErrors.DuplicateNameException();
                }
                this.accelerationSensor.put(name, hardwareMap.accelerationSensor.get(name));
                break;
            case COMPASS_SENSOR:
                if (this.compassSensor.containsKey(name)) {
                    throw new customErrors.DuplicateNameException();
                }
                this.compassSensor.put(name, hardwareMap.compassSensor.get(name));
                break;
            case VOLTAGE_SENSOR:
                if (this.voltageSensor.containsKey(name)) {
                    throw new customErrors.DuplicateNameException();
                }
                this.voltageSensor.put(name, hardwareMap.voltageSensor.get(name));
                break;
            case IMU:
                if (this.imu.containsKey(name)) {
                    throw new customErrors.DuplicateNameException();
                }
                this.imu.put(name, hardwareMap.get(BNO055IMU.class, "imu"));
                break;
        }
    }


//-------{IMU}--------------------------------------------------------------------------------------
    public void ResetIMUGyro(String IMUName) {
        BNO055IMU IMU = getIMU(IMUName);
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "imu";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        IMU.initialize(parameters);
    }
    public float[] ReadIMUGyro(String IMUName) {
        BNO055IMU IMU = getIMU(IMUName);
        float[] Values = new float[3];
        Values[0] = IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        Values[1] = IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).secondAngle;
        Values[2] = IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).thirdAngle;
        if (Values[0] < 0) Values[0] = (180 + (181 - Math.abs(Values[0])));
        if (Values[1] < 0) Values[1] = (180 + (181 - Math.abs(Values[1])));
        if (Values[2] < 0) Values[2] = (180 + (181 - Math.abs(Values[2])));

        return Values;
    }



//-------{GETTERS}----------------------------------------------------------------------------------
    private ColorSensor getColorSensor(String name) {
        return this.colorSensor.get(name);
    }
    private GyroSensor getGyroSensor(String name) {
        return this.gyroSensor.get(name);
    }
    private TouchSensor getTouchSensor(String name) {
        return this.touchSensor.get(name);
    }
    private UltrasonicSensor getUltrasonicSensor(String name) {
        return this.ultrasonicSensor.get(name);
    }
    private OpticalDistanceSensor getOpticalDistanceSensor(String name) {
        return this.opticalDistanceSensor.get(name);
    }
    private LightSensor getLightSensor(String name) {
        return this.lightSensor.get(name);
    }
    private IrSeekerSensor getIrSeekerSensor(String name) {
        return this.irSeekerSensor.get(name);
    }
    private AccelerationSensor getAccelerationSensor(String name) {
        return this.accelerationSensor.get(name);
    }
    private CompassSensor getCompassSensor(String name) {
        return this.compassSensor.get(name);
    }
    private VoltageSensor getVoltageSensor(String name) {
        return this.voltageSensor.get(name);
    }
    private BNO055IMU getIMU(String name) {
        return this.imu.get(name);
    }
}
