package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Класс Board отвечает за поле игрока
 */
abstract public class Board extends JPanel implements ActionListener, def
{
	/**
	 * Массив кнопок buttons - это клетки поля. Их 12, а не 10, потому что красоты ради
	 * было выполнено окаймление из неактивных кнопок с координатной сеткой
	 */
	protected JButton[][] buttons;
	private boolean start; // статус начала игры

	Board()
	{
		start = false;
		create_obs();  //добавление наблюдателя. Нужно для логики программы (см комментарии к класса Observer)
		this.setBackground(Color.black);
		int field_size = Field.field_size;
		this.setSize(field_size * 12 + Field.space, field_size * 12 + Field.space);
		this.setLayout(new GridLayout(12, 12, 2, 2));
		//начальная позиция панели по x и y
		int square_first = 10;
		this.setLocation(square_first, square_first);
		buttons = new JButton[12][12];
		int label = 1;                          //временные переменные для заполнения координат на окоймляющих кнопках
		char text_label = 'a';
		/*
		 * Дальнейший цикл инициализирует кнопки поля, причем оставляет окаймление неактивным.
		 * К каждой кнопке поля добавляется ActionListener для возможности взаимодействия
		 * При нажатии кнопки происходит прерывание, в результате через переопределенный метод
		 * actionPerformed программа узнает, какая кнопка была нажата
		 */
		for (int i = 0; i < field_size * 12; i += field_size)
		{
			for (int j = 0; j < field_size * 12; j += field_size)
			{
				buttons[i / field_size][j / field_size] = new JButton();
				buttons[i / field_size][j / field_size].setEnabled(!get_status());
				buttons[i / field_size][j / field_size].setBackground(Color.GRAY);
				buttons[i / field_size][j / field_size].setCursor(new Cursor(Cursor.WAIT_CURSOR));
				buttons[i / field_size][j / field_size].addActionListener(this);
				this.add(buttons[i / field_size][j / field_size]);
				if (i == 0 || j == 0 || i == 11 * field_size || j == 11 * field_size)
				{
					buttons[i / field_size][j / field_size].setBackground(Color.black);
					if ((i == 0 && j > 0) && label <= 10)
					{
						buttons[i / field_size][j / field_size].setText(Integer.toString(label));
						label++;
					}
					if (j == 0 && i > 0 && text_label <= 'j')
					{
						String s = "";
						s += text_label;
						buttons[i / field_size][j / field_size].setText(s);
						text_label++;
					}
					buttons[i / field_size][j / field_size].setEnabled(false);
				}
			}
		}
	}

	abstract protected boolean get_status();

	abstract protected void create_obs();

	abstract public boolean fix(Field field);

	public boolean set_start(Field field)
	{
		if(fix(field))
			start = true;
		return start;

	}

	public boolean get_start()
	{
		return start;
	}
}
