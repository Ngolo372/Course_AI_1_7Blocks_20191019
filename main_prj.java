import java.io.IOException;
import java.util.*;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class main_prj {

	public static void main(String[] args) throws IOException {

	//主窗口
		MainView mainView = new MainView();

	//读取图库
		Vector<getDesti> des = new Vector<getDesti>();
		for (int i = 0; i < 27; i++) {
			des.add(new getDesti(String.valueOf(i + 1)));
		}

	//画布定时刷新
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				mainView.westPanel.repaint();
			}
		}, 100, 500);

	//按下“开始”按键：开始拼接
		mainView.AutoStartBtn.addActionListener(new ActionListener() {

			//事件监听器
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				//拼接过程中禁用开始键，保证稳定
				mainView.AutoStartBtn.setEnabled(false);

				boolean continuous = mainView.AutoTimeCombo.getSelectedIndex() == 0 ? false : true;

				boolean dealSafe = true;

				List<point> points = new ArrayList<point>();

				//读入对应编号图像
				if(mainView.ModeBox.getSelectedIndex() == 0){
					points = des.get(mainView.getIndex()).q;
				}

				if(mainView.ModeBox.getSelectedIndex() == 1){
					if(mainView.AutoStartBtn.getText() == "开始拼接"){
						dealSafe = true;
						mainView.AutoStartBtn.setText("自定义图像");
					}

					else if(mainView.AutoStartBtn.getText() == "自定义图像"){
						String pointsString = JOptionPane.showInputDialog("请输入点列,格式为：x1,y1;x2,y2;x3,y3;……xn,yn");

						// 取消输入
						if(pointsString == null){
							dealSafe = false;
						}
						else{
							dealSafe = false;
							strToPts stp = new strToPts(pointsString);
							stp.trans();
							if(stp.ok == false){
								JOptionPane.showMessageDialog(null, "自定义图片格式错误", null,JOptionPane.PLAIN_MESSAGE);
							}
							if(stp.ok == true){
								points = stp.q;
								dealSafe = true;
							}

						}
					}
				}

				Stack<singleBlock> Blocks = new Stack<singleBlock>();

				if(dealSafe == true)
					{state mainstate = new state(points, Blocks);

					mainView.setState(mainstate);

					Object[] options ={ "开始拼接", "暂不拼接" };

					//待用户确认permission: ==0开始拼接   ==1取消操作
					int permission = JOptionPane.showOptionDialog(null,
					mainView.ModeBox.getSelectedIndex() == 0 ? "图片编号 No." + (mainView.getIndex() + 1) : "自定义图片成功", 
					null,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

					mainView.westPanel.revalidate();

					//拼接过程
					if(permission == 0)
					{	points_calculate points_calculate_7 = new points_calculate(points, Blocks);

						boolean success_flag = points_calculate_7.search(continuous);

						JOptionPane.showMessageDialog(null, (success_flag == true ? "成功拼接!" : "拼不出来"), null,JOptionPane.PLAIN_MESSAGE);
					}
				}
					mainView.AutoStartBtn.setEnabled(true);

			}
        });

		// 游戏模式检测：图库 or 自定义
		mainView.ModeBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(mainView.ModeBox.getSelectedIndex() == 0){
					mainView.AutoStartBtn.setEnabled(true);
					mainView.AutoStartBtn.setText("开始");
				}
				if(mainView.ModeBox.getSelectedIndex() == 1){
					mainView.AutoStartBtn.setText("自定义图像");
				}
			}
		});

	}
    
}
