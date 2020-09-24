package com.example.digitacioncaja.Webserver;

import com.google.gson.annotations.SerializedName;

public class Poblado {

    public Poblado(){
        this.desdes = "";
    }
    @SerializedName("coddes")
    private String coddes;

    @SerializedName("desdes")
    private String desdes;

    @SerializedName("codage")
    private String codage;

    @SerializedName("codpai")
    private String codpai;

    @SerializedName("diaser")
    private String diaser;

    public String getCoddes() {
        return coddes;
    }

    public String getDesdes() {
        return desdes;
    }

    public String getCodage() {
        return codage;
    }

    public String getCodpai() {
        return codpai;
    }

    public String getDiaser() {
        return diaser;
    }

    public void setCoddes(String coddes) {
        this.coddes = coddes;
    }

    public void setDesdes(String desdes) {
        this.desdes = desdes;
    }

    public void setCodage(String codage) {
        this.codage = codage;
    }

    public void setCodpai(String codpai) {
        this.codpai = codpai;
    }

    public void setDiaser(String diaser) {
        this.diaser = diaser;
    }
}
