package org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

public class Sensors {
    private ColorSensor SuperNitron9000;
    private BNO055IMU IMU;
    Sensors(ColorSensor SuperNitron9000, BNO055IMU IMU){
        this.SuperNitron9000 = SuperNitron9000;
        this.IMU = IMU;
    }
    int ReadColor(String color){
        if(color.equalsIgnoreCase("RED")){
            return this.SuperNitron9000.red();
        }else if(color.equalsIgnoreCase("BLUE")){
            return this.SuperNitron9000.blue();
        }else if(color.equalsIgnoreCase("GREEN")){
            return this.SuperNitron9000.green();
        }
        return 900990999;
    }
    public float ReadGyro(){
        float Value = this.IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle ;
        if(Value<0){
            Value = (180+(181 - Math.abs(Value)));
        }
        return Value;
    }
}
