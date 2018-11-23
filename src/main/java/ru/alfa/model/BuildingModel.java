package ru.alfa.model;

/* Модель объекта */
public class BuildingModel {
    double lon; // Долгота центра фигуры в градусах
    double lat; // Широта центра фигуры в градусах
    public double distErr; // радиус апроксимации - для точки это просто радиус
    public double fi; // Угол между главной осью прямоугольного контура объекта и меридианом, пересекающим центр объекта
    double lmin1; // Меньшая сторона прямоугольника
    double lmax2; // Большая сторона прямоугольника

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getDistErr() {
        return distErr;
    }

    public void setDistErr(double distErr) {
        this.distErr = distErr;
    }

    public double getFi() {
        return fi;
    }

    public void setFi(double fi) {
        this.fi = fi;
    }

    public double getLmin1() {
        return lmin1;
    }

    public void setLmin1(double lmin1) {
        this.lmin1 = lmin1;
    }

    public double getLmax2() {
        return lmax2;
    }

    public void setLmax2(double lmax2) {
        this.lmax2 = lmax2;
    }
}
