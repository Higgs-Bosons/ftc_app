package org.firstinspires.ftc.teamcode.officialcode.ImageCapturing;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

public class PineappleStrainer {
    public void findYellowCube(Bitmap picture){
        long start =  System.currentTimeMillis();
        int MostYellow = 0;
        int PixelColor;
        int currentPixelYellow;

        int[] Xcords = {0};
        int[] Ycords = {0};
        int[] oldXcords;
        int[] oldYcords;
        for(int X = 0; X < picture.getWidth(); X += 10){
            for(int Y = 0; Y < picture.getHeight(); Y += 10){
               PixelColor = picture.getPixel(X,Y);
               currentPixelYellow = ((int) ((Color.red(PixelColor)+Color.green(PixelColor))/5.1));
               MostYellow = (MostYellow < currentPixelYellow && (Color.blue(PixelColor) < 50)) ? currentPixelYellow : MostYellow;
            }
        }
        for(int X = 5; X < picture.getWidth(); X += 10){
            for(int Y = 5; Y < picture.getHeight(); Y += 10){
                PixelColor = picture.getPixel(X,Y);
                currentPixelYellow = ((int) ((Color.red(PixelColor)+Color.green(PixelColor))/5.1));
                if((currentPixelYellow) > (MostYellow - 10)){
                    oldXcords = Xcords;
                    oldYcords = Ycords;
                    System.arraycopy(oldXcords, 0, Xcords, 1, oldXcords.length-1);
                    System.arraycopy(oldYcords, 0, Ycords, 1, oldYcords.length-1);
                    Xcords[0] = X;
                    Ycords[0] = Y;
                }
            }
        }





        long finish =  System.currentTimeMillis();
        Log.d("Time", (finish - start)+" mls");

        for(int counter = 0; counter < Xcords.length; counter ++){
            Log.d("Xcords","["+Xcords[counter]+"]");
        }
    }

}
