package org.firstinspires.ftc.teamcode.officialcode.ImageCapturing;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.officialcode.Tools;

public class PineappleStrainer {
    private int PictureWidth, PictureHeight,precision, contrast;
    private Bitmap picture;
    private StringBuilder toDisplay = new StringBuilder();
    private EpicPineapple epicPineapple;
    
    public PineappleStrainer(Bitmap picture, int contrast, int precision){
        this.picture = picture;
        this.PictureHeight = picture.getHeight();
        this.PictureWidth = picture.getWidth();
        this.precision = precision;
        this.contrast = (int) ((-7.65*contrast)+765);
        this.epicPineapple = epicPineapple;
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
        boolean[][] cords = getFilledArray((PictureWidth*precision),(PictureHeight*precision));
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
        return (offTotal < contrast);
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
        final int NUMBER_OF_WHITE_SPOTS = 3;

        long start = System.currentTimeMillis();
        PineappleChunks pineappleChunks = new PineappleChunks();

        boolean[][] cords = findColorPixels(colorToFind);
        boolean[][] alreadyFound = getFilledArray((PictureWidth*precision),(PictureWidth*precision));


        int numOfWhiteSpots;
        for(int Y = 0; Y < cords[0].length; Y ++) {
            for(int X = 0; X < cords.length; X++){
                if(cords[X][Y] && !alreadyFound[X][Y]){
                    int size = 0;
                    int LeftX  = X;
                    int RightX = X;
                    int DownY  = Y;
                    boolean stillFoundSome = false;
                    for(int counterY = Y; counterY < cords[0].length; counterY++){
                        numOfWhiteSpots = 0;
                        for(int counterX = X; counterX >= 0  && numOfWhiteSpots <= NUMBER_OF_WHITE_SPOTS; counterX--){
                            if((cords[counterX][counterY] && !alreadyFound[counterX][counterY]) ){
                                size++;
                                LeftX = (counterX > LeftX) ?  LeftX : counterX;
                                stillFoundSome = true;
                                alreadyFound[counterX][counterY] = true;
                                DownY = counterY;
                            }else{
                                numOfWhiteSpots ++;
                            }

                        }

                        numOfWhiteSpots = 0;
                        for(int counterX = X; counterX < cords.length && numOfWhiteSpots <= NUMBER_OF_WHITE_SPOTS; counterX++){
                            if((cords[counterX][counterY] && !alreadyFound[counterX][counterY])){
                                size++;
                                RightX = (counterX < RightX) ?  RightX : counterX;
                                stillFoundSome = true;
                                alreadyFound[counterX][counterY] = true;
                                DownY = counterY;
                            }else{
                                numOfWhiteSpots ++;
                            }
                        }
                        if(!stillFoundSome){counterY = cords[0].length + 10;}// This kills it
                    }
                    if(size > 3){

                        int width = (RightX-LeftX)+1;
                        int height = (DownY-Y)+1;
                        int x = (X + (width/2));
                        int y = (Y + (height/2));
                        int z = 0;

                        int reliability = 0;
                        pineappleChunks.addChunk(x,y,z,width,height,size,reliability);
                    }
                }
            }
        }
        showCordsArray(cords);
        int bigger = pineappleChunks.getBiggerChunkSize();
        for(int counter = 0; counter < pineappleChunks.numberOfChunks;counter ++){
            if(pineappleChunks.getChunk(counter)[pineappleChunks.SIZE] < bigger){
                pineappleChunks.removeChunk(counter);
                counter--;
            }
        }
        long finish =  System.currentTimeMillis();

        Tools.showToast("I found " + pineappleChunks.numberOfChunks + " cube(s). " +
                "\n It took " + (finish - start) + " mls");
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
            toDisplay.append(OneLine).append("\n");
            RandomThingy = !RandomThingy;
            OneLine = new StringBuilder(" |");
        }
        AppUtil.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FtcRobotControllerActivity.setTextDisplay(toDisplay.toString());
                epicPineapple.hidePreview();

            }
        });

    }
}
