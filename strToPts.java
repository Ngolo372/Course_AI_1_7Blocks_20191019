//strToPts类：将用户输入的字符串转化为多边形点列

import java.util.ArrayList;
import java.util.List;

public class strToPts {

    boolean ok;
    List<point> q;
    String s;
    
    public strToPts (String s) {
        this.s = s;
        this.q = new ArrayList<point>();
        this.ok = false;
    }

    public void trans() {
        if(this.s == null || this.s == ""){
            ok = false;
            return;
        }

        String[] spoint = s.split(";");

        if(spoint.length < 3){
            ok = false;
            return;
        }

        for(int i=0,len=spoint.length;i<len;i++){
            String[] spixel = spoint[i].split(",");
            if(spixel.length != 2){
                ok = false;
                return;
            }

            float x, y;

            try {
                x = Float.parseFloat(spixel[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                ok = false;
                return;
            }

            try {
                y = Float.parseFloat(spixel[1]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                ok = false;
                return;
            }

            q.add(new point(x, y));
        }

        ok = true;

        for (int i = 0; i < q.size(); i++) {
            q.get(i).el = (float)Math.sqrt( (double)(q.get(i).x - q.get((i - 1 + q.size()) % q.size()).x) * (q.get(i).x - q.get((i - 1 + q.size()) % q.size()).x)
             + (q.get(i).y - q.get((i - 1 + q.size()) % q.size()).y) * (q.get(i).y - q.get((i - 1 + q.size()) % q.size()).y) );
            q.get(i).eh = (float)Math.sqrt( (double)(q.get(i).x - q.get((i + 1) % q.size()).x) * (q.get(i).x - q.get((i + 1) % q.size()).x)
             + (q.get(i).y - q.get((i + 1) % q.size()).y) * (q.get(i).y - q.get((i + 1) % q.size()).y) );
            q.get(i).setAngle((int)Math.toDegrees(Math.acos((double)(q.get((i - 1 + q.size()) % q.size()).x - q.get(i).x) / (double)q.get(i).el))
            , (int)Math.toDegrees(Math.acos((double)(q.get((i + 1) % q.size()).x - q.get(i).x) / (double)q.get(i).eh)));
            if(q.get(i).y > q.get((i - 1 + q.size()) % q.size()).y)q.get(i).setAngle(360 - q.get(i).al, q.get(i).ah);
            if(q.get(i).y > q.get((i + 1) % q.size()).y)q.get(i).setAngle(q.get(i).al,360 - q.get(i).ah);
        }
    }
}