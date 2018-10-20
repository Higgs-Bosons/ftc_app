package org.firstinspires.ftc.teamcode.officialcode.ImageCapturing;

import java.util.ArrayList;

public class PineappleChunks {

    public final int X = 0;
    public final int Y = 1;
    public final int Z = 2;
    public final int WIDTH = 3;
    public final int HEIGHT = 4;
    public final int SIZE = 5;
    public final int RELIABILITY = 6;
     
    private ArrayList<Integer> x = new ArrayList<>();
    private ArrayList<Integer> y= new ArrayList<>();
    private ArrayList<Integer> z = new ArrayList<>();
    private ArrayList<Integer> width = new ArrayList<>();
    private ArrayList<Integer> height = new ArrayList<>();
    private ArrayList<Integer> size = new ArrayList<>();
    private ArrayList<Integer> reliability = new ArrayList<>();
    private int averageChunkSize = 0;
    private boolean FirstItem = true;
     
    int numberOfChunks = 0;
    public PineappleChunks(){}
    public PineappleChunks(int x, int y, int z, int width, int height, int size, int reliability){
            this.x.add(x);
            this.y.add(y);
            this.z.add(z);
            this.width.add(width);
            this.height.add(height);
            this.size.add(size);
            this.reliability.add(reliability);

            averageChunkSize = size;
            FirstItem = false;
            numberOfChunks = 1;
        }
    public void addChunk(int x, int y, int z, int width, int height, int size, int reliability){
            this.x.add(x);
            this.y.add(y);
            this.z.add(z);
            this.width.add(width);
            this.height.add(height);
            this.size.add(size);
            this.reliability.add(reliability);

            if(FirstItem){
                FirstItem = false;
                averageChunkSize = size;
            }else{
                averageChunkSize += size;
            }
            numberOfChunks++;
        }
    public void removeChunk(int spotNum){
            this.x.remove(spotNum);
            this.y.remove(spotNum);
            this.z.remove(spotNum);
            this.width.remove(spotNum);
            this.height.remove(spotNum);
            this.size.remove(spotNum);
            this.reliability.remove(spotNum);
            numberOfChunks --;
        }
    public int getBiggerChunkSize(){
            int average = averageChunkSize / width.size();

            for(int counter = 0; counter < size.size(); counter++){
                if(size.get(counter) > (average + 10)){
                    return size.get(counter);
                }
            }

            return average;
        }
    public int[] getChunk(int spotNum){
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
