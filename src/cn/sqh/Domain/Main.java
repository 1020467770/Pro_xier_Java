package cn.sqh.Domain;

import cn.sqh.Covid19Utils.Covid19Util;

public class Main {
    public static void main(String[] args) {

        Covid19Util.pushCountrytIntodb("China");
        Covid19Util.pushCountrytIntodb("US");
        Covid19Util.pushCountrytIntodb("United%20Kingdom");
        Covid19Util.pushCountrytIntodb("Japan");

    }
}
