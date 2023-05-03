package com.texier.orowan2;

public class Data {
    private String login;
    private String password;
    private int privilege;

    public Data(String column1, String column2, int column3) {
        this.login = column1;
        this.password = column2;
        this.privilege = column3;
    }

    public String getColumn1() {
        return login;
    }

    public String getColumn2() {
        return password;
    }

    public int getColumn3() {
        return privilege;
    }
}
