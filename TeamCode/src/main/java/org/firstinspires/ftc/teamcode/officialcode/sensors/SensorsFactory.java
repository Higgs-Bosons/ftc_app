package org.firstinspires.ftc.teamcode.officialcode.sensors;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Created by Higgs Bosons on 11/22/2016.
 */
public class SensorsFactory {
    private static Sensors sensors;

    public static synchronized Sensors getInstance(OpMode opMode) throws InterruptedException{
        if(sensors == null){
            sensors = getSensors(opMode);
        }
        return sensors;
    }

    private static Sensors getSensors(OpMode opMode) throws InterruptedException{
        ColorSensor coloring = opMode.hardwareMap.colorSensor.get(Constants.COLOR_SENSOR);
//        GyroSensor gyro = opMode.hardwareMap.gyroSensor.get(Constants.GYRO_SENSOR);
        OpticalDistanceSensor rightEOPD = opMode.hardwareMap.opticalDistanceSensor.get(Constants.RIGHT_EOPD);
        OpticalDistanceSensor leftEOPD = opMode.hardwareMap.opticalDistanceSensor.get(Constants.LEFT_EOPD);

        coloring.enableLed(false);

        Sensors sensors = new Sensors(leftEOPD, rightEOPD, coloring/*, gyro*/);

        return sensors;
    }
}
