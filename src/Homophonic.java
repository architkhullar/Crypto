/*************************************************************************/
/*Cryptography Techniques over Block Cipher       */
/*-----------------------------------------------------------------------*/
/*-----------------------------------------------------------------------*/
/*This program provides a tool to encrypt and decrypt text using the     */
/*Homophonic Cipher.                                                     */
/*Encryption: The user loads in plain text and the program displays      */
/*choice boxes which contain the number of times that each letter appears*/
/*in the plain text. The user can then choose the number of keys that he */
/*wants to use to encrypt each letter. The max number of keys for a      */
/*is the number of times the letter appears in the plain text and the min*/
/*is 1. If a letter does not appear in the plaintext, it does not have   */
/*any keys. The program then generates random unique keys for each letter*/
/*and assigns them to the respective characters randomly. The cipher text*/
/*and key is then displayed to the user with the option to save it in a  */
/*file.                                                                  */
/*Decryption: The user loads in the ciphertext and keytext and the       */
/*program uses the keytext to generate the plaintext, which is then      */
/*displayed.                                                             */
/*Error checking is performed to ensure that plaintext, ciphertext, and  */
/*key file is valid.                                                     */
/*-----------------------------------------------------------------------*/
/*Limitations: The user must load the ciphertext, plaintext, and keytext */
/*from a file because the text areas are not editable. Also, the keyfile */
/*is tested for the right format. However, if a user uses a wrong keyfile*/
/*for deciphering (mismatching the keyfiles), no error is displayed and  */
/*the plaintext will be incorrectly generated.                           */
/*************************************************************************/

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Homophonic extends Frame implements WindowListener {
	/* Menu Components */
	MenuBar menuBar;
	Menu fileMenu;
	MenuItem openPT;
	MenuItem openCT;
	MenuItem openKey;
	MenuItem savePT;
	MenuItem saveCT;
	MenuItem saveKey;
	MenuItem restart;
	MenuItem quit;

	/* Encrypt & Decrypt Buttons for main menu */
	Button encrypt;
	Button decrypt;

	/* Panel and components used to display the */
	/* frequency of each letter in plain text */
	Panel letterPanel;
	Choice count[];
	Label letter[];

	/* Text areas */
	TextArea cipherText;
	TextArea keyText;
	TextArea plainText;

	/* Buttons for enciphering/deciphering the text */
	Button encryptSubmit;
	Button decryptSubmit;

	/* Array used to store the keys for each letter */
	int keys[][];

	/* Total number of keys */
	int total_keys;

	/* Boolean indicating if there were any file errrors */
	boolean error;

	/* Mode working in: Encrypt or Decrypt */
	String mode;

	/* Handels Window Events */
	public void windowActivated(WindowEvent e) {
		;
	}

	public void windowClosed(WindowEvent e) {
		;
	}

	public void windowDeactivated(WindowEvent e) {
		;
	}

	public void windowDeiconified(WindowEvent e) {
		;
	}

	public void windowIconified(WindowEvent e) {
		;
	}

	public void windowOpened(WindowEvent e) {
		;
	}

	/* Destroy Window and Exit */
	public void windowClosing(WindowEvent e) {
		setVisible(false);
		dispose();
		System.exit(0);
	}

	/*****************************************************************/
	/* Homophonic(): Initializes frame with Title, Foreground & */
	/* Background Color. */
	/*****************************************************************/

	public Homophonic() {
		super("A Competitive Study of Cryptography Techniques over Block Cipher");
		this.setBackground(java.awt.Color.white);
		this.setForeground(new java.awt.Color(0, 0, 100));

		this.addWindowListener(this);
	}

	/*****************************************************************/
	/* main(): Creates a new Homophonic object and start it up. */
	/*****************************************************************/

	public static void main(String args[]) {
		Homophonic h = new Homophonic();
		h.startup();
	}

	/*****************************************************************/
	/* startup(): Sets up the main menu. */
	/*****************************************************************/

	public void startup() {
		Label titleLabel, nameLabel;

		setupMenuBar();

		encrypt = new Button("Encrypt");
		encrypt.setBounds(139, 91, 125, 30);
		decrypt = new Button("Decrypt");
		decrypt.setBounds(139, 133, 125, 30);

		titleLabel = new java.awt.Label();
		titleLabel.setFont(new java.awt.Font("Serif", 3, 16));
		titleLabel.setText("A Competitive Study of Cryptography Techniques over Block Cipher");
		titleLabel.setBounds(42, 51, 356, 34);

		nameLabel = new java.awt.Label();
		nameLabel.setFont(new java.awt.Font("Serif", 3, 14));

		nameLabel.setBounds(270, 204, 140, 30);

		this.add(encrypt);
		this.add(decrypt);
		this.add(titleLabel);
		this.add(nameLabel);

		this.setLayout(null);
		this.setSize(426, 240);
		this.pack();
		this.setVisible(true);

		/* Bring up the appropriate panels when user clicks Encrypt */
		/* or Decrypt */

		encrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupEncryptUserPanel();
			}
		});

		decrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupDecryptUserPanel();
			}
		});

	}

	/*****************************************************************/
	/* setupMenuBar(): Creates menu components and assigns attributes */
	/* and places them on the window. */
	/*****************************************************************/

	public void setupMenuBar() {
		menuBar = new MenuBar();
		fileMenu = new Menu("File");
		openPT = new MenuItem("Load Plain Text");
		openCT = new MenuItem("Load Cipher Text");
		openKey = new MenuItem("Load Key");
		savePT = new MenuItem("Save Plain Text");
		saveCT = new MenuItem("Save Cipher Text");
		saveKey = new MenuItem("Save Key");
		restart = new MenuItem("Restart");
		quit = new MenuItem("Quit");
	}

	/*****************************************************************/
	/* setupEncryptUserPanel(): sets up the panel that is displayed */
	/* when user chooses "ENCRYPT" from the main panel. */
	/*****************************************************************/

	public void setupEncryptUserPanel() {
		this.setVisible(false);
		this.removeAll();

		fileMenu.add(openPT);
		fileMenu.add(restart);
		fileMenu.add(quit);
		menuBar.add(fileMenu);
		this.setMenuBar(menuBar);

		plainText = new TextArea(8, 30);
		plainText.setBounds(7, 97, 619, 129);
		Label PTLabel = new Label();
		PTLabel.setFont(new java.awt.Font("Serif", 3, 14));
		PTLabel.setText("Plain Text:");
		PTLabel.setBounds(9, 68, 125, 30);

		letter = new Label[26];
		count = new Choice[26];

		Label CCLabel = new Label();
		CCLabel.setFont(new java.awt.Font("Serif", 3, 14));
		CCLabel.setText("Number of Keys per Character:");
		CCLabel.setBounds(9, 236, 248, 27);

		encryptSubmit = new Button("Encrypt");
		encryptSubmit.setBounds(15, 476, 125, 30);
		encryptSubmit.setEnabled(false);

		Label titleLabel = new Label();
		titleLabel.setFont(new java.awt.Font("Serif", 3, 16));
		titleLabel.setText("A Competitive Study of Cryptography Techniques over Block Cipher");
		titleLabel.setBounds(143, 50, 356, 34);

		Label nameLabel = new Label();
		nameLabel.setFont(new java.awt.Font("Serif", 3, 14));
		nameLabel.setBounds(510, 524, 145, 30);

		this.add(plainText);
		this.add(encryptSubmit);
		this.add(titleLabel);
		this.add(nameLabel);
		this.add(PTLabel);
		this.add(CCLabel);
		this.add(encryptSubmit);

		this.setLayout(null);
		this.setSize(675, 564);
		this.pack();
		this.setVisible(true);

		/* When user wants to open the plaintext, read it and place */
		/* it in the text area and also count the frequency of each */
		/* letter and display the choice boxes */

		openPT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				plainText = LoadText(plainText);
				countChars();
				pack();

			}
		});

		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				dispose();
				Homophonic h = new Homophonic();
				h.startup();
			}
		});

		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				dispose();
				System.exit(0);
			}
		});

		/* When user clicks the Encrypt button, encrypt the plain */
		/* text. If any error occurs with the file, display the */
		/* error panel. */

		encryptSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					encryptPlainText();
				}

				catch (Exception ex) {
					error = true;
				}

				if (error == false)
					setupEncryptCipherPanel();
				else {
					mode = new String("Encrypt");
					setupErrorPanel();
				}
			}
		});
	}

	/*****************************************************************/
	/* encryptPlain Text(): Essentially encrypts the cipher text. It */
	/* reads in the number of keys needed for each letter and */
	/* generates the random keys. Then it calls on replaceLetters() to */
	/* replace create the ciphertext. */
	/*****************************************************************/

	public void encryptPlainText() {
		int numkeys[] = new int[26];
		int nextnum;

		keys = new int[26][];
		total_keys = 0;

		cipherText = new TextArea(8, 30);
		keyText = new TextArea(8, 30);

		/* Retrieves from the checkboxes the number of keys per */
		/* letter and the total number of keys. */

		for (int i = 0; i < 26; i++) {
			numkeys[i] = count[i].getSelectedIndex() + 1;
			keys[i] = new int[numkeys[i]];
			total_keys += numkeys[i];
		}

		int allKeys[] = new int[total_keys];

		/* Generates the keys (Intger between 1-1000000) */

		Random r = new Random();
		int i = 0;
		while (true) {
			boolean repeat = false;
			nextnum = (java.lang.Math.abs(r.nextInt())) % 1000000;

			/* Make sure the keys are unique */
			for (int j = 0; j < i; j++) {
				if (nextnum == allKeys[j])
					repeat = true;
			}
			if (repeat == true)
				continue;
			allKeys[i] = nextnum;
			if (i == total_keys - 1)
				break;
			i++;
		}

		i = 0;
		for (int k = 0; k < 26; k++) {
			int len = keys[k].length;
			for (int j = 0; j < len; j++) {
				keys[k][j] = allKeys[i];
				i++;
				if (i == total_keys)
					break;
			}
			if (i == total_keys)
				break;
		}
		replaceLetters();

	}

	/*****************************************************************/
	/* replaceLetters():Retrieves the plain text from the text area */
	/* and uses the key array to encrypt. It essentially replaces the */
	/* letters with numbers. It displays the ciphertext and key text */
	/* in the text areas. */
	/*****************************************************************/

	public void replaceLetters() {
		String text_to_encipher = new String(plainText.getText());
		int random_index, num_choices, index;
		char char_to_encrypt;
		Random r = new Random();

		for (int i = 0; i < text_to_encipher.length(); i++) {
			char_to_encrypt = text_to_encipher.charAt(i);

			/* If the character is a letter, choose a random key and */
			/* replace the letter with the number. */

			if (java.lang.Character.isLetter(char_to_encrypt)) {
				index = MapAlphabet(char_to_encrypt);
				num_choices = keys[index].length;

				/* From the keys assigned to the character, choose a */
				/* random key */

				random_index = (java.lang.Math.abs(r.nextInt())) % num_choices;
				Integer k = new Integer(keys[index][random_index]);
				cipherText.append(k.toString() + " ");
			}

			/* Otherwise, just append the character */
			else {
				Character c = new Character(char_to_encrypt);
				cipherText.append(c.toString() + " ");
			}
		}

		/* append the keytext to the text area */

		for (int i = 0; i < 26; i++) {
			Character alpha = new Character(MapNumber(i));
			Integer num = new Integer(keys[i].length);

			keyText.append(num.toString() + " " + alpha.toString() + ":\n");

			for (int j = 0; j < keys[i].length; j++) {
				Integer key = new Integer(keys[i][j]);
				keyText.append(key.toString() + "\n");
			}
			keyText.append("\n");
		}

	}

	/*****************************************************************/
	/* setupEncryptCipherPanel(): sets up the panel that is used to */
	/* display the cipher text after encryption. */
	/*****************************************************************/

	public void setupEncryptCipherPanel() {
		this.setVisible(false);
		this.removeAll();

		fileMenu.removeAll();
		fileMenu.add(saveCT);
		fileMenu.add(saveKey);
		fileMenu.add(restart);
		fileMenu.add(quit);
		menuBar.add(fileMenu);
		this.setMenuBar(menuBar);

		cipherText.setEditable(false);
		keyText.setEditable(false);
		cipherText.setBounds(7, 97, 619, 129);
		keyText.setBounds(7, 270, 619, 129);

		Label titleLabel = new Label();
		titleLabel.setFont(new java.awt.Font("Serif", 3, 16));
		titleLabel.setText("Cryptography Techniques over Block Cipher");
		titleLabel.setBounds(143, 50, 356, 34);

		Label nameLabel = new Label();
		nameLabel.setFont(new java.awt.Font("Serif", 3, 14));
		nameLabel.setBounds(471, 427, 155, 31);

		Label CTLabel = new Label();
		CTLabel.setFont(new java.awt.Font("Serif", 3, 14));
		CTLabel.setText("Cipher Text:");
		CTLabel.setBounds(9, 68, 125, 30);

		Label KeyLabel = new Label();
		KeyLabel.setFont(new java.awt.Font("Serif", 3, 14));
		KeyLabel.setText("Key:");
		KeyLabel.setBounds(9, 240, 125, 30);

		this.add(cipherText);
		this.add(keyText);
		this.add(titleLabel);
		this.add(nameLabel);
		this.add(CTLabel);
		this.add(KeyLabel);

		this.setLayout(null);
		this.setSize(631, 467);
		this.pack();
		this.setVisible(true);

		saveCT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				saveText(cipherText);
			}
		});

		saveKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				saveText(keyText);
			}
		});

	}

	/*****************************************************************/
	/* saveText(): Bring up a File Dialog Box and save text in given */
	/* TextArea to file. */
	/*****************************************************************/

	public void saveText(TextArea T) {
		try {
			FileDialog fd = new FileDialog(this, "Save", FileDialog.SAVE);
			fd.show();
			String s = fd.getFile();
			if (s != null) {
				File outf = new File(s);
				PrintWriter out = new PrintWriter(new FileOutputStream(outf));
				String st = T.getText();
				out.print(st);
				out.flush();
				out.close();
			}
		}

		catch (IOException se) {
			System.err.println("Caught" + se);
		}
	}

	/*****************************************************************/
	/* setupDecryptUserPanel(): sets up the panel which is displayed */
	/* when user chooses the "Decrypt" option from the initial screen */
	/*****************************************************************/

	public void setupDecryptUserPanel() {
		this.setVisible(false);
		this.removeAll();

		/* Menu Components */
		fileMenu.add(openCT);
		fileMenu.add(openKey);
		fileMenu.add(restart);
		fileMenu.add(quit);
		menuBar.add(fileMenu);
		this.setMenuBar(menuBar);

		/* Text Areas & Button */
		cipherText = new TextArea(8, 30);
		keyText = new TextArea(8, 30);
		decryptSubmit = new Button("Decrypt");

		cipherText.setEditable(false);
		keyText.setEditable(false);
		decryptSubmit.setEnabled(false);
		cipherText.setBounds(7, 97, 619, 129);
		keyText.setBounds(7, 270, 619, 129);
		decryptSubmit.setBounds(11, 411, 125, 30);

		/* Labels */
		Label titleLabel = new Label();
		titleLabel.setFont(new java.awt.Font("Serif", 3, 16));
		titleLabel.setText("Cryptography Techniques over Block Cipher");
		titleLabel.setBounds(143, 50, 356, 34);

		Label nameLabel = new Label();
		nameLabel.setFont(new java.awt.Font("Serif", 3, 14));
		nameLabel.setBounds(480, 429, 155, 31);

		Label CTLabel = new Label();
		CTLabel.setFont(new java.awt.Font("Serif", 3, 14));
		CTLabel.setText("Cipher Text:");
		CTLabel.setBounds(9, 68, 125, 30);

		Label KeyLabel = new Label();
		KeyLabel.setFont(new java.awt.Font("Serif", 3, 14));
		KeyLabel.setText("Key:");
		KeyLabel.setBounds(9, 241, 125, 30);

		this.add(cipherText);
		this.add(keyText);
		this.add(decryptSubmit);
		this.add(titleLabel);
		this.add(nameLabel);
		this.add(CTLabel);
		this.add(KeyLabel);

		this.setLayout(null);
		this.setSize(631, 467);
		this.pack();
		this.setVisible(true);

		/* When user chooses to open the ciphertext, load the file into */
		/* the text area and enable the decrypt button. */

		openCT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				cipherText = LoadText(cipherText);
				cipherText.setBounds(7, 97, 619, 129);

				if (cipherText.getText().length() != 0 && keyText.getText().length() != 0)
					decryptSubmit.setEnabled(true);

				pack();
			}
		});

		/* When user chooses to open the keytext, load the file into */
		/* the text area and enable the decrypt button. */

		openKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				keyText = LoadText(keyText);
				keyText.setBounds(7, 270, 619, 129);

				if (cipherText.getText().length() != 0 && keyText.getText().length() != 0)
					decryptSubmit.setEnabled(true);

				pack();
			}
		});

		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				dispose();
				Homophonic h = new Homophonic();
				h.startup();
			}
		});

		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				dispose();
				System.exit(0);
			}
		});

		/* When user chooses clicks the decrypt button, decrypt the */
		/* the ciphertext. If any problems with the files, call on */
		/* setupErrorPanel(). */

		error = false;
		decryptSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					decryptCipherText();
				}

				catch (Exception ex) {
					error = true;
				}

				if (error == false)
					setupDecryptCipherPanel();
				else {
					mode = new String("Decrypt");
					setupErrorPanel();
				}
			}
		});
	}

	/*****************************************************************/
	/* setupErrorPanel(): sets up the panel that is displayed when */
	/* user loads an incorrect cipher, plain, or key text. */
	/*****************************************************************/

	public void setupErrorPanel() {

		Label errorLabel = new Label("Error: Incorrect File Format", Label.CENTER);
		Button errorButton = new Button("OK");

		this.removeAll();
		this.setMenuBar(null);

		errorButton.setBounds(50, 91, 150, 30);
		errorLabel.setFont(new java.awt.Font("Serif", 1, 14));
		errorLabel.setBounds(40, 51, 175, 34);

		this.add(errorButton);
		this.add(errorLabel);

		this.setLayout(null);
		this.setSize(250, 150);
		this.pack();
		this.setVisible(true);

		/* When user clicks OK button, bring them back to the screen */
		/* they were on. */
		errorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
				Homophonic h = new Homophonic();
				h.setupMenuBar();
				if (mode.equals("Decrypt"))
					h.setupDecryptUserPanel();
				if (mode.equals("Encrypt"))
					h.setupEncryptUserPanel();
			}
		});

	}

	/*****************************************************************/
	/* decryptCipherText(): Essentially decrypts the cipher text. It */
	/* reads the key text and parses it and develops the key array. It */
	/* then calls on ReplaceNumbers() to map the numbers with the */
	/* appropriate character. */
	/*****************************************************************/

	public void decryptCipherText() {

		String key_text = new String(keyText.getText());
		plainText = new TextArea(8, 30);

		int first_newline, next_newline;

		keys = new int[26][];

		int start = 0;
		for (int i = 0; i < 26; i++) {
			int space = key_text.indexOf(' ', start);
			String num = new String(key_text.substring(start, space));
			Integer num_char_key = new Integer(num);
			int num_key = num_char_key.intValue();
			keys[i] = new int[num_key];

			first_newline = key_text.indexOf('\n', space);
			for (int j = 0; j < num_key; j++) {
				next_newline = key_text.indexOf("\n", first_newline + 1);
				Integer val = new Integer(key_text.substring(first_newline + 1, next_newline));
				keys[i][j] = val.intValue();
				first_newline = next_newline;
			}
			start = first_newline + 2;
		}
		replaceNumbers();
	}

	/*****************************************************************/
	/* replaceNumbers(): Retrieves the ciphertext from the text area */
	/* and uses the key array to decrypt. It essentially replaces the */
	/* numbers with letters. */
	/*****************************************************************/

	public void replaceNumbers() {
		String text_to_decipher = new String(cipherText.getText().trim());

		/* used to identify end of ciphertext */
		text_to_decipher = text_to_decipher.concat(" eof ");
		int start = 0;
		int letter_idx = 0;
		String word;
		int end;

		while (start < text_to_decipher.length()) {

			/* Parses a number */
			end = text_to_decipher.indexOf(" ", start);
			word = text_to_decipher.substring(start, end);

			if (word.equals("eof"))
				break;

			/* If the word is a number, decrypt it and append it */
			if (isNumber(word)) {
				Integer val = new Integer(word);

				for (int i = 0; i < 26; i++) {
					for (int j = 0; j < keys[i].length; j++) {
						if (val.intValue() == keys[i][j]) {
							letter_idx = i;
							break;
						}
					}
				}
				Character letter = new Character(MapNumber(letter_idx));
				plainText.append(letter.toString());
			}

			/* just append the character */
			else
				plainText.append(word);

			/* increment start to the index of the next character that is not a space */
			start = end + 1;
			while (true) {
				if ((start < text_to_decipher.length()) && (text_to_decipher.charAt(start) == ' ')) {
					plainText.append(" ");
					start++;
				} else
					break;
			}
		}

	}

	/*****************************************************************/
	/* isNumber(): takes in a string and returns true if the string */
	/* is composed of all digits. */
	/*****************************************************************/

	public boolean isNumber(String word) {
		if (word == null)
			return false;
		for (int i = 0; i < word.length(); i++) {
			if (java.lang.Character.isDigit(word.charAt(i)) == false)
				return false;
		}
		return true;
	}

	/*****************************************************************/
	/* setupDecryptCipherPanel(): Sets up the panel that is displayed */
	/* to show the plaintext after decryption. */
	/*****************************************************************/

	public void setupDecryptCipherPanel() {
		this.setVisible(false);
		this.removeAll();

		fileMenu.removeAll();
		fileMenu.add(savePT);
		fileMenu.add(restart);
		fileMenu.add(quit);
		menuBar.add(fileMenu);
		this.setMenuBar(menuBar);

		plainText.setEditable(false);
		plainText.setBounds(7, 97, 619, 129);

		Label titleLabel = new Label();
		titleLabel.setFont(new java.awt.Font("Serif", 3, 16));
		titleLabel.setText("Cryptography Techniques over Block Cipher");
		titleLabel.setBounds(143, 50, 356, 34);

		Label nameLabel = new Label();
		nameLabel.setFont(new java.awt.Font("Serif", 3, 14));
		nameLabel.setBounds(480, 239, 155, 31);

		Label PTLabel = new Label();
		PTLabel.setFont(new java.awt.Font("Serif", 3, 14));
		PTLabel.setText("Plain Text:");
		PTLabel.setBounds(9, 68, 125, 30);

		this.add(plainText);
		this.add(titleLabel);
		this.add(nameLabel);
		this.add(PTLabel);

		this.setLayout(null);
		this.setSize(631, 269);
		this.pack();
		this.setVisible(true);

		savePT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				saveText(plainText);
			}
		});

	}

	/*****************************************************************/
	/* countChars(): Counts the number of times each character appears */
	/* in the plaintext and creates the checkboxes appropriately. */
	/*****************************************************************/

	public void countChars() {
		letter = new Label[26];
		count = new Choice[26];

		String text = new String(plainText.getText());

		if (letterPanel != null)
			this.remove(letterPanel);

		letterPanel = new Panel();
		letterPanel.setLayout(new GridLayout(7, 8));

		int char_cnt[] = new int[26];
		int index;
		Character c;

		/* Counts the number of times each character appears */

		for (int i = 0; i < text.length(); i++) {
			c = new Character(text.charAt(i));
			if (c.isLetter(text.charAt(i))) {
				index = MapAlphabet(c.charValue());
				char_cnt[index] = char_cnt[index] + 1;
			}
		}

		/* Creates the checkboxes appropriately */
		for (int i = 0; i < 26; i++) {
			count[i] = new Choice();
			for (int j = 1; j <= char_cnt[i]; j++) {
				Integer value = new Integer(j);
				count[i].add(value.toString());
			}

			c = new Character(MapNumber(i));
			letter[i] = new Label();
			letter[i].setText(c.toString().toUpperCase() + ":");
			letter[i].setAlignment(Label.CENTER);

			letterPanel.add(letter[i]);
			letterPanel.add(count[i]);
		}

		letterPanel.setBounds(11, 268, 616, 200);
		encryptSubmit.setEnabled(true);

		this.add(letterPanel);

	}

	/*****************************************************************/
	/* LoadText(): Bring up File Dialog box and load ciphertext into */
	/* text area. */
	/*****************************************************************/

	public TextArea LoadText(TextArea T) {
		File inf;

		try {
			FileDialog fd = new FileDialog(this, "Open", FileDialog.LOAD);
			fd.show();
			String s = fd.getFile();
			if (s != null) {
				T.setText("");
				inf = new File(s);
				BufferedReader in = new BufferedReader(new FileReader(inf));
				String l = new String();
				while ((l = in.readLine()) != null) {
					T.append(l.toLowerCase() + "\n");
				}
			}
			T.setEditable(false);

		} catch (IOException b) {
			System.err.println("Caught: " + b);
		}
		return T;
	}

	/*****************************************************************/
	/* MapAlphabet: Takes in an alphabetic character and returns the */
	/* Integer index of it. */
	/*****************************************************************/

	public int MapAlphabet(char c) {
		if (c == 'a')
			return 0;
		if (c == 'b')
			return 1;
		if (c == 'c')
			return 2;
		if (c == 'd')
			return 3;
		if (c == 'e')
			return 4;
		if (c == 'f')
			return 5;
		if (c == 'g')
			return 6;
		if (c == 'h')
			return 7;
		if (c == 'i')
			return 8;
		if (c == 'j')
			return 9;
		if (c == 'k')
			return 10;
		if (c == 'l')
			return 11;
		if (c == 'm')
			return 12;
		if (c == 'n')
			return 13;
		if (c == 'o')
			return 14;
		if (c == 'p')
			return 15;
		if (c == 'q')
			return 16;
		if (c == 'r')
			return 17;
		if (c == 's')
			return 18;
		if (c == 't')
			return 19;
		if (c == 'u')
			return 20;
		if (c == 'v')
			return 21;
		if (c == 'w')
			return 22;
		if (c == 'x')
			return 23;
		if (c == 'y')
			return 24;
		if (c == 'z')
			return 25;
		return 100;
	}

	/*****************************************************************/
	/* MapNumber(): Takes in an integer and returns the character that */
	/* the integer maps to. */
	/*****************************************************************/

	public char MapNumber(int i) {
		if (i == 0)
			return 'a';
		if (i == 1)
			return 'b';
		if (i == 2)
			return 'c';
		if (i == 3)
			return 'd';
		if (i == 4)
			return 'e';
		if (i == 5)
			return 'f';
		if (i == 6)
			return 'g';
		if (i == 7)
			return 'h';
		if (i == 8)
			return 'i';
		if (i == 9)
			return 'j';
		if (i == 10)
			return 'k';
		if (i == 11)
			return 'l';
		if (i == 12)
			return 'm';
		if (i == 13)
			return 'n';
		if (i == 14)
			return 'o';
		if (i == 15)
			return 'p';
		if (i == 16)
			return 'q';
		if (i == 17)
			return 'r';
		if (i == 18)
			return 's';
		if (i == 19)
			return 't';
		if (i == 20)
			return 'u';
		if (i == 21)
			return 'v';
		if (i == 22)
			return 'w';
		if (i == 23)
			return 'x';
		if (i == 24)
			return 'y';
		if (i == 25)
			return 'z';
		return '%';
	}

}
