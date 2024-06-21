import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class CreateFile {
    private AllChecks checks;
    private View view;

    public CreateFile(AllChecks checks, View view) {
        this.checks = checks;
        this.view = view;
    }

    public void createFile(String data) throws GenderException, BirthDateException,
            TelephoneNumberException, NameException, LanguageNameException {
        try {
            String lastName = checks.getArrayFromString(data)[0];
            checks.checkLastName(lastName);
            String firstName = checks.getArrayFromString(data)[1];
            checks.checkFirstName(firstName);
            String secondName = checks.getArrayFromString(data)[2];
            checks.checkSecondName(secondName);
            checks.checkLanguageName(lastName, firstName, secondName);
            String birthDate = checks.getArrayFromString(data)[3];
            checks.checkDate(birthDate);
            String telephoneNumber = checks.getArrayFromString(data)[4];
            checks.checkTelephoneNumber(telephoneNumber);
            String gender = checks.getArrayFromString(data)[5];
            checks.checkGender(gender);

            String contactData = "<" + lastName + ">" + "<" + firstName + ">" +
                    "<" + secondName + ">" + "<" + birthDate + ">" +
                    "<" + telephoneNumber + ">" + "<" + gender + ">";

            File file = new File(lastName);
            FileWriter writer = new FileWriter(file, true);
            boolean check = Files.lines(Paths.get(lastName), StandardCharsets.UTF_8).anyMatch(contactData::equals);
            if (!check) {
                writer.write(contactData + "\n");
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

        public void start(){
        String data = view.enterData();
            try {
                int result = checks.checkAmoundData(data);
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
    }

