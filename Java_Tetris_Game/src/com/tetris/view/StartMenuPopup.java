package com.tetris.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

import com.tetris.util.ScreenUtil;

//泥섏쓬 �뀒�듃由ъ뒪 �봽�젅�엫 �쓣�썙二쇰뒗 �겢�옒�뒪

@SuppressWarnings("serial")
public class StartMenuPopup extends JDialog {

	JButton start;
	//JButton ranking;
	//JButton setting;
	JButton exit;
	public static ImageIcon icon;
	
	public StartMenuPopup() {
		startFrameSetting();
		startButton();
		startButtonEvents();
		icon = new ImageIcon("src/image/image11.png");
		
	}
	
	

//	�뀒�듃由ъ뒪 �쐢�룄�슦 �봽�젅�엫
	private void startFrameSetting() {
		setTitle("TETRIS MAIN MENU");
		setModal(true);
		setLayout(null);
		setSize(500, 400);
		setLocation(ScreenUtil.getCenterPosition(this));
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
//	�봽�젅�엫�뿉 �궡 踰꾪듉
	private void startButton() {
		
		start = new JButton("S T A R T");
		exit = new JButton("E X I T");
		//ranking = new JButton("R A N K I N G");
		//setting = new JButton("S E T T I N G");
		
		
		start.setBounds(150, 200, 200, 30);
		//ranking.setBounds(150, 230, 200, 30);
		//setting.setBounds(150, 230, 200, 30);
		exit.setBounds(150, 230, 200, 30);
		
		add(start);
		//add(ranking);
		//add(setting);
		add(exit);
	}

//	踰꾪듉 �씠踰ㅽ듃
	private void startButtonEvents() {
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
				new TetrisView().setVisible(true);
			}
			
		});	
		
		/*ranking.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				start.requestFocus();
			}
			
		});
		
		
		setting.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				start.requestFocus();
			}
			
		});
		*/
		
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
			
		});
	}
	
//	�봽�젅�엫 �궡�뿉 TETRIS 臾멸뎄 �엯�� �궡蹂대깂
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		g.drawImage(icon.getImage(), 0, 0, null); // 0,0좌표부터 이미지를 뿌림
		setOpaque(false); // 그림을 표시하게 설정,투명하게 조절
		
		
		//T
		g.setColor(Color.RED);
		g.fill3DRect(40, 70, 20, 20, true);
		g.fill3DRect(60, 70, 20, 20, true);
		g.fill3DRect(80, 70, 20, 20, true);
		g.fill3DRect(60, 90, 20, 20, true);
		g.fill3DRect(60, 110, 20, 20, true);
		g.fill3DRect(60, 130, 20, 20, true);
		g.fill3DRect(60, 150, 20, 20, true);

		//E
		g.setColor(Color.ORANGE);
		g.fill3DRect(120, 70, 20, 20, true);
		g.fill3DRect(140, 70, 20, 20, true);
		g.fill3DRect(160, 70, 20, 20, true);
		g.fill3DRect(120, 90, 20, 20, true);
		g.fill3DRect(120, 110, 20, 20, true);
		g.fill3DRect(140, 110, 20, 20, true);
		g.fill3DRect(160, 110, 20, 20, true);
		g.fill3DRect(120, 130, 20, 20, true);
		g.fill3DRect(120, 150, 20, 20, true);
		g.fill3DRect(140, 150, 20, 20, true);
		g.fill3DRect(160, 150, 20, 20, true);

		//T
		g.setColor(Color.YELLOW);
		g.fill3DRect(200, 70, 20, 20, true);
		g.fill3DRect(220, 70, 20, 20, true);
		g.fill3DRect(240, 70, 20, 20, true);
		g.fill3DRect(220, 90, 20, 20, true);
		g.fill3DRect(220, 110, 20, 20, true);
		g.fill3DRect(220, 130, 20, 20, true);
		g.fill3DRect(220, 150, 20, 20, true);

		//R
		g.setColor(Color.GREEN);
		g.fill3DRect(280, 70, 20, 20, true);
		g.fill3DRect(300, 70, 20, 20, true);
		g.fill3DRect(280, 90, 20, 20, true);
		g.fill3DRect(310, 90, 20, 20, true);
		g.fill3DRect(280, 110, 20, 20, true);
		g.fill3DRect(300, 110, 20, 20, true);
		g.fill3DRect(280, 130, 20, 20, true);
		g.fill3DRect(310, 130, 20, 20, true);
		g.fill3DRect(280, 150, 20, 20, true);
		g.fill3DRect(320, 150, 20, 20, true);

		//I
		g.setColor(Color.BLUE);
		g.fill3DRect(360, 70, 20, 20, true);
		g.fill3DRect(360, 90, 20, 20, true);
		g.fill3DRect(360, 110, 20, 20, true);
		g.fill3DRect(360, 130, 20, 20, true);
		g.fill3DRect(360, 150, 20, 20, true);

		//S
		g.setColor(Color.PINK);
		g.fill3DRect(410, 70, 20, 20, true);
		g.fill3DRect(430, 70, 20, 20, true);
		g.fill3DRect(400, 90, 20, 20, true);
		g.fill3DRect(400, 110, 20, 20, true);
		g.fill3DRect(420, 110, 20, 20, true);
		g.fill3DRect(440, 110, 20, 20, true);
		g.fill3DRect(440, 130, 20, 20, true);
		g.fill3DRect(430, 150, 20, 20, true);
		g.fill3DRect(410, 150, 20, 20, true);
		
	}



private void setOpaque(boolean b) {
	// TODO Auto-generated method stub
	
}
}