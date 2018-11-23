package ru.alfa.sampleDrawShapes;

import com.esri.core.geometry.Point2D;
import ru.alfa.ShapeAproxim;

import java.awt.*;
import javax.swing.*;
import java.lang.*;
import java.util.Objects;

public class PaintGraph extends JPanel
{
    private int ny , smesh;
    private    float kx , ky , hx;

    public PaintGraph()
       { smesh = 300; }

    public void paint(Graphics g)
    {
        super.paint(g);
        funcPolyLine(g);
    }


    void funcPolyLine(Graphics g)
    {
        ShapeAproxim shapeAproxim = new ShapeAproxim();
        Polygon polyMaster = null;
        if (Objects.nonNull(shapeAproxim.Am) && Objects.nonNull(shapeAproxim.Bm) && Objects.nonNull(shapeAproxim.Cm) && Objects.nonNull(shapeAproxim.Dm)) {
            polyMaster = new Polygon();
            polyMaster.addPoint(getX(shapeAproxim.Am.getX()), getY(shapeAproxim.Am.getY()));
            polyMaster.addPoint(getX(shapeAproxim.Bm.getX()), getY(shapeAproxim.Bm.getY()));
            polyMaster.addPoint(getX(shapeAproxim.Cm.getX()), getY(shapeAproxim.Cm.getY()));
            polyMaster.addPoint(getX(shapeAproxim.Dm.getX()), getY(shapeAproxim.Dm.getY()));
            polyMaster.addPoint(getX(shapeAproxim.Am.getX()), getY(shapeAproxim.Am.getY()));
        }

        Polygon polyDetail = null;
        if (Objects.nonNull(shapeAproxim.Ad) && Objects.nonNull(shapeAproxim.Bd) && Objects.nonNull(shapeAproxim.Cm) && Objects.nonNull(shapeAproxim.Dm)) {
            polyDetail = new Polygon();
            polyDetail.addPoint(getX(shapeAproxim.Ad.getX()), getY(shapeAproxim.Ad.getY()));
            polyDetail.addPoint(getX(shapeAproxim.Bd.getX()), getY(shapeAproxim.Bd.getY()));
            polyDetail.addPoint(getX(shapeAproxim.Cd.getX()), getY(shapeAproxim.Cd.getY()));
            polyDetail.addPoint(getX(shapeAproxim.Dd.getX()), getY(shapeAproxim.Dd.getY()));
            polyDetail.addPoint(getX(shapeAproxim.Ad.getX()), getY(shapeAproxim.Ad.getY()));
        }
        Point2D[] points = ((com.esri.core.geometry.Polygon) shapeAproxim.detailBufferGeom).getCoordinates2D();
        Polygon poly = new Polygon();
        for (int i = 0; i < points.length; i++) {
            poly.addPoint(getX(points[i].x), getY(points[i].y));
        }
        int i=0;
        poly.addPoint(getX(points[i].x), getY(points[i].y));

        Point2D[] points2 = ((com.esri.core.geometry.Polygon) shapeAproxim.masterBufferGeom).getCoordinates2D();
        Polygon poly2 = new Polygon();
        for (int i2 = 0; i2 < points2.length; i2++) {
            poly2.addPoint(getX(points2[i2].x), getY(points2[i2].y));
        }
        int i2=0;
        poly2.addPoint(getX(points2[i2].x), getY(points2[i2].y));

        g.clearRect(0,0,smesh*3, smesh*3);
        g.drawPolygon(poly);
        g.drawPolygon(poly2);
        if (Objects.nonNull(polyMaster)) {
           g.drawPolygon(polyMaster);
        }

        if (Objects.nonNull(polyDetail)) {
           g.drawPolygon(polyDetail);
        }

       // System.out.println(OperatorCrosses.local().execute(shapeAproxim.masterBufferGeom, shapeAproxim.detailBufferGeom, null, null));
        g.drawLine(smesh, 0, smesh, smesh*2);
        g.drawLine(0, smesh, smesh*2, smesh);
        if (shapeAproxim.cross) {
             g.drawString("Полигоны пересекаются", 20, 20);
        } else {
            g.drawString("Полигоны НЕ пересекаются", 20, 20);
        }
    }

    public int getX (double x) {
        return (int) Math.round(x + smesh);
    }
    public int getY (double y) {
        return (int) Math.round(smesh - y);
    }
}