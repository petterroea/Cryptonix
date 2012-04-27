import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class FileEncrypt extends JFrame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new FileEncrypt();

	}
	File encryptFile;
	File decryptFile;
	File corruptFile;
	JLabel encryptLabel = new JLabel("NO FILE");
	JLabel decryptLabel = new JLabel("NO FILE");
	JLabel corruptLabel = new JLabel("NO FILE");
	JPasswordField pass;
	public FileEncrypt()
	{
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		this.setSize(450, 500);
		this.setTitle("Cryptonix v1.0 - Protecting your files since 2012!");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		pass = new JPasswordField();
		pass.setPreferredSize(new Dimension(300, 40));
		pass.setMaximumSize(new Dimension(300, 40));
		pass.setMinimumSize(new Dimension(40, 40));
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		JTabbedPane tabbedPane = new JTabbedPane();
		//"Shared"
		JButton sendTocorrupt = new JButton("Send decrypted(original) file to corrupter");
		sendTocorrupt.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				corruptFile = encryptFile;
				corruptLabel.setText(encryptLabel.getText());
			}});
		final JFileChooser fc = new JFileChooser();
		//Encrypt panel
		JPanel encrypt = new JPanel();
		encrypt.setLayout(new GridLayout(5, 1));
		encrypt.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		JButton encryptadd = new JButton("Select file...");
		encrypt.add(encryptadd);
		encrypt.add(encryptLabel);
		JButton startEncrypt = new JButton("Encrypt");
		startEncrypt.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				encrypt();
				
			}});
		encrypt.add(startEncrypt);
		JLabel notice = new JLabel("The encrypted file will be named #Name#.encrypted");
		encrypt.add(notice);
		encrypt.add(sendTocorrupt);
		final JFrame me = this;
		encryptadd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(me);
				if(returnVal==JFileChooser.APPROVE_OPTION)
				{
					encryptFile = fc.getSelectedFile();
					encryptLabel.setText(fc.getSelectedFile().getAbsolutePath());
				}
				
			}});
		
		
		//Decrypt panel
		JPanel decrypt = new JPanel();
		decrypt.setLayout(new GridLayout(5, 1));
		decrypt.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		JButton decryptadd = new JButton("Select file...");
		decrypt.add(decryptadd);
		decrypt.add(decryptLabel);
		JButton startDecrypt = new JButton("Decrypt");
		startDecrypt.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				decrypt();
				
			}});
		decrypt.add(startDecrypt);
		notice = new JLabel("The decrypted file will be named #Name#");
		decrypt.add(notice);
		JButton sendDecryptTocorrupt = new JButton("Send decrypted(new) file to corrupter");
		decrypt.add(sendDecryptTocorrupt);
		sendDecryptTocorrupt.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				corruptFile = new File(decryptFile.getAbsolutePath()+".decrypted");
				corruptLabel.setText(decryptLabel.getText());
			}});
		decryptadd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(me);
				if(returnVal==JFileChooser.APPROVE_OPTION)
				{
					decryptFile = fc.getSelectedFile();
					decryptLabel.setText(fc.getSelectedFile().getAbsolutePath());
				}
				
			}});
		
		//corrupt panel
		JPanel corrupt = new JPanel();
		corrupt.setLayout(new GridLayout(5, 1));
		corrupt.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		JButton corruptAdd = new JButton("Select file...");
		corrupt.add(corruptAdd);
		corrupt.add(corruptLabel);
		JButton corruptButton = new JButton("Corrupt file");
		corruptButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				corrupt();
				
			}});
		corrupt.add(corruptButton);
		corrupt.add(new JLabel("Corrupted files are basically the same file name, but the contents is gibberish"));
		corruptAdd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(me);
				if(returnVal==JFileChooser.APPROVE_OPTION)
				{
					corruptFile = fc.getSelectedFile();
					corruptLabel.setText(fc.getSelectedFile().getAbsolutePath());
				}
				
			}});
		
		
		//Put it together
		panel.setLayout(new GridLayout(1, 2));
		tabbedPane.add(encrypt, "Encrypt file");
		tabbedPane.add(decrypt, "Decrypt file");
		JPanel passwordHolder = new JPanel();
		passwordHolder.add(new JLabel("Password:"));
		this.add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.add(corrupt, "Corrupt file");
		passwordHolder.add(pass);
		passwordHolder.setPreferredSize(pass.getPreferredSize());
		this.add(passwordHolder, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	public void encrypt()
	{
		if(encryptFile==null)
		{
			JOptionPane.showMessageDialog(this,
				    "Please select a file",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(pass.getText().length()==0)
		{
			JOptionPane.showMessageDialog(this,
				    "Please enter a password",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		File to = new File(encryptFile.getAbsolutePath()+".encrypted");
		File from = encryptFile;
		String key = getBetterPass(pass.getText());
		try{
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			SecretKey desKey = skf.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for SunJCE
			
			cipher.init(Cipher.ENCRYPT_MODE, desKey);
			CipherInputStream cis = new CipherInputStream(new FileInputStream(from), cipher);
			doCopy(cis, new FileOutputStream(to));
			JOptionPane.showMessageDialog(this,
				    "Done!",
				    "Status",
				    JOptionPane.INFORMATION_MESSAGE);
			} catch(Exception e) {
				JOptionPane.showMessageDialog(this,
					    "Error while encrypting",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
			}
	}
	public void decrypt()
	{
		if(decryptFile==null)
		{
			JOptionPane.showMessageDialog(this,
				    "Please select a file",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(pass.getText().length()==0)
		{
			JOptionPane.showMessageDialog(this,
				    "Please enter a password",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		File from = decryptFile;
		File to = new File(from.getAbsolutePath().replace(".encrypted", ""));
		String key = getBetterPass(pass.getText());
		try{
		DESKeySpec dks = new DESKeySpec(key.getBytes());
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey desKey = skf.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for SunJCE
		
		cipher.init(Cipher.DECRYPT_MODE, desKey);
		CipherOutputStream cos = new CipherOutputStream(new FileOutputStream(to), cipher);
		doCopy(new FileInputStream(from), cos);
		JOptionPane.showMessageDialog(this,
			    "Done!",
			    "Status",
			    JOptionPane.INFORMATION_MESSAGE);
		return;
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this,
				    "Error while decrypting",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		}
	}
	public String getBetterPass(String original)
	{
		if(original.length() < 8)
		{
			String newString = ""+original;
			for(int i = original.length(); i < 9; i++)
			{
				newString = newString + "0";
			}
			return newString;
		}
		return original;
	}
	public void corrupt()
	{
		if(corruptFile==null)
		{
			JOptionPane.showMessageDialog(this,
				    "Please select a file",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		try{
		doCopy(new FileInputStream(corruptFile), new FileOutputStream(new File(corruptFile.getAbsolutePath()+".temp")));
		FileInputStream isf = new FileInputStream(new File(corruptFile.getAbsolutePath()+".temp"));
		FileOutputStream osf = new FileOutputStream(corruptFile);
		InputStream is = isf;
		OutputStream os = osf;
		byte[] bytes = new byte[64];
		int numBytes;
		Random random = new Random();
		while ((numBytes = is.read(bytes)) != -1) {
			random.nextBytes(bytes);
			os.write(bytes, 0, numBytes);
		}
		os.flush();
		os.close();
		is.close();
		File temp = new File(corruptFile.getAbsolutePath()+".temp");
		temp.delete();
		
		JOptionPane.showMessageDialog(this,
			    "Done!",
			    "Status",
			    JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			
		}
	}
	public static void doCopy(InputStream is, OutputStream os) throws IOException {
		byte[] bytes = new byte[64];
		int numBytes;
		while ((numBytes = is.read(bytes)) != -1) {
			os.write(bytes, 0, numBytes);
		}
		os.flush();
		os.close();
		is.close();
	}

}
