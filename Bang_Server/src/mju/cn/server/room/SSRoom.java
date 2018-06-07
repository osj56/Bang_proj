package mju.cn.server.room;

import java.util.Vector;

import Server.Game;
import Server.SocketManager;
import mju.cn.server.player.SSPlayer;

public class SSRoom {
	// Attributes
	private int m_roomNum; // 방 번호
	private int m_order; // 현제 플레이어의 인덱스
	private int m_maxPlayer = 2; //최대인원
	private int m_playTime; // 플레이 시간
	
	// Components
	private Vector<SSPlayer> m_player; //플레이어 목록	
	private boolean m_waiting; //방 상태
	private String m_roomSubject; //방제목
	private SSLobby m_parent; // 부모 객체
	Game game;
	// Constructor
	public SSRoom(int roomNum, int maxPlayer, String subject, SSLobby lobby){
		m_roomNum = roomNum;
		m_maxPlayer = 2;
		m_player = new Vector<SSPlayer>();
		m_roomSubject = subject;
		m_parent = lobby;
		m_waiting = true;
		game = new Game(roomNum);
		game.setSsRoom(this);
		m_parent.currentGame = game;

	}	
	
	// Initialization
	// 방의 상태를 대기로하고 0번순서로 한다.
	// 모든 플레이어의 게임정보를 초기화 한다.
	public void initGame(){
		m_waiting = false;
		m_order = 0;

		for (int index = 0; index < m_player.size(); index++) {
			SSPlayer player = m_player.get(index);
			player.initInfo();
		}
	}
	
	// 게임종료 함수
	// 방을 대기룸 상태로 놓는다.
	public void endGame (){
		m_waiting = true;
	}
	
	// 플레이어 경험치 저장함수
	public void updateExp(){
		for(SSPlayer player : m_player){
			player.updateExp();
		}
	}
	
	// 입장 함수
	// 최대 인원수 내에 입장이 가능하다.
	public boolean enterRoom(SSPlayer player) {
		SSLobby lobby = SSLobby.getLobby();	
		lobby.currentGame = game;
		lobby.CurrentRoom = m_roomNum;
		if (m_player.size()>=m_maxPlayer) {
			return false;
		}else{
			
			m_player.add(player);
			m_parent.getWaitPlayerList().remove(player);	
			return true;
		}		
	}

	// 퇴장함수
	// 방에서 플레이어를  삭제하고 대기방에 입력한다.
	// 마지막 플레이어가 나가면 방을 삭제한다.
	public boolean exitRoom(SSPlayer player){
		m_player.remove(player);
		m_parent.getWaitPlayerList().add(player);
		
		if(m_player.size() == 0){
			SSLobby lobby = SSLobby.getLobby();
			lobby.deleteRoom(this);
			return false;
		}
		return true;
	}
	
	public void exitRoom(SocketManager manager){
		for(SSPlayer player : m_player){
			if(player.getId().equals(manager.getID())){
				exitRoom(player);
				break;
			}
		}
	}
	
	// Getters and Setters
	public Vector<SSPlayer> getPlayerList() {
		return m_player;
	}

	public SSPlayer getGameHost() {
		return m_player.get(0);
	}

	public int getOrder() {
		return m_order;
	}

	public boolean isStart() {
		return !m_waiting;
	}

	public int getMaxPlayer() {
		return m_maxPlayer;
	}

	public void setMaxPlayer(int m_maxPlayer) {
		this.m_maxPlayer = m_maxPlayer;
	}

	public boolean isWaiting() {
		return m_waiting;
	}

	public void setWaiting(boolean m_waiting) {
		this.m_waiting = m_waiting;
	}

	public String getRoomSubject() {
		return m_roomSubject;
	}

	public void setRoomSubject(String m_roomSubject) {
		this.m_roomSubject = m_roomSubject;
	}

	public int getRoomNum() {
		return m_roomNum;
	}

	public int getPlayTime() {
		return m_playTime;
	}

	public void setPlayTime(int playTime) {
		this.m_playTime = playTime;
	}

	public Game getGame() {
		// TODO Auto-generated method stub
		return game;
	}

}
