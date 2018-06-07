package mju.cn.client.controller;

import mju.cn.client.gui.ContentPane;
import mju.cn.client.gui.panel.LoginPanel;
import mju.cn.client.network.ClientStub;
import mju.cn.common.RequestPacket;
import mju.cn.common.ResponsePacket;
import mju.cn.common.RequestPacket.SYNC_TYPE;

public class LoginController extends ClientStub {
	// Components
	LoginPanel m_loginPanel; // 로그인패널
	String id;
	String pw;
	boolean isRelogin = false;

	// Constructor
	public LoginController(String ip, LoginPanel loginPanel) {
		super(ip);
		m_loginPanel = loginPanel;
	}
	
	public void reLogin(){
		isRelogin = true;
		login(id,pw);
		ContentPane parent = m_loginPanel.getContentPane();
		parent.getLobbyPanel().getPlayerListController().getList();
		parent.getLobbyPanel().getRoomListController().getList();
	}

	// 로그인 함수
	public void login(String id, String pw) {
		this.id = id; this.pw = pw;
		m_loginPanel.getContentPane().showLoadingDialog("로그인 중 입니다.");
		RequestPacket packet = new RequestPacket();
		packet.setClassName("SSLoginController");
		packet.setMethodName("login");
		packet.setSyncType(SYNC_TYPE.SYNCHRONOUS);
		packet.setArgs(new Object[] { id, pw });
		this.send(packet);
	}

	// 서버측 login 결과 처리 함수
	private void resultLogin(ResponsePacket packet) {
		boolean success = (Boolean) packet.getArgs()[0];
		ContentPane parent = m_loginPanel.getContentPane();
		if (success) {

			parent.getLobbyPanel().initManager((String) packet.getArgs()[1]);
			parent.getLobbyPanel().initUserInfo((String) packet.getArgs()[1],
					(String) packet.getArgs()[2], (String) packet.getArgs()[3],
					(Integer) packet.getArgs()[4]);
			parent.viewPanel(parent.getLobbyPanel().getClass().getName());
			parent.hideDialog();
			parent.getLobbyPanel().getPlayerListController().getList();
			parent.getLobbyPanel().getRoomListController().getList();
			if(!isRelogin){
			m_loginPanel.getContentPane().showMessageDialog("로그인에 성공 하였습니다.");
			}
	
		} else {
			m_loginPanel.getContentPane().hideDialog();
			if(!isRelogin){
			m_loginPanel.getContentPane().showMessageDialog("로그인에 실패 하였습니다.");
			}
		}
	}

	@Override
	public void run() {
		while (m_isConnected) {
			try {
				Object obj = inputStream.readObject();
				ResponsePacket responesPacket = (ResponsePacket) obj;
				if (responesPacket.getResponseType().equals("login")) {
					// 로그인 응답 처리
					this.resultLogin(responesPacket);
				} else {
					// 모르는 패킷은 버린다.
				}
			} catch (Exception e) {
				e.printStackTrace();
				m_isConnected = false;
			}
		}
	}
}