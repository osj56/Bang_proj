package kr.ac.mju.cn.server.controller;

import mju.cn.server.room.BSLobby;


public class JoinController {

	public JoinController() {
	}
	//ȸ������
	public Object[] submit(String id, String pw, String name, String avatarName) {
		BSLobby lobby = BSLobby.getLobby();
		boolean valid;
		synchronized(lobby){
			//lobby�� ȸ������ �޼ҵ尡 ȣ��
			valid = lobby.getDBManager().enrollPlayer(id, pw, name, avatarName);
		}
		return new Object[] {valid};
	}
}
