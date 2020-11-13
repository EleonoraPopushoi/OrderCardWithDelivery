package ru.netology.generator;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    private Faker faker;

    public DataGenerator(Faker faker) {
        this.faker = faker;
    }


    //public static String randomDate() {
    //        Faker faker = new Faker(new Locale("ru"));
    //        long randomCount = faker.number().numberBetween(1,9);
    //        String randomDate = LocalDate.now().plusDays(3).plusDays(randomCount).format(DataTimeFormatter.ofPattern("dd.MM.yyyy"));
    //        return randomDate;
    //
    //    }

    public static String forwardDate(int plusDays) {
        LocalDate today = LocalDate.now();
        LocalDate newDate = today.plusDays(plusDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return formatter.format(newDate);
    }

    public String makeCity() {
        Faker faker = new Faker(new Locale("ru"));
        String[] myCityList = new String[]{"Абакан", "Анадырь", "Архангельск", "Астрахань", "Барнаул", "Владикавказ", "Горно-Алтайск", "Йошкар-Ола", "Казань", "Калининград", "Калуга", "Краснодар", "Магас", "Махачкала", "Нарьян-Мар", "Салехард", "Самара", "Саранск", "Саратов", "Хабаровск", "Ханты-Мансийск", "Южно-Сахалинск", "Великий Новгород", "Владивосток", "Владимир", "Вологда", "Рязань", "Биробиджан", "Чебоксары", "Москва", "Санкт-Петербург", "Ульяновск", "Симферополь", "Ростов-на-Дону"};
        int city = (int) Math.floor(Math.random() * myCityList.length);
        return myCityList[city];
    }

    public String makeName() {
        Faker faker = new Faker(new Locale("ru"));
        String lastName = faker.name().name();
        return faker.name().name();
    }

    public String makePhone() {
        Faker faker = new Faker(new Locale("ru"));
        String phone = faker.phoneNumber().phoneNumber();
        return faker.phoneNumber().phoneNumber();
    }
}
