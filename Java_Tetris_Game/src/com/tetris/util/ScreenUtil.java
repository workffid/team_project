package com.tetris.util;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;

//해당 화면 사이즈를 구해 팝업을 모니터 중앙으로 위치하게 하는 클래스
public class ScreenUtil {
	public static Point getCenterPosition (Window window) {
//		Toolkit 클래스의 메소드를 호출하기 위해서는
//		우선 getDefaultToolkit() 메소드로 Toolkit 인스턴스 생성해야 함
		Dimension wholeScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension currentWindowSize = window.getSize();
		
		System.out.println("console> ScreenUtil wholeScreenSize : " + wholeScreenSize);
		System.out.println("console> ScreenUtil currentWindowSize : " + currentWindowSize);
		
		int left = (wholeScreenSize.width / 2) - (currentWindowSize.width / 2);
		int top = (wholeScreenSize.height / 2) - (currentWindowSize.height / 2);
		
		System.out.println("console> ScreenUtil left : " + left + " , top : " + top);
		
		return new Point(left, top);
	}
}