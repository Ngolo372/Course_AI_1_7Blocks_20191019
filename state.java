import java.util.*;

public class state  implements Cloneable{
    List<point> q;

    Stack<singleBlock> blocks;
    //List<singleBlock> blocks;

    int stateBlock;
    Vector<Integer> statesingleblock_up;
    Vector<Integer> statesingleblock_down;

    public state(){
        stateBlock = 0;
        q = new ArrayList<point>();
        blocks = new Stack<singleBlock>();
        statesingleblock_up = new Vector<Integer>(7);
        statesingleblock_down = new Vector<Integer>(7);
        for (int i = 0; i < 7; i++) {
            statesingleblock_up.add(new Integer(0));
            statesingleblock_down.add(new Integer(0));
        }
    }

    public state(List<point>  points){
        q = points;
        stateBlock = 0;
        blocks = new Stack<singleBlock>();
        statesingleblock_up = new Vector<Integer>(7);
        statesingleblock_down = new Vector<Integer>(7);
        for (int i = 0; i < 7; i++) {
            statesingleblock_up.add(new Integer(0));
            statesingleblock_down.add(new Integer(0));
        }
    }

    public state(List<point>  points, Stack<singleBlock> b){
        q = points;
        stateBlock = 0;
        blocks = b;
        statesingleblock_up = new Vector<Integer>(7);
        statesingleblock_down = new Vector<Integer>(7);
        for (int i = 0; i < 7; i++) {
            statesingleblock_up.add(new Integer(0));
            statesingleblock_down.add(new Integer(0));
        }
    }

    public state(List<point>  points, int state, Stack<singleBlock> b){
        q = points;
        stateBlock = state;
        blocks = new Stack<singleBlock>();
        blocks = b;
        statesingleblock_up = new Vector<Integer>(7);
        statesingleblock_down = new Vector<Integer>(7);
        for (int i = 0; i < 7; i++) {
            statesingleblock_up.add(new Integer(0));
            statesingleblock_down.add(new Integer(0));
        }
    }

    public state(state s){
        this.q = new ArrayList<point>();
        //this.q = s.q;
        for (int i = 0; i < s.q.size(); i++) {
            q.add(new point(s.q.get(i).x, s.q.get(i).y, s.q.get(i).al, s.q.get(i).ah, s.q.get(i).el, s.q.get(i).eh));
        }
        this.stateBlock = s.stateBlock;
        this.blocks = s.blocks;
        this.statesingleblock_up = new Vector<Integer>(7);
        this.statesingleblock_down = new Vector<Integer>(7);
        for (int i = 0; i < 7; i++) {
            this.statesingleblock_up.add(new Integer(s.statesingleblock_up.get(i)));
            this.statesingleblock_down.add(new Integer(s.statesingleblock_down.get(i)));
        }
    }

    void stateBlock_add(int i){
        stateBlock |= (1 << i);
    }

    void stateBlock_away(int i){
        stateBlock &= ~(1 << i);
    }

    void statesingleblock_up_ban(int iob, int iobp){
        statesingleblock_up.set(iob, (statesingleblock_up.get(iob) | (1 << iobp)));
    }

    void statesingleblock_down_ban(int iob, int iobp){
        statesingleblock_down.set(iob, (statesingleblock_down.get(iob) | (1 << iobp)));
    }

    public Object clone() throws CloneNotSupportedException {  
        return super.clone();  
    }  

}