package org.firstinspires.ftc.teamcode.officialcode.sensors;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Created by Higgs Bosons on 11/5/2016.
 */
public class Sensors {
    private OpticalDistanceSensor EOPD;
    private ColorSensor coloring;
    private GyroSensor gyro;

    public Sensors(ColorSensor coloring, OpticalDistanceSensor EOPD, GyroSensor gyro){
        this.coloring = coloring;
        this.EOPD = EOPD;
        this.gyro = gyro;
    }

    public ColorSensor getColoring(){
        return coloring;
    }

    public GyroSensor getGyro(){
        return gyro;
    }

    public OpticalDistanceSensor getEOPD(){
        return EOPD;
    }

    public int getHeading(){
        return this.getGyro().getHeading();
    }

    public void gyroCalibrate(){
        this.getGyro().calibrate();
    }

    public int getHue(){
        return this.getColoring().argb();
    }
}
