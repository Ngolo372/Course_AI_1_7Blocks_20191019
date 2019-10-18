import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.KeyStore.PrivateKeyEntry;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.imageio.ImageIO;

public class MainView extends JFrame {

    state stateofThisView = new state();

    JButton AutoStartBtn;

    JButton selectBtn;

    JComboBox RoundBox;

    MyPanel westPanel;
    
    public MainView() throws IOException {

		this.setTitle("巧板");
		this.setSize(600, 600);
		this.setLocation(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// type
		JPanel panelType = new JPanel();
		JLabel TypeLabel = new JLabel("巧板类型选择:");
        JComboBox TypeComboBox = new JComboBox();
        TypeComboBox.addItem("7");
        // TypeComboBox.addItem("13");
        // TypeComboBox.addItem("14");
        // TypeComboBox.addItem("15");
        // TypeComboBox.addItem("16");
		panelType.setLayout(new GridLayout(1, 2));
		panelType.add(TypeLabel);
		panelType.add(TypeComboBox);

		// round
		JPanel panelRound = new JPanel();
		panelRound.setLayout(new GridLayout(1, 3));
		JLabel RoundLabel = new JLabel("选择关卡：");;
        RoundBox = new JComboBox();
        RoundBox.addItem("1");RoundBox.addItem("2");RoundBox.addItem("3");RoundBox.addItem("4");RoundBox.addItem("5");
        RoundBox.addItem("6");RoundBox.addItem("7");RoundBox.addItem("8");RoundBox.addItem("9");RoundBox.addItem("10");
        RoundBox.addItem("11");RoundBox.addItem("12");RoundBox.addItem("13");RoundBox.addItem("14");RoundBox.addItem("15");
        RoundBox.addItem("16");RoundBox.addItem("17");RoundBox.addItem("18");RoundBox.addItem("19");RoundBox.addItem("20");
        RoundBox.addItem("21");RoundBox.addItem("22");RoundBox.addItem("23");RoundBox.addItem("24");RoundBox.addItem("25");
        RoundBox.addItem("26");
        selectBtn = new JButton("选定");
		panelRound.add(RoundLabel);
        panelRound.add(RoundBox);
        // panelRound.add(selectBtn);

		// choose type and round
		JPanel panelbasicselect = new JPanel();
		panelbasicselect.setLayout(new BoxLayout(panelbasicselect, BoxLayout.Y_AXIS));
		panelbasicselect.add(panelType);
		panelbasicselect.add(panelRound);

		// auto
		JPanel panelAuto = new JPanel();
		panelAuto.setLayout(new BoxLayout(panelAuto, BoxLayout.Y_AXIS));
        AutoStartBtn = new JButton("开始");
        // AutoStartBtn.setEnabled(false);
        JLabel AutoTimeLabel = new JLabel("选择步长");
        JComboBox AutoTimeCombo = new JComboBox();
        AutoTimeCombo.addItem("1");
        AutoTimeCombo.addItem("2");
        JPanel panelAutoTime = new JPanel();
        panelAutoTime.setLayout(new GridLayout(1, 2));
        panelAutoTime.add(AutoTimeLabel);
        panelAutoTime.add(AutoTimeCombo);
        panelAuto.add(panelAutoTime);
        panelAuto.add(AutoStartBtn);
        panelAuto.setBorder(BorderFactory.createTitledBorder("自动模式"));

        // manual
        JPanel panelManual = new JPanel();
        panelManual.setLayout(new BoxLayout(panelManual, BoxLayout.Y_AXIS));
        JButton ManualStartBtn = new JButton("开始拼接");
        JLabel manualrateLabel_00 = new JLabel("覆盖率:");
        JLabel manualrateLabel_01 = new JLabel("0.00");
        JLabel manualrateLabel_10 = new JLabel("利用率:");
        JLabel manualrateLabel_11 = new JLabel("0.00");
        JPanel panelMaunalResult = new JPanel();
        panelMaunalResult.setLayout(new GridLayout(2, 2));
        panelMaunalResult.add(manualrateLabel_00);
        panelMaunalResult.add(manualrateLabel_01);
        panelMaunalResult.add(manualrateLabel_10);
        panelMaunalResult.add(manualrateLabel_11);
        panelManual.add(ManualStartBtn);
        panelManual.add(panelMaunalResult);
        panelManual.setBorder(BorderFactory.createTitledBorder("手动模式"));

        // mode select
        JPanel  panelmodeSelect = new JPanel();
        panelmodeSelect.setLayout(new GridLayout(2,1));
        panelmodeSelect.add(panelAuto);
        panelmodeSelect.add(panelManual);


        // panel north
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(3, 1));
        northPanel.add(panelbasicselect);
        northPanel.add(panelmodeSelect);

        //panel south
        JPanel southPanel = new JPanel();
        ImageIcon BlockIcon = new ImageIcon("img/b07.jpg");
        BlockIcon.setImage(BlockIcon.getImage().getScaledInstance(200, 200, 0));
        JLabel BlockLabel=new JLabel(BlockIcon);
        southPanel.add(BlockLabel);

        // panel west
        westPanel = new MyPanel();
        //westPanel = new JPanel();
        westPanel.setVisible(true);
        // westPanel.setState(this.stateofThisView);
        // Image srcImage = ImageIO.read(new File("lib1/1.bmp"));
        Image destImage = new BufferedImage(720, 720, BufferedImage.TYPE_INT_RGB);
        Graphics g = destImage.getGraphics();
        g.fillRect(0,0,720,720);        
        ImageIcon destIcon = new ImageIcon(destImage);
        destIcon.setImage(destIcon.getImage().getScaledInstance(720, 720, 0));
        JLabel DestiLabel=new JLabel(destIcon);
        westPanel.add(DestiLabel);

        // panel east
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BorderLayout());
        eastPanel.add(northPanel, BorderLayout.NORTH);
        eastPanel.add(southPanel, BorderLayout.SOUTH);
        eastPanel.setVisible(true);

        // panel whole
        this.setLayout(new BorderLayout());
        this.add(westPanel, BorderLayout.WEST);
        this.add(eastPanel, BorderLayout.EAST);
		this.setVisible(true);
	}

	// public static void main(String[] args) {
	// 	// Single mode
	// 	MainView mainView = new MainView();
	// }

    public void setState(state s){
        this.stateofThisView = s;
        westPanel.setState(this.stateofThisView);
    }

    public int getIndex(){
        return RoundBox.getSelectedIndex();
    }
}
