package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Field extends JFrame implements ActionListener
{
	public static int field_size = 50;
	private JButton start_button;
	My_fleet fleet = new My_fleet();
	Board my_board;
	Field()
	{
		//observer = new Observer();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(1500, 800);
		this.setResizable(false);
		my_board = new Board();

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

		if (e.getSource() == start_button)
		{
			if (!fleet.full_flag)
				return;
			System.out.println("Started!");
			my_board.fix();
			start_button.setBackground(Color.black);
			start_button.setText("Started");
			start_button.setEnabled(false);
		}
	}

}
