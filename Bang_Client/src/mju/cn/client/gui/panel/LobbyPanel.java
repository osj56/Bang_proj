package mju.cn.client.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.JPanel;

import mju.cn.client.controller.ChatController;
import mju.cn.client.controller.PlayerListController;
import mju.cn.client.controller.RoomListController;
import mju.cn.client.gui.ContentPane;
import mju.cn.client.gui.item.Avatar;
import mju.cn.client.gui.item.PlayerListItem;
import mju.cn.client.gui.item.RoomListItem;


public class LobbyPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	// Attributes
	private String m_userId; // 사용자 아이디
	private String m_name; // 사용자이름
	private String m_avatarName; // 아바타이름
	private Vector<PlayerListItem> m_playerList; // 플레이어목록
	private Vector<RoomListItem> m_roomList; // 방목록
	private Image m_userBackground; // 유저 배경

	// Components
	private ContentPane m_parent; // 상위패널
	private ChatPanel m_chatPanel; // 채팅패널
	private RoomListPanel m_roomPanel; // 룸목록패널
	private Avatar m_avatar; // 아바타
	private PlayerListPanel m_playerListPanel; // 플레이어목록패널
	private ChatController m_chatController; // 채팅컨트롤러
	private PlayerListController m_playerListController; // 플레이어리스트컨트롤러
	private RoomListController m_roomListController; // 룸리스트컨트롤러

	// Constructor
	public LobbyPanel(ContentPane parent) {
		super();
		m_parent = parent;
		m_userBackground = Toolkit.getDefaultToolkit().getImage(
				"images/user_info_bg.png");
		m_playerList = new Vector<PlayerListItem>();
		m_roomList = new Vector<RoomListItem>();
		m_chatPanel = new ChatPanel(this);
		m_playerListPanel = new PlayerListPanel(this);
		m_roomPanel = new RoomListPanel(this);
	}

	// Initialization
	public void init(String serverIP) {
		this.setLayout(new BorderLayout());
		this.setOpaque(false);

		this.add(this.createWestPanel(), BorderLayout.EAST);
		this.add(this.createEastPanel(), BorderLayout.WEST);

		m_chatController = new ChatController(serverIP, this);
		m_chatController.start();
		m_playerListController = new PlayerListController(serverIP, this);
		m_playerListController.start();
		m_roomListController = new RoomListController(serverIP, this);
		m_roomListController.start();

	}

	// 매니저초기화 함수
	public void initManager(String uid) {
		m_userId = uid;
		m_chatController.init(m_userId);
		m_playerListController.init(m_userId);
		m_roomListController.init(m_userId);
	}

	// 왼쪽 패널그룹 생성 함수
	private JPanel createWestPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(655, 0));
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());

		panel.add(m_chatPanel, BorderLayout.SOUTH);
		panel.add(m_roomPanel, BorderLayout.NORTH);

		return panel;
	}

	// 오른쪽 패널 생성 함수
	private JPanel createEastPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(245, 0));
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());

		panel.add(m_playerListPanel, BorderLayout.SOUTH);
		panel.add(this.createUserInfo(), BorderLayout.NORTH);

		return panel;
	}

	// 유저정보 생성 함수
	private JPanel createUserInfo() {
		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {					//이미지크기에맞춰서 그린다.
				g.drawImage(m_userBackground, 0, 50, this.getWidth(),
						200, this);
				if(m_avatarName.equals("av01")||m_avatarName.equals("av03")){
				m_avatar.drawAvatar(g, 10, 110, 105, 120);
				}else if(m_avatarName.equals("av07")){
					m_avatar.drawAvatar(g, 10, 110, 78, 130);
				}else if(m_avatarName.equals("av06")){
					m_avatar.drawAvatar(g, 10, 110, 90, 120);
				}else if(m_avatarName.equals("av02")){
					m_avatar.drawAvatar(g, 10, 110, 89, 120);
				}else if((m_avatarName.equals("av05"))){
					m_avatar.drawAvatar(g, 10, 110, 88, 110);
				}else if((m_avatarName.equals("av04"))){
					m_avatar.drawAvatar(g, 10, 110, 93, 120);
			

				}

				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				Font oldFont = g2d.getFont();
				g2d.setFont(new Font("돋움", Font.BOLD, 17));
				g2d.setColor(Color.BLACK);
				g2d.drawString(m_userId, 140, 150);
				g2d.drawString(" " , 140, 150);
				g2d.setColor(Color.BLACK);
				g2d.drawString(m_name, 140, 210);
				g2d.setFont(oldFont);
				super.paintComponent(g);
			}
		};
		panel.setPreferredSize(new Dimension(245, 245));
		panel.setOpaque(false);

		return panel;
	}
	// 유저정보 초기화 함수
	public void initUserInfo(String id, String name, String avatar, int exp) {
		m_userId = id;
		m_name = name;
		m_avatarName = avatar;
		m_avatar = new Avatar(Toolkit.getDefaultToolkit().getImage(
				"avatar/" + m_avatarName + ".png"), m_avatarName, this);
	}

	// 플레이어 목록 추가 함수
	public void addPlayerList(String id, String name, String avatar, int exp) {
		m_playerList.add(new PlayerListItem(this, id, name, exp,
				new Avatar(Toolkit.getDefaultToolkit().getImage(
						"avatar/game_" + avatar + ".jpg"), avatar, this)));
	}

	// 방목록 추가 함수
	public void addRoomList(int roomNumber, int playerNumber, String roomName,
			int maxPlayerNumber, boolean isStart) {

		m_roomList.add(new RoomListItem(this, roomNumber, playerNumber,
				roomName, 2, isStart));
	}
	

	// Getters and Setters
	public ContentPane getContentPane() {
		return m_parent;
	}

	public ChatPanel getChatPanel() {
		return m_chatPanel;
	}

	public ChatController getChatController() {
		return m_chatController;
	}

	public PlayerListController getPlayerListController() {
		return m_playerListController;
	}

	public RoomListController getRoomListController() {
		return m_roomListController;
	}

	public PlayerListPanel getPlayListPanel() {
		return m_playerListPanel;
	}

	public RoomListPanel getRoomListPanel() {
		return m_roomPanel;
	}

	public String getUserId() {
		return m_userId;
	}

	public Vector<PlayerListItem> getPlayerList() {
		return m_playerList;
	}

	public Vector<RoomListItem> getRoomList() {
		return m_roomList;
	}

	public void setDefaultCloseOperation(int exitOnClose) {
		// TODO Auto-generated method stub
		
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	

}
