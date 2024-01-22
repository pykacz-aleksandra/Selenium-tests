package org.example;

import java.util.ArrayList;
import java.util.List;

public class UsersDataSet {
    List<User> correctLoggingUsersList = new ArrayList<>();
    List<User> incorrectLoggingUsersList = new ArrayList<>();
    public void addUsersToLists(){
        User user1 = new User("standard_user", "secret_sauce");
        User user2 = new User("locked_out_user", "secret_sauce");
        User user3 = new User("problem_user", "secret_sauce");
        User user4 = new User("performance_glitch_user", "secret_sauce");
        User user5 = new User("error_user", "secret_sauce");
        User user6 = new User("visual_user", "secret_sauce");

        correctLoggingUsersList.add(user1);
        correctLoggingUsersList.add(user3);
        correctLoggingUsersList.add(user4);
        correctLoggingUsersList.add(user5);
        correctLoggingUsersList.add(user6);

        incorrectLoggingUsersList.add(user2);
    }

    public List<User> getListOfValidUsers(){
        return correctLoggingUsersList;
    };
    public List<User> getListOfInValidUsers(){ return incorrectLoggingUsersList; };

}
