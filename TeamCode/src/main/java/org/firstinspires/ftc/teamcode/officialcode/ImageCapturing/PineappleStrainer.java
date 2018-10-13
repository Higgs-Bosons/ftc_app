package org.firstinspires.ftc.teamcode.officialcode.ImageCapturing;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;

public class PineappleStrainer {


    class PineappleJuice {
        ArrayList<Integer> X = new ArrayList<Integer>();
        ArrayList<Integer> Y = new ArrayList<Integer>();
        ArrayList<Integer> width = new ArrayList<Integer>();
        ArrayList<Integer> height = new ArrayList<Integer>();
        ArrayList<Integer> reliability = new ArrayList<Integer>();
        public PineappleJuice() {}
        public PineappleJuice(int x, int y, int width, int height, int reliability) {
            this.X.add(x);
            this.Y.add(y);
            this.width.add(width);
            this.height.add(height);
            this.reliability.add(reliability);
        }

        public Object[] getX() { return this.X.toArray(); }
        public Object[] getY() { return this.Y.toArray(); }
        public Object[] getWidth() { return this.width.toArray(); }
        public Object[] getHeight() { return this.height.toArray(); }
        public Object[] getReliability() {
            return this.reliability.toArray();
        }

        public void addAll(int x, int y, int width, int height, int reliability) {
            this.X.add(x);
            this.Y.add(y);
            this.width.add(width);
            this.height.add(height);
            this.reliability.add(reliability);
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
            for (boolean[] cord : cords) {
                if (cord[Y]) {
                    OneLine += "o";
                } else {
                    OneLine += " ";
                }
            }
            Log.d("Results:", OneLine);
        }
        long finish =  System.currentTimeMillis();


        Log.d("Time", (finish - start)+" mls");

    }

}
