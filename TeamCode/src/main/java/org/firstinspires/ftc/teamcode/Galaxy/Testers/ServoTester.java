package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.CanOfPineapple;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.*;

@Autonomous(name = "Servo Tester", group = "Tester")
public class ServoTester extends LinearOpMode {
    private final String[] servoNames = {Holder,Grabby,WeightLifter,XThing,YThing,Spinny,ArmY,Rampy,BucketDumper};
    private int[] positions;
    private int servoCounter = 1;

    public void runOpMode() {
        positions = new int[servoNames.length];
        for(int counter = 0; counter < positions.length; counter++)
            positions[counter] = 500;
        
        telemetry.setCaptionValueSeparator("");
        Servo servo = hardwareMap.servo.get(servoNames[servoCounter]);
        servo.setPosition(0.5);
        CanOfPineapple canOfPineapple = new CanOfPineapple();
        waitForStart();

        while (opModeIsActive()) {

            while(gamepad1.a){
                if (gamepad1.dpad_down) {
                    servoCounter = (servoCounter + 1 + (servoNames.length * 10)) % servoNames.length;
                    servo = hardwareMap.servo.get(servoNames[servoCounter]);
                    servo.setPosition(positions[servoCounter]);
                    updateTelemetry();
                    while (gamepad1.dpad_down);
                } else if (gamepad1.dpad_up) {
                    servoCounter = ((servoCounter - 1) + (servoNames.length * 10)) % servoNames.length;
                    servo = hardwareMap.servo.get(servoNames[servoCounter]);
                    servo.setPosition(positions[servoCounter]);
                    updateTelemetry();
                    while (gamepad1.dpad_up) ;
                }
            }


            if(gamepad1.dpad_up){
                long startTime = System.currentTimeMillis(), currentTime;
                int startValue = positions[servoCounter];
                positions[servoCounter] += 5;
                while (gamepad1.dpad_up){
                    currentTime = System.currentTimeMillis();
                    if(startTime < currentTime - 1000){
                        positions[servoCounter] += (Math.abs(positions[servoCounter] - startValue) > 100) ? 50 : 10;
                        startTime += (Math.abs(positions[servoCounter] - startValue) > 200) ? 200L : 250L;
                        positions[servoCounter] = (positions[servoCounter] > 1000) ? 1000 : positions[servoCounter];
                    }
                    servo.setPosition(positions[servoCounter] / 1000.0);
                    updateTelemetry();
                }
                servo.setPosition(positions[servoCounter] / 1000.0);
                updateTelemetry();

            }else if(gamepad1.dpad_down){
                long startTime = System.currentTimeMillis(), currentTime;
                int startValue = positions[servoCounter];
                positions[servoCounter] -= 5;
                while (gamepad1.dpad_down){
                    currentTime = System.currentTimeMillis();
                    if(startTime < currentTime - 1000){
                        positions[servoCounter] -= (Math.abs(positions[servoCounter] - startValue) > 100) ? 50 : 10;
                        positions[servoCounter] = (positions[servoCounter] < 0) ? 0 : positions[servoCounter];
                        startTime += (Math.abs(positions[servoCounter] - startValue) > 200) ? 200L : 250L;
                    }
                    servo.setPosition(positions[servoCounter] / 1000.0);
                    updateTelemetry();
                }
                servo.setPosition(positions[servoCounter] / 1000.0);
                updateTelemetry();
            }
            updateTelemetry();
        }
        canOfPineapple.closeCanOfPineapple();
    }
    private void updateTelemetry(){
        for(int counter = 0; counter < servoNames.length; counter++){
            if(counter == servoCounter){
                telemetry.addData(servoNames[servoCounter] + ": ", (positions[servoCounter] / 1000.0));
            }else{
                telemetry.addData(servoNames[counter],"");
            }
        }
        telemetry.update();
    }
}
