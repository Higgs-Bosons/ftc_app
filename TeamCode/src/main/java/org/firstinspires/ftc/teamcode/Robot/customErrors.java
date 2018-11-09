package org.firstinspires.ftc.teamcode.Robot;

public class customErrors {
    public static class DuplicateTagException extends Exception{
        @Override
        public String getMessage(){
            return " You tried to use a tag that was already in use. That tag is non-repeatable.";
        }
        DuplicateTagException() {
        }
    }
    public static class DuplicateNameException extends Exception{
        @Override
        public String getMessage(){
            return "You tried to name a motor the same name as a previous motor. Please change the name.";
        }
        DuplicateNameException(){}
    }
}
