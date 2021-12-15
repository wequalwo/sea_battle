package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Класс Opponents_board реализует графику и логику поля оппонента
 */
public class Opponents_board extends Board
{
	public boolean game_status;
	private Op_observer observer;

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

	@Override
	public boolean fix(Field field)
	{
		game_status = field.game_status;
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
		return true;
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public void actionPerformed(ActionEvent e)
	{
		boolean search_status = true;
		for (int i = 1; i < 11; i++)
		{
			if (!search_status)
				break;
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
						buttons[i][j].setEnabled(false);

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
							if (observer.get_if_zero_counter())
							{
								game_status = false;
							}
						}
						ArrayList<int[]> off_but = observer.fill(grind);
						for (int[] p : off_but)
						{
							buttons[p[0]][p[1]].setBackground(Color.blue);
							buttons[p[0]][p[1]].setEnabled(false);
						}
					}
					search_status = false;
					break;
				}
			}
		}
		if (!game_status)
		{
			opponent.force_stop(observer.get_counter() == 0);
		}
	}
}

