package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import android.graphics.*;
import com.qualcomm.robotcore.eventloop.opmode.*;

import org.firstinspires.ftc.teamcode.Galaxy.Constants;
import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.*;

@Autonomous(name = "Camera Tester", group = "Tester")
public class CameraTester extends LinearOpMode {
    public void runOpMode(){
        CanOfPineapple thePineappleCan = new CanOfPineapple(Constants.UPRIGHT);
        waitForStart();
        Bitmap picture;
        PineappleStrainer pineappleStrainer = new PineappleStrainer(thePineappleCan);
        PineappleChunks pineappleChunks;


        while (opModeIsActive()){
            picture = thePineappleCan.getBitmap();
            pineappleChunks = pineappleStrainer
                    .findShadedObject(85,80, picture, Color.rgb(250,200, 0), 130);
            telemetry.addData("Num of chunks", pineappleChunks.getNumberOfChunks());
            if (pineappleChunks.doesChunkExist()) {
                telemetry.addData("X ",pineappleChunks.getChunk(0)[PineappleChunks.X]);
                telemetry.addData("Y ",pineappleChunks.getChunk(0)[PineappleChunks.Y]);
                telemetry.addData("SIZE ",pineappleChunks.getChunk(0)[PineappleChunks.SIZE]);
            }

            telemetry.update();
        }
        thePineappleCan.closeCanOfPineapple();
    }
}