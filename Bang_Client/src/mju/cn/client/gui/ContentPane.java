package mju.cn.client.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

import mju.cn.client.gui.btns.ExitButton;
import mju.cn.client.gui.dialog.CreateRoomDialog;
import mju.cn.client.gui.dialog.ExitDialog;
import mju.cn.client.gui.dialog.LoadingDialog;
import mju.cn.client.gui.dialog.MessageDialog;
import mju.cn.client.gui.panel.JoinPanel;
import mju.cn.client.gui.panel.LobbyPanel;
import mju.cn.client.gui.panel.LoginPanel;

public class ContentPane extends JPanel {
	private static final long serialVersionUID = 1L;

	// Attributes
	private CardLayout m_cardLayout; // 카드레이아웃
	private MainFrame m_frame; // 메인프레임
	private JPanel m_topPanel; // 위쪽패널
	private JPanel m_content; // 내용
	private Image m_bgImage; // 배경 이미
	private boolean m_isFullScreen = false; // 풀스크린

	// Components
	private ExitButton m_exitButton; // X버튼
	private GlassPane m_glassPane; // GLASSPANE
	private LoginPanel m_loginPanel; // 로그인화면
	private JoinPanel m_joinPanel; // 회원가입화면
	private LobbyPanel m_lobbyPanel; // 로비 화면

	// Constructor
	public ContentPane(MainFrame parent) {
		m_frame = parent;
		
		m_content = new JPanel();
		m_topPanel = new JPanel();
		m_exitButton = new ExitButton();
		m_glassPane = new GlassPane();
		
		m_loginPanel = new LoginPanel(this);
		m_joinPanel = new JoinPanel(this);
		m_lobbyPanel = new LobbyPanel(this);		
	}

	// Initialization
	public void init(String serverIP) {
		m_glassPane.init();
		m_loginPanel.init(serverIP);
		m_joinPanel.init(serverIP);
		m_lobbyPanel.init(serverIP);

		Toolkit.getDefaultToolkit().getImage("images/title.png");
		m_bgImage = Toolkit.getDefaultToolkit().getImage("images/title.png");

		m_content.setOpaque(false);
		m_topPanel.setOpaque(false);
		m_topPanel.setPreferredSize(new Dimension(0, 100));

		m_topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		m_topPanel.add(this.m_exitButton);

		m_cardLayout = new CardLayout();
		m_content.setLayout(this.m_cardLayout);
		m_content.add(m_loginPanel, m_loginPanel.getClass().getName());
		m_content.add(m_joinPanel, m_joinPanel.getClass().getName());
		m_content.add(m_lobbyPanel, m_lobbyPanel.getClass().getName());
		//m_content.add(m_gameRoomPanel, m_gameRoomPanel.getClass().getName());

		m_frame.setGlassPane(m_glassPane);

		this.setPreferredSize(new Dimension(MainFrame.FRAME_WIDTH,
				MainFrame.FRAME_HEIGHT));
		this.setLayout(new BorderLayout());
		this.add(m_topPanel, BorderLayout.NORTH);
		this.add(createEmptyPanel(0, 50), BorderLayout.SOUTH);
		this.add(createEmptyPanel(50, 0), BorderLayout.EAST);
		this.add(createEmptyPanel(50, 0), BorderLayout.WEST);
		this.add(m_content, BorderLayout.CENTER);
		this.setOpaque(false);

		this.initEventHandler();
	}

	// 패널추가 함수 함수
	public void addPanel(Component comp, String panelName) {
		this.m_content.add(comp, panelName);
	}

	// 빈 패널 생성 함수
	private JPanel createEmptyPanel(int width, int height) {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(width, height));
		panel.setOpaque(false);
		return panel;
	}

	// 이벤트핸들러 초기화 함수
	public void initEventHandler() {
		this.m_exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showExitDialog();
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			Point point;

			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				if (m_isFullScreen) {

				} else {
					if (point == null) {
						point = e.getPoint();
					}
					m_frame.setLocation(e.getLocationOnScreen().x - point.x,
							e.getLocationOnScreen().y - point.y);
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				point = null;
			}
		});
	}

	// 배경 그리기 함수
	public void drawBackground(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Paint oldPaint = g2d.getPaint();


		
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

		g2d.setPaint(oldPaint);


		
		g2d.drawImage(m_bgImage, 0, 0,
				MainFrame.FRAME_WIDTH,
				MainFrame.FRAME_HEIGHT, this);

	}

	// 컴포넌트 그리기 함수
	@Override
	public void paintComponent(Graphics g) {
		this.drawBackground(g);
		super.paintComponent(g);
	}

	// 패널 표시 함수
	public void viewPanel(String panelName) {
		this.m_cardLayout.show(this.m_content, panelName);
		
	}

	// 메시지 다이어로그 표시 함수
	public void showMessageDialog(String msg) {
		m_glassPane.setComponent(new MessageDialog(m_frame, msg));
		m_frame.getGlassPane().setVisible(true);
	}

	// 종료 다이어로그 표시 함수
	public void showExitDialog() {
		m_glassPane.setComponent(new ExitDialog(m_frame));
		m_frame.getGlassPane().setVisible(true);
	}

	// 로딩화면 다이어로그 표시 함수
	public void showLoadingDialog(String msg) {
		m_glassPane.setComponent(new LoadingDialog(msg));
		m_frame.getGlassPane().setVisible(true);
	}

	// 방 만들기 다이어로그 표시 함수
	public void showCreateRoomDialog() {
		m_glassPane.setComponent(new CreateRoomDialog(m_frame));
		m_frame.getGlassPane().setVisible(true);
	}

	// 다이어로그 숨기기 함수
	public void hideDialog() {
		m_frame.getGlassPane().setVisible(false);
	}

	// Getters and Setters
	public LoginPanel getLoginPanel() {
		return m_loginPanel;
	}

	public JoinPanel getJoinPanel() {
		return m_joinPanel;
	}

	public LobbyPanel getLobbyPanel() {
		return m_lobbyPanel;
	}

	public void setDefaultCloseOperation(int exitOnClose) {
		// TODO Auto-generated method stub
		
	}

}