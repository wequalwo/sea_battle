package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Field extends JFrame implements ActionListener
{
	private static int width = 1500;
	private static int height = 800;

	public static int field_size = 50;//коэффициент размера поля
	private static int panel_position_x = 10; //положение начальной панели
	private static int panel_position_y = 100;// -//-
	private static int button_long = 100;// -//-
	private static int button_height = 50;// -//-
	public static int space = 30; //расстоянием между панельями

	private JButton start_button;
	My_fleet fleet = new My_fleet();
	Board my_board;
	Opponents_board opponents_board;
	Field()
	{
		//observer = new Observer();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(width, height);
		this.setResizable(false);
		my_board = new Board();
		opponents_board = new Opponents_board();

		start_button = new JButton("Start");
		start_button.setBackground(Color.red);
		start_button.setForeground(Color.white);
		start_button.setBounds(field_size*12 + 2*space, space, button_long, button_height);
		start_button.addActionListener(this);
		this.add(start_button);

		my_board.setLocation(panel_position_x, panel_position_y);
		opponents_board.setLocation(field_size*12 + 2*space + button_long + space, panel_position_y);
		this.add(my_board);
		this.add(opponents_board);
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
			opponents_board._fix();
			start_button.setBackground(Color.black);
			start_button.setText("Started");
			start_button.setEnabled(false);
		}
	}

}
