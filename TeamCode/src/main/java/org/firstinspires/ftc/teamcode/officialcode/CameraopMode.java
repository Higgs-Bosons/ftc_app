
package org.firstinspires.ftc.teamcode.officialcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcontroller.internal.CameraOp;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.ui.UILocation;


@Autonomous(name="Camera Tester", group ="Tools")
public class CameraopMode extends LinearOpMode {
    CameraOp Camera;
    @Override public void runOpMode() {
        Camera = new CameraOp();
        Camera.init();
        waitForStart();
        Camera.convertImage();

       // AppUtil.getInstance().showToast(UILocation.BOTH,Camera.blue(Camera.image.getPixel(1,1))+"");

    }


}
