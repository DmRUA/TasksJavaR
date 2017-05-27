package com.javarush.task.task36.task3608.view;

import com.javarush.task.task36.task3608.bean.User;
import com.javarush.task.task36.task3608.controller.Controller;
import com.javarush.task.task36.task3608.model.ModelData;

import java.util.List;

/**
 * Created by DmRG on 26.05.2017.
 */
public class UsersView implements View {
    private Controller controller;


    public void fireEventShowAllUsers(){
        controller.onShowAllUsers();
    }


    @Override
    public void refresh(ModelData modelData) {
        if (modelData.isDisplayDeletedUserList())
            System.out.println("All deleted users:");
        else
            System.out.println("All users:");

        for (User x:modelData.getUsers()) {
            System.out.println("\t" + x.toString());
        }
        System.out.println("===================================================");

    }

    public void fireEventOpenUserEditForm(long id) {
        controller.onOpenUserEditForm(id);
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void fireEventShowDeletedUsers() {
        controller.onShowAllDeletedUsers();
    }
}
