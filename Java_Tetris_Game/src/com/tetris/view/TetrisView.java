package com.tetris.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.tetris.constant.Constant;
import com.tetris.controller.TetrisManager;
import com.tetris.util.ScreenUtil;

//�뀒�듃由ъ뒪 援щ룞�릺�뒗 �봽�젅�엫
@SuppressWarnings("serial")
public class TetrisView extends JFrame {
	final Object object = new Object();
	
	static final int REACHED_COUNT = 2;
	
	TetrisManager tetrisManager;
	Constant.ProcessType processType;
	Constant.GameStatus gameStatus;
	int direction;
	int processReachedCaseCount;
	boolean isKeyPressed;
	long timeMilliSecond;
	
	
	public TetrisView () {
		tetrisFrameSetting();
		tetrisButton();
		tetrisButtonEvents();
	}
	
	private void tetrisFrameSetting() {
		setTitle("TETRIS - ING...");
		getContentPane().setLayout(null);
		setSize(800, 700);
		setLayout(null);
		setLocation(ScreenUtil.getCenterPosition(this));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
	}
	
	private void tetrisButton() {
		
//		TetrisManager �겢�옒�뒪瑜� 媛��옣 �궙�� �뒪�뵾�뱶濡� �떎�뻾�떆�궡
		tetrisManager = new TetrisManager(Constant.MIN_SPEED_LEVEL);
		
//		�뒪�젅�뱶 �깮�꽦
		new Thread(new Runnable() {
			@Override
			public void run() {
//				start() : �뒪�젅�뱶 �깮�꽦
//						  run() 硫붿냼�뱶媛� �샇異쒕맖 => 硫붿씤硫붿냼�뱶濡� �쉶洹�
//						  run()�뿉�꽌 �떎�뻾�맂 �뒪�젅�뱶 / start()�뿉 �쓽�븳 �뒪�젅�뱶 珥� 2媛쒓� �릺�뒗 寃껋엫
				start();
				
				gameStatus = Constant.GameStatus.PLAYING;
				isKeyPressed = false;
				timeMilliSecond = System.currentTimeMillis();
			
//				寃뚯엫�씠 �걹�궃 寃쎌슦
				while (gameStatus != Constant.GameStatus.END) {
//					寃뚯엫 �긽�깭媛� �씪�떆以묒��씤 寃쎌슦
					if (gameStatus == Constant.GameStatus.PAUSE) {
//						synchronized : �뒪�젅�뱶 �룞湲고솕(�빐�떦 �옉�뾽�씠 �걹�궇 �븣源뚯��뒗 �� �뒪�젅�뱶媛� �궗�슜�븷 �닔 �뾾�룄濡�)
//						1. �듅�젙 媛앹껜 lock : synchronized(媛앹껜�쓽 李몄“蹂��닔) { }
//						2. 硫붿냼�뱶 lock : public synchronized void method() { }
						synchronized (object) {
							try {
//								�빐�떦 �뒪�젅�뱶媛� �걹�궇 �븣源뚯� 媛앹껜瑜� ��湲곗떆�궡
								object.wait();
							} catch (InterruptedException e) { }
						}
					}
					
					processType = Constant.ProcessType.AUTO;
					direction = Constant.Direction.DOWN;
				
					while (true) {
//						mIsKeyPressed�뒗 default媛� false�엫
//						true�씪 寃쎌슦 false濡� 蹂��솚
						if (isKeyPressed) {
							isKeyPressed = false;
							
							break;
						}
						
//						mIsKeyPressed媛� true媛� �븘�땲怨� �쁽�옱�떆媛� - �꽕�젙�떆媛�(�쁽�옱�떆媛꾩쑝濡� �쐞�뿉�꽌 �꽕�젙�뻽�쓬)�씠 �꽕�젙�떆媛꾨낫�떎 �겙 寃쎌슦
						if (!isKeyPressed && System.currentTimeMillis() - timeMilliSecond > getDownMilliSecond()) {
//							�꽕�젙 珥덇린�솕
							processType = Constant.ProcessType.AUTO;
							direction = Constant.Direction.DOWN;
							timeMilliSecond = System.currentTimeMillis();
							
							break;
						}
						
						try {
//							sleep() : �룞湲고솕�맂 �떎以� �뒪�젅�뱶瑜� �떆媛� �떒�쐞濡� 以묒��떆�궎�뒗 硫붿냼�뱶
//									  �뙆�씪誘명꽣濡� 泥쒕텇�쓽 1珥덈�� 諛쏆쓬
//							(Object瑜� 以묒��떆�궎�뒗 硫붿냼�뱶 : wait() / 諛섎났�떆 �떆媛꾩쓣 �넻�빐 以묒� : sleep())
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					process(processType, direction);
				}
			}
		}).start();
	}
	
//	�궎蹂대뱶瑜� �닃���쓣 �븣 諛쒖깮�븯�뒗 �씠踰ㅽ듃
/* <Key 愿��젴 �씠踰ㅽ듃>
 * 1. keyPressed : key �늻瑜대뒗 �닚媛� 諛쒖깮
 * 2. keyTyped : �늻瑜� key瑜� �뼹�뒗 �닚媛� 諛쒖깮(�쑀�땲肄붾뱶 �궎�쓽 寃쎌슦 異붽� �씠踰ㅽ듃 諛쒖깮�븷 �닔 �엳�쓬)
 * 3. keyReleased : �늻瑜� key瑜� �뼹�뒗 �닚媛�
 *  - �떎�뻾 �닚�꽌 : keyPressed -> keyTyped -> keyReleased
 */
	private void tetrisButtonEvents() {
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) { }

			@Override
			public void keyReleased(KeyEvent e) { }

//			�궎 �늻瑜대뒗 �닚媛� 諛쒖깮�븯�뒗 �씠踰ㅽ듃
			@Override
			public void keyPressed(KeyEvent e) {
//				�쐞
				if (e.getKeyCode() == Constant.keyCode.UP) {
					isKeyPressed = true;
					processType = Constant.ProcessType.DIRECTION;
					direction = Constant.Direction.UP;
//				�븘�옒
				} else if (e.getKeyCode() == Constant.keyCode.DOWN) {
					isKeyPressed = true;
					processType = Constant.ProcessType.DIRECTION;
					direction = Constant.Direction.DOWN;
					timeMilliSecond = System.currentTimeMillis();
//				�쇊履�
				} else if (e.getKeyCode() == Constant.keyCode.LEFT) {
					isKeyPressed = true;
					processType = Constant.ProcessType.DIRECTION;
					direction = Constant.Direction.LEFT;
//				�삤瑜몄そ
				} else if (e.getKeyCode() == Constant.keyCode.RIGHT) {
					isKeyPressed = true;
					processType = Constant.ProcessType.DIRECTION;
					direction = Constant.Direction.RIGHT;
//				�뒪�럹�씠�뒪諛�
				} else if (e.getKeyCode() == Constant.keyCode.SPACE_BAR) {
					isKeyPressed = true;
					processType = Constant.ProcessType.DIRECT_DOWN;
					timeMilliSecond = System.currentTimeMillis();
//				ESC
				} else if (e.getKeyCode() == Constant.keyCode.ESC) {
					isKeyPressed = true;
					processType = Constant.ProcessType.AUTO;
					timeMilliSecond = System.currentTimeMillis();
					
					pause();
				}
			}
		});
		
//		�봽�젅�엫 李쎌쓣 �걣 �븣 諛쒖깮�븯�뒗 �씠踰ㅽ듃
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				end();
			}
			
		});
	}
	
	public void start() {
//		repaint() : �빐�떦 而댄룷�꼳�듃�쓽 paint() 硫붿냼�뱶瑜� �샇異쒗븳 �뮘 �깉濡쒓퀬移� �븯�뒗 硫붿냼�뱶
		repaint();
	}
	
	public void process(Constant.ProcessType processType, int direction) {
		System.out.println("console> TetrisView process processType : " + processType +
								", direction : " + direction);
		
		if (processType == Constant.ProcessType.DIRECTION) {
			tetrisManager.changeBoardByDirection(direction);
		} else if (processType == Constant.ProcessType.DIRECT_DOWN) {
			tetrisManager.processDirectDown();
		} else if (processType == Constant.ProcessType.AUTO) {
			tetrisManager.changeBoardByAuto();
		}
		
		
		if (tetrisManager.isReachedToBottom()) {
			if (processType == Constant.ProcessType.DIRECT_DOWN) {
				System.out.println("console> TetriwView isReachedToBottom DIRECT_DOWN");
				
				processReachedCaseCount = 0;
				
				if (tetrisManager.processReachedCase() == Constant.GameStatus.END) {
					end();
					
					new EndMenuPopup().setVisible(true);
					
					return;
				}
			} else {
				if (processReachedCaseCount == REACHED_COUNT) { 
					System.out.println("console> TetriwView isReachedToBottom PROCESS_REACHED_CASE_COUNT");
					
					if (tetrisManager.processReachedCase() == Constant.GameStatus.END) {
						end();
					
						new EndMenuPopup().setVisible(true);
						
						return;
					}
					
					processReachedCaseCount = 0;
					
				} else {
					processReachedCaseCount++;
				}
			}
		}
		
//		repaint() : �빐�떦 而댄룷�꼳�듃�쓽 paint() 硫붿냼�뱶瑜� �샇異쒗븳 �뮘 �깉濡쒓퀬移� �븯�뒗 硫붿냼�뱶
//		paint() : repaint() 硫붿냼�뱶�뿉 �쓽�빐 �샇異쒕릺怨� �뀒�듃由ъ뒪 釉붾윮 �뼥�뼱吏��뒗 �� 援ъ꽦�븯�뒗 硫붿냼�뱶
		repaint();
		
		tetrisManager.processDeletingLines(getGraphics());
	}
	
