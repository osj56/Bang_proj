package mju.cn.client.gui.btns;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class ExitButton extends JButton {
	private static final long serialVersionUID = 1L;

	// Constuctor
	public ExitButton() {
		super();
		this.setContentAreaFilled(false);
		this.setBorder(null);
		this.setIcon(new ImageIcon("images/icon_exit_out.png"));
		this.setRolloverIcon(new ImageIcon("images/icon_exit_over.png"));
		this.setPressedIcon(new ImageIcon("images/icon_exit_press.png"));
		this.setSize(30, 30);
	}

}
