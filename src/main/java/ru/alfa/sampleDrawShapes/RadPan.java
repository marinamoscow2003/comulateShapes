package ru.alfa.sampleDrawShapes;
import ru.alfa.ShapeAproxim;
import ru.alfa.sampleDrawShapes.PaintGraph;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RadPan extends JPanel implements ActionListener
{
    private JRadioButton jrb1,jrb2,jrb3,jrb4;
    private ButtonGroup bg;
    PaintGraph pg;
    public void actionPerformed(ActionEvent e)
    {
       pg.repaint();
    }

    public RadPan(PaintGraph p)
    {
        setLayout(new FlowLayout());
        pg=p ;
        jrb4 = new JRadioButton("Полигон",false);
        jrb4.addActionListener(this);
        jrb4.doClick();
        bg = new ButtonGroup();
        bg.add(jrb4);
        add(jrb4);
    }
}
