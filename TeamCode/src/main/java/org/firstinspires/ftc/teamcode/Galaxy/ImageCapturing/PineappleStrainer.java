package org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;


import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.Galaxy.Tools;


/*
(Average in parentheses)
precession: 80:
15 cm - 156, 139, 140, 109, 108 (130.4)

precession: 90:
15 cm - 931, 768, 636, 553, 611 - 636 (42.4)

precession: 100:
15 cm - 58903, 77245, 33277, 54508 (55,973)
*/

public class PineappleStrainer {
    private int PictureWidth, PictureHeight, contrast, precision;
    private Bitmap picture;
    private StringBuilder toDisplay = new StringBuilder();
    private CanOfPineapple epicPineapple;
    private boolean didIFindACloseEnoughColor = true;

    public PineappleStrainer(int contrast, int precision, CanOfPineapple epicPineapple){
        this.precision = (precision >= 100) ?  1 : 100 - precision;
        this.contrast = (int) ((-7.65*contrast)+765);
        this.epicPineapple = epicPineapple;
    }

    public PineappleChunks findColoredObject(Bitmap picture, int colorToFind, int sizeFrom15cm){
        this.PictureHeight = picture.getHeight();
        this.PictureWidth = picture.getWidth();
        this.picture = picture;

        long start = System.currentTimeMillis();
        PineappleChunks pineappleChunks = new PineappleChunks();

        boolean[][] cords = findColorPixels(colorToFind);


        if(!didIFindACloseEnoughColor){
            Tools.showToast("NO COLOR FOUND");
            showCordsArray(cords);
            Log.d("Results:", "NO COLOR");
            return null;
        }

        pineappleChunks = getChunks(cords, sizeFrom15cm);
        showCordsArray(cords);

        int bigger = pineappleChunks.getBiggerChunkSize();
        for(int counter = 0; counter < pineappleChunks.numberOfChunks;counter ++){
            if(pineappleChunks.getChunk(counter)[PineappleChunks.SIZE] <= bigger){
                pineappleChunks.removeChunk(counter);
                counter--;
            }
        }
        long finish =  System.currentTimeMillis();

        Tools.showToast("I found " + pineappleChunks.numberOfChunks + " cube(s). " +
                "\n It took " + (finish - start) + " mls.");
        return pineappleChunks;
    }
    public PineappleChunks findShadedObject(Bitmap picture, int colorToFind, int sizeFrom15cm){
        this.PictureHeight = picture.getHeight();
        this.PictureWidth = picture.getWidth();
        this.picture = picture;

        long start = System.currentTimeMillis();
        PineappleChunks pineappleChunks;

        boolean[][] cords = findColorPixels(colorToFind);


        if(!didIFindACloseEnoughColor){
            Tools.showToast("NO COLOR FOUND");
            showCordsArray(cords);
            Log.d("Results:", "NO COLOR");
            return null;
        }

        pineappleChunks = getChunks(cords, sizeFrom15cm);
        showCordsArray(cords);

        int bigger = pineappleChunks.getBiggerChunkSize();
        for(int counter = 0; counter < pineappleChunks.numberOfChunks;counter ++){
            if(pineappleChunks.getChunk(counter)[PineappleChunks.SIZE] <= bigger){
                pineappleChunks.removeChunk(counter);
                counter--;
            }
        }
        long finish =  System.currentTimeMillis();

        Tools.showToast("I found " + pineappleChunks.numberOfChunks + " cube(s). " +
                "\n It took " + (finish - start) + " mls.");
        return pineappleChunks;
    }


