
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Task {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите строку с данными через пробел\n" +
                "Фамилия, Имя, Отчество, Дата Рождения (dd.MM.yyyy), " +
                "Номер Телефона(только цифры), Пол (m/f):");
        String data = sc.nextLine();
        try {
            int result = checkAmoundData(data);
            if (result == 1) {
                createFile(data);
            } else if (result == -1) {
                System.out.println("Вы ввели больше данных");
            } else if (result == -2) {
                System.out.println("Вы ввели меньше данных");
            }
        } catch (BirthDateException e) {
            System.out.println(e.getMessage());
        } catch (GenderException e) {
            System.out.println(e.getMessage());
        } catch (TelephoneNumberException e) {
            System.out.println(e.getMessage());
        } catch (NameException e) {
            System.out.println(e.getMessage());
        } catch (LanguageNameException e) {
            System.out.println(e.getMessage());
        }

    }

    public static int checkAmoundData(String data) {
        String[] dataArray = getArrayFromString(data);
        int result = 0;
        for (int i = 0; i < dataArray.length; i++) {
            if (dataArray.length > 6) {
                result = -1;
            } else if (dataArray.length < 6) {
                result = -2;
            } else if (dataArray.length == 6) {
                result = 1;
            }
        }
        return result;
    }

    public static String[] getArrayFromString(String data) {
        String[] dataArray = data.split(" ");
        return dataArray;
    }

    public static void createFile(String data) throws GenderException, BirthDateException,
            TelephoneNumberException, NameException, LanguageNameException {
        try {
            String lastName = getArrayFromString(data)[0];
            checkLastName(lastName);
            String firstName = getArrayFromString(data)[1];
            checkFirstName(firstName);
            String secondName = getArrayFromString(data)[2];
            checkSecondName(secondName);
            checkLanguageName(lastName, firstName, secondName);
            String birthDate = getArrayFromString(data)[3];
            checkDate(birthDate);
            String telephoneNumber = getArrayFromString(data)[4];
            checkTelephoneNumber(telephoneNumber);
            String gender = getArrayFromString(data)[5];
            checkGender(gender);

            String contactData = "<" + lastName + ">" + "<" + firstName + ">" +
                    "<" + secondName + ">" + "<" + birthDate + ">" +
                    "<" + telephoneNumber + ">" + "<" + gender + ">";

            File file = new File(lastName);
            FileWriter writer = new FileWriter(file, true);
            boolean check = Files.lines(Paths.get(lastName), StandardCharsets.UTF_8).anyMatch(contactData::equals);
            if(!check){
                writer.write(contactData+"\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Ошибка записи");
        } catch (BirthDateException e) {
            System.out.println("Неверный формат даты рождения");
        } catch (GenderException e) {
            System.out.println("Неверный формат пола");
        } catch (TelephoneNumberException e) {
            System.out.println("Неверный формат номера телефона");
        } catch (NameException e) {
            System.out.println(e.getMessage());
        } catch (LanguageNameException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void checkDate(String birthDate) throws BirthDateException {
        String[] date = birthDate.split("\\.");

        if (date.length != 3) {
            throw new BirthDateException();
        }

        String day = date[0];
        String month = date[1];
        String year = date[2];
        int dayNumber;
        int monthNumber;
        int yearNumber;

        boolean isDay = day.matches("-?\\d+");
        boolean isMonth = month.matches("-?\\d+");
        boolean isYear = year.matches("-?\\d+");

        if (isDay && isMonth && isYear) {
            dayNumber = Integer.parseInt(day);
            monthNumber = Integer.parseInt(month);
            yearNumber = Integer.parseInt(year);
        } else {
            throw new BirthDateException();
        }
        if (day.length() != 2 || month.length() != 2 || year.length() != 4 || dayNumber < 1 ||
                dayNumber > 31 || monthNumber < 1 || monthNumber > 12) {
            throw new BirthDateException();
        } else if (monthNumber == 02 && dayNumber == 29) {
            if (yearNumber % 4 != 0 || yearNumber % 400 != 0 && yearNumber % 100 == 0) {
                throw new BirthDateException();
            }
        } else if (monthNumber == 02 && dayNumber > 28) {
            throw new BirthDateException();
        } else if (dayNumber > 30) {
            if (monthNumber == 04 || monthNumber == 06 || monthNumber == 9 || monthNumber == 11) {
                throw new BirthDateException();
            }
        }
    }

    public static boolean checkName(String name) {
        char firstChar = name.charAt(0);
        String firstPartName = Character.toString(firstChar);
        String secondPartName = "";
        for (int i = 1; i < name.length(); i++) {
            secondPartName += name.charAt(i);
        }
        boolean isName = Pattern.matches("[A-Z]+", firstPartName)
                && Pattern.matches("[a-z]+", secondPartName) ||
                Pattern.matches("[А-Я]+", firstPartName)
                && Pattern.matches("[а-я]+", secondPartName);
        return isName;
    }

    public static boolean checkLanguage(String lastName, String firstName, String secondName){
        boolean isLanguage = Pattern.matches("[a-zA-Z]+", lastName)
                && Pattern.matches("[a-zA-Z]+", firstName) &&
                Pattern.matches("[a-zA-Z]+", secondName) ||
                Pattern.matches("[а-яА-Я]+", lastName) &&
                        Pattern.matches("[а-яА-Я]+", firstName) &&
                        Pattern.matches("[а-яА-Я]+", secondName);
        return isLanguage;
    }

    public static void checkLanguageName(String lastName, String firstName, String secondName) throws LanguageNameException {
        if (!checkLanguage(lastName, firstName, secondName)){
            throw new LanguageNameException("Языки ввода фамилии, имени и отчества отличаются");
        }
    }

    public static void checkLastName(String lastName) throws NameException {
        if(!checkName(lastName)){
            throw new NameException("Неверный формат фамилии");
        }
    }

    public static void checkFirstName(String firstName) throws NameException {
        if(!checkName(firstName)){
            throw new NameException("Неверный формат имени");
        }
    }

    public static void checkSecondName(String secondName) throws NameException {
        if(!checkName(secondName)){
            throw new NameException("Неверный формат отчества");
        }
    }

    public static void checkTelephoneNumber(String telephoneNumber) throws TelephoneNumberException {
        boolean isTelNum = telephoneNumber.matches("-?\\d+");
        if (!isTelNum) {
            throw new TelephoneNumberException();
        }
    }

    public static void checkGender(String gender) throws GenderException {
        if (!Objects.equals(gender, "m") && !Objects.equals(gender, "f")) {
            throw new GenderException();
        }
    }
}
