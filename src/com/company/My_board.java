package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;


/**
 * Класс Opponents_board реализует графику и логику поля игрока
 */
public class My_board extends Board
{
	private My_observer observer;

	@Override
	protected boolean get_status()
	{
		return MINE;
	}

	@Override
	protected void create_obs()
	{
		observer = new My_observer(get_status());
	}

	/**
	 * Метод fix реалмзует старт игры - все кнопки поля игрока становятся неактивными
	 */
	@Override
	public void fix(Field field)
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

	public int hit(int[] pos)
	{
		if (observer.get_sea(pos[0], pos[1]) == 1)
		{
			buttons[pos[0] + DELTA][pos[1] + DELTA].setBackground(Color.ORANGE);
			// TODO: проверка на убийство, проверка на убитый корабль, прверка на конец игры
			return SUNKEN;
		} else
		{
			buttons[pos[0] + DELTA][pos[1] + DELTA].setBackground(Color.cyan);
			return MISS;
		}
	}
}
