package com.example.navmenu.fan;

import com.example.navmenu.datamodel.RegistrationModel;

import java.util.ArrayList;

public interface InterfaceFileJson {
    interface save{
        void successSimpan(String message);
    }
    interface list{
        void listRegistration(ArrayList<RegistrationModel> registrationModels);
    }

}
