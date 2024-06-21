package View;

import java.util.Scanner;

public class View {
    public String enterData(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите строку с данными через пробел\n" +
                "Фамилия, Имя, Отчество, Дата Рождения (dd.MM.yyyy), " +
                "Номер Телефона(только цифры), Пол (m/f):");
        String data = sc.nextLine();
        return data;
    }
}