//	�걹�궓
	public void end() {
		gameStatus = Constant.GameStatus.END;
		
		System.out.println("console> TetrisView end gameStatus : " + gameStatus);
		
		dispose();
	}
	
//	�씪�떆�젙吏�
	public void pause() {
		gameStatus = Constant.GameStatus.PAUSE;
		
		System.out.println("console> TetrisView pause gameStatus : " + gameStatus);
		
		new PauseMenuPopup(this).setVisible(true);
		
//		synchronized �궎�썙�뱶瑜� �씠�슜�븯�뿬 硫��떚 �뒪�젅�뱶媛� �룞�옉�븯�뒗 �룞�븞 �빐�떦 媛앹껜瑜� �룞湲고솕�떆�궡
//		(�쁽�옱 �뀒�듃由ъ뒪 寃뚯엫�씠 �룞�옉�븯吏� �븡怨� �엳湲� �븣臾몄뿉 main硫붿냼�뱶�뒗 �룎�븘媛�吏�留� �뀒�듃由ъ뒪�뒗 �룎�븘媛�吏� �븡�쓬)
//		=> �뀒�듃由ъ뒪 �룞湲고솕
		synchronized (object) {
			object.notify();
		}
		
		if (gameStatus != Constant.GameStatus.END) {

			gameStatus = Constant.GameStatus.PLAYING;
		}
	}
	
//	�봽�젅�엫�뿉 洹몃옒�뵿 �엯�� 異쒕젰(�뀒�듃由ъ뒪 寃뚯엫 寃��젙 ��)
	@Override
	public void paint(Graphics g) {
		Image buffer = createImage(getWidth(), getHeight());
		Graphics graphics = buffer.getGraphics();
		
		graphics.setColor(Color.black);
		
		
		
		System.out.println("console> TetrisView paint getColor : " + graphics.getColor());
		
		Font font = graphics.getFont();
		
		graphics.setFont(new Font(font.getName(), Font.BOLD, 30));
		tetrisManager.print(graphics);
		g.drawImage(buffer, 0, 0, this);
		
	}
	
	



	public long getDownMilliSecond() {
		return tetrisManager.getDownMilliSecond();
	}
}