﻿package mju.cn.client.main;

import java.awt.AWTException;


import mju.cn.client.gui.MainFrame;

public class ClientMain {
	public static void main(String dd[]) throws AWTException {
		MainFrame frame = new MainFrame();
		frame.init("192.168.38.184");
	}
}