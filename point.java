
//point类：角点
public class point implements Cloneable{
    public float x, y, el, eh;
    public int al, ah, angle;
    public point(){
        this.x = -1;
        this.y = -1;
        this.al = -1;
        this.ah = -1;
        this.el = -1;
        this.eh = -1;
        this.angle = 0;
    }
    public point(float x, float y, int al, int ah, float el, float eh){
        this.x = x;
        this.y = y;
        this.al = al;
        this.ah = ah;
        this.el = el;
        this.eh = eh;
        this.angle = (this.al <= this.ah) ? (this.ah - this.al) : (this.ah - this.al + 360); 
    }
    public point(int al, int ah, float el, float eh){
        this.al = al;
        this.ah = ah;
        this.el = el;
        this.eh = eh;
        this.angle = (this.al <= this.ah) ? (this.ah - this.al) : (this.ah - this.al + 360); 
    }

    public point(point p){
        this.x = p.x;
        this.y = p.y;
        this.al = p.al;
        this.ah = p.ah;
        this.el = p.el;
        this.eh = p.eh;
        this.angle = (this.al <= this.ah) ? (this.ah - this.al) : (this.ah - this.al + 360); 
    }

    public point(float x, float y){
        this.x = x;
        this.y = y;
        this.al = -1;
        this.ah = -1;
        this.el = -1;
        this.eh = -1;
        this.angle = 0;
    }

    public Object clone() throws CloneNotSupportedException {  
        return super.clone();  
    }  

    public void getAngle(){
        this.angle = (this.al <= this.ah) ? (this.ah - this.al) : (this.ah - this.al + 360); 
    }

    public void setAngle(int low, int high){
        this.al = low;
        this.ah = high;
        this.angle = (this.al <= this.ah) ? (this.ah - this.al) : (this.ah - this.al + 360); 
    }
}