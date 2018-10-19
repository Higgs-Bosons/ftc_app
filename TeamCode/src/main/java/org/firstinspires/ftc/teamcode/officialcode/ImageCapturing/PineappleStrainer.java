package org.firstinspires.ftc.teamcode.officialcode.ImageCapturing;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import org.firstinspires.ftc.teamcode.officialcode.Tools;

import java.util.ArrayList;

public class PineappleStrainer {
    private int PictureWidth, PictureHeight,precision, contrast;
    private Bitmap picture;
    
    public PineappleStrainer(Bitmap picture, int precision, int contrast){
        this.picture = picture;
        this.precision = precision;
        this.PictureHeight = picture.getHeight();
        this.PictureWidth = picture.getWidth();
        this.precision = ((int) ((picture.getHeight()*((100.0-precision)/100.0))+1));
        this.contrast = contrast;
    }

    private boolean isTheColorCloser(int colorInQuestion, int currentClosest, int colorToFind){

        int offRed =   Math.abs(Color.red(colorInQuestion) - Color.red(colorToFind));
        int offGreen = Math.abs(Color.green(colorInQuestion) - Color.green(colorToFind));
        int offBlue =  Math.abs(Color.blue(colorInQuestion) - Color.blue(colorToFind));
        int offTotal = offRed + offBlue + offGreen;

        int offRed2 =   Math.abs(Color.red(currentClosest) - Color.red(colorToFind));
        int offGreen2 = Math.abs(Color.green(currentClosest) - Color.green(colorToFind));
        int offBlue2 =  Math.abs(Color.blue(currentClosest) - Color.blue(colorToFind));
        int offTotal2 = offRed2 + offBlue2 + offGreen2;

        return (offTotal < offTotal2);
    }
    private boolean[][] findColorPixels(int colorToFind){
        boolean[][] cords = getFilledArray((PictureWidth/precision+1),(PictureHeight/precision+1));
        int PixelColor;
        int closestColor = 0;

        for(int X = 0; X < picture.getWidth(); X += 10){
            for(int Y = 0; Y < picture.getHeight(); Y += 10){
                PixelColor = picture.getPixel(X,Y);

                if(isTheColorCloser(PixelColor, closestColor, colorToFind))
                    closestColor = PixelColor;
            }
        }

        for(int X = 0; X < PictureWidth; X += precision){
            for(int Y = 0; Y < PictureHeight; Y += precision){
                PixelColor = picture.getPixel(X,Y);
                cords[X/precision][Y/precision] = isCloseEnough(PixelColor, closestColor);
            }
        }
        return cords;
    }
    private boolean isCloseEnough(int ColorToTest, int ColorBase){
        int offRed =   Math.abs(Color.red(ColorToTest) - Color.red(ColorBase));
        int offGreen = Math.abs(Color.green(ColorToTest) - Color.green(ColorBase));
        int offBlue =  Math.abs(Color.blue(ColorToTest) - Color.blue(ColorBase));
        int offTotal = offRed + offBlue + offGreen;
        return (offTotal < (255 * (100/(101-contrast))));
    }
    
    private boolean[][] getFilledArray(int size1, int size2){
        boolean[][] toReturn = new boolean[size1][size2];
        for(int X = 0; X < size1; X ++) {
            for (int Y = 0; Y < size2; Y++) {
                toReturn[X][Y] = false;
            }
        }
        return  toReturn;
    }
    public void findColoredObject(int colorToFind){
        long start = System.currentTimeMillis();

        boolean[][] cords = findColorPixels(colorToFind);
        boolean[][] alreadyFound = getFilledArray((PictureWidth/precision+1),(PictureHeight/precision+1));

        for(int Y = 0; Y < cords[0].length; Y ++) {
            for(int X = 0; X < cords.length; X++){
                if(cords[X][Y] && !alreadyFound[X][Y]){
                    int size = 0;
                    int LeftX  = X;
                    int RightX = X;
                    int DownY  = Y;
                    boolean stillFoundSome = false;
                    for(int counterY = 0; counterY < cords[0].length; counterY++){
                        DownY = counterY;

                        for(int counterX = X; counterX >= 0; counterX--){
                            if(cords[counterX][counterY] && !alreadyFound[counterX][counterY]){
                                size++;
                                LeftX = (counterX > LeftX) ?  LeftX : counterX;
                                stillFoundSome = true;
                                alreadyFound[counterX][counterY] = true;
                            }else{
                                counterX = -1;
                            }

                        }
                        for(int counterX = X; counterX < cords.length; counterX++){
                            if(cords[counterX][counterY] && !alreadyFound[counterX][counterY]){
                                size++;
                                RightX = (counterX > RightX) ?  RightX: counterX;
                                stillFoundSome = true;
                                alreadyFound[counterX][counterY] = true;
                            }else{
                                counterX = cords.length+10;

                            }
                        }
                        if(!stillFoundSome){counterY = cords[0].length + 10;}
                    }
                    if(size > 10){
                        Log.d("FOUND ONE!------------ ","-)");
                        Log.d("Size", size+"");
                        Log.d("LeftX", LeftX+"");
                        Log.d("RightX", RightX+"");
                        Log.d("UpY", Y +"");
                        Log.d("DownY", DownY+"");
                    }
                }
            }
        }

        long finish =  System.currentTimeMillis();

        showCordsArray(cords);
        Log.d("Time", (finish - start)+" mls");
        Tools.showToast("DONE!!");
    }
    private void showCordsArray(boolean[][] cords){
        boolean RandomThingy = true;
        StringBuilder OneLine = new StringBuilder();
        for(int Y = 0; Y < cords[0].length; Y ++) {
            for (boolean[] cord : cords) {
                if (cord[Y]) {
                    OneLine.append("[]");
                } else {
                    OneLine.append("  ");
                }
            }
            Log.d((RandomThingy+" "+!RandomThingy), OneLine.toString()+ "|");
            RandomThingy = !RandomThingy;
            OneLine = new StringBuilder(" |");
        }
    }


    class PineappleChunks {
        ArrayList<Integer> X = new ArrayList<Integer>();
        ArrayList<Integer> Y = new ArrayList<Integer>();
        ArrayList<Integer> Z = new ArrayList<Integer>();
        ArrayList<Integer> width = new ArrayList<Integer>();
        ArrayList<Integer> height = new ArrayList<Integer>();
        ArrayList<Integer> reliability = new ArrayList<Integer>();
        public PineappleChunks() {}
        public PineappleChunks(int x, int y, int z, int width, int height, int reliability) {
            this.X.add(x);
            this.Y.add(y);
            this.Z.add(z);
            this.width.add(width);
            this.height.add(height);
            this.reliability.add(reliability);
        }

        public Object[] getX() { return this.X.toArray(); }
        public Object[] getY() { return this.Y.toArray(); }
        public Object[] getZ() { return this.Z.toArray(); }
        public Object[] getWidth() { return this.width.toArray(); }
        public Object[] getHeight() { return this.height.toArray(); }
        public Object[] getReliability() {
            return this.reliability.toArray();
        }

        public void AddSpot(int x, int y, int z, int width, int height, int reliability) {
            this.X.add(x);
            this.Y.add(y);
            this.Z.add(z);
            this.width.add(width);
            this.height.add(height);
            this.reliability.add(reliability);
        }

    }
}
