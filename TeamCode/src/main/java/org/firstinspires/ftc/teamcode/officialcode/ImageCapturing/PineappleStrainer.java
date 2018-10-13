package org.firstinspires.ftc.teamcode.officialcode.ImageCapturing;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

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

        public void AddSpot(int x, int y, int width, int height, int reliability) {
            this.X.add(x);
            this.Y.add(y);
            this.width.add(width);
            this.height.add(height);
            this.reliability.add(reliability);
        }

    }

    public void findYellowCube(Bitmap picture){
        final int PRECISION = 5;
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

        boolean[][] cords = new boolean[PictureWidth/PRECISION+1][PictureHeight/PRECISION+1];

        for(int X = 0; X < PictureWidth/PRECISION; X ++) {
            for (int Y = 0; Y < PictureHeight/PRECISION; Y++) {
                 cords[X][Y] = false;
            }
        }

        for(int X = 0; X < PictureWidth; X += PRECISION){
            for(int Y = 0; Y < PictureHeight; Y += PRECISION){
                PixelColor = picture.getPixel(X,Y);
                currentPixelYellow = ((int) ((Color.red(PixelColor)+Color.green(PixelColor))/5.1));
                cords[X/PRECISION][Y/PRECISION] = ((currentPixelYellow) > (MostYellow * 0.9));
            }
        }
        boolean RandomThingy = true;
        long finish =  System.currentTimeMillis();
        StringBuilder OneLine = new StringBuilder();
        for(int Y = 0; Y < cords[0].length; Y ++) {
            for (int X = 0; X < cords.length; X++) {
                if(cords[X][Y]){
                    OneLine.append("||");
                }else{
                    OneLine.append("  ");
                }
            }
            Log.d((RandomThingy+" "+!RandomThingy), OneLine.toString());
            RandomThingy = !RandomThingy;
            OneLine = new StringBuilder("|");
        }



        Log.d("Time", (finish - start)+" mls");

    }

}
