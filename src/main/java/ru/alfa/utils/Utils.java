package ru.alfa.utils;


public class Utils {


    /**
     * @param lat1 Longitude
     * @param lon1 Longitude
     * @param lat2 Latitude
     * @param lon2 Latitude
     * @param unit - KM is kilometers  / M is meters /MI is miles / N is nautical miles
     * @return - вычисление расстояния между геоточками
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit == "KM") {
                dist = dist * 1.609344;
            } else if (unit == "N") {
                dist = dist * 0.8684;
            } else if (unit == "M") {
                dist =  dist * 1.609344 * 1000;
            }
            return (dist);
        }
    }

    // расстояние между точками в географических координатах
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        return round(distance(lat1, lon1, lat2, lon2, "M"));
    }



    /* Проверка является ли фигура кругом */
    public static boolean checkTRiskAsCircle(double l1, double l2) {
        if (l1 == l2) {
            return true;
        } else return false;
    }

    // Округление до 2-х знаков
    public static double round (double d) {
        d = d * 100;
        int i = (int) Math.round(d);
        d = (double)i / 100;
        return d;
    }



    // Получение координат в декартовой системе
    public static double getCord(double lat0, double lon0, double lat1, double lon1, int cordType) {
        switch (cordType) {
            case 0:
                if (lon0 == lon1) {
                    return round(distance(lat0, lon0, lat0, lon1));
                } else {
                    return round(distance(lat0, lon0,  lat0, lon1) * (lon1 - lon0 ) / Math.abs(lon1 - lon0));
                }
            case 1:
                if (lat0 == lat1) {
                    return round(distance(lat0, lon0, lat1, lat0));
                } else {
                    return round(distance(lat0, lon0, lat1, lon0) * (lat1 - lat0) / Math.abs(lat1 - lat0));
                }
            default:
                System.out.println("X=");
        }
        return 0;
    }

}
