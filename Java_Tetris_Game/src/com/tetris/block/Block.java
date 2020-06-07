package com.tetris.block;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import com.tetris.constant.Constant;

//블럭 관련 클래스
public class Block {
	static final int BLOCK_EXAMPLES_SIZE = 7;
	static final int POSITIONS_SIZE = 4;
//	블럭 모양 뽑아내는 포인트 데이터 타입 배열
	static final Point BLOCK_EXAMPLES[][][] = {
		{
			{ new Point(0, 5), new Point(0, 6), new Point(0, 7), new Point(0, 8) },
			{ new Point(-1, 6), new Point(0, 6), new Point(1, 6), new Point(2, 6) },
			{ new Point(0, 5), new Point(0, 6), new Point(0, 7), new Point(0, 8) },
			{ new Point(-1, 6), new Point(0, 6), new Point(1, 6), new Point(2, 6) }
		},
		
		{
			{ new Point(0, 8), new Point(1, 6), new Point(1, 7), new Point(1, 8) },
			{ new Point(-1, 7), new Point(0, 7), new Point(1, 7), new Point(1, 8) },
			{ new Point(0, 6), new Point(0, 7), new Point(0, 8), new Point(1, 6) },
			{ new Point(-1, 6), new Point(-1, 7), new Point(0, 7), new Point(1, 7) }
		},
		
		{
			{ new Point(0, 7), new Point(0, 8), new Point(1, 6), new Point(1, 7) },
			{ new Point(-1, 6), new Point(0, 6), new Point(0, 7), new Point(1, 7) },
			{ new Point(0, 7), new Point(0, 8), new Point(1, 6), new Point(1, 7) },
			{ new Point(-1, 6), new Point(0, 6), new Point(0, 7), new Point(1, 7) } },
		{
			{ new Point(0, 6), new Point(0, 7), new Point(1, 7), new Point(1, 8) },
			{ new Point(-1, 8), new Point(0, 8), new Point(0, 7), new Point(1, 7) },
			{ new Point(0, 6), new Point(0, 7), new Point(1, 7), new Point(1, 8) },
			{ new Point(-1, 8), new Point(0, 8), new Point(0, 7), new Point(1, 7) }
		},
		
		{
			{ new Point(0, 7), new Point(1, 6), new Point(1, 7),  new Point(1, 8) },
			{ new Point(-1, 7), new Point(0, 7), new Point(0, 8), new Point(1, 7) },
			{ new Point(0, 6), new Point(0, 7), new Point(0, 8), new Point(1, 7) },
			{ new Point(-1, 7), new Point(0, 6), new Point(0, 7), new Point(1, 7) }
		},
		
		{
			{ new Point(0, 6), new Point(1, 6), new Point(1, 7), new Point(1, 8) },
			{ new Point(-1, 8), new Point(-1, 7), new Point(0, 7), new Point(1, 7) },
			{ new Point(0, 6), new Point(0, 7), new Point(0, 8), new Point(1, 8) },
			{ new Point(-1, 7), new Point(0, 7), new Point(1, 7), new Point(1, 6) } },
		{
			{ new Point(0, 6), new Point(0, 7), new Point(1, 6), new Point(1, 7) },
			{ new Point(0, 6), new Point(0, 7), new Point(1, 6), new Point(1, 7) },
			{ new Point(0, 6), new Point(0, 7), new Point(1, 6), new Point(1, 7) },
			{ new Point(0, 6), new Point(0, 7), new Point(1, 6), new Point(1, 7) }
		}
	};
	
	Point positions[][];
	int current;
	int next;
	int direction;
	int color;

//	블럭 상태 초기화
	public Block () {
		positions = new Point[POSITIONS_SIZE][POSITIONS_SIZE];
		current = 0;
		next = 0;
		direction = Constant.Direction.UP;
		color = 0;
	}

	public Block (Block block) {
		System.out.println("console> Block constructor block : " + block);
		
//		최초의 블럭일 경우 랜덤으로 사이즈를 뽑아옴
		if (block == null) {
			current = new Random().nextInt(BLOCK_EXAMPLES_SIZE);
//		이전에 나왔던 블럭이 있을 경우 다음꺼 가져옴
		} else {
			current = block.getNext();
		}
		
		positions = new Point[POSITIONS_SIZE][POSITIONS_SIZE];
		
		System.out.println("console> Block constructor positions : " + positions);
		
		for (int i = 0; i < POSITIONS_SIZE; i++) {
			for (int j = 0; j < POSITIONS_SIZE; j++) {
				positions[i][j] = new Point(BLOCK_EXAMPLES[current][i][j]);
			}
		}
		
		next = new Random().nextInt(BLOCK_EXAMPLES_SIZE);
		direction = Constant.Direction.UP;
		color = new Random().nextInt(Constant.COLORS.length);
		
		System.out.println("console> Block constructor current : " + current);
		System.out.println("console> Block constructor next : " + next);
		System.out.println("console> Block constructor direction : " + direction);
	}

