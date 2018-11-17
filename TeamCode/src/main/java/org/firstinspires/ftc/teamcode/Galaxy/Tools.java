package org.firstinspires.ftc.teamcode.Galaxy;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.ui.UILocation;

public class Tools {
    public static void wait(int milliseconds){
        try{
            Thread.sleep((long) milliseconds);
        }catch(InterruptedException ignore){}
    }
    public static void showToast(String Message){

        AppUtil.getInstance().showToast(UILocation.BOTH,Message);
    }
    public static boolean isTagRepeatable(int tag){
        return (tag/100) == 1;
    }
}
