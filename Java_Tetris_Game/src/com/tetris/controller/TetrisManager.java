package com.tetris.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import com.tetris.block.Block;
import com.tetris.constant.Constant;
import com.tetris.constant.Constant.BoardType;
import com.tetris.constant.Constant.GameStatus;

//�뀒�듃由ъ뒪 援ъ꽦�븯�뒗 �겢�옒�뒪
public class TetrisManager {
	static final int POSITIONS_SIZE = 4;
	static final int BOARD_ROW_SIZE = 24;
	static final int BOARD_COL_SIZE = 14;
	static final int INITIAL_SPEED = 300;
	static final int SPEED_LEVEL_OFFSET = 40;
	static final int LEVEL_UP_CONDITION = 3;
	static final int LINES_TO_DELETE_HIGHLIGHTING_MILLISECOND = 10;
		
	Constant.BoardType boardType[][];
	Block block;
	int deletedLineCount;
	public static int speedLevel;
	int colorIndex = 0;
	
	public TetrisManager (int speedLevel) {
		boardType = new Constant.BoardType[BOARD_ROW_SIZE][BOARD_COL_SIZE];
		
//		BOARD_ROW_SIZE 諛곗뿴�쓣 諛섎났臾몄쓣 �룎�젮 mBoard[i]瑜� BoardType.EMPTY濡� 珥덇린�솕 �떆�궡
//		Arrays.fill(Object obj, param) : obj 諛곗뿴�쓣 param�쑝濡� �쟾泥� 珥덇린�솕�븯�뒗 硫붿냼�뱶
		for (int i = 0; i < BOARD_ROW_SIZE; i++) {
			Arrays.fill(boardType[i], BoardType.EMPTY);
		}
		
				
		clearBoard();
		
		block = new Block(null);
		deletedLineCount = 0;
		
		this.speedLevel = speedLevel;
		
		System.out.println("console> TestrisManager speedLevel: " + speedLevel);
	}
	
	public Constant.BoardType checkValidPosition (int direction) {
		System.out.println("TetrisManager checkValidPosition direction : " + direction);
		
		Block temp = new Block();
		
		temp.copyOf(block);
		temp.move(direction);
		
		for (int i = 0; i < POSITIONS_SIZE; i++) {
			int x = temp.getPositions()[temp.getDirection()][i].x;
			int y = temp.getPositions()[temp.getDirection()][i].y;

			System.out.println("TetrisManager checkValidPosition x : " + x + ", y : " + y);
			
			if (x <= 0) {
				return Constant.BoardType.TOP_WALL;
			}

			if (!(boardType[x][y] == Constant.BoardType.EMPTY || boardType[x][y] == Constant.BoardType.MOVING_BLOCK)) {
				return boardType[x][y];
			}
		}
		
		return Constant.BoardType.EMPTY;
	}
	
	public void changeBoardByDirection (int direction) {
		System.out.println("TetrisManager changeBoardByDirection direction : " + direction);
		
		int tempDirection = Constant.Direction.DOWN;
	
		Constant.BoardType tempCheckResult = Constant.BoardType.EMPTY;
	
		System.out.println("TetrisManager changeBoardByDirection tempDirection : " + tempDirection);
		System.out.println("TetrisManager changeBoardByDirection tempCheckResult : " + tempCheckResult);
		
		clearBoard();
	
		Constant.BoardType checkResult = checkValidPosition(direction);
		
		System.out.println("TetrisManager changeBoardByDirection checkResult : " + checkResult);
	
		if (checkResult == Constant.BoardType.EMPTY) {
			block.move(direction);
		} else {
			if (direction == Constant.Direction.UP && checkResult != Constant.BoardType.FIXED_BLOCK) {
				if (checkResult == Constant.BoardType.TOP_WALL) {
					tempDirection = Constant.Direction.DOWN;
					tempCheckResult = Constant.BoardType.TOP_WALL;
				} else if (checkResult == Constant.BoardType.RIGHT_WALL) {
					tempDirection = Constant.Direction.LEFT;
					tempCheckResult = Constant.BoardType.RIGHT_WALL;
				} else if (checkResult == Constant.BoardType.LEFT_WALL) {
					tempDirection = Constant.Direction.RIGHT;
					tempCheckResult = Constant.BoardType.LEFT_WALL;
				}
			
				do {
					block.move(tempDirection);
				} while (checkValidPosition(direction) == tempCheckResult);
					block.move(direction);
			}
		}
	
		changeBoardByStatus(Constant.BoardType.MOVING_BLOCK);
	}
	
