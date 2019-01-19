package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.Galaxy.Names.*;

@Autonomous(name = "Servo Tester", group = "Tester")
public class ServoTester extends LinearOpMode {
    private String[] servoNames = {Holder,Grabby,WeightLifter,XThing,YThing,Spinny,ArmY,Rampy,BucketDumper};
    private int nameCounter = 1;
    private boolean doneIt = true;
    private int position = 500;
    private Servo servo;

    public void runOpMode() {
        telemetry.setCaptionValueSeparator("");
        servo = hardwareMap.servo.get(servoNames[nameCounter]);
        servo.setPosition(0.5);
        waitForStart();

        while (opModeIsActive()) {

            do {
                if (gamepad1.dpad_up) {
                    nameCounter = (nameCounter > 0) ? --nameCounter : servoNames.length - 1;
                    servo = hardwareMap.servo.get(servoNames[nameCounter]);
                    servo.setPosition(0.5);
                    updateTelemetry();
                    while (gamepad1.dpad_up) ;
                } else if (gamepad1.dpad_down) {
                    nameCounter = (nameCounter < servoNames.length - 1) ? ++nameCounter : 0;
                    servo = hardwareMap.servo.get(servoNames[nameCounter]);
                    servo.setPosition(0.5);
                    updateTelemetry();
                    while (gamepad1.dpad_down) ;
                }
            }while(gamepad1.a);


            if(gamepad1.dpad_up){
                long startTime = System.currentTimeMillis(), currentTime;
                position += 5;
                while (gamepad1.dpad_up){
                    currentTime = System.currentTimeMillis();
                    if(startTime < currentTime - 1000){
                        position += 5;
                        position = (position > 1000) ? 1000 : position;
                        startTime += 250L;
                    }
                    servo.setPosition(position / 1000.0);
                    updateTelemetry();
                }
                servo.setPosition(position / 1000.0);
                updateTelemetry();

            }else if(gamepad1.dpad_down){
                long startTime = System.currentTimeMillis(), currentTime;
                position -= 5;
                while (gamepad1.dpad_down){
                    updateTelemetry();
                    currentTime = System.currentTimeMillis();
                    if(startTime < currentTime - 1000){
                        position -= 5;
                        position = (position < 0) ? 0 : position;
                        startTime += 250L;
                    }
                    servo.setPosition(position / 1000.0);
                    updateTelemetry();
                }
                servo.setPosition(position / 1000.0);
                updateTelemetry();
            }


            updateTelemetry();
        }
    }
    private void updateTelemetry(){
        for(int counter = nameCounter; counter != nameCounter || doneIt; counter++){
            doneIt = false;
            if(counter == nameCounter){
                telemetry.addData("------------------------","");
                telemetry.addData(servoNames[counter] + ": ", (position / 1000.0));
                telemetry.addData("------------------------","");
            }else{
                telemetry.addData(servoNames[counter],"");
            }
            if(counter + 1 == servoNames.length){counter = -1;}
        }
        telemetry.update();
    }
}
