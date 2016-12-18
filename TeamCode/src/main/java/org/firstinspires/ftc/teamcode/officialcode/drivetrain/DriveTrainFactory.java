package org.firstinspires.ftc.teamcode.officialcode.drivetrain;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.sensors.Sensors;
import org.firstinspires.ftc.teamcode.officialcode.sensors.SensorsFactory;

/**
 * Created by Higgs Bosons on 11/22/2016.
 */
public class DriveTrainFactory {
    private static IDrivetrain drivetrain;

    public static synchronized IDrivetrain getInstance(OpMode opMode) throws InterruptedException {
        if(drivetrain == null){
            DriveMotors motors = getDriveMotors(opMode);
            Sensors sensors = SensorsFactory.getInstance(opMode);
            drivetrain = new Drivetrain(motors, sensors);
        }
        return drivetrain;
    }

    private static DriveMotors getDriveMotors(OpMode opMode){
        DcMotor leftFront = opMode.hardwareMap.dcMotor.get(Constants.LF_MOTOR);
        DcMotor leftRear = opMode.hardwareMap.dcMotor.get(Constants.LR_MOTOR);
        DcMotor rightFront = opMode.hardwareMap.dcMotor.get(Constants.RF_MOTOR);
        DcMotor rightRear = opMode.hardwareMap.dcMotor.get(Constants.RR_MOTOR);

        return new DriveMotors(leftFront, leftRear, rightFront, rightRear);
    }
}
