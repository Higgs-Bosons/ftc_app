package org.firstinspires.ftc.teamcode.officialcode.servos;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.launcher.ILauncher;
import org.firstinspires.ftc.teamcode.officialcode.teleop.TeleopMessages;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Higgs Bosons on 1/4/2017.
 */
public class TouchEnablers{
    private Servo right;
    private Servo left;
    private static final double READY_POS = 0.5d;
    private static final double UP_POS = 0.0d;

    public TouchEnablers(Servo right, Servo left){
        this.right = right;
        this.left = left;
    }

    public void activate() throws InterruptedException {
        this.right.setPosition(1.0d - UP_POS);
        this.left.setPosition(UP_POS);
        Thread.sleep(500);
    }

    public void retract() throws InterruptedException {
        this.right.setPosition(1.0d - READY_POS);
        this.left.setPosition(READY_POS);
        Thread.sleep(500);
    }
}
