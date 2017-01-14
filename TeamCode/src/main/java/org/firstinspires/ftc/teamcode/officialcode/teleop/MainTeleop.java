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
 * Class for activating teleop
 */

@TeleOp(name = "main", group = "TeleOp")
public class MainTeleop extends LinearOpMode {
    //declare threads
    Thread dpscThread;
    Thread llcThread;
    Thread gcThread;

    /**
     * Initialize actions
     * @throws InterruptedException
     */
    private void initialize() throws InterruptedException{

    }//initialize

    /**
     * Declares and initializes instances of all components and their threads
     * @throws InterruptedException
     */
    private void play() throws InterruptedException{
        IDrivetrain drivetrain = DriveTrainFactory.getInstance(this);

        IPusher pusher = PusherFactory.getInstance(this);

        DrivetrainPusherController dpsc = new DrivetrainPusherController(drivetrain, pusher);
        dpscThread = new Thread(dpsc);
        dpscThread.start();

        ILift lift = LiftFactory.getInstance(this);

        ILauncher launcher = LauncherFactory.getInstance(this);

        MyServos servos = ServosFactory.getInstance(this);

        LauncherLiftController llc = new LauncherLiftController(lift, launcher,  servos, servos, servos);
        llcThread = new Thread(llc);
        llcThread.start();

        MyGamepadController gcController = new MyGamepadController(this);
        gcThread = new Thread(gcController);
        gcThread.start();
    }//play

    /**
     * LinearOpModes's runOpMode()
     * @throws InterruptedException
     */
    @Override
    public void runOpMode() throws InterruptedException {
        //initialization
        this.initialize();

        //wait for play button
        waitForStart();

        //try to start threads
        try{
            play();
        }catch (Exception e){
            e.printStackTrace();
        }//try-catch

        //sleep while op mode is running and interrupt threads when stopping it
        try {
            while(opModeIsActive()){
                Thread.sleep(5000);
                idle();
            }
        } catch(InterruptedException e){
            //System.out.println("Interrupting Threads");

            gcThread.interrupt();
            llcThread.interrupt();
            dpscThread.interrupt();
            throw e;
        }//try-catch
    }//runOpMode
}//class