	public void changeBoardByAuto () {
		changeBoardByDirection(Constant.Direction.DOWN);
	}

	public void processDirectDown () {
		while (!isReachedToBottom()) {
			changeBoardByDirection(Constant.Direction.DOWN);
		}
	}
	
	public void processDeletingLines (Graphics g) {
		Color highlightingColors[] = { Color.GRAY, Color.WHITE };
		ArrayList<Integer> indexes = new ArrayList<Integer>();
	
		System.out.println("console> TetrisManager processDeletingLines highlightingColors : " + highlightingColors);
		System.out.println("console> TetrisManager processDeletingLines indexes : " + indexes);
		
		searchLineIndexesToDelete(indexes);
		
		if (indexes.size() > 0) {
			Timer timer = new Timer(
				LINES_TO_DELETE_HIGHLIGHTING_MILLISECOND, new ActionListener() {
						
					@Override
					public void actionPerformed(ActionEvent e) {
						highlightLinesToDelete(g, highlightingColors[colorIndex], indexes);
						colorIndex = 1 - colorIndex;
							
						System.out.println("console> TetrisManager processDeletingLines colorIndex : " + colorIndex);
					}
					
				});
		
			timer.start();
		
			try {
				Thread.sleep(LINES_TO_DELETE_HIGHLIGHTING_MILLISECOND * 40);
			} catch (InterruptedException e1) { }
		
			timer.stop();
			deleteLines(indexes);
		
			for (int i = speedLevel; i <= deletedLineCount / LEVEL_UP_CONDITION; i++) {
				upSpeedLevel();
			}
		}
	}
	
	public boolean isReachedToBottom() {
		System.out.println("console> TetrisManager isReachedToBottom POSITIONS_SIZE : " + POSITIONS_SIZE);
		
		boolean b = false;
		
		for (int i = 0; i < POSITIONS_SIZE; i++) {
			int x = block.getPositions()[block.getDirection()][i].x;
			int y = block.getPositions()[block.getDirection()][i].y;
			
			System.out.println("console> TetrisManager isReachedToBottom x : " + x + ", y : " + y);
			
			if (boardType[x + 1][y] != Constant.BoardType.EMPTY &&
					boardType[x + 1][y] != Constant.BoardType.MOVING_BLOCK) {
				b = true;
			}
		}
		
		return b;
	}
	
	public Constant.GameStatus processReachedCase() {
		changeBoardByStatus(Constant.BoardType.FIXED_BLOCK);
		
		block = new Block(block);
		
		if (isReachedToBottom()) {
			return GameStatus.END;
		} else {
			return GameStatus.PLAYING;
		}
	}

