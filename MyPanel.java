import java.awt.*;
import javax.swing.*;
import java.util.*;

//MyPanel类：更新画布
class MyPanel extends JPanel	//我自己的面板，用于绘图和实现绘图区域
{
	boolean paintflag = false;
	state stateofThisPanel;
	java.util.List<point> lp;
	Stack<singleBlock> sb;

	void setState(state s){
		lp = s.q;
		sb = s.blocks;
		paintflag = true;
	}
	
	//更新画布
	public void paint(Graphics g)
	{
		super.paint(g);		//继承父类
		if(paintflag)
		{	

			stateofThisPanel = new state(lp, sb);

			Polygon p = new Polygon();

			for (int i = 0; i < stateofThisPanel.q.size(); i++) {
				p.addPoint((int)(100 + 20 * stateofThisPanel.q.get(i).x), (int)(800 - 20 * stateofThisPanel.q.get(i).y));
			}

			setBackground(Color.white);		
			
			//待拼图形
			g.setColor(Color.black);
			g.fillPolygon(p);

			for (int i = 0; i < this.stateofThisPanel.blocks.size(); i++) {
				Polygon pp = new Polygon();
				for (int j = 0; j < stateofThisPanel.blocks.get(i).blockpoints.size(); j++) {
					pp.addPoint((int)(100 + 20 * stateofThisPanel.blocks.get(i).blockpoints.get(j).x), (int)(800 - 20 * stateofThisPanel.blocks.get(i).blockpoints.get(j).y));
				}
				
				//巧板
				g.setColor(stateofThisPanel.blocks.get(i).c);
				g.fillPolygon(pp);
			}
		}
	}
}
