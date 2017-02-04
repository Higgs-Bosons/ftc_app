package org.firstinspires.ftc.teamcode.officialcode.autonomous;


import org.firstinspires.ftc.teamcode.officialcode.drivetrain.DriveTrainFactory;
import org.firstinspires.ftc.teamcode.officialcode.drivetrain.IDrivetrain;
import org.firstinspires.ftc.teamcode.officialcode.launcher.ILauncher;
import org.firstinspires.ftc.teamcode.officialcode.launcher.LauncherFactory;
import org.firstinspires.ftc.teamcode.officialcode.servos.MyServos;
import org.firstinspires.ftc.teamcode.officialcode.servos.ServosFactory;

/**
 * Created by Higgs Bosons on 1/21/2017.
 */
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Backup", group="Backup")
public class BallAndPark extends Autonomous {
    private IDrivetrain dDrive;
    private ILauncher dLaunch;
    private MyServos dServos;

    @Override
    public void initialize() throws InterruptedException {
        this.dDrive = DriveTrainFactory.getInstance(this);
        this.dServos = ServosFactory.getInstance(this);
        this.dLaunch = LauncherFactory.getInstance(this);

        this.dServos.getCapGrabber().closeGrabber();
        this.dServos.getBallGrabber().partialClose();
        this.dServos.getBallLoader().downLoader();
    }

    @Override
    public void runAutonomous() throws InterruptedException {
        Thread.sleep(15000);
        this.dLaunch.fire();
        this.dDrive.moveDistance(42, 1.0d);
        this.dServos.getBallLoader().raiseLoader();
        Thread.sleep(1000);
        this.dDrive.timedMove(1.0d, 1000);
        this.dLaunch.cease();
    }
}
