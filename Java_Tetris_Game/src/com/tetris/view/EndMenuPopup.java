package com.tetris.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import com.tetris.controller.TetrisManager;
import com.tetris.util.ScreenUtil;

//�뀒�듃由ъ뒪 留덉튇 �썑�쓽 �봽�젅�엫 �쓣�썙二쇰뒗 �겢�옒�뒪
@SuppressWarnings("serial")
public class EndMenuPopup extends JDialog {
	//JButton ranking;
	JLabel point;
	JButton mainMenu;
	JButton exit;
	public static ImageIcon icon1;

	public EndMenuPopup() {
		endFrameSetting();
		endButton();
		endButtonEvents();
		icon1 = new ImageIcon("bin/image/background11.png");
		
	}

//	�뀒�듃由ъ뒪 �쐢�룄�슦 �봽�젅�엫
	private void endFrameSetting() {
		setTitle("TETRIS END MENU");
		setModal(true);
		setLayout(null);
		setSize(500, 400);
		setLocation(ScreenUtil.getCenterPosition(this));
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

//	�봽�젅�엫�뿉 �궡 踰꾪듉
	private void endButton() {
		//ranking = new JButton("R A N K I N G");
		mainMenu = new JButton("M A I N M E N U");
		exit = new JButton("E X I T"); 
		point = new JLabel("점수 : " + (Integer.toString(TetrisManager.speedLevel)));
		
		
		
		point.setFont(new Font("굴림", Font.BOLD, 22));
		point.setForeground(Color.RED);
		
		
		
		//ranking.setBounds(150, 220, 200, 30);
		point.setBounds(200,200,200,30);
		mainMenu.setBounds(150, 250, 200, 30);
		exit.setBounds(150, 280, 200, 30);
		
		//add(ranking);
		add(point);
		add(mainMenu);
		add(exit);
	}

//	踰꾪듉 �씠踰ㅽ듃	
	private void endButtonEvents() {
		/*ranking.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) { }
		
		});
		*/
		
		mainMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				dispose() : �빐�떦 李쎈쭔�쓣 醫낅즺�떆�궎�뒗 硫붿냼�뱶(�떎瑜� �쐢�룄�슦 �떎�뻾 李쎈뱾�씠 醫낅즺�릺吏� �븡�룄濡�)
				dispose();
				
				new StartMenuPopup().setVisible(true);
			}
		});
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

//	�봽�젅�엫 �궡�뿉 T_T 臾멸뎄 �엯�� �궡蹂대깂
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		g.drawImage(icon1.getImage(), 0, 0, null); // 0,0좌표부터 이미지를 뿌림
		setOpaque(false); // 그림을 표시하게 설정,투명하게 조절
		
		
		 
		// T
		g.setColor(Color.RED);
		g.fill3DRect(145, 90, 20, 20, true);
		g.fill3DRect(165, 90, 20, 20, true);
		g.fill3DRect(185, 90, 20, 20, true);
		g.fill3DRect(165, 110, 20, 20, true);
		g.fill3DRect(165, 130, 20, 20, true);
		g.fill3DRect(165, 150, 20, 20, true);
		g.fill3DRect(165, 170, 20, 20, true);

		// _
		g.setColor(Color.RED);
		g.fill3DRect(225, 170, 20, 20, true);
		g.fill3DRect(245, 170, 20, 20, true);
		g.fill3DRect(265, 170, 20, 20, true);

		// T
		g.setColor(Color.RED);
		g.fill3DRect(305, 90, 20, 20, true);
		g.fill3DRect(325, 90, 20, 20, true);
		g.fill3DRect(345, 90, 20, 20, true);
		g.fill3DRect(325, 110, 20, 20, true);
		g.fill3DRect(325, 130, 20, 20, true);
		g.fill3DRect(325, 150, 20, 20, true);
		g.fill3DRect(325, 170, 20, 20, true);
	}
	private void setOpaque(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
}