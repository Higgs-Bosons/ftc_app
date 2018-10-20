package org.firstinspires.ftc.teamcode.officialcode.ImageCapturing;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

class PineappleChunks {

    final int X = 0;
    final int Y = 1;
    final int Z = 2;
    final int WIDTH = 3;
    final int HEIGHT = 4;
    final int SIZE = 5;
    final int RELIABILITY = 6;
     
    private ArrayList<Integer> x = new ArrayList<>();
    private ArrayList<Integer> y= new ArrayList<>();
    private ArrayList<Integer> z = new ArrayList<>();
    private ArrayList<Integer> width = new ArrayList<>();
    private ArrayList<Integer> height = new ArrayList<>();
    private ArrayList<Integer> size = new ArrayList<>();
    private ArrayList<Integer> reliability = new ArrayList<>();
     
    int numberOfChunks = 0;
    private int averageChunkSize = 0;
    PineappleChunks(){}
    PineappleChunks(int x, int y, int z, int width, int height, int size, int reliability){
        this.x.add(x);
        this.y.add(y);
        this.z.add(z);
        this.width.add(width);
        this.height.add(height);
        this.size.add(size);
        this.reliability.add(reliability);

        this.numberOfChunks = 1;
        this.averageChunkSize += size;
    }
    void addChunk(int x, int y, int z, int width, int height, int size, int reliability){
        this.x.add(x);
        this.y.add(y);
        this.z.add(z);
        this.width.add(width);
        this.height.add(height);
        this.size.add(size);
        this.reliability.add(reliability);

        this.numberOfChunks++;
        this.averageChunkSize += size;
    }
    void removeChunk(int spotNum){
        this.averageChunkSize -= this.size.get(spotNum);
        this.x.remove(spotNum);
        this.y.remove(spotNum);
        this.z.remove(spotNum);
        this.width.remove(spotNum);
        this.height.remove(spotNum);
        this.size.remove(spotNum);
        this.reliability.remove(spotNum);
        this.numberOfChunks --;

    }
    int getBiggerChunkSize() {
        int average = averageChunkSize / width.size();
        for(int counter = size.size()-1; counter >= 0; counter++){
            if(size.get(counter) > (average + 10)){
                return size.get(counter);
            }
        }
        return average;
    }
    int[] getChunk(int spotNum){
            int[] returnArray = new int[7];
            returnArray[0] = this.x.get(spotNum);
            returnArray[1] = this.y.get(spotNum);
            returnArray[2] = this.z.get(spotNum);
            returnArray[3] = this.width.get(spotNum);
            returnArray[4] = this.height.get(spotNum);
            returnArray[5] = this.size.get(spotNum);
            returnArray[6] = this.reliability.get(spotNum);

            return returnArray;
    }
}
