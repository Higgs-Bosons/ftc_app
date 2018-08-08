package org.firstinspires.ftc.teamcode.officialcode;



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
