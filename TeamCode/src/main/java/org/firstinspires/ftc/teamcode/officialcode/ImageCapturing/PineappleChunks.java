package org.firstinspires.ftc.teamcode.officialcode.ImageCapturing;

import java.util.ArrayList;

public class PineappleChunks {

    private ArrayList<Integer> X = new ArrayList<>();
    private ArrayList<Integer> Y = new ArrayList<>();
    private ArrayList<Integer> Z = new ArrayList<>();
    private ArrayList<Integer> width = new ArrayList<>();
    private ArrayList<Integer> height = new ArrayList<>();
    private ArrayList<Integer> size = new ArrayList<>();
    private ArrayList<Integer> reliability = new ArrayList<>();
    private int averageChunkSize = 0;
    private boolean FirstItem = true;

    public PineappleChunks(){}
    public PineappleChunks(int x, int y, int z, int width, int height, int size, int reliability){
            this.X.add(x);
            this.Y.add(y);
            this.Z.add(z);
            this.width.add(width);
            this.height.add(height);
            this.size.add(size);
            this.reliability.add(reliability);

            averageChunkSize = size;
            FirstItem = false;
        }
    public void addChunk(int x, int y, int z, int width, int height, int size, int reliability){
            this.X.add(x);
            this.Y.add(y);
            this.Z.add(z);
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



        }
    public void removeChunk(int spotNum){
            this.X.remove(spotNum);
            this.Y.remove(spotNum);
            this.Z.remove(spotNum);
            this.width.remove(spotNum);
            this.height.remove(spotNum);
            this.size.remove(spotNum);
            this.reliability.remove(spotNum);
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
            int[] returnArray = new int[6];
            returnArray[0] = this.X.get(spotNum);
            returnArray[1] = this.Y.get(spotNum);
            returnArray[2] = this.Z.get(spotNum);
            returnArray[3] = this.width.get(spotNum);
            returnArray[4] = this.height.get(spotNum);
            returnArray[5] = this.reliability.get(spotNum);

            return returnArray;
        }
}
