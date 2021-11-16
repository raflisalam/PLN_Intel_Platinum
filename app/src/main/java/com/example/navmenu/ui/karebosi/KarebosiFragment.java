package com.example.navmenu.ui.karebosi;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navmenu.R;
import com.example.navmenu.datamodel.RegistrationModel;
import com.example.navmenu.fan.FAN;
import com.example.navmenu.fan.FileJson;
import com.example.navmenu.fan.InterfaceFAN;
import com.example.navmenu.fan.InterfaceFileJson;
import com.example.navmenu.ui.karebosi.adapter.RegistrationListAdapter;

import org.json.JSONException;

import java.util.ArrayList;


public class KarebosiFragment extends Fragment implements InterfaceFAN, InterfaceFileJson, InterfaceFileJson.list{

    private RecyclerView rvUser;
    private ArrayList<RegistrationModel> registrationModels = new ArrayList<>();
    private EditText searching;
    RegistrationListAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_karebosi, container, false);

        rvUser = view.findViewById(R.id.rvUser);
        searching = view.findViewById(R.id.inputSearching);

        adapter = new RegistrationListAdapter(getContext(), registrationModels);

        setHasOptionsMenu(false);

        String url = "https://script.google.com/macros/s/AKfycbx_XUKr9SHFtxCt5Xy1_S3DdqZdDAKLJBv894PZiyo8cu91JLlsxwfJtMJwX7opRGrZ/exec?action=read";
        FAN fan = new FAN();
        fan.getFAN(url,"list", this);

        rvUser.setHasFixedSize(true);
        rvUser.setLayoutManager(new LinearLayoutManager(getContext()));

        searching.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.getFilter().filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
//                filter(s.toString());
            }
        });

        return view;
    }

//    private void filter(String list) {
//        ArrayList<RegistrationModel> filteredList = new ArrayList<>();
//        for (RegistrationModel item : registrationModels) {
//            if (item.getNama().toLowerCase().contains(list.toLowerCase())) {
//                filteredList.add(item);
//            }
//        }
//        adapter.filterList(filteredList);
//    }

    @Override
    public void successFAN(String json) throws JSONException {
        FileJson fileJson = new FileJson();
        fileJson.listRegistration(json, this);
    }

    @Override
    public void errorFAN(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void listRegistration(ArrayList<RegistrationModel> registrationModels) {
        rvUser.setAdapter(new RegistrationListAdapter(getContext(), registrationModels));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        
    }
}