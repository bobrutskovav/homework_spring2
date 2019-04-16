package ru.otus.domain;

public class User {

    private String login;
    private String password;
    private boolean isWantToBecomeALibraryMan;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isWantToBecomeALibraryMan() {
        return isWantToBecomeALibraryMan;
    }

    public void setWantToBecomeALibraryMan(boolean wantToBecomeALibraryMan) {
        isWantToBecomeALibraryMan = wantToBecomeALibraryMan;
    }
}