	public void sleep() {
		try {
			Thread.sleep(getDownMilliSecond());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
//	�삤瑜몄そ�뿉 �젏�닔 / �떎�쓬 �뀒�듃由ъ뒪 釉붾윮 紐⑥뼇 異쒕젰
	public void print(Graphics graphics) {
		int x;
		int y = 60;
		
		
		
		for (int i = 0; i < BOARD_ROW_SIZE; i++) {
			x = 30;
			
			for (int j = 0; j < BOARD_COL_SIZE; j++) {
				switch (boardType[i][j]) {
					case LEFT_TOP_EDGE :
					case RIGHT_TOP_EDGE :
					case LEFT_BOTTOM_EDGE :
					case RIGHT_BOTTOM_EDGE :
					case LEFT_WALL :
					case RIGHT_WALL :
					case TOP_WALL :
					case BOTTOM_WALL :
						graphics.fill3DRect(x, y, 25, 25, true);
						
						break;
					case EMPTY :
						break;
					case MOVING_BLOCK :
						graphics.setColor(Constant.COLORS[block.getColor()]);
						graphics.fill3DRect(x, y, 25, 25, true);
						graphics.setColor(Color.BLACK);
					
						break;
					case FIXED_BLOCK :
						graphics.setColor(Color.GRAY);
						graphics.fill3DRect(x, y, 25, 25, true);
						graphics.setColor(Color.BLACK);
						
						break;
				}
				
				x += 25;
			}
			
			y += 25;
		}
	
		x = 460;
		y = 150;
		
		Font font = graphics.getFont();
	
		graphics.setFont(new Font(font.getName(), Font.BOLD, 20));
		graphics.drawString("[" + speedLevel + " level / " + deletedLineCount + " lines]", x, y);
	
		y += 80;
	
		graphics.drawString("[Key Description]", x, y);
	
		y += 30;
	
		graphics.drawString("←", x, y);
	
		x = 560;
	
		graphics.drawString(": move left", x, y);
	
		x = 460;
		y += 30;
	
		graphics.drawString("→", x, y);
	
		x = 560;
	
		graphics.drawString(": move right", x, y);
	
		x = 460;
		y += 30;
	
		graphics.drawString("↓", x, y);
		
		x = 560;
	
		graphics.drawString(": move down", x, y);
	
		x = 460;
		y += 30;
	
		graphics.drawString("↑", x, y);
	
		x = 560;
	
		graphics.drawString(": rotate", x, y);
	
		x = 460;
		y += 30;
	
		graphics.drawString("SpaceBar", x, y);
	
		x = 560;
	
		graphics.drawString(": direct down", x, y);
		
		x = 460;
		y += 30;
		
		graphics.drawString("ESC", x, y);
	
		x = 560;
	
		graphics.drawString(": pause", x, y);
	
		x = 460;
		
		block.printNext(graphics, x, y + 80);
	}

	public void setBoard(Constant.BoardType[][] board) {
		this.boardType = board;
	}

	public Constant.BoardType[][] getBoard() {
		return boardType;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public Block getBlock() {
		return block;
	}

	public void setDeletedLineCount(int deletedLineCount) {
		this.deletedLineCount = deletedLineCount;
	}

	public int getDeletedLineCount() {
		return deletedLineCount;
	}

	public void setSpeedLevel(int speedLevel) {
		this.speedLevel = speedLevel;
	}

	public int getSpeedLevel() {
		return speedLevel;
	}

//	釉붾윮 �뼥�뼱吏��뒗 �냽�룄 議곗젅
	public long getDownMilliSecond() {
		long milliSecond = INITIAL_SPEED;
		
		System.out.println("console> TetrisManager getDownMilliSecond milliSecond : " + milliSecond);
		
		for (int i = Constant.MIN_SPEED_LEVEL; i < speedLevel; i++) {
			if (i < Constant.MAX_SPEED_LEVEL / 2) {
				milliSecond -= SPEED_LEVEL_OFFSET;
			} else {
				milliSecond -= (SPEED_LEVEL_OFFSET / 5);
			}
		}
		
		System.out.println("console> TetrisManager getDownMilliSecond final milliSecond : " + milliSecond);
		
		return milliSecond;
	}
	
	private void clearBoard() {
		for (int i = 0; i < BOARD_ROW_SIZE; i++) {
			boardType[i][0] = Constant.BoardType.LEFT_WALL;
			boardType[i][BOARD_COL_SIZE - 1] = Constant.BoardType.RIGHT_WALL;
			
			System.out.println("TetrisManage clearBoard boardType[" + i + "][0] = " + boardType[i][0]);
			System.out.println("TetrisManage clearBoard boardType[" + i + "][" 
									+ (BOARD_COL_SIZE - 1) + "] = " + boardType[i][BOARD_COL_SIZE - 1]);
		}
		
		for (int i = 0; i < BOARD_COL_SIZE; i++) {
			boardType[0][i] = Constant.BoardType.TOP_WALL;
			boardType[BOARD_ROW_SIZE - 1][i] = Constant.BoardType.BOTTOM_WALL;
			
			System.out.println("TetrisManage clearBoard boardType[0][" + i + "] = " + boardType[0][i]);
			System.out.println("TetrisManage clearBoard boardType[" + (BOARD_COL_SIZE - 1) + "][" 
					+ i + "] = " + boardType[BOARD_ROW_SIZE - 1][i]);
		}
		
		for (int i = 1; i < BOARD_ROW_SIZE - 1; i++) {
			for (int j = 1; j < BOARD_COL_SIZE - 1; j++) {
				if (boardType[i][j] != Constant.BoardType.FIXED_BLOCK) {
					boardType[i][j] = Constant.BoardType.EMPTY;
				}
			}
		}
		
		boardType[0][0] = Constant.BoardType.LEFT_TOP_EDGE;
		boardType[0][BOARD_COL_SIZE - 1] = Constant.BoardType.RIGHT_TOP_EDGE;
		boardType[BOARD_ROW_SIZE - 1][0] = Constant.BoardType.LEFT_BOTTOM_EDGE;
		boardType[BOARD_ROW_SIZE - 1][BOARD_COL_SIZE - 1] = Constant.BoardType.RIGHT_BOTTOM_EDGE;
	}
	
//	釉붾윮 紐⑥뼇 諛붽퓭吏� �긽�깭
	private void changeBoardByStatus (Constant.BoardType status) {
		System.out.println("console> TestrisManager changeBoardStatus status : " + status);
		
		for (int i = 0; i < POSITIONS_SIZE; i++) {
			int x = block.getPositions()[block.getDirection()][i].x;
			int y = block.getPositions()[block.getDirection()][i].y;
			
			boardType[x][y] = status;
		}
	}

//	釉붾윮 �뼥�뼱吏��뒗 �냽�룄 利앷��떆�궡
	private void upSpeedLevel() {
		if (speedLevel < Constant.MAX_SPEED_LEVEL) {
			speedLevel++;
		}
	}

//	�븘�옒�뿉 �뙎�씤 釉붾윮�뱾 以� 吏��썙吏� 釉붾윮�뱾�쓣 寃��깋�븯�뒗 硫붿냼�뱶
	private void searchLineIndexesToDelete (ArrayList<Integer> indexes) {
//		ArrayList clear() : ArrayList �븞�쓽 紐⑤뱺 媛믪씠 �궘�젣�릺�뒗 硫붿냼�뱶
		indexes.clear();
	
		System.out.println("console> TetrisManager searchLineIndexesToDelete BOARD_ROW_SIZE : " + BOARD_ROW_SIZE);
		
		for (int i = 1; i < BOARD_ROW_SIZE - 1; i++) {
			boolean toDelete = true;
			
			for (int j = 1; j < BOARD_COL_SIZE - 1; j++) {
				if (boardType[i][j] != Constant.BoardType.FIXED_BLOCK) {
					toDelete = false;
				
					break;
				}
			}
			
			if (toDelete) {
				indexes.add(i);
			}
		}
	}

//	�븘�옒�뿉 �뙎�씤 釉붾윮 吏��슦�뒗 硫붿냼�뱶
	private void deleteLines (ArrayList<Integer> indexes) {
		System.out.println("console> TetrisManager deleteLines indexes : " + indexes);
		
		int k = BOARD_ROW_SIZE - 2;
		Constant.BoardType[][] temp = new Constant.BoardType[BOARD_ROW_SIZE][BOARD_COL_SIZE];
		
		System.out.println("console> TetrisManager deleteLines k : " + k);
		System.out.println("console> TetrisManager deleteLines temp : " + temp);
		
		for (int i = 0; i < BOARD_ROW_SIZE; i++) {
//			Arrays.fill(Objecct obj, param) : obj 諛곗뿴�쓣 param�씠�씪�뒗 蹂��닔濡� 移섑솚�븯�뒗 硫붿냼�뱶 
			Arrays.fill(temp[i], Constant.BoardType.EMPTY);
		}
		
		for (int i = BOARD_ROW_SIZE - 2; i > 0; i--) {
			boolean toDelete = false;
			
			for (int j = 0; j < indexes.size(); j++) {
				if (i == indexes.get(j)) {
					toDelete = true;
					
					break;
				}
			}
			
			if (!toDelete) {
				for (int j = 0; j < BOARD_COL_SIZE; j++) {
					temp[k][j] = boardType[i][j];
				}
				
				k--;
			}
		}
		
		for (int i = 1; i < BOARD_ROW_SIZE - 1; i++) {
			for (int j = 1; j < BOARD_COL_SIZE - 1; j++) {
				boardType[i][j] = temp[i][j];
			}
		}
	
		deletedLineCount += indexes.size();
		
		System.out.println("console> TetrisManager deleteLines deletedLineCount : " + deletedLineCount);
	}

//	�뙎�씤 釉붾윮�뱾 吏��썙吏� �븣 �굹�뒗 洹몃옒�뵿 �슚怨� 二쇰뒗 硫붿냼�뱶
	private void highlightLinesToDelete (Graphics g, Color color, ArrayList<Integer> indexes) {
		System.out.println("console> TetrisManager highlightLinesToDelete color : " + color);
		System.out.println("console> TetrisManager highlightLinesToDelete indexes : " + indexes);
		
		g.setColor(color);
		
		System.out.println("console> TetrisManager highlightLinesToDelete getColor : " + g.getColor());
		
		int x = 55;
		int y = 60 + indexes.get(0) * 25;
		
		System.out.println("console> TetrisManager highlightLinesToDelete x : " + x + " + , y : " + y);
		
		g.fill3DRect(x, y, 25 * (BOARD_COL_SIZE - 2), 25 * indexes.size(), true);
	}

}