//singleBlock 类：一块板
import java.util.*;
import java.awt.Color;

public class singleBlock {

    int id;
    Vector<point> blockpoints = new Vector<point>();
    Color c;
    public singleBlock(int type, int index, boolean ud){
        switch (type) {
            case 7:                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
                switch (index) {
                    case 0: case 1:
                        blockpoints.add(new point(0, 45, 14.14f, 10));
                        blockpoints.add(new point(225, 315, 10, 10));
                        blockpoints.add(new point(135, 180, 10, 14.14f));
                        if(index == 0)c = new Color(255, 0, 0);
                        if(index == 1)c = new Color(0, 0, 128);
                        break;
                    case 2:
                        blockpoints.add(new point(0, 45, 10, 7.07f));
                        blockpoints.add(new point(225, 315, 7.07f, 7.07f));
                        blockpoints.add(new point(135, 180, 7.07f, 10));
                        c = new Color(0, 255, 0);
                        break;
                    case 3:
                        blockpoints.add(new point(0, 90, 5, 5));
                        blockpoints.add(new point(270, 0, 5, 5));
                        blockpoints.add(new point(180, 270, 5, 5));
                        blockpoints.add(new point(90, 180, 5, 5));
                        c = new Color(255, 128, 0);
                        break;
                    case 4:
                        if(ud){
                            blockpoints.add(new point(0, 45, 5, 7.07f));
                            blockpoints.add(new point(225, 0, 7.07f, 5));
                            blockpoints.add(new point(180, 225, 5, 7.07f));
                            blockpoints.add(new point(45, 180, 7.07f, 5));
                        }
                        else{
                            blockpoints.add(new point(0, 45, 7.07f, 5));
                            blockpoints.add(new point(225, 0, 5, 7.07f));
                            blockpoints.add(new point(180, 225, 7.07f, 5));
                            blockpoints.add(new point(45, 180, 5, 7.07f));
                        }
                        c = new Color(255, 0, 128);
                        break;
                    case 5: case 6:
                        blockpoints.add(new point(0, 45, 7.07f, 5));
                        blockpoints.add(new point(225, 315, 5, 5));
                        blockpoints.add(new point(135, 180, 5, 7.07f));
                        if(index == 5)c = new Color(0, 255, 255);
                        if(index == 6)c = new Color(0, 0, 255);
                        break;
                    default:
                        break;
                }
                break;
        
            default:
                break;
        }
    }
}