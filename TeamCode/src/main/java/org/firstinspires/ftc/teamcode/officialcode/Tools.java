package org.firstinspires.ftc.teamcode.officialcode;


import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.ui.UILocation;

public class Tools {
    public static void wait(int miliseconds){
        try{
            Thread.sleep((long) miliseconds);
        }catch(InterruptedException ignore){}
    }
    public static void showToast(String Message){

        AppUtil.getInstance().showToast(UILocation.BOTH,Message);
    }

}
