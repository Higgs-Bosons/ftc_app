package org.firstinspires.ftc.teamcode.Galaxy.Testers;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.Galaxy.Names.BucketDumper;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.VSlide;

@Autonomous(name = "V-Slide", group = "Tester")
public class MoveVSlide extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException{
        DcMotor vSlide = hardwareMap.dcMotor.get(VSlide);
        Servo bucket = hardwareMap.servo.get(BucketDumper);
        waitForStart();

        // Up, -175 drop -85 Motor
        // Servo: 0.565 prep, 1.0 dump, down 0.395

        vSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while(vSlide.getCurrentPosition() > -174)
            vSlide.setPower(-0.4);
        vSlide.setPower(0);

        bucket.setPosition(0.565);
        Thread.sleep(2000);

        while(vSlide.getCurrentPosition() < -90)
            vSlide.setPower(0.3);
        vSlide.setPower(0);

        bucket.setPosition(1.0);
        Thread.sleep(5000);

        bucket.setPosition(0.565);
        Thread.sleep(2000);

        while(vSlide.getCurrentPosition() > -174)
            vSlide.setPower(-0.4);
        vSlide.setPower(0);

        bucket.setPosition(0.395);
        Thread.sleep(2000);

        while(vSlide.getCurrentPosition() < 0)
            vSlide.setPower(0.3);
        vSlide.setPower(0);



        while (opModeIsActive()) {

            vSlide.setPower(gamepad1.left_stick_y);

            if(gamepad1.a){
                vSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                vSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }

            telemetry.addData("Tick Count", vSlide.getCurrentPosition());
            telemetry.update();

        }
    }
}
