package Exceptions;

public class BirthDateException extends Exception{
    public BirthDateException(String message){
        super("Неверный формат даты рождения (" + message + ")");
    }
}
