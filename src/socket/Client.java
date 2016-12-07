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
		System.out.println("1.注册\n2.登陆\n3.退出");
		boolean flag = true;
		while (flag) {
			System.out.print("\n请输入指令：");
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
		System.out.print("请输入用户名：");
		user.setUserName(in.next());
		System.out.print("请输入密码：");
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
				System.out.print("请输入你想上传的文件路径：");
				file = new File();
				file.setFileName(in.next());
				fis = new FileInputStream(new java.io.File(file.getFileName()));
				byte[] content = new byte[fis.available()];
				fis.read(content);
				file.setFileContent(content);
				fs = new FileService();
				fs.save(file);
				System.out.println("上传成功");
			} else if (isTrue == 0) {
				System.out.println("账号或密码错误");
			} else {
				System.out.println("请输入正确的操作指令");
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
		System.out.print("请输入用户名：");
		user.setUserName(in.next());
		System.out.print("请输入密码：");
		user.setPassword(in.next());
		System.out.print("请再输入一次");
		String pwd = in.next();
		if (pwd.compareTo(user.getPassword()) != 0) {
			System.out.println("两次密码不一致");
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
				System.out.println("注册成功");
			} else {
				System.out.println("请输入正确的操作指令");
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
