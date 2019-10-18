import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//MainView类：主窗口
public class MainView extends JFrame {

    state stateofThisView = new state();

    JButton AutoStartBtn;

    JButton ManualStartBtn;

    JComboBox RoundBox;

    JComboBox ModeBox;

    JComboBox AutoTimeCombo;

    MyPanel westPanel;
    
    public MainView() throws IOException {

        //窗口大小设置，不可调整，位置不固定
		this.setTitle("巧板");
		this.setSize(1200, 840);
		this.setResizable(false);
        this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// type
		JPanel panelType = new JPanel();
		JLabel TypeLabel = new JLabel("巧板类型选择:");
        JComboBox TypeComboBox = new JComboBox();
        TypeComboBox.addItem("7");

        //待扩展
        // TypeComboBox.addItem("13");
        // TypeComboBox.addItem("14");
        // TypeComboBox.addItem("15");
        // TypeComboBox.addItem("16");
		panelType.setLayout(new GridLayout(1, 2));
		panelType.add(TypeLabel);
		panelType.add(TypeComboBox);

        // choose imglib or input
        JPanel panelMode = new JPanel();
        panelMode.setLayout(new GridLayout(1, 2));
        JLabel ModeLabel = new JLabel("选择基础模式：");
        ModeBox = new JComboBox();
        ModeBox.addItem("图库模式");
        ModeBox.addItem("自定义图像");
        panelMode.add(ModeLabel);
        panelMode.add(ModeBox);
        panelMode.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));

 		// choose   round
		JPanel panelRound = new JPanel();
		panelRound.setLayout(new GridLayout(1, 2));
		JLabel RoundLabel = new JLabel("选择关卡：");
        RoundBox = new JComboBox();
        RoundBox.addItem("1");RoundBox.addItem("2");RoundBox.addItem("3");RoundBox.addItem("4");RoundBox.addItem("5");
        RoundBox.addItem("6");RoundBox.addItem("7");RoundBox.addItem("8");RoundBox.addItem("9");RoundBox.addItem("10");
        RoundBox.addItem("11");RoundBox.addItem("12");RoundBox.addItem("13");RoundBox.addItem("14");RoundBox.addItem("15");
        RoundBox.addItem("16");RoundBox.addItem("17");RoundBox.addItem("18");RoundBox.addItem("19");RoundBox.addItem("20");
        RoundBox.addItem("21");RoundBox.addItem("22");RoundBox.addItem("23");RoundBox.addItem("24");RoundBox.addItem("25");
        RoundBox.addItem("26");RoundBox.addItem("27");
		panelRound.add(RoundLabel);
        panelRound.add(RoundBox);
        panelRound.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));

        //扩展接口：扩展14+巧板用
		// JPanel panelbasicselect = new JPanel();
		// panelbasicselect.setLayout(new BoxLayout(panelbasicselect, BoxLayout.Y_AXIS));
		// panelbasicselect.add(panelType); 
		// panelbasicselect.add(panelRound);

		// 自动拼接
		JPanel panelAuto = new JPanel();
		panelAuto.setLayout(new BoxLayout(panelAuto, BoxLayout.Y_AXIS));
        AutoStartBtn = new JButton("开始");
        JLabel AutoTimeLabel = new JLabel("选择模式");
        AutoTimeCombo = new JComboBox();
        AutoTimeCombo.addItem("步进");
        AutoTimeCombo.addItem("直接成图");
        JPanel panelAutoTime = new JPanel();
        panelAutoTime.setLayout(new GridLayout(1, 2));
        panelAutoTime.add(AutoTimeLabel);
        panelAutoTime.add(AutoTimeCombo);
        panelAutoTime.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));
        panelAuto.add(panelAutoTime);
        panelAuto.add(AutoStartBtn);       
        panelAuto.setBorder(BorderFactory.createTitledBorder("自动拼接"));        

        // manual 待扩展：手动拼图
        // JPanel panelManual = new JPanel();
        // panelManual.setLayout(new BoxLayout(panelManual, BoxLayout.Y_AXIS));
        // ManualStartBtn = new JButton("自定义图像");
        // ManualStartBtn.setEnabled(false);
        // JLabel manualrateLabel_00 = new JLabel("覆盖率:");
        // JLabel manualrateLabel_01 = new JLabel("0.00");
        // JLabel manualrateLabel_10 = new JLabel("利用率:");
        // JLabel manualrateLabel_11 = new JLabel("0.00");
        // JPanel panelMaunalResult = new JPanel();
        // panelMaunalResult.setLayout(new GridLayout(2, 2));
        // panelMaunalResult.add(manualrateLabel_00);
        // panelMaunalResult.add(manualrateLabel_01);
        // panelMaunalResult.add(manualrateLabel_10);
        // panelMaunalResult.add(manualrateLabel_11);
        // panelManual.add(ManualStartBtn);
        // panelManual.add(panelMaunalResult);
        // panelManual.setBorder(BorderFactory.createTitledBorder("自定义"));

        // mode select
        JPanel  panelmodeSelect = new JPanel();
        panelmodeSelect.setLayout(new GridLayout(2,1));
        panelmodeSelect.add(panelAuto);

        // panel north
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, 1));
        northPanel.add(panelMode);
        northPanel.add(panelRound);
        northPanel.add(panelmodeSelect);
        northPanel.add(new JLabel("1.图像以左下为原点，右+x.下+y"));
        northPanel.add(new JLabel("2.自定义图像顺时针输入各角点"));
        northPanel.add(new JLabel("  如图可输入  0,0;0,14.14;14.14,14.14;14.14,0"));
        northPanel.add(new JLabel("3.各板编号（0~6）如下"));

        //panel south
        JPanel southPanel = new JPanel();
        ImageIcon BlockIcon = new ImageIcon("img/b07.png");
        BlockIcon.setImage(BlockIcon.getImage().getScaledInstance(270, 270, 0));
        JLabel BlockLabel=new JLabel(BlockIcon);
        southPanel.add(BlockLabel);

        // panel west
        westPanel = new MyPanel();
        westPanel.setVisible(true);
        Image destImage = new BufferedImage(720, 800, BufferedImage.TYPE_INT_RGB);
        Graphics g = destImage.getGraphics();
        g.fillRect(0,0,720,800);        
        ImageIcon destIcon = new ImageIcon(destImage);
        destIcon.setImage(destIcon.getImage().getScaledInstance(720, 800, 0));
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

    public void setState(state s){
        this.stateofThisView = s;
        westPanel.setState(this.stateofThisView);
    }

    public int getIndex(){
        return RoundBox.getSelectedIndex();
    }
}
