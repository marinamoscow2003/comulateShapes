package ru.alfa.utils;

import com.esri.core.geometry.*;
import ru.alfa.model.BuildingModel;
import sun.misc.IOUtils;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map;

/*
    * Эквидистанта
    *
    **/
public class ShapeAproxim {

    double lon; // Долгота центра фигуры в градусах
    double lat; // Широта центра фигуры в градусах
    double distErr; //
    double fi; // Угол между главной осью прямоугольного контура объекта и меридианом, пересекающим центр объекта
    double lmin1; // Меньшая сторона прямоугольника
    double lmax2; // Большая сторона прямоугольника
    double r; // Радиус круга, которым может аппроксимироваться фигура
    double xc; // Lon переведенная в метры!
    double yc; // Lat переведенная в метры!


       /* Функция вычисления координат по геоданным */
    public Map<String, Shape> getF (BuildingModel bmodel) {
        HashMap<String, Shape> shapes = new HashMap<>();
        //Geometry geometry = new Line();
        //System.out.println(geometry);
        Line2D line4 = new Line2D.Double();
        Line2D line5 = new Line2D.Double();
        Line2D line6 = new Line2D.Double();
        Line2D line7 = new Line2D.Double();
        shapes.put("like4", line4);
        shapes.put("like5", line5);
        shapes.put("like6", line6);
        shapes.put("like7", line7);
        geometryCrosses(createPolyline1(), createPolyline1(), null);
        return shapes;
    }

   public static boolean geometryCrosses(Geometry geometryA, Geometry geometryB, SpatialReference sr)
    {
        boolean crosses = OperatorCrosses.local().execute(geometryA, geometryB, sr, null);
        return crosses;
    }

   public static Polyline createPolyline1() {

        Polyline line = new Polyline();

        // Path 1
        line.startPath(6.9, 9.1);
        line.lineTo(7, 8.8);

        // Path 2
      //  line.startPath(6.8, 8.8);
      //  line.lineTo(7, 9);
       // line.lineTo(7.2, 8.9);
       // line.lineTo(7.4, 9);

        // Path 3
      //  line.startPath(7.4, 8.9);
      //  line.lineTo(7.25, 8.6);
     //   line.lineTo(7.15, 8.8);

        return line;
    }
    public static Polyline createPolyline2() {

        Polyline line = new Polyline();

        // Path 1
    //    line.startPath(6.9, 9.1);
    //    line.lineTo(9, 7);

        // Path 2
      //  line.startPath(6.8, 8.8);
      //  line.lineTo(7, 9);
      //  line.lineTo(7.2, 8.9);
      //  line.lineTo(7.4, 9);

        // Path 3
        line.startPath(7.4, 8.9);
        line.lineTo(7.25, 8.6);
        line.lineTo(7.15, 8.8);

        return line;
    }


}
