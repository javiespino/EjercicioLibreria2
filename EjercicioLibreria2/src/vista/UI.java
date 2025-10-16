package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import utiles.Validaciones;

public class UI extends JFrame {

	protected static final long serialVersionUID = 1L;
	protected JPanel contentPane, panel, formato, formato_1;
	protected JLabel lblISBN, lblTitulo, lblAutor, lblFoto, lblCantidad, lblEstado, lblEditorial, lblPrecio, lblFormato,
			lblPrincipal;
	protected JButton btnGuardar, btnSalida, btnEditar, btnBorrar, btnConsultar, btnComprar, btnVender;
	protected JTextField textISBN, textEditorial, textAutor, textTitulo, textPrecio, textCantidad;
	protected JScrollPane scrollPane;
	protected JTable tablaLibros;
	protected JRadioButton rdbtnReedicion, rdbtnNovedad;
	protected JRadioButton rdbtnCartone, rdbtnRustica, rdbtnGrapada, rdbtnEspiral;
	protected ButtonGroup grupoFormato, grupoEstado;

	public UI() {
		setTitle("LIBRERIA DE JAVIER");
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(UI.class.getResource("/img/libros-removebg-preview (1).png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 674);
		contentPane = new JPanel();
		contentPane.setSize(new Dimension(10000, 10000));
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panelSuperior = new JPanel();
		panelSuperior.setBackground(new Color(128, 128, 0));
		contentPane.add(panelSuperior, BorderLayout.NORTH);
		panelSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lblPrincipal = new JLabel("LIBRERIA DE JAVIER");
		lblPrincipal.setForeground(new Color(255, 255, 255));
		lblPrincipal.setFont(new Font("Times New Roman", Font.PLAIN, 35));
		panelSuperior.add(lblPrincipal);

		JPanel panelInferior = new JPanel();
		panelInferior.setBackground(new Color(128, 128, 0));
		contentPane.add(panelInferior, BorderLayout.SOUTH);

		btnGuardar = new JButton("GUARDAR");
		btnGuardar.setIcon(new ImageIcon(UI.class.getResource("/img/save.png")));
		btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelInferior.add(btnGuardar);

		btnEditar = new JButton("EDITAR");
		btnEditar.setIcon(new ImageIcon(UI.class.getResource("/img/editar.png")));
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelInferior.add(btnEditar);

		btnBorrar = new JButton("BORRAR");
		btnBorrar.setIcon(new ImageIcon(UI.class.getResource("/img/basura.png")));

		btnBorrar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelInferior.add(btnBorrar);

		btnConsultar = new JButton("CONSULTAR");
		btnConsultar.setIcon(new ImageIcon(UI.class.getResource("/img/consultar.png")));
		btnConsultar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelInferior.add(btnConsultar);

		btnComprar = new JButton("COMPRAR");
		btnComprar.setIcon(new ImageIcon(UI.class.getResource("/img/comprar.png")));
		btnComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnComprar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelInferior.add(btnComprar);

		btnVender = new JButton("VENDER");
		btnVender.setIcon(new ImageIcon(UI.class.getResource("/img/vender.png")));
		btnVender.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelInferior.add(btnVender);

		btnSalida = new JButton("SALIR");
		btnSalida.setIcon(new ImageIcon(UI.class.getResource("/img/salida.png")));
		btnSalida.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelInferior.add(btnSalida);
		btnSalida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		grupoFormato = new ButtonGroup();
		grupoEstado = new ButtonGroup();

		JPanel panel = new JPanel();
		panel.setBackground(new Color(204, 204, 153));
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		ImageIcon iconoLibro = new ImageIcon(getClass().getResource("/img/libro.png"));
		tabbedPane.addTab("LIBRO", iconoLibro, panel, "Sección de libros");

		lblTitulo = new JLabel("Titulo:");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 24));

		lblISBN = new JLabel("ISBN:");
		lblISBN.setFont(new Font("Tahoma", Font.PLAIN, 24));

		lblAutor = new JLabel("Autor:");
		lblAutor.setFont(new Font("Tahoma", Font.PLAIN, 24));

		lblEditorial = new JLabel("Editorial:");
		lblEditorial.setFont(new Font("Tahoma", Font.PLAIN, 24));

		lblPrecio = new JLabel("Precio:");
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 24));

		textISBN = new JTextField();
		textISBN.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textISBN.setColumns(10);
		
		textEditorial = new JTextField();
		textEditorial.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textEditorial.setColumns(10);

		textAutor = new JTextField();
		textAutor.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textAutor.setColumns(10);

		textTitulo = new JTextField();
		textTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textTitulo.setColumns(10);

		textPrecio = new JTextField();
		textPrecio.setFont(new Font("Tahoma", Font.PLAIN, 24));
		textPrecio.setColumns(10);

		lblFoto = new JLabel("");
		lblFoto.setIcon(new ImageIcon(UI.class.getResource("/img/libros-removebg-preview (1).png")));

		formato = new JPanel();
		formato.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		lblCantidad = new JLabel("Cantidad:");
		lblCantidad.setFont(new Font("Tahoma", Font.PLAIN, 24));

		lblEstado = new JLabel("Estado:");
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 24));

		formato_1 = new JPanel();
		formato_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		rdbtnReedicion = new JRadioButton("Reedición");
		rdbtnReedicion.setFont(new Font("Tahoma", Font.PLAIN, 20));
		formato_1.add(rdbtnReedicion);

		rdbtnNovedad = new JRadioButton("Novedad");
		rdbtnNovedad.setFont(new Font("Tahoma", Font.PLAIN, 20));
		formato_1.add(rdbtnNovedad);

		rdbtnCartone = new JRadioButton("Cartoné");
		rdbtnCartone.setFont(new Font("Tahoma", Font.PLAIN, 20));
		formato.add(rdbtnCartone);

		rdbtnRustica = new JRadioButton("Rústica");
		rdbtnRustica.setFont(new Font("Tahoma", Font.PLAIN, 20));
		formato.add(rdbtnRustica);

		rdbtnGrapada = new JRadioButton("Grapada");
		rdbtnGrapada.setFont(new Font("Tahoma", Font.PLAIN, 20));
		formato.add(rdbtnGrapada);

		rdbtnEspiral = new JRadioButton("Espiral");
		rdbtnEspiral.setFont(new Font("Tahoma", Font.PLAIN, 20));
		formato.add(rdbtnEspiral);

		grupoFormato.add(rdbtnCartone);
		grupoFormato.add(rdbtnEspiral);
		grupoFormato.add(rdbtnGrapada);
		grupoFormato.add(rdbtnRustica);
		grupoEstado.add(rdbtnNovedad);
		grupoEstado.add(rdbtnReedicion);

		lblFormato = new JLabel("Formato:");
		lblFormato.setFont(new Font("Tahoma", Font.PLAIN, 24));

		textCantidad = new JTextField();
		textCantidad.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textCantidad.setColumns(10);

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGap(43)
						.addGroup(gl_panel
								.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(
												lblAutor, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
										.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel
												.createParallelGroup(Alignment.TRAILING)
												.addComponent(textAutor, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														357, Short.MAX_VALUE)
												.addComponent(textPrecio, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														357, Short.MAX_VALUE)
												.addComponent(textISBN, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														357, Short.MAX_VALUE)
												.addComponent(lblISBN, Alignment.LEADING, GroupLayout.PREFERRED_SIZE,
														123, GroupLayout.PREFERRED_SIZE)
												.addComponent(formato_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														357, Short.MAX_VALUE))
												.addGap(148))
										.addGroup(gl_panel.createSequentialGroup()
												.addComponent(lblPrecio, GroupLayout.PREFERRED_SIZE, 109,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)))
										.addGroup(gl_panel
												.createSequentialGroup()
												.addComponent(lblEstado, GroupLayout.PREFERRED_SIZE, 123,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED))))
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
								.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(formato, GroupLayout.PREFERRED_SIZE, 430,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 99, Short.MAX_VALUE))
								.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel
										.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel
												.createParallelGroup(Alignment.LEADING)
												.addComponent(textTitulo, GroupLayout.DEFAULT_SIZE, 335,
														Short.MAX_VALUE)
												.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
														.addComponent(lblTitulo, Alignment.LEADING)
														.addComponent(lblEditorial, Alignment.LEADING)
														.addComponent(textEditorial, Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
												.addComponent(lblCantidad)).addGap(86))
										.addGroup(gl_panel.createSequentialGroup()
												.addComponent(textCantidad, GroupLayout.PREFERRED_SIZE, 335,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblFoto, GroupLayout.PREFERRED_SIZE, 116,
												GroupLayout.PREFERRED_SIZE)
										.addGap(39)))
								.addGroup(gl_panel.createSequentialGroup().addComponent(lblFormato,
										GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
										.addContainerGap()))));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel
						.createParallelGroup(Alignment.LEADING,
								false)
						.addGroup(
								gl_panel.createSequentialGroup().addContainerGap()
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblISBN)
												.addComponent(lblTitulo))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(textISBN, GroupLayout.PREFERRED_SIZE, 34,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(textTitulo, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblEditorial).addComponent(lblAutor,
														GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
												.addComponent(textAutor, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(textEditorial, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(28)
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblPrecio).addComponent(lblCantidad))
										.addGap(18)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(textCantidad, GroupLayout.PREFERRED_SIZE, 31,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(textPrecio, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(Alignment.TRAILING,
								gl_panel.createSequentialGroup()
										.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblFoto).addGap(33)))
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(gl_panel.createSequentialGroup().addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblEstado, GroupLayout.PREFERRED_SIZE, 37,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(lblFormato, GroupLayout.PREFERRED_SIZE, 29,
														GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
												.addComponent(formato, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(formato_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE,
														51, Short.MAX_VALUE))))
						.addGap(125)));

		panel.setLayout(gl_panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(204, 204, 153));
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		ImageIcon iconoBiblioteca = new ImageIcon(getClass().getResource("/img/libros.png"));
		tabbedPane.addTab("BIBLIOTECA", iconoBiblioteca, panel_1, "Sección de biblioteca");


		scrollPane = new JScrollPane();
		panel_1.add(scrollPane);

		tablaLibros = new JTable();
		tablaLibros.setBackground(new Color(204, 204, 153));
		tablaLibros.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPane.setViewportView(tablaLibros);
	}
}