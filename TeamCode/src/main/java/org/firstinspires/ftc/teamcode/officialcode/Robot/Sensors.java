package org.firstinspires.ftc.teamcode.officialcode.Robot;

import com.qualcomm.robotcore.hardware.ColorSensor;

public class Sensors {
    private ColorSensor SuperNitron9000;
    public Sensors(ColorSensor SuperNitron9000){
        this.SuperNitron9000 = SuperNitron9000;
    }
    public int ReadColor(String color){
        if(color.equalsIgnoreCase("RED")){
            return this.SuperNitron9000.red();
        }else if(color.equalsIgnoreCase("BLUE")){
            return this.SuperNitron9000.blue();
        }else if(color.equalsIgnoreCase("GREEN")){
            return this.SuperNitron9000.green();
        }
        return 900990999;
    }

}
