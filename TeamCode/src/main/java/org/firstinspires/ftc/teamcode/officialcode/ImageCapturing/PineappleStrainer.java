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

        public void setAll(int x, int y, int width, int height, int reliability) {
            this.X.add(x);
            this.Y.add(y);
            this.width.add(width);
            this.height.add(height);
            this.reliability.add(reliability);
        }
        public void setX(int x) {
            this.X.add(x);
        }
        public void setY(int y) {
            this.Y.add(y);
        }
        public void setWidth(int width) {
            this.width.add(width);
        }
        public void setHeight(int height) {
            this.height.add(height);
        }
        public void setReliability(int reliability) {
            this.reliability.add(reliability);
        }
    }

    public void findYellowCube(Bitmap picture){
        int MostYellow = 0;
        int PixelColor;
        int currentPixelYellow;
        long start = System.currentTimeMillis();

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
                    Xcords = new int[oldXcords.length+1];
                    Ycords = new int[oldYcords.length+1];

                    System.arraycopy(oldXcords, 0, Xcords, 1, oldXcords.length);
                    System.arraycopy(oldYcords, 0, Ycords, 1, oldYcords.length);

                    Xcords[0] = X;
                    Ycords[0] = Y;
                }
            }
        }

        int[] numInARow = new int[picture.getHeight()/5];
        for(int c=0;c<numInARow.length;c++){numInARow[c]=0;}
        Log.d("Length",Xcords.length+"");
        for(int counter = 0; counter < Xcords.length; counter++){
            for(int Xcounter = 0; Xcounter < (Xcords.length/5)-1; Xcounter++){
                if(Xcords[counter] == (Xcords[counter+Xcounter]-5)){
                    numInARow[counter]++;
                }
            }
            Log.d("Number of Items in Row"+counter,numInARow[counter]+"");
        }

        long finish =  System.currentTimeMillis();


        Log.d("Time", (finish - start)+" mls");

    }

}
