import java.util.*;
import javax.swing.JOptionPane;

//points_calculate ：搜索拼图
public class points_calculate {

    //拼在图上的板
    Stack<singleBlock> blocks;

    //初始点集
    List<point> desq = new ArrayList<point>();

    //运算中点集
    List<point> q = new ArrayList<point>();

    //DFS open表中的state
    Stack<state> states = new Stack<state>();

    //是否步进  0：步进  1：一次成型（进影响显示效果，不影响搜索过程）
    boolean ifc;

    public points_calculate(List<point> d, Stack<singleBlock> b) {
        desq = d;
        for (int i = 0; i < desq.size(); i++) {
            try {
                q.add((point) desq.get(i).clone());
            } catch (CloneNotSupportedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        blocks = b;
        states.push(new state(desq));
    }

    //判断两线段是否相交
    boolean get_line_intersection(float p0_x, float p0_y, float p1_x, float p1_y, float p2_x, float p2_y, float p3_x,
            float p3_y) {

        float s02_x, s02_y, s10_x, s10_y, s32_x, s32_y, s_numer, t_numer, denom, t;
        s10_x = p1_x - p0_x;
        s10_y = p1_y - p0_y;
        s32_x = p3_x - p2_x;
        s32_y = p3_y - p2_y;

        denom = s10_x * s32_y - s32_x * s10_y;
        if (denom == 0) {
            return false;
        }
        boolean denomPositive = denom > 0;

        s02_x = p0_x - p2_x;
        s02_y = p0_y - p2_y;
        s_numer = s10_x * s02_y - s10_y * s02_x;
        if (s_numer == 0 || (s_numer < 0) == denomPositive) {
            return false;
        }

        t_numer = s32_x * s02_y - s32_y * s02_x;
        if (t_numer == 0 || (t_numer < 0) == denomPositive) {
            return false;
        }
        if (Math.abs(s_numer) >= Math.abs(denom) || Math.abs(t_numer) >= Math.abs(denom)) {
            return false;
        }
        return true;
    }

    //判断某块板是否放入待拼图案
    boolean Edgefit(List<point> points, int type, int index, int iobp, int highangle, Boolean ud) {
        boolean flag = true;
        List<point> q = points;
        point pd, pdn;
        singleBlock block = new singleBlock(type, index, ud);
        int sizeofDesti = q.size();
        int sizeofBlock = block.blockpoints.size();
        block.blockpoints.get(iobp).x = q.get(q.size() - 1).x;
        block.blockpoints.get(iobp).y = q.get(q.size() - 1).y;
        block.blockpoints.get(iobp).al = highangle - block.blockpoints.get(iobp).angle;
        block.blockpoints.get(iobp).ah = highangle;
        for (int i = 0; i < sizeofBlock - 1; i++) {
            block.blockpoints.get((i + iobp + 1) % sizeofBlock).x = block.blockpoints.get((i + iobp) % sizeofBlock).x
                    + (float) (block.blockpoints.get((i + iobp) % sizeofBlock).eh
                            * Math.cos(block.blockpoints.get((i + iobp) % sizeofBlock).ah * Math.PI / 180));
            block.blockpoints.get((i + iobp + 1) % sizeofBlock).y = block.blockpoints.get((i + iobp) % sizeofBlock).y
                    + (float) (block.blockpoints.get((i + iobp) % sizeofBlock).eh
                            * Math.sin(block.blockpoints.get((i + iobp) % sizeofBlock).ah * Math.PI / 180));
            block.blockpoints.get(
                    (i + iobp + 1) % sizeofBlock).al = (block.blockpoints.get((i + iobp) % sizeofBlock).ah + 180) % 360;
            block.blockpoints
                    .get((i + iobp + 1) % sizeofBlock).ah = block.blockpoints.get((i + iobp + 1) % sizeofBlock).al
                            + block.blockpoints.get((i + iobp + 1) % sizeofBlock).angle;
        }

        for (int i = 0; i < sizeofDesti - 1; i++) {
            pd = q.get(i);
            pdn = q.get((i + 1) % sizeofDesti);

            //遍历顶点，柔化边界
            for (int j = 0; j < sizeofBlock; j++) {
                flag = flag && (!get_line_intersection(block.blockpoints.get(j).x, block.blockpoints.get(j).y,
                        block.blockpoints.get((j + 1) % sizeofBlock).x, block.blockpoints.get((j + 1) % sizeofBlock).y,
                        (float) (pd.x + 0.2 * (float) Math
                                .cos((0.5 * ((pd.al < pd.ah ? pd.al : (pd.al - 360)) + pd.ah) + 180) * Math.PI / 180)),
                        (float) (pd.y + 0.2 * (float) Math
                                .sin((0.5 * ((pd.al < pd.ah ? pd.al : (pd.al - 360)) + pd.ah) + 180) * Math.PI / 180)),
                        (float) (pdn.x + 0.2 * (float) Math.cos(
                                (0.5 * ((pdn.al < pdn.ah ? pdn.al : (pdn.al - 360)) + pdn.ah) + 180) * Math.PI / 180)),
                        (float) (pdn.y + 0.2
                                * (float) Math.sin((0.5 * ((pdn.al < pdn.ah ? pdn.al : (pdn.al - 360)) + pdn.ah) + 180)
                                        * Math.PI / 180))));

                flag = flag && (!get_line_intersection(block.blockpoints.get(j).x, block.blockpoints.get(j).y,
                        block.blockpoints.get((j + 2) % sizeofBlock).x, block.blockpoints.get((j + 2) % sizeofBlock).y,
                        (float) (pd.x + 0.2 * (float) Math
                                .cos((0.5 * ((pd.al < pd.ah ? pd.al : (pd.al - 360)) + pd.ah) + 180) * Math.PI / 180)),
                        (float) (pd.y + 0.2 * (float) Math
                                .sin((0.5 * ((pd.al < pd.ah ? pd.al : (pd.al - 360)) + pd.ah) + 180) * Math.PI / 180)),
                        (float) (pdn.x + 0.2 * (float) Math.cos(
                                (0.5 * ((pdn.al < pdn.ah ? pdn.al : (pdn.al - 360)) + pdn.ah) + 180) * Math.PI / 180)),
                        (float) (pdn.y + 0.2
                                * (float) Math.sin((0.5 * ((pdn.al < pdn.ah ? pdn.al : (pdn.al - 360)) + pdn.ah) + 180)
                                        * Math.PI / 180))));

            }

        }

        return flag;
    }

    //放板：把iob号的iobp顶点贴住目标点，高位边对齐，ud控制正反面
    boolean addblock7(int iob, int iobp, int highangle, Boolean ud) {
        boolean success = false;

        int desindex = q.size() - 1; // the index being operated
        point desPoint = q.get(desindex); // the point being operated
        point lastPoint = q.get(desindex - 1);
        point nextPoint = q.get(0);

        singleBlock thisblock = new singleBlock(7, iob, ud);
        point thisblockpoint = thisblock.blockpoints.get(iobp);
        int thisblockangle = thisblockpoint.angle;
        point lastblockpoint = thisblock.blockpoints
                .get((iobp + thisblock.blockpoints.size() - 1) % thisblock.blockpoints.size());
        point nextblockpoint = thisblock.blockpoints.get((iobp + 1) % thisblock.blockpoints.size());
        point oppoblockpoint = thisblock.blockpoints.get((iobp + 2) % thisblock.blockpoints.size());

        int lowangle = (highangle - thisblockangle + 360) % 360;

        boolean anglefit = (thisblockangle <= desPoint.angle + 3)
                && (highangle - thisblockangle >= ((desPoint.al < desPoint.ah) ? desPoint.al : desPoint.al - 360) - 2)
                && (highangle <= desPoint.ah + 2);
        boolean edgefit = Edgefit(q, 7, iob, iobp, highangle, ud);

        if (anglefit && edgefit) {

            singleBlock nsb = new singleBlock(7, iob, false);
            nsb.blockpoints.get(iobp).x = desPoint.x;
            nsb.blockpoints.get(iobp).y = desPoint.y;
            nsb.blockpoints.get((iobp + 1) % nsb.blockpoints.size()).x = desPoint.x
                    + (float) (thisblockpoint.eh * Math.cos(Math.PI * highangle / 180));
            nsb.blockpoints.get((iobp + 1) % nsb.blockpoints.size()).y = desPoint.y
                    + (float) (thisblockpoint.eh * Math.sin(Math.PI * highangle / 180));
            nsb.blockpoints.get((iobp - 1 + nsb.blockpoints.size()) % nsb.blockpoints.size()).x = desPoint.x
                    + (float) (thisblockpoint.el * Math.cos(Math.PI * lowangle / 180));
            nsb.blockpoints.get((iobp - 1 + nsb.blockpoints.size()) % nsb.blockpoints.size()).y = desPoint.y
                    + (float) (thisblockpoint.el * Math.sin(Math.PI * lowangle / 180));
            if (iob == 3 || iob == 4) {
                nsb.blockpoints.get((iobp + 2) % nsb.blockpoints.size()).x = -desPoint.x
                        + nsb.blockpoints.get((iobp + 1) % nsb.blockpoints.size()).x
                        + nsb.blockpoints.get((iobp - 1 + nsb.blockpoints.size()) % nsb.blockpoints.size()).x;
                nsb.blockpoints.get((iobp + 2) % nsb.blockpoints.size()).y = -desPoint.y
                        + nsb.blockpoints.get((iobp + 1) % nsb.blockpoints.size()).y
                        + nsb.blockpoints.get((iobp - 1 + nsb.blockpoints.size()) % nsb.blockpoints.size()).y;
            }
            blocks.push(nsb);

            q.remove(desindex);

            if ((lowangle - desPoint.al > -2) && (lowangle - desPoint.al < 2)) {
                point ql = new point(lastPoint);
                if (desPoint.el - thisblockpoint.el > 0.2f) {
                    ql.eh = desPoint.el - thisblockpoint.el;
                    q.set(q.size() - 1, new point(ql));
                    q.add(new point((float) (desPoint.x + thisblockpoint.el * Math.cos(lowangle * Math.PI / 180)),
                            (float) (desPoint.y + thisblockpoint.el * Math.sin(lowangle * Math.PI / 180)), desPoint.al,
                            (desPoint.al + 180 - lastblockpoint.angle) % 360, desPoint.el - thisblockpoint.el,
                            lastblockpoint.el));
                } else if ((desPoint.el - thisblockpoint.el) <= 0.2f && (desPoint.el - thisblockpoint.el) >= -0.2f) {
                    q.get(q.size() - 1).eh = lastblockpoint.el;
                    q.get(q.size() - 1).setAngle(ql.al, ql.ah - lastblockpoint.angle);
                } else {
                    ql.eh = thisblockpoint.el - desPoint.el;
                    ql.setAngle(ql.al, (ql.ah + 180) % 360);
                    q.set(q.size() - 1, new point(ql));
                    q.add(new point((float) (desPoint.x + thisblockpoint.el * Math.cos(lowangle * Math.PI / 180)),
                            (float) (desPoint.y + thisblockpoint.el * Math.sin(lowangle * Math.PI / 180)),
                            (desPoint.al + 180) % 360, (desPoint.al + 180 - lastblockpoint.angle) % 360,
                            thisblockpoint.el - desPoint.el, lastblockpoint.el));
                }
            }

            else {
                point ql = new point(desPoint);
                ql.eh = thisblockpoint.el;
                ql.setAngle(ql.al, ql.ah - thisblockangle);
                q.add(new point(ql));
                q.add(new point((float) (desPoint.x + thisblockpoint.el * Math.cos(lowangle * Math.PI / 180)),
                        (float) (desPoint.y + thisblockpoint.el * Math.sin(lowangle * Math.PI / 180)),
                        (desPoint.ah - thisblockangle + 180) % 360,
                        (desPoint.ah - thisblockangle - lastblockpoint.angle + 180) % 360, thisblockpoint.el,
                        lastblockpoint.el));
            }

            // if iob == 3 / 4
            if (iob == 3 || iob == 4) {
                q.add(new point(
                        (float) (desPoint.x + thisblockpoint.eh * Math.cos(highangle * Math.PI / 180)
                                + thisblockpoint.el * Math.cos(lowangle * Math.PI / 180)),
                        (float) (desPoint.y + thisblockpoint.eh * Math.sin(highangle * Math.PI / 180)
                                + thisblockpoint.el * Math.sin(lowangle * Math.PI / 180)),
                        (highangle + 180) % 360, (lowangle + 180) % 360, oppoblockpoint.eh, oppoblockpoint.el));
            }

            // align the high edge : block.ah == point.ah
            if (highangle == desPoint.ah) {
                point qh = nextPoint;
                if (desPoint.eh - thisblockpoint.eh > 0.2f) {
                    qh.el = desPoint.eh - thisblockpoint.eh;
                    q.set(0, new point(qh));
                    q.add(new point((float) (desPoint.x + thisblockpoint.eh * Math.cos(highangle * Math.PI / 180)),
                            (float) (desPoint.y + thisblockpoint.eh * Math.sin(highangle * Math.PI / 180)),
                            (desPoint.ah + 180 + nextblockpoint.angle) % 360, desPoint.ah, nextblockpoint.eh,
                            desPoint.eh - thisblockpoint.eh));
                } else if (desPoint.eh - thisblockpoint.eh <= 0.2f && desPoint.eh - thisblockpoint.eh >= -0.2f) {
                    qh.el = nextblockpoint.eh;
                    qh.setAngle((qh.al + nextblockpoint.angle) % 360, qh.ah);
                    q.set(0, new point(qh));
                } else {
                    qh.el = thisblockpoint.eh - desPoint.eh;
                    qh.setAngle((qh.al + 180) % 360, qh.ah);
                    q.set(0, new point(qh));
                    q.add(new point((float) (desPoint.x + thisblockpoint.eh * Math.cos(highangle * Math.PI / 180)),
                            (float) (desPoint.y + thisblockpoint.eh * Math.sin(highangle * Math.PI / 180)),
                            (desPoint.ah + 180 + lastblockpoint.angle) % 360, (desPoint.ah + 180) % 360,
                            nextblockpoint.eh, thisblockpoint.eh - desPoint.eh));
                }
            }

            else {
                point qh = nextPoint;
                q.add(new point((float) (desPoint.x + thisblockpoint.eh * Math.cos(highangle * Math.PI / 180)),
                        (float) (desPoint.y + thisblockpoint.eh * Math.sin(highangle * Math.PI / 180)),
                        (desPoint.ah - thisblockangle + 180) % 360,
                        (desPoint.ah - thisblockangle - lastblockpoint.angle + 180) % 360, thisblockpoint.el,
                        lastblockpoint.el));
                qh.el = thisblockpoint.eh;
                qh.setAngle(qh.al - thisblockangle, qh.ah);
                q.add(new point(qh));
            }

            success = true;
        }

        return success;
    }

    //点处理：消去目标图像中的共线点
    boolean pointsdeal() {

        if (q.size() < 3) {
            return true;
        }

        for (int i = 0; i < q.size(); i++) {
            int thisangle = q.get(i).angle;
            float thisel = q.get(i).el;
            float thiseh = q.get(i).eh;

            if (thisangle == 0) {
                if (thisel - thiseh < -0.12) {
                    
                    q.get((i + 1) % q.size()).el = thiseh - thisel;
                    q.get((i - 1 + q.size()) % q.size()).eh = thiseh - thisel;
                    q.get((i - 1 + q.size()) % q.size()).setAngle(q.get((i - 1 + q.size()) % q.size()).al, q.get(i).ah);
                }
                if (thisel - thiseh > 0.12) {
                    q.get((i - 1 + q.size()) % q.size()).eh = thisel - thiseh;
                    q.get((i + 1) % q.size()).el = thisel - thiseh;
                    q.get((i + 1) % q.size()).setAngle(q.get(i).al, q.get((i + 1) % q.size()).ah);
                }
                q.remove(i);

                if (thisel - thiseh >= -0.12 && thisel - thiseh <= 0.12) {
                    q.get((i - 1 + q.size()) % q.size()).eh = q.get((i) % q.size()).eh;
                    q.get((i - 1 + q.size()) % q.size()).setAngle(q.get((i - 1 + q.size()) % q.size()).al,
                            q.get((i) % q.size()).ah);
                    q.remove(i % q.size());
                }
                i--;
                if (q.size() < 3)
                    return true;
            } else if (thisangle == 180) {
                q.get((i - 1 + q.size()) % q.size()).eh = thiseh + thisel;
                q.get((i + 1) % q.size()).el = thiseh + thisel;
                q.remove(i);
                i--;
                if (q.size() < 3)
                    return true;
            }
        }
        return false;
    }

    //深度优先搜索
    boolean search(Boolean c) {
        this.ifc = c;
        boolean success = false;
        boolean flag;
        boolean last_success = false;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 3; j++) {
                if (j > 0 && i == 3)
                    continue;
                if (j == 2 && i == 4)
                    continue;
                
                if (((states.peek().stateBlock
                        & (1 << i)) == 0) ) {

                    flag = addblock7(i, j, q.get(q.size() - 1).ah, false);

                    if (flag == false) {
                        state presentstate;

                        try {
                            presentstate = (state) states.peek().clone();
                            presentstate.statesingleblock_up_ban(i, j);
                            states.pop();
                            states.push(new state(presentstate));
                        } catch (CloneNotSupportedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                    //match success: i^th block ,j^th point
                    else {
                        state presentstate;

                        try {
                            presentstate = (state) states.peek().clone();
                            
                            success = pointsdeal();
                            success = pointsdeal();

                            presentstate.q = this.q;             
                            presentstate.stateBlock |= (1 << i);
                            presentstate.blocks = this.blocks;
                            
                            states.push(new state(presentstate));

                        } catch (CloneNotSupportedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        if(this.ifc == false){
                                Object[] options ={ "确认", "退出步进" };
                                int quit = JOptionPane.showOptionDialog(null, "匹配第" + i + "块板", null,JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                if(quit == 1)this.ifc = true;
                        }  

                        if (success) return true;
                        
                        last_success = search(this.ifc);
                        
                        if (last_success == false) {
                            blocks.pop();
                            states.pop();
                            
                            List<point> present = new ArrayList<point>();
                            for (int isp = 0; isp < states.peek().q.size(); isp++) {
                                try {
                                    present.add((point) states.peek().q.get(isp).clone());
                                } catch (CloneNotSupportedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                
                            }

                            if(this.ifc == false){
                                Object[] options ={ "确认", "退出步进" };
                                int quit = JOptionPane.showOptionDialog(null, "第" + i + "块板拼不下去", null,JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                if(quit == 1)this.ifc = true;
                            }

                            this.q = new ArrayList<point>(present);
                            
                            states.peek().stateBlock &= ~(1 << i);
                            
                        } else {
                            return true;
                        }
                    }

                }

                if (i != 4)
                    continue;

                if (((states.peek().stateBlock
                        & (1 << i)) == 0) ) {
                    
                    flag = addblock7(i, j, q.get(q.size() - 1).ah, true);

                    if (flag == false) {
                        state presentstate;
                        try {
                            presentstate = (state) states.peek().clone();
                            presentstate.statesingleblock_down_ban(i, j);
                            states.pop();
                            states.push(new state(presentstate));
                        } catch (CloneNotSupportedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    else {

                        state presentstate;

                        try {
                            presentstate = (state) states.peek().clone();
                            presentstate.q = this.q;
                            presentstate.blocks = this.blocks;
                            presentstate.stateBlock |= (1 << i);
                            states.push(new state(presentstate));
                        } catch (CloneNotSupportedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        
                        success = pointsdeal();
                      
                        if(this.ifc == false){
                                Object[] options ={ "确认", "退出步进" };
                                int quit = JOptionPane.showOptionDialog(null, "匹配第" + i + "块板", null,JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                if(quit == 1)this.ifc = true;
                        }

                        if(success)return true;

                        last_success = search(this.ifc);
                        if(last_success == false){
                            blocks.pop();
                            states.pop();
                            
                            states.peek().stateBlock &= ~(1 << i);
                            

                                List<point> present = new ArrayList<point>();
                                for (int isp = 0; isp < states.peek().q.size(); isp++) {
                                    try {
                                        present.add((point) states.peek().q.get(isp).clone());
                                    } catch (CloneNotSupportedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    
                                }

                            if(this.ifc == false){
                                Object[] options ={ "确认", "退出步进" };
                                int quit = JOptionPane.showOptionDialog(null, "第" + i + "块板拼不下去", null,JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                if(quit == 1)c = true;
                            }
                                this.q = new ArrayList<point>(present);

                        }
                        else return true;
                    }
                    
                }
            }
            
        }
        return false;
    }

}