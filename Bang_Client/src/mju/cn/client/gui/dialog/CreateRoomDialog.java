package mju.cn.client.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import mju.cn.client.gui.MainFrame;
import mju.cn.client.gui.panel.LobbyPanel;

public class CreateRoomDialog extends JPanel {
	private static final long serialVersionUID = 1L;

	// Components
	private Image m_background; // 배경이미지
	private JTextField m_roomName; // 방이름 필드
	private JSlider m_slider1; // 게임 횟수 슬라이더
	private JSlider m_slider2; // 시간 조절 슬라이더
	private JButton m_yes; // 만들기 버튼
	private JButton m_no; // 취소 버튼
	private MainFrame m_owner; // 상위 프레임

	// Constructor
	public CreateRoomDialog(MainFrame owner) {
		m_roomName = new JTextField();
		m_slider1 = new JSlider(JSlider.HORIZONTAL, 5, 20, 5);
		m_slider2 = new JSlider(JSlider.HORIZONTAL, 1, 3, 1);
		m_yes = new JButton();
		m_no = new JButton();
		m_owner = owner;

		this.init();
		this.initEventHandler();
	}

	// Initialization
	private void init() {
		initInputField(m_roomName);

		m_background = Toolkit.getDefaultToolkit().getImage(
				"images/dialog_bg.png");

		m_yes.setContentAreaFilled(false);
		m_yes.setBorder(null);
		m_yes.setIcon(new ImageIcon("images/exit_yes.png"));
		m_yes.setCursor(new Cursor(Cursor.HAND_CURSOR));
		m_yes.setSize(126, 27);
		m_yes.setOpaque(false);

		m_no.setContentAreaFilled(false);
		m_no.setBorder(null);
		m_no.setIcon(new ImageIcon("images/exit_no.png"));
		m_no.setCursor(new Cursor(Cursor.HAND_CURSOR));
		m_no.setSize(126, 27);
		m_no.setOpaque(false);

		this.setOpaque(false);
		this.setPreferredSize(new Dimension(340, 230));
		this.setLayout(new BorderLayout());
		this.add(this.createTitle(), BorderLayout.NORTH);
		this.add(this.createBottomPanel(), BorderLayout.SOUTH);
	}

	// 이벤트핸들러 초기화 함수
	private void initEventHandler() {
		m_yes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (m_roomName.getText().trim().equals("")) {
					m_owner.getChild().hideDialog();
					m_owner.getChild().showMessageDialog("방 제목을 입력하세요.");
				}

				try {
					String roomName = m_roomName.getText().trim();
					int slider1 = m_slider1.getValue();
					int slider2 = m_slider2.getValue();
					LobbyPanel lobbyPanel = m_owner.getChild()
							.getLobbyPanel();
					lobbyPanel.getRoomListController().createRoom(
							lobbyPanel.getUserId(), roomName, slider1, slider2);
					m_owner.getChild().hideDialog();
				} catch (Exception ex) {
					m_owner.getChild().hideDialog();
		
				}
			}
		});

		m_no.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m_owner.getGlassPane().setVisible(false);
			}
		});
	}

	// 입력필드 초기화 함수
	private void initInputField(JTextField input) {
		input.setBorder(null);
		input.setFont(new Font("돋움", Font.BOLD, 15));
		input.setForeground(Color.DARK_GRAY);
		input.setPreferredSize(new Dimension(180, 20));
	}

	// 타이틀생성 함수
	private JPanel createTitle() {
		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				Font oldFont = g2d.getFont();
				g2d.setFont(new Font("돋움", Font.BOLD, 18));
				g2d.setColor(Color.WHITE);
				g2d.drawString("               방 만들기", 20, 30);
				g2d.setFont(oldFont);
				super.paintComponent(g);
			}

		};
		panel.setOpaque(false);
		panel.setPreferredSize(new Dimension(0, 40));
		return panel;
	}

	// 아래쪽패널 생성 함수
	private Box createBottomPanel() {
		Box box = Box.createVerticalBox();
		box.add(this.createInputPanel("방 제목", m_roomName, m_slider1,
				m_slider2));
		box.add(this.createButtonPanel());

		return box;
	}

	// 입력패널 생성 함수
	private JPanel createInputPanel(String text, JTextField input,
			JSlider Slider1, JSlider Slider2) {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(0, 130));
		panel.setOpaque(false);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 50));
		panel.add(this.createLabel(text));
		panel.add(input);

		return panel;
	}

	// 버튼패널 생성 함수
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(0, 50));
		panel.setOpaque(false);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
		panel.add(m_yes);
		panel.add(m_no);

		return panel;
	}

	// 라벨생성 함수
	private JLabel createLabel(String text) {
		JLabel label = new JLabel(text);
		label.setOpaque(false);
		label.setFont(new Font("돋움", Font.BOLD, 15));
		label.setForeground(Color.BLACK);
		label.setPreferredSize(new Dimension(80, 30));
		return label;
	}

	// 그리기 함수
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(m_background, 0, 0, m_background.getWidth(this) + 20,
				m_background.getHeight(this), this);
		super.paintComponent(g);
	}
}
