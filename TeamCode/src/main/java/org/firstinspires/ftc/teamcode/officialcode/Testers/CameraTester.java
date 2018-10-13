package org.firstinspires.ftc.teamcode.officialcode.Testers;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.officialcode.ImageCapturing.EpicPineapple;

@Autonomous(name = "Camera Tester", group = "Tester")
public class CameraTester extends LinearOpMode {
    EpicPineapple thePineapple;
    Bitmap picture;

    public void runOpMode() throws InterruptedException {
        thePineapple = new EpicPineapple();
        waitForStart();
        thePineapple.openEpicPineapple();
        while (opModeIsActive()) {
            picture = thePineapple.getFrame(EpicPineapple.RECENT_FRAME);
            Bitmap small = getResizedBitmap(picture, 100, 100);
            Bitmap pixelated = getResizedBitmap(small, picture.getWidth(), picture.getHeight());

            telemetry.addData("Pixel Red", Color.red(picture.getPixel(1, 1)));
            telemetry.update();
        }
        thePineapple.closeEpicPineapple();
    }


    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }
}