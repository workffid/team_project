package com.tetris.constant;

import java.awt.Color;

//�궗�슜�븷 �긽�닔�뱾 紐⑥븘 �넃�� �겢�옒�뒪
public class Constant {
	public static final int MAX_SPEED_LEVEL = 20;
	public static final int MIN_SPEED_LEVEL = 1;
	
	public enum GameStatus {
		PLAYING, END, PAUSE
	}
	
	public enum ProcessType { 
		DIRECTION, DIRECT_DOWN, AUTO
	}
	
	public enum BoardType {
		EMPTY, MOVING_BLOCK, FIXED_BLOCK,
		LEFT_WALL, RIGHT_WALL, BOTTOM_WALL,
		TOP_WALL, LEFT_TOP_EDGE, RIGHT_TOP_EDGE,
		LEFT_BOTTOM_EDGE, RIGHT_BOTTOM_EDGE
	}
	
	public interface Direction {
		static final int SIZE = 4;
		static final int UP = 0;
		static final int RIGHT = 1;
		static final int DOWN = 2;
		static final int LEFT = 3;
	}
	
	public interface keyCode {
		static final int UP = 38;
		static final int LEFT = 37;
		static final int RIGHT = 39;
		static final int DOWN = 40;
		static final int SPACE_BAR = 32;
		static final int ESC = 27;
	}
	
	public interface MainMenu {
		static final int START = 1;
		//static final int RANKING = 2;
		//static final int SETTING = 3;
		static final int EXIT = 4;
	}
	
	public interface PauseMenu {
		static final int RESUME = 1;
		static final int MAIN_MENU = 2;
	}
	
	public interface EndMenu {
		//static final int RANKING = 1;
		static final int MAIN_MENU = 2;
		static final int EXIT = 3;
	}
	
	public static final Color[] COLORS = {
		Color.RED,
		Color.ORANGE,
		Color.YELLOW,
		Color.GREEN,
		Color.BLUE,
		Color.decode("#4B0082"),
		Color.decode("#800080")
	};
}