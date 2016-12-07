package socket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

import javax.imageio.stream.FileCacheImageInputStream;

import entity.File;
import entity.User;
import service.FileService;
import util.CommandTransfer;

public class Client {
	private Scanner in = new Scanner(System.in);
	private File file = null;
	private User user = null;
	private Socket socket = null;
	private ObjectOutputStream oos = null;
	private BufferedReader br = null;
	private CommandTransfer ct = null;
	private FileInputStream fis = null;
	private FileService fs = null;

	public void showMenu() {
		System.out.println("1.ע��\n2.��½\n3.�˳�");
		boolean flag = true;
		while (flag) {
			System.out.print("\n������ָ�");
			switch (in.nextInt()) {
			case 1:
				Register();
				break;
			case 2:
				Login();
				break;
			default:
				flag = false;
				break;
			}
		}
	}

	public void Login() {
		user = new User();
		System.out.print("�������û�����");
		user.setUserName(in.next());
		System.out.print("���������룺");
		user.setPassword(in.next());
		try {
			socket = new Socket("localhost", 8888);

			oos = new ObjectOutputStream(socket.getOutputStream());
			ct = new CommandTransfer();
			ct.setCmd("login");
			ct.setObj(user);
			oos.writeObject(ct);
			oos.flush();
			socket.shutdownOutput();

			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			int isTrue = br.read();
			socket.shutdownInput();

			if (isTrue == 1) {
				System.out.print("�����������ϴ����ļ�·����");
				file = new File();
				file.setFileName(in.next());
				fis = new FileInputStream(new java.io.File(file.getFileName()));
				byte[] content = new byte[fis.available()];
				fis.read(content);
				file.setFileContent(content);
				fs = new FileService();
				fs.save(file);
				System.out.println("�ϴ��ɹ�");
			} else if (isTrue == 0) {
				System.out.println("�˺Ż��������");
			} else {
				System.out.println("��������ȷ�Ĳ���ָ��");
			}
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
				if (oos != null) {
					oos.close();
				}
				if (br != null) {
					br.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void Register() {
		user = new User();
		System.out.print("�������û�����");
		user.setUserName(in.next());
		System.out.print("���������룺");
		user.setPassword(in.next());
		System.out.print("��������һ��");
		String pwd = in.next();
		if (pwd.compareTo(user.getPassword()) != 0) {
			System.out.println("�������벻һ��");
			return;
		}

		try {
			socket = new Socket("localhost", 8888);
			
			oos = new ObjectOutputStream(socket.getOutputStream());
			ct = new CommandTransfer();
			ct.setCmd("register");
			ct.setObj(user);
			oos.writeObject(ct);
			oos.flush();
			socket.shutdownOutput();

			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			int isTrue = br.read();
			socket.shutdownInput();

			if (isTrue == 1) {
				System.out.println("ע��ɹ�");
			} else {
				System.out.println("��������ȷ�Ĳ���ָ��");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
				if (oos != null) {
					oos.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
