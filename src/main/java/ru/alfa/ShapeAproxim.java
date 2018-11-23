package ru.alfa;

import com.esri.core.geometry.*;
import com.esri.core.geometry.Point;
import ru.alfa.model.BuildingModel;

import static java.lang.Math.PI;

public class ShapeAproxim {
   public Geometry detailBufferGeom;
   public Geometry masterBufferGeom;
    public Geometry detailBufferGeom1;
    public Geometry masterBufferGeom1;
   public boolean cross;
   public Point Am;
   public Point Bm;
   public Point Cm;
   public Point Dm;

    public Point Ad;
    public Point Bd;
    public Point Cd;
    public Point Dd;

   public static boolean geometryCrosses(Geometry geometryA, Geometry geometryB, SpatialReference sr)
    {
        boolean crosses = OperatorCrosses.local().execute(geometryA, geometryB, sr, null);
        return crosses;
    }

    public ShapeAproxim() {
        BuildingModel master = new BuildingModel();
        master.setLon(55.720911); // долгота
        master.setLat(37.612979); // широта
        master.setFi(10);
        master.setDistErr(100);
        master.setLmin1(100);
        master.setLmax2(50);

        BuildingModel delail = new BuildingModel();
        delail.setLon(55.71949); // долгота
        delail.setLat(37.61163); // широта
        delail.setFi(45);
        delail.setDistErr(20);
        delail.setLmin1(100);
        delail.setLmax2(50);

        Point center = new Point(0,0);
        double deltaSinL1;
        double deltaCosL1;
        double deltaCosL2;
        double deltaSinL2;
        // Create a spatial reference object for GCS_WGS_1984.
        SpatialReference spatialRef = SpatialReference.create(4326);
        if ((master.getLmax2() == 0) || (master.getLmin1() == 0)) {
            Point masterPoly = new Point();
            masterPoly.setX(0);
            masterPoly.setY(0);
            masterBufferGeom = OperatorBuffer.local().execute(masterPoly, spatialRef, master.distErr, null);
        } else {
            deltaSinL1 = getDeltaSin(master.getLmin1(), master.fi);
            deltaCosL1 = getDeltaCos(master.getLmin1(), master.fi);
            deltaCosL2 = getDeltaCos(master.getLmax2(), master.fi);
            deltaSinL2 = getDeltaSin(master.getLmax2(), master.fi);
            Am = new Point(center.getX() + deltaSinL1 - deltaCosL2, center.getY() + deltaCosL1 + deltaSinL2);
            Bm = new Point(center.getX() + deltaSinL1 + deltaCosL2, center.getY() + deltaCosL1 - deltaSinL2);
            Cm = new Point(center.getX() - deltaSinL1 + deltaCosL2, center.getY() - deltaCosL1 - deltaSinL2);
            Dm = new Point(center.getX() - deltaSinL1 - deltaCosL2, center.getY() - deltaCosL1 + deltaSinL2);
            System.out.println("A - " + Am.getX() + "          " + Am.getY());
            System.out.println("B - " + Bm.getX() + "          " + Bm.getY());
            System.out.println("C - " + Cm.getX() + "          " + Cm.getY());
            System.out.println("D - " + Dm.getX() + "          " + Dm.getY());

            com.esri.core.geometry.Polygon masterPoly = new com.esri.core.geometry.Polygon();
            masterPoly.startPath(Am);
            masterPoly.lineTo(Bm);
            masterPoly.lineTo(Cm);
            masterPoly.lineTo(Dm);
            masterPoly.lineTo(Am);
            // Buffer the geometry by a distance = 0.5.
            masterBufferGeom = OperatorBuffer.local().execute(masterPoly, spatialRef, master.distErr, null);
        }
        center = new Point(getCord(master.getLat(), master.getLon(), delail.getLat(), delail.getLon(), 0),
                getCord(master.getLat(), master.getLon(), delail.getLat(), delail.getLon(), 1));
        System.out.println("Центр  " + center.getX() + "          " + center.getY());

        if ((delail.getLmax2() == 0) || (delail.getLmin1() == 0)) {
            Point delailp = new Point();
            delailp.setX(center.getX());
            delailp.setY(center.getY());
            detailBufferGeom = OperatorBuffer.local().execute(delailp, spatialRef, delail.distErr, null);
        } else {
            deltaSinL1 = getDeltaSin(delail.getLmin1(), delail.fi);
            deltaCosL1 = getDeltaCos(delail.getLmin1(), delail.fi);
            deltaCosL2 = getDeltaCos(delail.getLmax2(), delail.fi);
            deltaSinL2 = getDeltaSin(delail.getLmax2(), delail.fi);
            Ad = new Point(center.getX() + deltaSinL1 - deltaCosL2, center.getY() + deltaCosL1 + deltaSinL2);
            Bd = new Point(center.getX() + deltaSinL1 + deltaCosL2, center.getY() + deltaCosL1 - deltaSinL2);
            Cd = new Point(center.getX() - deltaSinL1 + deltaCosL2, center.getY() - deltaCosL1 - deltaSinL2);
            Dd = new Point(center.getX() - deltaSinL1 - deltaCosL2, center.getY() - deltaCosL1 + deltaSinL2);
            System.out.println("A - " + Ad.getX() + "          " + Ad.getY());
            System.out.println("B - " + Bd.getX() + "          " + Bd.getY());
            System.out.println("C - " + Cd.getX() + "          " + Cd.getY());
            System.out.println("D - " + Dd.getX() + "          " + Dd.getY());

            com.esri.core.geometry.Polygon detailPoly = new com.esri.core.geometry.Polygon();
            detailPoly.startPath(Ad);
            detailPoly.lineTo(Bd);
            detailPoly.lineTo(Cd);
            detailPoly.lineTo(Dd);
            detailPoly.lineTo(Ad);

            detailBufferGeom = OperatorBuffer.local().execute(detailPoly, spatialRef, delail.distErr, null);
            detailBufferGeom1 = OperatorBuffer.local().execute(detailBufferGeom, spatialRef, 0.001, null);
            detailBufferGeom = OperatorBuffer.local().execute(detailBufferGeom1, spatialRef, 0.001, null);
            detailBufferGeom1 = OperatorBuffer.local().execute(detailBufferGeom, spatialRef, 0.001, null);
            detailBufferGeom = OperatorBuffer.local().execute(detailBufferGeom1, spatialRef, 0.001, null);
        }

        Point2D[] points = ((com.esri.core.geometry.Polygon) detailBufferGeom).getCoordinates2D();
        Polyline poly = new Polyline();
        poly.startPath(points[0].x, points[0].y);
        System.out.println(" points.length  =  "+ points.length);
        for (int i = 1; i < points.length; i++) {
            poly.lineTo(points[i].x, points[i].y);
        }
        int i=0;
        poly.lineTo(points[i].x, points[i].y);

        Point2D[] points1 = ((com.esri.core.geometry.Polygon) masterBufferGeom).getCoordinates2D();
        Polyline poly1 = new Polyline();
        poly1.startPath(points1[0].x, points1[0].y);
        for (int i1 = 1; i1 < points1.length; i1++) {
            poly1.lineTo(points1[i1].x, points1[i1].y);
        }
        int i1=0;
        poly1.lineTo(points1[i1].x, points1[i1].y);


        System.out.println(OperatorCrosses.local().execute(poly, poly1, null, null));

        if (geometryCrosses(poly1, poly, spatialRef))  {
            cross = true;
            System.out.println("Буфферные зоны пересекаются!");
        } else {
            cross = false;
            System.out.println("Буфферные зоны НЕ пересекаются!");
        }
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

     // Округление до 2-х знаков
    public static double round (double d) {
        d = d * 100;
        int i = (int) Math.round(d);
        d = (double)i / 100;
        return d;
    }

    public static double getDeltaSin(double l1, double Fi) {
        return l1/2*Math.sin(Fi* PI/180);
    }

    public static double getDeltaCos(double l1, double Fi) {
        return l1/2*Math.cos(Fi* PI/180);
    }
}
