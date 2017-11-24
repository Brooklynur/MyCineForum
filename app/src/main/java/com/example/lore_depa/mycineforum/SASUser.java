package com.example.lore_depa.mycineforum;

/**
 * Created by SSBS_SELECT on 22/11/2017.
 */

public class SASUser {

    private String nick;
    private String mail;
    private String password;
    private String id;

    public SASUser(String nick, String mail, String password) {
        this.nick = nick;
        this.mail = mail;
        this.password = password;
    }

    public SASUser() {
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