    private PineappleChunks getChunks(boolean[][] cords, int sizeFrom15cm){
        boolean[][] alreadyFound = getFilledArray(PictureWidth / precision, PictureHeight / precision);
        final int NUMBER_OF_WHITE_SPOTS = 3;
        PineappleChunks pineappleChunks = new PineappleChunks();

        int numOfWhiteSpots;
        for(int Y = 0; Y < cords[0].length; Y ++) {
            for(int X = 0; X < cords.length; X++){
                if(cords[X][Y] && !alreadyFound[X][Y]){
                    int size = 0;
                    int LeftX  = X;
                    int RightX = X;
                    int DownY  = Y;
                    int filledAmount = 0;
                    boolean stillFoundSome = false;
                    for(int counterY = Y; counterY < cords[0].length; counterY++){
                        numOfWhiteSpots = 0;
                        for(int counterX = X; counterX >= 0  && numOfWhiteSpots <= NUMBER_OF_WHITE_SPOTS; counterX--){
                            if(cords[counterX][counterY] && !alreadyFound[counterX][counterY]){
                                size++;
                                LeftX = (counterX > LeftX) ?  LeftX : counterX;
                                stillFoundSome = true;
                                alreadyFound[counterX][counterY] = true;
                                DownY = counterY;
                                filledAmount = (numOfWhiteSpots > 0) ? filledAmount + 1 : filledAmount;
                            }else{
                                numOfWhiteSpots ++;
                            }

                        }

                        numOfWhiteSpots = 0;
                        for(int counterX = X; counterX < cords.length && numOfWhiteSpots <= NUMBER_OF_WHITE_SPOTS; counterX++){
                            if(cords[counterX][counterY] && !alreadyFound[counterX][counterY]){
                                size++;
                                RightX = (counterX < RightX) ?  RightX : counterX;
                                stillFoundSome = true;
                                alreadyFound[counterX][counterY] = true;
                                DownY = counterY;
                                filledAmount = (numOfWhiteSpots > 0) ? filledAmount + 1 : filledAmount;
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
                        int z = ((sizeFrom15cm-size)/sizeFrom15cm)*-100;
                        // Less than Expected: negative percent, Equal = 0,  More than Expected: positive percent

                        x = (int) ((x/(double) PictureWidth) * 100);
                        y = (int) ((y/(double) PictureHeight) * 100);

                        int reliability = (100 - Math.abs(sizeFrom15cm - size));

                        filledAmount = (filledAmount < 0) ? 0 : filledAmount;
                        filledAmount = (filledAmount > 100) ? 0 : filledAmount;
                        reliability = (reliability + filledAmount) / 2;
                        reliability = (reliability < 0) ? 0 : reliability;
                        reliability = (reliability > 100) ? 100 : reliability;

                        pineappleChunks.addChunk(x,y,z,width,height,size,reliability);
                    }
                }
            }
        }
        return pineappleChunks;
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
        boolean[][] cords = getFilledArray(PictureWidth / precision,PictureHeight / precision);
        int PixelColor;
        int closestColor = 0;

        for(int X = 0; X < PictureWidth; X += precision){
            for(int Y = 0; Y < PictureHeight; Y += precision){
                PixelColor = picture.getPixel(X,Y);

                if(isTheColorCloser(PixelColor, closestColor, colorToFind))
                    closestColor = PixelColor;
            }
        }

        int offBlue = Math.abs(Color.blue(closestColor) - Color.blue(colorToFind));
        int offRed = Math.abs(Color.red(closestColor) - Color.red(colorToFind));
        int offGreen = Math.abs(Color.green(closestColor) - Color.green(colorToFind));
        didIFindACloseEnoughColor = ((offBlue <= contrast/2) && (offRed <= contrast/2) && (offGreen <= contrast/2));


        int PictureHeightTrun = (int) (Math.floor(PictureHeight / 100.0) *100);
        int PictureWidthTrun = (int) (Math.floor(PictureWidth / 100.0) *100);
        for(int X = 0; X < PictureWidthTrun; X += precision){
            for(int Y = 0; Y < PictureHeightTrun; Y += precision){
                PixelColor = picture.getPixel(X,Y);
                cords[X/precision][(Y/precision)] = isCloseEnough(PixelColor, closestColor);
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
        return toReturn;
    }
    private void showCordsArray(boolean[][] cords){
        boolean RandomThingy = true;

        StringBuilder OneLine = new StringBuilder(" |");
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
