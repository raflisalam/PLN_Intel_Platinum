package com.example.navmenu.fan;

import org.json.JSONException;

public interface InterfaceFAN {
    void successFAN(String json) throws JSONException;
    void errorFAN(String error);
}
