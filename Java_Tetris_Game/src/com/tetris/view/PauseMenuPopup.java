package com.tetris.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import com.tetris.util.ScreenUtil;

//테트리스 중지했을 때의 프레임 띄워주는 클래스
@SuppressWarnings("serial")
public class PauseMenuPopup extends JDialog {
	TetrisView tetrisView;
	JButton resume;
	JButton mainMenu;

	public PauseMenuPopup(TetrisView tetrisView) {
		pauseFrameSetting();
		pauseButton(tetrisView);
		pauseButtonEvents();
	}

//	테트리스 윈도우 프레임
	private void pauseFrameSetting() {
		setUndecorated(true);
		setModal(true);
		setLayout(null);
		setSize(200, 60);
		setLocation(ScreenUtil.getCenterPosition(this));
	}

//	프레임에 내 버튼
	private void pauseButton(TetrisView tetrisView) {
		this.tetrisView = tetrisView;
		
		resume = new JButton("R E S U M E");
		mainMenu = new JButton("M A I N M E N U");
		
		resume.setBounds(0, 0, 200, 30);
		mainMenu.setBounds(0, 30, 200, 30);
		
		add(resume);
		add(mainMenu);
	}

//	버튼 이벤트
	private void pauseButtonEvents() {
		resume.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
			
		});
		
		mainMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				tetrisView.end();
				
				new StartMenuPopup().setVisible(true);
			}
			
		});
	}
}