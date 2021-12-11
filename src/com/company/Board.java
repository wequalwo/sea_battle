package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Класс Board отвечает за поле игрока
 */
class Board extends JPanel implements ActionListener, def
{
	/**
	 * Массив кнопок buttons - это клетки поля. Их 12, а не 10, потому что красоты ради
	 * было выполнено окаймление из неактивных кнопок с координатной сеткой
	 */
	protected final JButton[][] buttons;
	protected final Observer observer;
	boolean start = false;

	protected boolean get_status()
	{
		return def.MINE;
	}
	Board()
	{

		observer = new Observer(get_status());              //добавление наблюдателя. Нужно для логики программы (см комментарии к класса Observer)
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

	@Override
	public void actionPerformed(ActionEvent e)
	{
		for (int i = 1; i < 11; i++)
		{
			for (int j = 1; j < 11; j++)
			{
				/*
				 * Если нажата определенная кнопка, то проверяем, поставлена ли уже на эту кнопку метка.
				 */
				if (e.getSource() == buttons[i][j])
				{
					if (observer.check_sea(i - 1, j - 1) == 0)
					{
						/*
						 * если метод проверки из класса,
						 * хранящего положения всех судов в виде массива, вернул 0 -
						 * значит в данную клетку можно уставноить часть суна
						 */
						buttons[i][j].setBackground(Color.GREEN);
					} else if (observer.check_sea(i - 1, j - 1) == 1)
					{
						/*
						 * если метод проверки из класса,
						 * хранящего положения всех судов в виде массива, вернул 1 -
						 * значит в данной клетке уже стоит что-то. Убираем
						 */
						buttons[i][j].setBackground(Color.GRAY);
					} else
					{
						/*
						 * В остальных случаях установка невозможна.
						 */
						buttons[i][j].setBackground(Color.red);
						return;
					}
					/*
					 * Если установка возможна - инвертируем статус клетки:
					 * Если была установлена - снимаем
					 * Если не была установлена - устанавливаем
					 */
					observer.invert_point(i - 1, j - 1);
				}

			}
		}
	}
	/**
	 * Метод fix реализует старт игры - все кнопки нашего поля становятся неактивными
	 * TODO: помимо неактивных кнопок запускает игру (очередность + оппонента)
	 */
	public void fix()
	{
		for (int i = 1; i < 11; i++)
		{
			for (int j = 1; j < 11; j++)
			{
				float[] a = new float[3];
				int r = 0;
				// цвет кнопки в rgb
				int g = 0;
				int b = 139;
				Color.RGBtoHSB(r, g, b, a);
				if (observer.get_sea(i - 1, j - 1) == 0)
					buttons[i][j].setBackground(Color.getHSBColor(a[0], a[1], a[2]));
				buttons[i][j].setEnabled(false);
			}
		}
	}
}

/**
 * Класс Opponents_board реализует графику и логику поля оппонента
 */
class Opponents_board extends Board implements def
{

	@Override
	protected boolean get_status()
	{
		return OPP;
	}
	{
		fix();    // в момент инициализации (перед началом игры) поле оппонента должно быть неактивно
	}
	/**
	 * Метод _fix реалмзует старт игры - все кнопки поля оппонента становятся активными
	 * ! Используеся только в дочернем классе, реализующем поле оппонента !
	 */
	public void _fix()
	{
		for (int i = 1; i < 11; i++)
		{
			for (int j = 1; j < 11; j++)
				buttons[i][j].setEnabled(true);
		}
	}
	private void op_move()
	{

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
					ArrayList<int[]> grind = null;
					try
					{
						grind = observer.shot(i - 1, j - 1);
					} catch (Exception exception)
					{
						exception.printStackTrace();
						System.out.println("Error 1: my set of ships collapsed");
					}

					if (grind.get(0)[0] == MISS) // выстрел мимо
					{
						buttons[i][j].setBackground(Color.blue);
					} else if (grind.get(0)[0] == HIT)// попал
					{
						buttons[i][j].setBackground(Color.orange);
						buttons[i][j].setEnabled(false);
					} else // ответ (выстрел) убил
					{
						for(int[] p: grind)
						{
							buttons[p[0]][p[1]].setBackground(Color.gray);
							buttons[p[0]][p[1]].setEnabled(false);
						}
					}
					op_move();
				}
			}
		}
	}
}
