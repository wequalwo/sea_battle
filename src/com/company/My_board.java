package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;


/**
 * The My_board class implements the graphics and logic of the players field.
 */
public class My_board extends Board
{
	/**
	 * observer of the board
	 */
	private My_observer observer;
	/**
	 * status of the board
	 */
	public boolean game_status;

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
	 * It starts game
	 */
	@Override
	public boolean fix(Field field)
	{
		game_status = field.game_status;
		if (!observer.create_fleet())
			return FAIL;
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
		return OK;
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
	 * It hits connected board
	 *
	 * @param pos hit position
	 * @return status of hit position after the hit
	 */
	public int hit(int[] pos)
	{
		ArrayList<int[]> grind = null;
		try
		{
			grind = observer.shot(pos[0], pos[1]);
		} catch (Exception exception)
		{
			exception.printStackTrace();
			System.out.println("Error 1: my set of ships collapsed");
		}
		if (grind == null)
		{
			throw new NullPointerException();
		}
		if (observer.get_if_zero_counter())
		{
			buttons[pos[0] + DELTA][pos[1] + DELTA].setBackground(Color.RED);
			return it_is_the_end;
		}

		if (grind.get(0)[0] == MISS) // выстрел мимо
		{
			buttons[pos[0] + DELTA][pos[1] + DELTA].setBackground(Color.blue);
			return MISS;
		} else if (grind.get(0)[0] == SUNKEN)
		{
			buttons[pos[0] + DELTA][pos[1] + DELTA].setBackground(Color.ORANGE);
			return SUNKEN;
		} else
		{
			for (int[] p : grind)
			{
				buttons[p[0]][p[1]].setBackground(Color.gray);
				buttons[p[0]][p[1]].setEnabled(false);
			}
			ArrayList<int[]> off_but = observer.fill(grind);
			for (int[] p : off_but)
			{
				buttons[p[0]][p[1]].setBackground(Color.blue);
				opponent.disable(new int[]{p[0] - 1, p[1] - 1});
			}
		}
		if (observer.get_if_zero_counter())
			return it_is_the_end;
		return DEAD;
	}
}
