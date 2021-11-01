package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
class My_board extends JPanel
{

}
class Field extends JFrame implements ActionListener
{
	private static int field_size = 50;
	private JButton[][] buttons;
	private JButton start_button;
	private Observer observer;
	My_fleet fleet = new My_fleet();

	Field()
	{
		observer = new Observer();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		buttons = new JButton[12][12];
		this.setSize(1500, 800);
		JPanel panel = new JPanel();
		JPanel op_panel = new JPanel();
		JPanel borders = new JPanel();
		//panel.setBackground(Color.cyan);
		panel.setBackground(Color.black);
		op_panel.setBackground(Color.black);
		panel.setSize(field_size * 12 + 20, field_size * 12 + 20);
		op_panel.setSize(field_size * 12 + 20, field_size * 12 + 20);
		panel.setLayout(new GridLayout(12, 12, 2, 2));
		panel.setLocation(10, 10);
		op_panel.setLayout(new GridLayout(12, 12, 2, 2));
		op_panel.setLocation(10, 10);

		this.setResizable(false);
		int label = 1;
		char text_label = 'a';
		for (int i = 0; i < field_size * 12; i += field_size)
		{
			for (int j = 0; j < field_size * 12; j += field_size)
			{
				buttons[i / field_size][j / field_size] = new JButton();
				buttons[i / field_size][j / field_size].setBackground(Color.blue);
				buttons[i / field_size][j / field_size].setCursor(new Cursor(Cursor.WAIT_CURSOR));
				buttons[i / field_size][j / field_size].addActionListener(this);
				panel.add(buttons[i / field_size][j / field_size]);
				if (i == 0 || j == 0 || i == 11 * field_size || j == 11 * field_size)
				{
					buttons[i / field_size][j / field_size].setBackground(Color.black);
					if ((i == 0 && j > 0) && label <= 10)
					{
						String s = new String(Integer.toString(label));
						buttons[i / field_size][j / field_size].setText(s);
						label++;
					}
					if (j == 0 && i > 0 && text_label <= 'j')
					{
						String s = new String();
						s += text_label;
						buttons[i / field_size][j / field_size].setText(s);
						text_label++;
					}
					buttons[i / field_size][j / field_size].setEnabled(false);
				}

			}
		}
		start_button = new JButton("Start");
		start_button.setBackground(Color.red);
		start_button.setForeground(Color.white);
		start_button.setBounds(650, 20, 100, 50);
		start_button.addActionListener(this);
		this.add(start_button);
		panel.setLocation(10, 100);
		this.add(panel);
		op_panel.setLocation(750 + 24, 100);
		this.add(op_panel);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		for (int i = 1; i < 11; i++)
		{
			for (int j = 1; j < 11; j++)
			{
				if (e.getSource() == buttons[i][j])
				{
					if (observer.check_sea(i - 1, j - 1) == 0)
					{
						buttons[i][j].setBackground(Color.GREEN);
					} else if (observer.check_sea(i - 1, j - 1) == 1)
					{
						buttons[i][j].setBackground(Color.blue);
					} else
					{
						buttons[i][j].setBackground(Color.red);
						return;
					}
					observer.invert_point(i - 1, j - 1);
				}
			}
		}
		if (e.getSource() == start_button)
		{
			if (!fleet.full_flag)
				return;
			System.out.println("Started!");
			fix();
			start_button.setBackground(Color.black);
			start_button.setText("Started");
			start_button.setEnabled(false);
		}
	}
	void fix()
	{
		for (int i = 1; i < 11; i++)
		{
			for (int j = 1; j < 11; j++)
				buttons[i][j].setEnabled(false);
		}
	}
}
