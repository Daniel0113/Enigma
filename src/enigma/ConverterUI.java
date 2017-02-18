package enigma;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ConverterUI extends JFrame
{
	private JTextArea codeInput = new JTextArea();
	private JTextArea codeOutput = new JTextArea();
	private JTextField directory = new JTextField(20);
	
	private JButton decode = new JButton("Decode");
	private JButton encode = new JButton("Encode");
	private JButton browse = new JButton("Browse");
	private JButton useFileAsInput = new JButton ("Use File As Input");
	
	private JPanel mainPanel = new JPanel();
	private JPanel middlePanel = new JPanel();
	private JPanel lowerPanel = new JPanel();
	private JPanel upperPanel = new JPanel();
	
	private File inputFile;
	
	JFileChooser filechooser = new JFileChooser();
	
	private Enigma enigma;
	
	public ConverterUI(Enigma e)
	{
		enigma = e;
		buildUI();
		addActionListeners();
	}
	
	private void buildUI()
	{
		middlePanel.setLayout(new GridLayout(1, 2, 5, 0));
		middlePanel.add(new JScrollPane(codeInput));
		middlePanel.add(new JScrollPane(codeOutput));
		middlePanel.setBorder(BorderFactory.createTitledBorder("Converter: Input / Output"));
		codeOutput.setEditable(false);
		
		lowerPanel.add(decode);
		lowerPanel.add(encode);
		lowerPanel.add(browse);
		lowerPanel.add(directory);
		lowerPanel.add(useFileAsInput);
		lowerPanel.setBorder(BorderFactory.createTitledBorder("Controls"));
		directory.setEditable(false);
		useFileAsInput.setEnabled(false);
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		
		add(mainPanel);
		setSize(800, 600);
		setTitle("Enigma Machine");
		setResizable(false);
		setLocationRelativeTo(null);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void addActionListeners()
	{
		decode.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				codeOutput.setText(enigma.decipher(codeInput.getText(), Enigma.DECRYPT));
			}
			
		});
		
		encode.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				codeOutput.setText(enigma.decipher(codeInput.getText(), Enigma.ENCRYPT));
			}
			
		});
		
		browse.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				filechooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
				if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					inputFile = filechooser.getSelectedFile();
					directory.setText(inputFile.getAbsolutePath());
					useFileAsInput.setEnabled(true);
				}
			}
			
		});
		
		useFileAsInput.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				try
				{
					useInputFromFile();
				}
				catch (FileNotFoundException e)
				{
					
				}
			}
			
		});
		
	}
	
	private void useInputFromFile() throws FileNotFoundException
	{
		String output = "";
		Scanner fileReader = new Scanner(inputFile);
		while(fileReader.hasNextLine())
		{
			output += fileReader.nextLine() + "\n";
		}
		
		codeInput.setText(output);
	}
	
}
