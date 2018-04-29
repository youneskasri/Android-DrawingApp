package gl2.kasri.younes.paintapplication.helpers;

import android.graphics.Point;

import java.util.ArrayList;

abstract class Numbers {

    ArrayList<Point> getNumberWithPoints(int number){

        ArrayList<Point> points = new ArrayList<>();
        switch(number){
            case 0 : drawNumberZeroWithPoints(points); break;
            case 1 : drawNumberOneWithPoints(points); break;
            case 2 : drawNumberTwoWithPoints(points); break;
            case 3 : drawNumberThreeWithPoints(points); break;
            case 4 : drawNumberFourWithPoints(points); break;
            case 5 : drawNumberFiveWithPoints(points); break;
            case 6 : drawNumberSixWithPoints(points); break;
            case 7 : drawNumberSevenWithPoints(points); break;
            case 8 : drawNumberEightWithPoints(points); break;
            case 9 : drawNumberNineWithPoints(points); break;
        }

        return  points;
    }


    public abstract void drawNumberZeroWithPoints(ArrayList<Point> points); /*{
        points.add(       new Point(190, 201));
        points.add(       new Point(213, 207));
        points.add(       new Point(223, 226));
        points.add(       new Point(225, 249));
        points.add(       new Point(225, 271));
        points.add(       new Point(225, 295));
        points.add(       new Point(213, 316));
        points.add(       new Point(190, 321));
        points.add(       new Point(168, 316));
        points.add(       new Point(155, 295));
        points.add(       new Point(155, 271));
        points.add(       new Point(155, 249));
        points.add(       new Point(155, 226));
        points.add(       new Point(168, 207));

        //
        points.add(       new Point(190, 201));
    }*/

    private void drawNumberOneWithPoints(ArrayList<Point> points){

        points.add(       new Point(156, 216));
        points.add(       new Point(177, 208));
        points.add(       new Point(197, 197));
        points.add(       new Point(198, 222));
        points.add(       new Point(197, 244));
        points.add(       new Point(197, 266));
        points.add(       new Point(197, 289));
        points.add(       new Point(198, 312));

    }

    private void drawNumberTwoWithPoints(ArrayList<Point> points){

        points.add(       new Point(147, 223));
        points.add(       new Point(160, 202));
        points.add(       new Point(185, 191));
        points.add(        new Point(214, 197));
        points.add(       new Point(228, 222));
        points.add(       new Point(223, 246));
        points.add(      new Point(207, 266));
        points.add(       new Point(186, 283));
        points.add(       new Point(166, 297));
        points.add(      new Point(147, 320));
        points.add(       new Point(179, 322));
        points.add(       new Point(210, 322));
        points.add(       new Point(232, 322));

    }

    private void drawNumberThreeWithPoints(ArrayList<Point> points){
        points.add(     new Point(        151,215));
        points.add(     new Point(        171,195));
        points.add(     new Point(        198,190));
        points.add(     new Point(        222,202));
        points.add(     new Point(        223,228));
        points.add(     new Point(        206,247));
        points.add(     new Point(        184,250));
        points.add(     new Point(         218,268));
        points.add(     new Point(        231,290));
        points.add(     new Point(           212,307));
        points.add(     new Point(           189,313));
        points.add(     new Point(          164,305));
        points.add(     new Point(          149,285));
    }

    private void drawNumberFourWithPoints(ArrayList<Point> points){

        points.add(       new Point(207, 313));
        points.add(       new Point(207, 290));
        points.add(       new Point( 207, 265));
        points.add(       new Point( 207, 240));
        points.add(       new Point(  207, 218));
        points.add(       new Point(  209, 191));

        points.add(       new Point(    185, 201));
        points.add(       new Point(     171, 225));
        points.add(       new Point(     152, 251));
        points.add(       new Point(     139, 276));
        points.add(       new Point(     170, 277));
        points.add(       new Point(      228, 277));
    }

    private void drawNumberFiveWithPoints(ArrayList<Point> points){
        points.add(       new Point(152,300));
        points.add(       new Point(169,320));
        points.add(       new Point(198,320));
        points.add(       new Point(223,305));
        points.add(       new Point(227,281));
        points.add(       new Point(215,255));
        points.add(       new Point(187,250));
        points.add(       new Point(157,260));
        points.add(       new Point(155,236));
        points.add(       new Point(153,205));
        points.add(       new Point(179,203));
        points.add(       new Point(201,203));
        points.add(       new Point(224,202));
    }

    private void drawNumberSixWithPoints(ArrayList<Point> points){
        points.add(       new Point(215, 200));
        points.add(       new Point(187, 205));
        points.add(       new Point(164, 222));
        points.add(       new Point(154, 247));
        points.add(       new Point(154, 274));
        points.add(       new Point(157, 300));
        points.add(       new Point(180, 320));
        points.add(       new Point(207, 321));
        points.add(       new Point(223, 302));
        points.add(       new Point(225, 281));
        points.add(       new Point(219, 259));
        points.add(       new Point(196, 250));
        points.add(       new Point(174, 260));

        //
        points.add(       new Point(154, 274));
    }

    private void drawNumberSevenWithPoints(ArrayList<Point> points){
        points.add(       new Point(145, 208));
        points.add(       new Point(177, 208));
        points.add(       new Point(205, 208));
        points.add(       new Point(233, 208));
        points.add(       new Point(222, 232));
        points.add(       new Point(211, 255));
        points.add(       new Point(199, 279));
        points.add(       new Point(188, 304));
        points.add(       new Point(178, 328));
    }

    private void drawNumberEightWithPoints(ArrayList<Point> points){
        points.add(       new Point(190, 194));
        points.add(       new Point(160, 208));
        points.add(       new Point(220, 208));
        points.add(       new Point(160, 234));
        points.add(       new Point(220, 234));

        points.add(       new Point(190, 251));

        points.add(       new Point(190, 308));
        points.add(       new Point(160, 268));
        points.add(       new Point(220, 268));
        points.add(       new Point(160, 293));
        points.add(       new Point(220, 293));
    }

    private void drawNumberNineWithPoints(ArrayList<Point> points){
        points.add(       new Point(166, 305));
        points.add(       new Point(194, 301));
        points.add(       new Point(216, 286));
        points.add(       new Point(226, 260));
        points.add(       new Point(227, 233));
        points.add(       new Point(223, 207));
        points.add(       new Point(201, 187));
        points.add(       new Point(175, 188));
        points.add(       new Point(158, 206));
        points.add(       new Point(155, 227));
        points.add(       new Point(162, 248));
        points.add(       new Point(185, 258));
        points.add(       new Point(207, 248));

        //
        points.add(       new Point(227, 233));
    }
}
