package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Главный класс, отвечающий за прорисовку полей, кнопок, добавления панелей
 */

class Field extends JFrame implements ActionListener, def
{

	public static int field_size = 50;                 //коэффициент размера поля
	private static final int panel_position_x = 10;    //положение начальной панели
	private static final int panel_position_y = 100;   // -//-
	private static final int button_long = 100;        // -//-
	private static final int button_height = 50;       // -//-
	private static final int button_space = 20;        //расстояние от верхней границы окна до кнопки "start" и текста
	public static int space = 30;                      //расстоянием между панельями

	/**
	 * Кнопка старта игры.
	 * TODO: Активна тогда и только тогда, когда поле игрока заполнено и заполнено правильно
	 */
	private final JButton start_button;
	/**
	 * Панели полей игрока и соперника
	 */
	My_board my_board;
	Opponents_board opponents_board;
	/**
	 * В конструкторе просто инициализируются все поля
	 */
	Field()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(FIELD_WIGHT, FIELD_HEIGHT);
		this.setResizable(false);
		my_board = new My_board();
		opponents_board = new Opponents_board();
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//Верхняя панель с кнопкой старта и подписями игроков
		JPanel upper_panel = new JPanel();
		upper_panel.setSize(FIELD_WIGHT, PANEL_HEIGHT);
		upper_panel.setLocation(0, 0);
		upper_panel.setLayout(null);    //сбрасываем настроки компоновки панели, чтобы расположить текст и кнопку так, как хотим
		Font text_format = new Font(Font.SERIF, Font.BOLD + Font.ITALIC, 30);    //Заготовили шрифт
		//Подпись "Ваш флот":
		JLabel your_fleet_l = new JLabel("Your fleet");
		your_fleet_l.setFont(text_format);                                                // Установить шрифт в метке
		your_fleet_l.setForeground(Color.black);                                          // Устанавливаем цвет текста в метке
		your_fleet_l.setBounds(250, button_space, 500, button_height);
		upper_panel.add(your_fleet_l);
		//Подпись "Флот оппонента":
		JLabel your_op_fleet_l = new JLabel("Your opponent's fleet");
		your_op_fleet_l.setFont(text_format);                                            // Установить шрифт в метке
		your_op_fleet_l.setForeground(Color.black);                                      // Устанавливаем цвет текста в метке
		your_op_fleet_l.setBounds(950, button_space, 500, button_height);
		upper_panel.add(your_op_fleet_l);
		//Кнопка начала игры:
		start_button = new JButton("Start");

		start_button.setFont(new Font(Font.SERIF, Font.BOLD + Font.ITALIC, 20));//Шрифт такой же, как и раньше, но совсем другой
		start_button.setBackground(Color.red);
		start_button.setForeground(Color.white);
		start_button.setBounds(field_size * 12 + 2 * space, button_space, button_long, button_height);
		start_button.addActionListener(this);
		upper_panel.add(start_button);       //добавляем кнопку в панель

		this.add(upper_panel);               //добавляем панель в фрейм
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		my_board.setLocation(panel_position_x, panel_position_y);
		opponents_board.setLocation(field_size * 12 + 2 * space + button_long + space, panel_position_y);
		this.add(my_board);
		this.add(opponents_board);
		this.setVisible(true);
	}
	private void disable_start_button()
	{
		start_button.setBackground(Color.black);
		start_button.setText("Started");
		start_button.setEnabled(false);
	}
	/**
	 * Метод работает с кнопкой старта игры
	 * TODO: реализовать кнопку
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == start_button)
		{
			System.out.println("Started!");

			disable_start_button();

			my_board.set_start();
			opponents_board.set_start();
		}
	}
}
