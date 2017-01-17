package org.firstinspires.ftc.teamcode.officialcode.sensors;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Class for sensors' factory
 */
public class SensorsFactory {
	//declare Sensors variable
    private static Sensors sensors;

	/**
	 * get instance for Sensors' variable if it is null
	 * @param opMode
	 */
    public static synchronized Sensors getInstance(OpMode opMode) throws InterruptedException{
        if(sensors == null){
            sensors = getSensors(opMode);
        }//if
        return sensors;
    }//getInstance

	/**
	 * method for getting sensors' instance
	 * @param opMode
	 */
    private static Sensors getSensors(OpMode opMode) throws InterruptedException{
		//hardware map the sensors
        ColorSensor coloring = opMode.hardwareMap.colorSensor.get(Constants.COLOR_SENSOR);
        GyroSensor gyro = opMode.hardwareMap.gyroSensor.get(Constants.GYRO_SENSOR);
        OpticalDistanceSensor rightEOPD = opMode.hardwareMap.opticalDistanceSensor.get(Constants.RIGHT_EOPD);
        OpticalDistanceSensor leftEOPD = opMode.hardwareMap.opticalDistanceSensor.get(Constants.LEFT_EOPD);
        TouchSensor rTouch = opMode.hardwareMap.touchSensor.get(Constants.RIGHT_TOUCHER);
        TouchSensor lTouch = opMode.hardwareMap.touchSensor.get(Constants.LEFT_TOUCHER);

		//disable color sensor led
        coloring.enableLed(false);

		//create Sensors object and return it
        Sensors sensors = new Sensors(leftEOPD, rightEOPD, coloring, gyro, rTouch, lTouch);

        return sensors;
    }//getSensors
}//class
