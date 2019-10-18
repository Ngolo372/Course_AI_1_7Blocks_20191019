import java.io.IOException;
import java.util.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.KeyStore.PrivateKeyEntry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javafx.event.ActionEvent;

import javax.imageio.ImageIO;

public class main_prj {

	public static void main(String[] args) throws IOException {
		// Single mode

		MainView mainView = new MainView();

		Vector<getDesti> des = new Vector<getDesti>();
		for (int i = 0; i < 26; i++) {
			des.add(new getDesti(String.valueOf(i + 1)));
		}

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				mainView.westPanel.repaint();
			}
		}, 100, 500);

		Timer timer2 = new Timer();

		mainView.AutoStartBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// TODO Auto-generated method stub
				mainView.AutoStartBtn.setEnabled(false);

				boolean continuous = mainView.AutoTimeCombo.getSelectedIndex() == 0 ? false : true;

				List<point> points = des.get(mainView.getIndex()).q;

				Stack<singleBlock> Blocks = new Stack<singleBlock>();

				state mainstate = new state(points, Blocks);

				mainView.setState(mainstate);

				Object[] options ={ "是", "否" };
				int permission = JOptionPane.showOptionDialog(null, "图片编号 No." + (mainView.getIndex() + 1), 
				null,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				mainView.westPanel.revalidate();

				if(permission == 0)
				{	points_calculate points_calculate_7 = new points_calculate(points, Blocks);

					boolean success_flag = points_calculate_7.search(continuous);

					JOptionPane.showMessageDialog(null, (success_flag == true ? "成功拼接!" : "拼不出来"), null,JOptionPane.PLAIN_MESSAGE);}

				mainView.AutoStartBtn.setEnabled(true);

			}
        });
			
		mainView.setSize(1024, 768);
		mainView.setResizable(false);
		mainView.setLocationRelativeTo(null); 

	}
    
}
