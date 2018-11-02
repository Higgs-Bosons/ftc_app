package org.firstinspires.ftc.teamcode.Robots;

public class customErrors {
    public static class motorNotFoundException extends Exception{
        String motorName;
        @Override
        public String getMessage(){
            return " Could not find a motor named: "+ motorName+".";
        }
        motorNotFoundException(String motorName) {
            this.motorName = motorName;
        }
    }
    public static class duplicateTagException extends Exception{

        @Override
        public String getMessage(){
            return " I found more than one motor with the same tag.";
        }
        duplicateTagException() {
        }
    }
}
