package com.islab.boredomappfase1;

import java.io.Serializable;

public class Entry implements Serializable {

    private String bateria;
    private String nAppSociais;
    private String nAppChatting;
    private String nOutrasApps;
    private String notificacesAppsAtuais;
    private String nAtivacoesEcra;
    private String nChamadasFeitas;
    private String getnChamadasRecebidas;
    private String proximidade;
    private String nSMSRecebidas;
    private String luminisidade;
    private String orientacao;
    private String nClicksHome;
    private String nClicksRecentes;
    private String wifi;
    private String dadosMoveis;
    private String bored;

    public Entry() {}

    public Entry(String bateria, String nAppSociais, String nAppChatting, String nOutrasApps, String notificacesAppsAtuais, String nAtivacoesEcra, String nChamadasFeitas, String getnChamadasRecebidas, String proximidade, String nSMSRecebidas, String luminisidade, String orientacao, String nClicksHome, String nClicksRecentes, String wifi, String dadosMoveis, String bored) {
        this.bateria=bateria;
        this.nAppSociais=nAppSociais;
        this.nAppChatting=nAppChatting;
        this.nOutrasApps=nOutrasApps;
        this.notificacesAppsAtuais=notificacesAppsAtuais;
        this.nAtivacoesEcra=nAtivacoesEcra;
        this.nChamadasFeitas=nChamadasFeitas;
        this.getnChamadasRecebidas=getnChamadasRecebidas;
        this.proximidade=proximidade;
        this.nSMSRecebidas=nSMSRecebidas;
        this.luminisidade=luminisidade;
        this.orientacao=orientacao;
        this.nClicksHome=nClicksHome;
        this.nClicksRecentes=nClicksRecentes;
        this.wifi=wifi;
        this.dadosMoveis=dadosMoveis;
        this.bored=bored;
    }

    public String getBateria() {
        return bateria;
    }

    public String getnAppSociais() {
        return nAppSociais;
    }

    public String getnAppChatting() {
        return nAppChatting;
    }

    public String getnOutrasApps() {
        return nOutrasApps;
    }

    public String getNotificacesAppsAtuais() {
        return notificacesAppsAtuais;
    }

    public String getnAtivacoesEcra() {
        return nAtivacoesEcra;
    }

    public String getnChamadasFeitas() {
        return nChamadasFeitas;
    }

    public String getGetnChamadasRecebidas() {
        return getnChamadasRecebidas;
    }

    public String getProximidade() {
        return proximidade;
    }

    public String getnSMSRecebidas() {
        return nSMSRecebidas;
    }

    public String getLuminisidade() {
        return luminisidade;
    }

    public String getOrientacao() {
        return orientacao;
    }

    public String getnClicksHome() {
        return nClicksHome;
    }

    public String getnClicksRecentes() {
        return nClicksRecentes;
    }

    public String getWifi() {
        return wifi;
    }

    public String getDadosMoveis() {
        return dadosMoveis;
    }

    public String getBored() {
        return bored;
    }

    public void setBateria(String bateria) {
        this.bateria = bateria;
    }

    public void setnAppSociais(String nAppSociais) {
        this.nAppSociais = nAppSociais;
    }

    public void setnAppChatting(String nAppChatting) {
        this.nAppChatting = nAppChatting;
    }

    public void setnOutrasApps(String nOutrasApps) {
        this.nOutrasApps = nOutrasApps;
    }

    public void setNotificacesAppsAtuais(String notificacesAppsAtuais) {
        this.notificacesAppsAtuais = notificacesAppsAtuais;
    }

    public void setnAtivacoesEcra(String nAtivacoesEcra) {
        this.nAtivacoesEcra = nAtivacoesEcra;
    }

    public void setnChamadasFeitas(String nChamadasFeitas) {
        this.nChamadasFeitas = nChamadasFeitas;
    }

    public void setGetnChamadasRecebidas(String getnChamadasRecebidas) {
        this.getnChamadasRecebidas = getnChamadasRecebidas;
    }

    public void setProximidade(String proximidade) {
        this.proximidade = proximidade;
    }

    public void setnSMSRecebidas(String nSMSRecebidas) {
        this.nSMSRecebidas = nSMSRecebidas;
    }

    public void setLuminisidade(String luminisidade) {
        this.luminisidade = luminisidade;
    }

    public void setOrientacao(String orientacao) {
        this.orientacao = orientacao;
    }

    public void setnClicksHome(String nClicksHome) {
        this.nClicksHome = nClicksHome;
    }

    public void setnClicksRecentes(String nClicksRecentes) {
        this.nClicksRecentes = nClicksRecentes;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public void setDadosMoveis(String dadosMoveis) {
        this.dadosMoveis = dadosMoveis;
    }

    public void setBored(String bored) {
        this.bored = bored;
    }
}