package org.firstinspires.ftc.teamcode.officialcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.officialcode.drivetrain.DriveTrainFactory;
import org.firstinspires.ftc.teamcode.officialcode.drivetrain.IDrivetrain;
import org.firstinspires.ftc.teamcode.officialcode.launcher.ILauncher;
import org.firstinspires.ftc.teamcode.officialcode.launcher.LauncherFactory;
import org.firstinspires.ftc.teamcode.officialcode.lift.ILift;
import org.firstinspires.ftc.teamcode.officialcode.lift.LiftFactory;
import org.firstinspires.ftc.teamcode.officialcode.pusher.IPusher;
import org.firstinspires.ftc.teamcode.officialcode.pusher.PusherFactory;
import org.firstinspires.ftc.teamcode.officialcode.servos.MyServos;
import org.firstinspires.ftc.teamcode.officialcode.servos.ServosFactory;

/**
 * Created by Higgs Bosons on 10/12/2016.
 */

@TeleOp(name = "main", group = "TeleOp")
public class MainTeleop extends LinearOpMode {
    private void initialize() throws InterruptedException{

    }

    private void play() throws InterruptedException{
        IDrivetrain drivetrain = DriveTrainFactory.getInstance(this);

        IPusher pusher = PusherFactory.getInstance(this);

        DrivetrainPusherController dpsc = new DrivetrainPusherController(drivetrain, pusher);
        new Thread(dpsc).start();

        ILift lift = LiftFactory.getInstance(this);

        ILauncher launcher = LauncherFactory.getInstance(this);

        MyServos servos = ServosFactory.getInstance(this);

        LauncherLiftController llc = new LauncherLiftController(lift, launcher,  servos, servos, servos);
        new Thread(llc).start();

        MyGamepadController gcController = new MyGamepadController(this);
        new Thread(gcController).start();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        this.initialize();

        waitForStart();

        try{
            play();
        }catch (Exception e){
            e.printStackTrace();
        }

        while(opModeIsActive()){
            Thread.sleep(5000);
            idle();
        }
    }
}
