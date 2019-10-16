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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javafx.event.ActionEvent;

import javax.imageio.ImageIO;

public class main_prj {

	public static void main(String[] args) throws IOException {
		// Single mode

		getDesti des1 = new getDesti(0);
		List<point> points = des1.q;
		Stack<singleBlock> Blocks = new Stack<singleBlock>();

		state mainstate = new state(points, Blocks);

		MainView mainView = new MainView(mainstate);

		mainView.AutoStartBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// TODO Auto-generated method stub
				points_calculate points_calculate_7 = new points_calculate(points, Blocks);

				// for (int i = 0; i < points_calculate_7.q.size(); i++) {
				// 	System.out.println( "x,y = " + points_calculate_7.q.get(i).x + "," + points_calculate_7.q.get(i).y);
				// }

				points_calculate_7.search();
			}
        });
			
		
		
		mainView.setSize(1024, 768);
		mainView.setResizable(false);
		mainView.setLocationRelativeTo(null); 

	}
    
}
