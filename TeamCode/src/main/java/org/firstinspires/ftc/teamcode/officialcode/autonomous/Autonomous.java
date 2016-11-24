package org.firstinspires.ftc.teamcode.officialcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Higgs Bosons on 10/5/2016.
 */

public abstract class Autonomous extends LinearOpMode implements IAutonomous{
    private static final long WAIT_TIME_MS = 0;

    @Override
    public void runOpMode() throws InterruptedException{
        this.initialize();

        waitForStart();
        Thread.sleep(WAIT_TIME_MS);

        this.runAutonomous();

        while(opModeIsActive()){
            Thread.sleep(10);
        }
    }

}
