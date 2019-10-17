import java.util.*;

public class points_calculate {

    Stack<singleBlock> blocks;
    List<point> desq = new ArrayList<point>();
    List<point> q = new ArrayList<point>();

    Stack<state> states = new Stack<state>();

    public points_calculate(List<point> d, Stack<singleBlock> b) {
        desq = d;
        // q = desq;
        for (int i = 0; i < desq.size(); i++) {
            try {
                q.add((point) desq.get(i).clone());
            } catch (CloneNotSupportedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // q.add(new point(new Float(desq.get(i).x), new Float(desq.get(i).y), new
            // Integer(desq.get(i).al), new Integer(desq.get(i).ah), new
            // Float(desq.get(i).el), new Float(desq.get(i).eh)));
        }
        System.out.println(",,,,,,,,,,初始" + desq.size());
        blocks = b;
        states.push(new state(desq));
    }

    public void setBlockType(int type) {

    }

    boolean get_line_intersection(float p0_x, float p0_y, float p1_x, float p1_y, float p2_x, float p2_y, float p3_x,
            float p3_y) {
        // System.out.println(p0_x + "," + p0_y + "," + p1_x + "," + p1_y + "," + p2_x +
        // "," + p2_y + "," + p3_x + "," + p3_y);

        float s02_x, s02_y, s10_x, s10_y, s32_x, s32_y, s_numer, t_numer, denom, t;
        s10_x = p1_x - p0_x;
        s10_y = p1_y - p0_y;
        s32_x = p3_x - p2_x;
        s32_y = p3_y - p2_y;

        denom = s10_x * s32_y - s32_x * s10_y;
        if (denom == 0) {
            // System.out.println("重合");
            return false;
        }
        boolean denomPositive = denom > 0;

        s02_x = p0_x - p2_x;
        s02_y = p0_y - p2_y;
        s_numer = s10_x * s02_y - s10_y * s02_x;
        if (s_numer == 0 || (s_numer < 0) == denomPositive) {
            // System.out.println("不相交1");
            return false;
        }

        t_numer = s32_x * s02_y - s32_y * s02_x;
        if (t_numer == 0 || (t_numer < 0) == denomPositive) {
            // System.out.println("不相交2");
            return false;
        }
        if (Math.abs(s_numer) >= Math.abs(denom) || Math.abs(t_numer) >= Math.abs(denom)) {
            // System.out.println("不相交3");
            return false;
        }
        // System.out.println("相交");
        return true;
    }

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

            // System.out.println("index" + i + "x,y" + pd.x + "," + pd.y+ "x,y" + pdn.x +
            // "," + pdn.y);

            for (int j = 0; j < sizeofBlock; j++) {
                flag = flag && (!get_line_intersection(block.blockpoints.get(j).x, block.blockpoints.get(j).y,
                        block.blockpoints.get((j + 1) % sizeofBlock).x, block.blockpoints.get((j + 1) % sizeofBlock).y,
                        (float) (pd.x + 0.1 * (float) Math
                                .cos((0.5 * ((pd.al < pd.ah ? pd.al : (pd.al - 360)) + pd.ah) + 180) * Math.PI / 180)),
                        (float) (pd.y + 0.1 * (float) Math
                                .sin((0.5 * ((pd.al < pd.ah ? pd.al : (pd.al - 360)) + pd.ah) + 180) * Math.PI / 180)),
                        (float) (pdn.x + 0.1 * (float) Math.cos(
                                (0.5 * ((pdn.al < pdn.ah ? pdn.al : (pdn.al - 360)) + pdn.ah) + 180) * Math.PI / 180)),
                        (float) (pdn.y + 0.1
                                * (float) Math.sin((0.5 * ((pdn.al < pdn.ah ? pdn.al : (pdn.al - 360)) + pdn.ah) + 180)
                                        * Math.PI / 180))));
                // if(index == 2)System.out.println(j + "flag -" + flag);

                flag = flag && (!get_line_intersection(block.blockpoints.get(j).x, block.blockpoints.get(j).y,
                        block.blockpoints.get((j + 2) % sizeofBlock).x, block.blockpoints.get((j + 2) % sizeofBlock).y,
                        (float) (pd.x + 0.1 * (float) Math
                                .cos((0.5 * ((pd.al < pd.ah ? pd.al : (pd.al - 360)) + pd.ah) + 180) * Math.PI / 180)),
                        (float) (pd.y + 0.1 * (float) Math
                                .sin((0.5 * ((pd.al < pd.ah ? pd.al : (pd.al - 360)) + pd.ah) + 180) * Math.PI / 180)),
                        (float) (pdn.x + 0.1 * (float) Math.cos(
                                (0.5 * ((pdn.al < pdn.ah ? pdn.al : (pdn.al - 360)) + pdn.ah) + 180) * Math.PI / 180)),
                        (float) (pdn.y + 0.1
                                * (float) Math.sin((0.5 * ((pdn.al < pdn.ah ? pdn.al : (pdn.al - 360)) + pdn.ah) + 180)
                                        * Math.PI / 180))));

                // if(index == 2)System.out.println(j + "flag -" + flag);
            }

        }

