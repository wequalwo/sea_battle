package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Board extends JPanel implements ActionListener
{
	private JButton[][] buttons;
	private Observer observer;
	private static int field_size = Field.field_size;
	private static int square_first = 10;//начальная позиция панели по x и y
	Board()
	{
		observer = new Observer();
		this.setBackground(Color.black);
		this.setSize(field_size * 12 + Field.space, field_size * 12 + Field.space);
		this.setLayout(new GridLayout(12, 12, 2, 2));
		this.setLocation( square_first,  square_first);
		buttons = new JButton[12][12];
		int label = 1;
		char text_label = 'a';
		for (int i = 0; i < field_size * 12; i += field_size)
		{
			for (int j = 0; j < field_size * 12; j += field_size)
			{
				buttons[i / field_size][j / field_size] = new JButton();
				buttons[i / field_size][j / field_size].setBackground(Color.GRAY);
				buttons[i / field_size][j / field_size].setCursor(new Cursor(Cursor.WAIT_CURSOR));
				buttons[i / field_size][j / field_size].addActionListener(this);
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
						buttons[i][j].setBackground(Color.GRAY);
					} else
					{
						buttons[i][j].setBackground(Color.red);
						return;
					}
					observer.invert_point(i - 1, j - 1);
				}
			}
		}
	}

	public void fix()
	{
		for (int i = 1; i < 11; i++)
		{
			for (int j = 1; j < 11; j++)
			{
				if(observer.get_sea(i - 1, j - 1) == 0)
					buttons[i][j].setBackground(Color.blue);
				buttons[i][j].setEnabled(false);
			}
		}
	}
	public void _fix()
	{
		for (int i = 1; i < 11; i++)
		{
			for (int j = 1; j < 11; j++)
				buttons[i][j].setEnabled(true);
		}
	}
}

class Opponents_board extends Board
{
	{
		fix();
	}
	void start()
	{
		_fix();
	}
}
