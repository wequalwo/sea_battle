package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Класс Opponents_board реализует графику и логику поля оппонента
 */
public class Opponents_board extends Board
{

	private Op_observer observer;
	private Opponent opponent;

	@Override
	protected boolean get_status()
	{
		return OPP;
	}

	@Override
	protected void create_obs()
	{
		observer = new Op_observer(get_status());
	}

	/**
	 * Метод fix реалмзует старт игры - все кнопки поля оппонентна становятся активными
	 */
	@Override
	public void fix(Field field)
	{
		opponent = new Opponent(field);
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
				buttons[i][j].setEnabled(true);
			}
		}
	}

	@SuppressWarnings("StatementWithEmptyBody")
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
					if (grind == null)
					{
						throw new NullPointerException();
					}

					if (grind.get(0)[0] == MISS) // выстрел мимо
					{
						buttons[i][j].setBackground(Color.blue);
						while (opponent.force_move() != MISS) ; // до тех пор, пока соперник не промазал, он ходит
					} else if (grind.get(0)[0] == SUNKEN)// попал
					{
						buttons[i][j].setBackground(Color.orange);
						buttons[i][j].setEnabled(false);
					} else // ответ (выстрел) убил
					{
						for (int[] p : grind)
						{
							buttons[p[0]][p[1]].setBackground(Color.gray);
							buttons[p[0]][p[1]].setEnabled(false);
						}
					}
				}
			}
		}
	}
}

