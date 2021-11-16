package com.example.navmenu.datamodel;

import android.os.Parcel;
import android.os.Parcelable;

public class RegistrationModel implements Parcelable {

    private String idpel, unit, nama, alamat, no_hp, tarif, daya, indikasi, standRek, tagLokasi, fotoMeter, fotoRumah;

    public RegistrationModel(){
    }

    protected RegistrationModel(Parcel in) {
        idpel = in.readString();
        unit = in.readString();
        nama = in.readString();
        alamat = in.readString();
        no_hp = in.readString();
        tarif = in.readString();
        daya = in.readString();
        indikasi = in.readString();
        standRek = in.readString();
        tagLokasi = in.readString();
        fotoMeter = in.readString();
        fotoRumah = in.readString();

    }

    public static final Creator<RegistrationModel> CREATOR = new Creator<RegistrationModel>() {
        @Override
        public RegistrationModel createFromParcel(Parcel source) {
            return new RegistrationModel(source);
        }

        @Override
        public RegistrationModel[] newArray(int size) {
            return new RegistrationModel[size];
        }
    };

    //insert setter & getter

    public String getIndikasi() {
        return indikasi;
    }

    public void setIndikasi(String indikasi) {
        this.indikasi = indikasi;
    }

    public String getStandRek() {
        return standRek;
    }

    public void setStandRek(String standRek) {
        this.standRek = standRek;
    }

    public String getTagLokasi() {
        return tagLokasi;
    }

    public void setTagLokasi(String tagLokasi) {
        this.tagLokasi = tagLokasi;
    }

    public String getFotoMeter() {
        return fotoMeter;
    }

    public void setFotoMeter(String fotoMeter) {
        this.fotoMeter = fotoMeter;
    }

    public String getFotoRumah() {
        return fotoRumah;
    }

    public void setFotoRumah(String fotoRumah) {
        this.fotoRumah = fotoRumah;
    }

    public String getIdpel() {
        return idpel;
    }

    public void setIdpel(String idpel) {
        this.idpel = idpel;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public String getDaya() {
        return daya;
    }

    public void setDaya(String daya) {
        this.daya = daya;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idpel);
        dest.writeString(unit);
        dest.writeString(nama);
        dest.writeString(alamat);
        dest.writeString(no_hp);
        dest.writeString(tarif);
        dest.writeString(daya);
        dest.writeString(indikasi);
        dest.writeString(standRek);
        dest.writeString(tagLokasi);
        dest.writeString(fotoMeter);
        dest.writeString(fotoRumah);
    }
}
