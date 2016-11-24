package org.firstinspires.ftc.teamcode.officialcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Higgs Bosons on 10/12/2016.
 */
public class MainTeleop extends LinearOpMode {
    private void initialize() throws InterruptedException{

    }

    private void postInitialize() throws InterruptedException{

    }

    @Override
    public void runOpMode() throws InterruptedException {
        this.initialize();

        waitForStart();

        try{
            postInitialize();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        while(opModeIsActive()){
            Thread.sleep(5000);
        }
    }
}
