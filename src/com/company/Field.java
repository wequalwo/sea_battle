package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
class Board extends JPanel
{
	public JButton[][] buttons;
	public Observer observer;
	private static int field_size = Field.field_size;
	Field my_field;
	Board(Field my_field)
	{
		this.my_field = my_field;
		observer = new Observer();
		this.setBackground(Color.black);
		this.setSize(Field.field_size * 12 + 20, Field.field_size * 12 + 20);
		this.setLayout(new GridLayout(12, 12, 2, 2));
		this.setLocation(10, 10);
		buttons = new JButton[12][12];
		int label = 1;
		char text_label = 'a';
		for (int i = 0; i < field_size * 12; i += field_size)
		{
			for (int j = 0; j < field_size * 12; j += field_size)
			{
				buttons[i / field_size][j / field_size] = new JButton();
				buttons[i / field_size][j / field_size].setBackground(Color.blue);
				buttons[i / field_size][j / field_size].setCursor(new Cursor(Cursor.WAIT_CURSOR));
				buttons[i / field_size][j / field_size].addActionListener(my_field);
				this.add(buttons[i / field_size][j / field_size]);
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
	}
}
class Field extends JFrame implements ActionListener
{
	public static int field_size = 50;
	//private JButton[][] buttons;
	private JButton start_button;
	//private Observer observer;
	My_fleet fleet = new My_fleet();
	Board my_board;
	Field()
	{
		//observer = new Observer();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(1500, 800);
		this.setResizable(false);
		my_board = new Board(this);
		
		start_button = new JButton("Start");
		start_button.setBackground(Color.red);
		start_button.setForeground(Color.white);
		start_button.setBounds(650, 20, 100, 50);
		start_button.addActionListener(this);
		this.add(start_button);
		my_board.setLocation(10, 100);
		this.add(my_board);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		for (int i = 1; i < 11; i++)
		{
			for (int j = 1; j < 11; j++)
			{
				if (e.getSource() == my_board.buttons[i][j])
				{
					if (my_board.observer.check_sea(i - 1, j - 1) == 0)
					{
						my_board.buttons[i][j].setBackground(Color.GREEN);
					} else if (my_board.observer.check_sea(i - 1, j - 1) == 1)
					{
						my_board.buttons[i][j].setBackground(Color.blue);
					} else
					{
						my_board.buttons[i][j].setBackground(Color.red);
						return;
					}
					my_board.observer.invert_point(i - 1, j - 1);
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
				my_board.buttons[i][j].setEnabled(false);
		}
	}
}