        // if(index == 2 && iobp <= 2){
        // System.out.println("待匹配点有几个" + q.size());
        // for (int jph = 0; jph < q.size(); jph++) {
        // System.out.println( "x,y,al,ah,angle,el,eh = " + q.get(jph).x + "," +
        // q.get(jph).y + "," + q.get(jph).al + "," + q.get(jph).ah + "," +
        // q.get(jph).angle + "," + q.get(jph).el + "," + q.get(jph).eh);
        // }
        // System.out.println("待匹配板有几个点" + block.blockpoints.size());
        // for (int jph = 0; jph < block.blockpoints.size(); jph++) {
        // System.out.println( "x,y,al,ah,angle,el,eh = " + block.blockpoints.get(jph).x
        // + "," + block.blockpoints.get(jph).y + "," + block.blockpoints.get(jph).al +
        // "," + block.blockpoints.get(jph).ah + "," + block.blockpoints.get(jph).angle
        // + "," + block.blockpoints.get(jph).el + "," + block.blockpoints.get(jph).eh);
        // }
        // }

        return flag;
    }

    boolean addblock7(/* List<point> q, */int iob, int iobp, int highangle, Boolean ud) {
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

        // System.out.println("板子的al, ah = "+ lowangle + "," + highangle);
        // System.out.println("当前点x,y=" + desPoint.x + "," + desPoint.y + "; angle = " +
        // desPoint.angle);
        boolean anglefit = (thisblockangle <= desPoint.angle)
                && (highangle - thisblockangle >= ((desPoint.al < desPoint.ah) ? desPoint.al : desPoint.al - 360))
                && (highangle <= desPoint.ah);
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

            // align the low edge : block.al == point.al
            // System.out.println("板子和目标的al = "+ lowangle + "," + desPoint.al);

            if ((lowangle == desPoint.al)) {
                // System.out.println("align the angle point");
                point ql = new point(lastPoint);
                if (desPoint.el - thisblockpoint.el > 0.2f) {
                    ql.eh = desPoint.el - thisblockpoint.el;
                    q.set(q.size() - 1, new point(ql));
                    q.add(new point((float) (desPoint.x + thisblockpoint.el * Math.cos(lowangle * Math.PI / 180)),
                            (float) (desPoint.y + thisblockpoint.el * Math.sin(lowangle * Math.PI / 180)), desPoint.al,
                            (desPoint.al + 180 - lastblockpoint.angle) % 360, desPoint.el - thisblockpoint.el,
                            lastblockpoint.el));
                } else if ((desPoint.el - thisblockpoint.el) <= 0.2f && (desPoint.el - thisblockpoint.el) >= -0.2f) {
                    // System.out.println("align the low edge");
                    // ql.eh = lastblockpoint.el;
                    // ql.setAngle(ql.al, ql.ah - lastblockpoint.angle);
                    // q.add(q.size() - 1, new point(ql));
                    q.get(q.size() - 1).eh = lastblockpoint.el;
                    q.get(q.size() - 1).setAngle(ql.al, ql.ah - lastblockpoint.angle);
                } else {
                    // System.out.println("not align the low edge");
                    ql.eh = thisblockpoint.el - desPoint.el;
                    ql.setAngle(ql.al, (ql.ah + 180) % 360);
                    q.set(q.size() - 1, new point(ql));
                    // System.out.println("add a point x,y =" + ql.x + "," + ql.y);
                    q.add(new point((float) (desPoint.x + thisblockpoint.el * Math.cos(lowangle * Math.PI / 180)),
                            (float) (desPoint.y + thisblockpoint.el * Math.sin(lowangle * Math.PI / 180)),
                            (desPoint.al + 180) % 360, (desPoint.al + 180 - lastblockpoint.angle) % 360,
                            thisblockpoint.el - desPoint.el, lastblockpoint.el));
                }
            }

            else {
                // System.out.println("not align the low angle");
                point ql = new point(desPoint);
                ql.eh = thisblockpoint.el;
                ql.setAngle(ql.al, ql.ah - thisblockangle);
                // System.out.println("add a point x,y =" + ql.x + "," + ql.y);
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

        // System.out.println("还有几个点？" + q.size());
        // for (int i = 0; i < q.size(); i++) {
        // System.out.println( "x,y,al,ah,angle,el,eh = " + q.get(i).x + "," +
        // q.get(i).y + "," + q.get(i).al + "," + q.get(i).ah + "," + q.get(i).angle +
        // "," + q.get(i).el + "," + q.get(i).eh);
        // }
        return success;
    }

    boolean pointsdeal() {

        // for (int iph = 0; iph < states.size(); iph++) {
        // System.out.println("本次优化前插入第" + (iph + 1) + "个搜索状态" + ",点数" +
        // states.get(iph).q.size());
        // System.out.println("优化之前还有几个点？" + q.size());
        // for (int jph = 0; jph < q.size(); jph++) {
        // System.out.println( "x,y,al,ah,angle,el,eh = " + q.get(jph).x + "," +
        // q.get(jph).y + "," + q.get(jph).al + "," + q.get(jph).ah + "," +
        // q.get(jph).angle + "," + q.get(jph).el + "," + q.get(jph).eh);
        // }
        // }

        System.out.println("点优化");
        if (q.size() < 3) {
            return true;
        }

        // point predp = q.get(q.size() - 1);
        // point thisp = q.get(0);
        // point succp = q.get(1);

        for (int i = 0; i < q.size(); i++) {
            int thisangle = q.get(i).angle;
            float thisel = q.get(i).el;
            float thiseh = q.get(i).eh;

            // System.out.println("index = " + i + "this angle = " + thisangle);

            if (thisangle == 0) {
                if (thisel < thiseh) {
                    // succp.el = thisp.eh - thisp.el;
                    // q.set((i + 1) % q.size(), new point(succp));
                    // predp.eh = thisp.eh - thisp.el;
                    q.get((i + 1) % q.size()).el = thiseh - thisel;
                    q.get((i - 1 + q.size()) % q.size()).eh = thiseh - thisel;
                    q.get((i - 1 + q.size()) % q.size()).setAngle(q.get((i - 1 + q.size()) % q.size()).al, q.get(i).ah);
                    // predp.setAngle(predp.al, (predp.ah + 180) % 360);
                }
                if (thisel > thiseh) {
                    // predp.eh = thisp.el - thisp.eh;
                    q.get((i - 1 + q.size()) % q.size()).eh = thisel - thiseh;
                    q.get((i + 1) % q.size()).el = thisel - thiseh;
                    q.get((i + 1) % q.size()).setAngle(q.get(i).al, q.get((i + 1) % q.size()).ah);
                    // q.set(i - 1, new point(predp));
                    // succp.el = thisp.el - thisp.eh;
                    // succp.setAngle((succp.al + 180) % 360, succp.ah);
                }
                q.remove(i);

                if (thisel == thiseh) {
                    q.get((i - 1 + q.size()) % q.size()).eh = q.get((i) % q.size()).eh;
                    q.get((i - 1 + q.size()) % q.size()).setAngle(q.get((i - 1 + q.size()) % q.size()).al,
                            q.get((i) % q.size()).ah);
                    q.remove(i % q.size());
                }
                i--;
                if (q.size() < 3)
                    return true;
            } else if (thisangle == 180) {
                // predp.eh = thisp.el + thisp.eh;
                // succp.el = thisp.el + thisp.eh;
                // q.set(i - 1, new point(predp));
                // q.set((i + 1) % q.size(), succp);
                q.get((i - 1 + q.size()) % q.size()).eh = thiseh + thisel;
                q.get((i + 1) % q.size()).el = thiseh + thisel;
                q.remove(i);
                i--;
                if (q.size() < 3)
                    return true;
            }
            // else{
            // predp = thisp;
            // }
            // thisp = succp;
            // succp = q.get((i + 1) % q.size());
        }

        // System.out.println("还有几个点？" + q.size());
        // for (int i = 0; i < q.size(); i++) {
        // System.out.println( "x,y,al,ah,el,eh = " + q.get(i).x + "," + q.get(i).y +
        // "," + q.get(i).al + "," + q.get(i).ah + "," + q.get(i).el + "," +
        // q.get(i).eh);
        // }
        // for (int iph = 0; iph < states.size(); iph++) {
        // System.out.println("本次优化后插入第" + (iph + 1) + "个搜索状态" + ",点数" +
        // states.get(iph).q.size());
        // System.out.println("优化之后还有几个点？" + q.size());
        // for (int jph = 0; jph < q.size(); jph++) {
        // System.out.println( "x,y,al,ah,angle,el,eh = " + q.get(jph).x + "," +
        // q.get(jph).y + "," + q.get(jph).al + "," + q.get(jph).ah + "," +
        // q.get(jph).angle + "," + q.get(jph).el + "," + q.get(jph).eh);
        // }
        // }

        return false;
    }

    boolean search() {
        boolean success = false;
        boolean flag;
        boolean last_success = false;
        // state presentstate = new state();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 3; j++) {
                if (j > 0 && i == 3)
                    continue;
                if (j == 2 && i == 4)
                    continue;
                // presentstate = states.peek();
                // System.out.println(i + "," + j + "等待搜索" + ",板子状态" +
                // states.peek().stateBlock);
                if (((states.peek().stateBlock
                        & (1 << i)) == 0) /* && (states.peek().statesingleblock_up.get(i) & (1 << j)) == 0 */) {

                    flag = addblock7(i, j, q.get(q.size() - 1).ah, false);
                    // System.out.println(i + "," + j + "+" + ",板子状态" + states.peek().stateBlock);

                    if (flag == false) {
                        // state presentstate = new state(states.peek());
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

                    else {
                        // state presentstate = new state(states.peek());
                        state presentstate;

                        try {
                            presentstate = (state) states.peek().clone();
                            success = pointsdeal();

                            presentstate.q = this.q;
                            // for (int iph = 0; iph < states.size(); iph++) {
                            // System.out.println("测试1插入第" + (iph + 1) + "个搜索状态" + ",点数" +
                            // states.get(iph).q.size());
                            // }
                            presentstate.stateBlock |= (1 << i);
                            // System.out.println("板的编码状态前" + presentstate.stateBlock);
                            presentstate.blocks = this.blocks;
                            // for (int iph = 0; iph < states.size(); iph++) {
                            // System.out.println("测试2插入第" + (iph + 1) + "个搜索状态" + ",点数" +
                            // states.get(iph).q.size());
                            // }
                            states.push(new state(presentstate));

                        } catch (CloneNotSupportedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        System.out.println("第" + i + "板" + j + "顶点" + "正面匹配"+ ",板子状态" + states.peek().stateBlock);

                        // for (int iph = 0; iph < states.size(); iph++) {
                        // System.out.println("测试0插入第" + (iph + 1) + "个搜索状态" + ",点数" +
                        // states.get(iph).q.size());
                        // System.out.println(",初始" + desq.size());
                        // }
                        // for (int iph = 0; iph < states.size(); iph++) {
                        //     System.out.println("插入第" + (iph + 1) + "个搜索状态" + ",点数" + states.get(iph).q.size());
                        //     System.out.println("我就问问你还有几个点？" + states.get(iph).q.size());
                        //     for (int jph = 0; jph < states.get(iph).q.size(); jph++) {
                        //         System.out.println("x,y,al,ah,angle,el,eh = " + states.get(iph).q.get(jph).x + ","
                        //                 + states.get(iph).q.get(jph).y + "," + states.get(iph).q.get(jph).al + ","
                        //                 + states.get(iph).q.get(jph).ah + "," + states.get(iph).q.get(jph).angle + ","
                        //                 + states.get(iph).q.get(jph).el + "," + states.get(iph).q.get(jph).eh);
                        //     }
                        // }

                        if (success) {
                            System.out.println("finish a search");
                            return true;
                        }

                        last_success = search();
                        if (!last_success)
                            System.out.println("这条路死了");
                        if (last_success == false) {
                            blocks.pop();
                            states.pop();
                            // System.out.println("pop1第" + (1 + states.size()) + "个搜索状态");
                            // states.pop();
                            // System.out.println("pop2第" + (1 + states.size()) + "个搜索状态");
                            // presentstate = states.peek();
                            
                                //presentstate = (state) states.peek().clone();
                            List<point> present = new ArrayList<point>();
                            for (int isp = 0; isp < states.peek().q.size(); isp++) {
                                try {
                                    present.add((point) states.peek().q.get(isp).clone());
                                } catch (CloneNotSupportedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                // q.add(new point(new Float(desq.get(i).x), new Float(desq.get(i).y), new
                                // Integer(desq.get(i).al), new Integer(desq.get(i).ah), new
                                // Float(desq.get(i).el), new Float(desq.get(i).eh)));
                            }

                            this.q = new ArrayList<point>(present);
                            
                            states.peek().stateBlock &= ~(1 << i);
                            // states.peek().statesingleblock_up_ban(i, j);

                            // states.push(new state(presentstate));
                            // System.out.println("pupush第" + states.size() + "个搜索状态");
                            // System.out.println("这个状态是" + states.peek().blocks.size() + "个板");
                            // System.out.println("这个状态是" + states.peek().q.size() + "还是" +
                            // presentstate.q.size() + "个点");

                            // for (int iph = 0; iph < states.size(); iph++) {
                            // System.out.println("pop之后0000000000000000000000000000000插入第" + (iph + 1) +
                            // "个搜索状态" + ",点数" + states.get(iph).q.size());

                            // }
                        } else {
                            System.out.println("finish a search");
                            return true;
                        }
                    }

                }

                if (i != 4)
                    continue;

                if (((states.peek().stateBlock
                        & (1 << i)) == 0) /* && (states.peek().statesingleblock_down.get(i) & (1 << j)) == 0 */) {
                    // System.out.println(i + "," + j + "-");
                    // for (int iph = 0; iph < states.size(); iph++) {
                    // System.out.println("本次开搜插入第" + (iph + 1) + "个搜索状态" + ",点数" +
                    // states.get(iph).q.size());
                    // }
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
                        // blocks.push(new singleBlock(7, i, true));
                        System.out.println("第" + i + "板" + j + "顶点" + "反面匹配"+ ",板子状态" + states.peek().stateBlock);

                        success = pointsdeal();

                        if(success)return true;
                        last_success = search();
                        if(last_success == false){
                            blocks.pop();
                            states.pop();
                            // presentstate = states.peek();
                            // q = presentstate.q;
                            states.peek().stateBlock &= ~(1 << i);
                            // this.q = new ArrayList<point>(presentstate.q) ;
                            
                                //presentstate = (state) states.peek().clone();

                                List<point> present = new ArrayList<point>();
                                for (int isp = 0; isp < states.peek().q.size(); isp++) {
                                    try {
                                        present.add((point) states.peek().q.get(isp).clone());
                                    } catch (CloneNotSupportedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    // q.add(new point(new Float(desq.get(i).x), new Float(desq.get(i).y), new
                                    // Integer(desq.get(i).al), new Integer(desq.get(i).ah), new
                                    // Float(desq.get(i).el), new Float(desq.get(i).eh)));
                                }

                                this.q = new ArrayList<point>(present);
                                // this.q = new ArrayList<point>();

                           
                            // states.peek().statesingleblock_up_ban(i, j);
                            // states.pop();
                            // states.push(new state(presentstate));
                        }
                        else return true;
                    }
                    
                }
            }
            
        }
        return false;
    }

}