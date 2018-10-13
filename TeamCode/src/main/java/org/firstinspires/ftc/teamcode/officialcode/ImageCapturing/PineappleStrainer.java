package org.firstinspires.ftc.teamcode.officialcode.ImageCapturing;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

public class PineappleStrainer {


    class PineappleJuice {
        int[] x, y, width, height, reliability;
        public PineappleJuice() {}

        public int[] getX() {
            return x;
        }
        public int[] getY() {
            return y;
        }
        public int[] getWidth() {
            return width;
        }
        public int[] getHeight() {
            return height;
        }
        public int[] getReliability() {
            return reliability;
        }
        public void setAll(int[] x, int[] y, int[] width, int[] height, int[] reliability) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.reliability =  reliability;
        }
        public void setX(int[] x) {
            this.x = x;
        }
        public void setY(int[] y) {
            this.y = y;
        }
        public void setWidth(int[] width) {
            this.width = width;
        }
        public void setHeight(int[] height) {
            this.height = height;
        }
        public void setReliability(int[] reliability) {
            this.reliability = reliability;
        }
    }

    public void findYellowCube(Bitmap picture){
        int MostYellow = 0;
        int PixelColor;
        int currentPixelYellow;

        int PictureHeight = picture.getHeight();
        int PictureWidth  = picture.getWidth();

        long start = System.currentTimeMillis();


        for(int X = 0; X < picture.getWidth(); X += 10){
            for(int Y = 0; Y < picture.getHeight(); Y += 10){
               PixelColor = picture.getPixel(X,Y);
               currentPixelYellow = ((int) ((Color.red(PixelColor)+Color.green(PixelColor))/5.1));
               MostYellow = (MostYellow < currentPixelYellow && (Color.blue(PixelColor) < 50)) ? currentPixelYellow : MostYellow;
            }
        }

        boolean[][] cords = new boolean[PictureWidth/5+1][PictureHeight/5+1];
        Log.d("Length",PictureWidth/5+"");
        Log.d("Height",PictureHeight/5+"");
        for(int X = 0; X < PictureWidth/5; X ++) {
            for (int Y = 0; Y < PictureHeight/5; Y++) {
                 cords[X][Y] = false;
            }
        }

        for(int X = 0; X < PictureWidth; X += 5){
            for(int Y = 0; Y < PictureHeight; Y += 5){
                PixelColor = picture.getPixel(X,Y);
                currentPixelYellow = ((int) ((Color.red(PixelColor)+Color.green(PixelColor))/5.1));
                if((currentPixelYellow) > (MostYellow - 10)){
                    cords[X/5][Y/5] = true;
                }
            }
        }


        String OneLine = "";
        for(int Y = 0; Y < cords[0].length; Y ++) {
            for (int X = 0; X < cords.length; X++) {
                if(cords[X][Y]){
                    OneLine +="o";
                }else{
                    OneLine += " ";
                }
            }
            Log.d("Results:", OneLine);
        }
        long finish =  System.currentTimeMillis();


        Log.d("Time", (finish - start)+" mls");

    }

}