	public void copyOf (Block src) {
		System.out.println("console> Block copyOf src : " + src);
		
		for (int i = 0; i < POSITIONS_SIZE; i++) {
			for (int j = 0; j < POSITIONS_SIZE; j++) {
				positions[i][j] = new Point(src.getPositions()[i][j]);
			}
		}
		
		current = src.getCurrent();
		next = src.getNext();
		direction = src.getDirection();
		
		System.out.println("console> Block copyOf current : " + current);
		System.out.println("console> Block copyOf next : " + next);
		System.out.println("console> Block copyOf direction : " + direction);
	}

	public void move (int direction) {
		switch (direction) {
			case Constant.Direction.LEFT :
				moveToLeft();
				
				break;
			case Constant.Direction.RIGHT :
				moveToRight();
				
				break;
			case Constant.Direction.DOWN :
				moveToDown();
				
				break;
			case Constant.Direction.UP :
				rotateRight();
				
				break;
		}
	}

//	블럭 종류가 6개이기 때문에 경우에 따라 Graphics를 이용하여 출력
	public void printNext (Graphics graphics, int x, int y) {
		System.out.println("console> Bloack printNext x : " + x + ", y : " + y);
		
		graphics.drawString("[Next block]", x, y);
		
		y += 30;
		
		graphics.setColor(Color.LIGHT_GRAY);
		
		switch (next) {
			case 0 :
				graphics.fill3DRect(x, y, 20, 20, true);
				graphics.fill3DRect(x + 20, y, 20, 20, true);
				graphics.fill3DRect(x + 40, y, 20, 20, true);
				graphics.fill3DRect(x + 60, y, 20, 20, true);
				
				break;
			case 1 :
				graphics.fill3DRect(x + 40, y, 20, 20, true);
				graphics.fill3DRect(x, y + 20, 20, 20, true);
				graphics.fill3DRect(x + 20, y + 20, 20, 20, true);
				graphics.fill3DRect(x + 40, y + 20, 20, 20, true);
				
				break;
			case 2 :
				graphics.fill3DRect(x + 20, y, 20, 20, true);
				graphics.fill3DRect(x + 40, y, 20, 20, true);
				graphics.fill3DRect(x, y + 20, 20, 20, true);
				graphics.fill3DRect(x + 20, y + 20, 20, 20, true);
				
				break;
			case 3 :
				graphics.fill3DRect(x, y, 20, 20, true);
				graphics.fill3DRect(x + 20, y, 20, 20, true);
				graphics.fill3DRect(x + 20, y + 20, 20, 20, true);
				graphics.fill3DRect(x + 40, y + 20, 20, 20, true);
				
				break;
			case 4 :
				graphics.fill3DRect(x + 20, y, 20, 20, true);
				graphics.fill3DRect(x, y + 20, 20, 20, true);
				graphics.fill3DRect(x + 20, y + 20, 20, 20, true);
				graphics.fill3DRect(x + 40, y + 20, 20, 20, true);
				
				break;
			case 5 :
				graphics.fill3DRect(x, y, 20, 20, true);
				graphics.fill3DRect(x, y + 20, 20, 20, true);
				graphics.fill3DRect(x + 20, y + 20, 20, 20, true);
				graphics.fill3DRect(x + 40, y + 20, 20, 20, true);
				
				break;
			case 6 :
				graphics.fill3DRect(x, y, 20, 20, true);
				graphics.fill3DRect(x + 20, y, 20, 20, true);
				graphics.fill3DRect(x, y + 20, 20, 20, true);
				graphics.fill3DRect(x + 20, y + 20, 20, 20, true);
				
				break;
		}
		
		graphics.setColor(Color.BLACK);
	}

	public void setPositions (Point[][] positions) {	
		this.positions = positions;
	}

	public Point[][] getPositions () {	
		return positions;
	}

	public void setCurrent (int current) {
		this.current = current;
	}

	public int getCurrent () {
		return current;
	}

	public void setNext (int next) {
		this.next = next;
	}

	public int getNext () {
		return next;
	}

	public void setDirection (int direction) {
		this.direction = direction;
	}

	public int getDirection () {
		return direction;
	}

	public void setColor (int color) {
		this.color = color;
	}

	public int getColor () {
		return color;
	}

//	좌표의 x, y를 이용하여 블럭 움직임 조절
	private void moveToDown () {
		System.out.println("console> Bloack is moveToDown ...");
		
		for (int i = 0; i < POSITIONS_SIZE; i++) {
			for (int j = 0; j < POSITIONS_SIZE; j++) {
				positions[i][j].x++;
			}
		}
	}

	private void moveToLeft () {
		System.out.println("console> Bloack is moveToLeft ...");
		
		for (int i = 0; i < POSITIONS_SIZE; i++) {
			for (int j = 0; j < POSITIONS_SIZE; j++) {
				positions[i][j].y--;
			}
		}
	}

	private void moveToRight () {
		System.out.println("console> Bloack is moveToRight ...");
		
		for (int i = 0; i < POSITIONS_SIZE; i++) {
			for (int j = 0; j < POSITIONS_SIZE; j++) {
				positions[i][j].y++;
			}
		}
	}

	private void rotateRight () {
		System.out.println("console> Bloack is rotateRight ...");
		
		direction = (direction + 1) % Constant.Direction.SIZE;
		
		System.out.println("console> Bloack rotateRight direction : " + direction);
	}
}