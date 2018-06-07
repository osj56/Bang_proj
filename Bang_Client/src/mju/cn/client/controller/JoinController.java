﻿package mju.cn.client.controller;

import mju.cn.client.gui.ContentPane;
import mju.cn.client.gui.panel.JoinPanel;
import mju.cn.client.network.ClientStub;
import mju.cn.common.RequestPacket;
import mju.cn.common.ResponsePacket;
import mju.cn.common.RequestPacket.SYNC_TYPE;

public class JoinController extends ClientStub {

	// Components
	private JoinPanel m_joinPanel; // 회원가입 패널

	// Constructor
	public JoinController(String ip, JoinPanel joinPanel) {
		super(ip);
		m_joinPanel = joinPanel;
	}

	// 회원가입요청 함수
	public void submit(String id, String pw, String name, String avatarName) {
		m_joinPanel.getContentPane().showLoadingDialog("가입 요청중 입니다.");
		RequestPacket packet = new RequestPacket();
		packet.setClassName("SSJoinController");
		packet.setMethodName("submit");
		packet.setSyncType(SYNC_TYPE.SYNCHRONOUS);
		packet.setArgs(new Object[] { id, pw, name, avatarName });
		this.send(packet);
	}

	// 서버측 submit 결과 처리 함수
	private void resultSubmit(ResponsePacket packet) {
		ContentPane parent = m_joinPanel.getContentPane();
		boolean success = (Boolean) packet.getArgs()[0];
		parent.hideDialog();
		if (success) {
			parent.showMessageDialog("가입 성공 하였습니다.");
		} else {
			parent.showMessageDialog("ID가 존재합니다.");
		}
		parent.viewPanel(parent.getLoginPanel().getClass().getName());
	}

	@Override
	public void run() {
		while (m_isConnected) {
			try {
				Object obj = inputStream.readObject();
				ResponsePacket responesPacket = (ResponsePacket) obj;
				if (responesPacket.getResponseType().equals("submit")) {
					// 가입 응답 처리
					this.resultSubmit(responesPacket);
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