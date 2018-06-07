package mju.cn.server.main;



import Server.ServerManager;
import mju.cn.server.network.SSServerMananger;

public class ServerMain {
	public static void main(String dd[]) {
		ServerManager gameServer = new ServerManager();
	SSServerMananger mainServer = new SSServerMananger();
		mainServer.setGameServer(gameServer);
		gameServer.setMainServer(mainServer);
		gameServer.start();
		mainServer.start();
//SSServerMananger con = new SSServerMananger();
		
		//con.startServer();
		
	}
}
