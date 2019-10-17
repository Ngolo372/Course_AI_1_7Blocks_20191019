import java.awt.*;
import javax.swing.*;
import java.util.*;

class MyPanel extends JPanel	//我自己的面板，用于绘图和实现绘图区域
{
	state stateofThisPanel;
	java.util.List<point> lp;
	
	//java.util.List <singleBlock> sb;
	Stack<singleBlock> sb;

	void setState(state s){
		
		// System.out.println( "x,y = " + 20 * q.get(i).x + "," + 20 * q.get(i).y);
		// System.out.println("初始变量总共几个角点" + s.q.size());
		lp = s.q;
		// System.out.println("初始变量总共几个板" + s.blocks.size());
		sb = s.blocks;
	}
	
	public void paint(Graphics g)
	{
		
		super.paint(g);		//继承父类

		stateofThisPanel = new state(lp, sb);
		// System.out.println("中间变量总共几个板" + sb.size());

		Polygon p = new Polygon();

		// System.out.println("剩几个点" + stateofThisPanel.q.size());

		for (int i = 0; i < stateofThisPanel.q.size(); i++) {
			p.addPoint((int)(100 + 20 * stateofThisPanel.q.get(i).x), (int)(100 + 20 * stateofThisPanel.q.get(i).y));
		}

		// System.out.println("更新画布");

		setBackground(Color.white);		
		
		//待拼图形
		g.setColor(Color.black);
		g.fillPolygon(p);

		//画7巧板
		// System.out.println("总共几个板" + this.stateofThisPanel.blocks.size());

		//g.fillRect(0, 0, 50, 50);

		for (int i = 0; i < this.stateofThisPanel.blocks.size(); i++) {
			Polygon pp = new Polygon();

			// System.out.println("总共几个板顶点" + this.stateofThisPanel.blocks.get(i).blockpoints.size());

			for (int j = 0; j < stateofThisPanel.blocks.get(i).blockpoints.size(); j++) {
				// System.out.println("x,y=" + (int)(20 * stateofThisPanel.blocks.get(i).blockpoints.get(j).x) + "," + (int)(20 * stateofThisPanel.blocks.get(i).blockpoints.get(j).y));
				pp.addPoint((int)(100 + 20 * stateofThisPanel.blocks.get(i).blockpoints.get(j).x), (int)(100 + 20 * stateofThisPanel.blocks.get(i).blockpoints.get(j).y));
			}
			
			//待拼图形
			g.setColor(stateofThisPanel.blocks.get(i).c);
			g.fillPolygon(pp);
		}

	}
}
