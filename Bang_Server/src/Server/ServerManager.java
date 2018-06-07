package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import GameController.ServerEngine;
import mju.cn.server.network.SSServerMananger;
import mju.cn.server.room.SSLobby;
import mju.cn.server.room.SSRoom;

public class ServerManager extends Thread {
	private boolean isRunning = true;
	private ServerSocket serverSocket;

	public CopyOnWriteArrayList<Game> game;
	private SSServerMananger mainServer;
	public ArrayList<SocketManager> socketList;

	private int currentRoom = 999;

	public int getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(int currentRoom) {
		this.currentRoom = currentRoom;
	}

	public ServerManager() {
		socketList = new ArrayList<SocketManager>();
		game = new CopyOnWriteArrayList<Game>();
		try {
			serverSocket = new ServerSocket(5553);
			ServerEngine.getTime();
			System.out.println("Server Started..");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		try {
			while (isRunning) {
				Socket socket = serverSocket.accept();
				ServerEngine.getTime();
				System.out.println(socket.getInetAddress() + "Á¢¼Ó..");
				socket.setTcpNoDelay(true);
				if (socket.isConnected()) {
					SocketManager manager;
					SSLobby lobby = SSLobby.getLobby();
					ServerEngine.getTime();
					System.out.println("Current Room : " + lobby.CurrentRoom);
					lobby.setCurrentUser(manager = new SocketManager(socket, this));
					socketList.add(manager);
					manager.setGame(lobby.currentGame);
					lobby.currentGame.addPlayer(manager);
					if (manager.getGame() != null) {
						ServerEngine.getTime();
						System.out.println("Manager Started.");
						manager.start();
					}

				}
			}
		} catch (IOException e) {
	//		e.printStackTrace();
			isRunning = false;
		}
	}

	public boolean isConnected() {
		return isRunning;
	}

	public void setMainServer(SSServerMananger mainServer) {
		this.mainServer = mainServer;
	}
}
