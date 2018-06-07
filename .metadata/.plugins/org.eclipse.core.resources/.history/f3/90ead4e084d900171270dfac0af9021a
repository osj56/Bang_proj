package mju.cn.server.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import GameController.ServerPlayer;
import Server.Game;
import Server.ServerManager;
//import Server.PlayerController;
import mju.cn.common.Constant;
import mju.cn.common.RequestPacket;
import mju.cn.common.RequestPacket.SYNC_TYPE;
import mju.cn.server.room.SSLobby;

public class SSServerMananger extends Thread{
	private Vector<ServerPlayer> playerlist;
	private ArrayList<Socket> socketList;
	private ServerManager gameServer;
	private ServerSocket m_serverSocket;
	private SSLobby m_lobby;
	private boolean isRunning = true;

	public SSServerMananger() {
		socketList = new ArrayList<Socket>();
		try {
			m_serverSocket = new ServerSocket(Constant.SERVER_PORT, 10);
			playerlist = new Vector<ServerPlayer>();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setGameServer(ServerManager server){
		this.gameServer = server;
		m_lobby = SSLobby.getLobby();
		m_lobby.setGameServer(gameServer);
	}
	
	@Override
	public void run(){
		while (isRunning) {
			try {
				Socket socket = m_serverSocket.accept();
				SSSocketManager manager = new SSSocketManager(socket, this);
				m_lobby.addSocketManager(manager);
				manager.start();
			} catch (IOException e) {
				e.printStackTrace();
				isRunning = false;
			}
		}
	}

	public Vector<ServerPlayer> getPlayerList(){
		return playerlist;
	}
	public ArrayList getSocketList(){
		return socketList;
	}
	public SSLobby getLobby() {
		return m_lobby;
	}
	
	
	public ServerManager getGameServer(){
		return gameServer;
	}
}