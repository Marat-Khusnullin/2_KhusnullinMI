package com.example.mvphomework;


import java.util.LinkedList;
import java.util.Random;

public class ModelUser {
    private static ModelUser instance;
    LinkedList<User> users;
    String[] names = {"Marat", "Artem", "Amir", "Airat", "Vladimir", "Vladislav", "Ilmaz", "Ruslan", "Dmitriy", "Yuriy"};
    String[] surnames = {"Khusnullin", "Kadyrov", "Gumerov", "Titov", "Han", "Zolotuhin", "Ivanov", "Mirhusainov", "Gavrilov", "Apraxin"};
    String[] emails = {"020202nikolaevsk@mail.ru", "06061991@mail.ru", "11morozov11@rambler.ru", "2222@mail.ru", "1234444@mail.ru", "234@mail.ru", "123kirill45@mail.ru", "123Tankist321@mail.ru", "131adsa1@mail.ru", "13333@mail.ru"};


    private ModelUser() {
        refreshUsers();
    }


    public LinkedList<User> getUsers() {

        return users;
    }

    private void refreshUsers() {
        users = new LinkedList<User>();
        String buf;
        int randomNumber;
        Random random = new Random();
        User user;
        for (int i = 0; i < 10; i++) {
            buf = names[i];
            randomNumber = random.nextInt(10);
            names[i] = names[randomNumber];
            names[randomNumber] = buf;

            buf = surnames[i];
            randomNumber = random.nextInt(10);
            surnames[i] = surnames[randomNumber];
            surnames[randomNumber] = buf;

            buf = emails[i];
            randomNumber = random.nextInt(10);
            emails[i] = emails[randomNumber];
            emails[randomNumber] = buf;
        }
        for (int i = 0; i < 10; i++) {
            user = new User(names[i], surnames[i], emails[i]);
            users.add(user);
        }
    }

    public static ModelUser getInstance() {
        if(instance == null) {
            instance = new ModelUser();
        }
        return instance;
    }

    public void newInfo(String name, String surname, String email, int id) {
        users.get(id).setName(name);
        users.get(id).setSurname(surname);
        users.get(id).setEmail(email);
    }



}
