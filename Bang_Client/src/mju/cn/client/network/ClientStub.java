package mju.cn.client.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import mju.cn.common.Constant;

// Controller 부모 클래스
public class ClientStub extends Thread {

	protected Socket m_Socket;
	protected ObjectOutputStream outputStream;
	protected ObjectInputStream inputStream;
	protected boolean m_isConnected = true;

	public ClientStub(String ip) {

		try {
			m_Socket = new Socket(ip, Constant.SERVER_PORT);
			outputStream = new ObjectOutputStream(m_Socket.getOutputStream());
			inputStream = new ObjectInputStream(m_Socket.getInputStream());
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "서버가 실행되지 않았습니다.");
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "서버가 실행되지 않았습니다.");
			System.exit(0);
			e.printStackTrace();
		}
	}

	public void send(Object obj) {
		try {
			outputStream.writeObject(obj);
			outputStream.flush();
		} catch (Exception e) {
	//		e.printStackTrace();
		}
	}

}
