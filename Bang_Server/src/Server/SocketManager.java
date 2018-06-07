package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import GameController.ServerEngine;
import GameController.ServerPlayer;
import mju.cn.common.RequestPacket;
import mju.cn.common.RequestPacket.SYNC_TYPE;
import mju.cn.server.player.SSPlayer;
import mju.cn.server.room.SSLobby;

public class SocketManager extends Thread implements Runnable{
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private Game game;
	private Socket socket;
	private ServerPlayer player;
	private String id;

	private boolean isConnected = true;

	public SocketManager(Socket socket, ServerManager server) {
		ServerEngine.getTime();
		System.out.println("make Socket.");
		this.socket = socket;
		try {
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("초기화 실패.");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (isConnected) {
			try {
				if(game!=null){
					socket.setTcpNoDelay(true);
					Object obj = inputStream.readObject();
					RequestPacket requestPacket = (RequestPacket) obj;
					
					if (requestPacket.getMethodName().equals("sendXY")) {
						Object[] args = requestPacket.getArgs();
						if(id == null){
							this.id = (String)args[0];
							ServerEngine.getTime();
							System.out.println("setting ID : "+this.id);
						}
						if((int)args[9]!=0){
							game.updatePlayer(this, (String) args[0], (int) args[1], (int) args[2], (int) args[3],
									(boolean) args[4], (int) args[5], (boolean) args[6], (int)args[7], (boolean)args[8]);
						}
						sendInfo();
					}
				}

			} catch (Exception e) {
				game.removePlayer(this);
				ServerEngine.getTime();
				exitSocket();
				System.out.println(socket.getInetAddress() + "접속 종료! 접속 인원: " + game.getPlayer().size());
				ServerEngine.getTime();
				System.out.println(e.getMessage());
				
//				e.printStackTrace();
				isConnected = false;
			}
		}
	}
	
	public void exitSocket(){
		game.getSsRoom().exitRoom(this);
	}

	public void sendInfo() {
		try {
			RequestPacket packet = new RequestPacket();
			packet.setClassName("Socket");
			packet.setMethodName("sendPlayer");
			packet.setArgs(new Object[] { game.getPlayer(), game.getEnemy(), game.getFood() });
			packet.setSyncType(SYNC_TYPE.SYNCHRONOUS);
			outputStream.writeObject(packet);
			outputStream.flush();
			outputStream.reset();
			player.setIsEat(false);
			player.setIsHit(false);
			outputStream.reset();
		} catch (Exception e) {
			exitSocket();
		}
	}

	public void setPlayer(ServerPlayer p) {
		this.player = p;
	}

	public ServerPlayer getPlayer() {
		return this.player;
	}

	public ObjectOutputStream getOutputStream() {
		return outputStream;
	}
	
	public String getID(){
		return id;
	}

	public void setGame(Game game) {
		this.game = game;
		
	}

	public Game getGame() {
		// TODO Auto-generated method stub
		return game;
	}

}
