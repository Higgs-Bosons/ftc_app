package org.firstinspires.ftc.teamcode.officialcode.sensors;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by Higgs Bosons on 11/5/2016.
 */
public class Sensors {
    private OpticalDistanceSensor leftEOPD;
    private OpticalDistanceSensor rightEOPD;
    private ColorSensor coloring;
    private GyroSensor gyro;
    private TouchSensor rTouch;
    private TouchSensor lTouch;

    public Sensors(OpticalDistanceSensor leftEOPD, OpticalDistanceSensor rightEOPD,
                   ColorSensor coloring, GyroSensor gyro, TouchSensor rTouch, TouchSensor lTouch){
        this.leftEOPD = leftEOPD;
        this.rightEOPD = rightEOPD;
        this.coloring = coloring;
        this.gyro = gyro;
        this.rTouch = rTouch;
        this.lTouch = lTouch;
    }

    public ColorSensor getColoring(){
        return coloring;
    }

    public GyroSensor getGyro(){
        return gyro;
    }

    public OpticalDistanceSensor getLeftEOPD(){
        return leftEOPD;
    }

    public OpticalDistanceSensor getRightEOPD(){
        return rightEOPD;
    }

    public TouchSensor getrTouch(){
        return rTouch;
    }

    public TouchSensor getlTouch() {
        return lTouch;
    }

    public int getHeading(){
        return this.getGyro().getHeading();
    }

    public void gyroCalibrate(){
        this.getGyro().calibrate();
    }
}
