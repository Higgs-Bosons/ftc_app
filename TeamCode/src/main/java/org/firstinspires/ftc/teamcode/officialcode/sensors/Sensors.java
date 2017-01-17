package org.firstinspires.ftc.teamcode.officialcode.sensors;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Class for declaring, initializing, and providing getters for sensors
 */
public class Sensors {
	//declare sensors
    private OpticalDistanceSensor leftEOPD;
    private OpticalDistanceSensor rightEOPD;
    private ColorSensor coloring;
    private GyroSensor gyro;
    private TouchSensor rTouch;
    private TouchSensor lTouch;

	/**
	 * initialize sensors
	 * @param leftEOPD
	 * @param rightEOPD
	 * @param coloring
	 * @param gyro
	 * @param rTouch
	 * @param lTouch
	 */
    public Sensors(OpticalDistanceSensor leftEOPD, OpticalDistanceSensor rightEOPD,
                   ColorSensor coloring, GyroSensor gyro, TouchSensor rTouch, TouchSensor lTouch){
        this.leftEOPD = leftEOPD;
        this.rightEOPD = rightEOPD;
        this.coloring = coloring;
        this.gyro = gyro;
        this.rTouch = rTouch;
        this.lTouch = lTouch;
    }//constructor

	/**
	 * getter for color sensor
	 */
    public ColorSensor getColoring(){
        return coloring;
    }//getColoring

	/**
	 * getter for gyro sensor
	 */
    public GyroSensor getGyro(){
        return gyro;
    }//getGyro

	/**
	 * getter for left EOPD sensor
	 */
    public OpticalDistanceSensor getLeftEOPD(){
        return leftEOPD;
    }//getLeftEOPD

	/**
	 * getter for right EOPD sensor
	 */
    public OpticalDistanceSensor getRightEOPD(){
        return rightEOPD;
    }//getRightEOPD

	/**
	 * getter for right touch sensor
	 */
    public TouchSensor getrTouch(){
        return rTouch;
    }//getrTouch

	/**
	 * getter for left touch sensor
	 */
    public TouchSensor getlTouch() {
        return lTouch;
    }//getlTouch

	/**
	 * method for getting current gyro heading
	 */
    public int getHeading(){
        return this.getGyro().getHeading();
    }//getHeading

	/**
	 * method for calibrating gyro sensor
	 */
    public void gyroCalibrate(){
        this.getGyro().calibrate();
    }//gyroCalibrate
}//class
