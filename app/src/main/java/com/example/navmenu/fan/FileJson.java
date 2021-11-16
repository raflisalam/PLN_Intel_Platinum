package com.example.navmenu.fan;

import com.example.navmenu.datamodel.RegistrationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FileJson {

    public void simpan(String json, InterfaceFileJson.save view) throws JSONException {
        JSONObject object = new JSONObject(json);
        view.successSimpan(object.getString("status"));
    }


    public void listRegistration(String json, InterfaceFileJson.list view) throws JSONException{
        JSONObject object = new JSONObject(json);
        JSONArray jsonArray = object.getJSONArray("records");
        ArrayList<RegistrationModel> registrationModels = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            RegistrationModel model = new RegistrationModel();
            model.setIdpel(jsonArray.getJSONObject(i).getString("idpel"));
            model.setUnit(jsonArray.getJSONObject(i).getString("unit"));
            model.setNama(jsonArray.getJSONObject(i).getString("nama"));
            model.setAlamat(jsonArray.getJSONObject(i).getString("alamat"));
            model.setNo_hp(jsonArray.getJSONObject(i).getString("no_hp"));
            model.setTarif(jsonArray.getJSONObject(i).getString("tarif"));
            model.setDaya(jsonArray.getJSONObject(i).getString("daya"));
            model.setIndikasi(jsonArray.getJSONObject(i).getString("indikasi"));
            model.setStandRek(jsonArray.getJSONObject(i).getString("stan_rek"));
            model.setTagLokasi(jsonArray.getJSONObject(i).getString("tagging_lokasi"));
            model.setFotoMeter(jsonArray.getJSONObject(i).getString("foto_meter"));
            model.setFotoRumah(jsonArray.getJSONObject(i).getString("foto_rumah"));

            registrationModels.add(model);
        }
        view.listRegistration(registrationModels);
    }
}
