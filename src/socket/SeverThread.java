package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

import entity.User;
import service.UserService;
import util.CommandTransfer;

public class SeverThread extends Thread {
	private Socket socket = null;
	private ObjectInputStream ois = null;
	private PrintWriter pw = null;
	private User user = null;
	private CommandTransfer ct = null;
	private UserService us = null;

	public SeverThread(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			ct = (CommandTransfer) ois.readObject();
			user = (User) ct.getObj();
			socket.shutdownInput();

			pw = new PrintWriter(socket.getOutputStream());
			us = new UserService();
			if (ct.getCmd().compareTo("login") == 0) {
				if (us.signIn(user)) {
					pw.write(1);
				} else {
					pw.write(0);
				}
			} else if (ct.getCmd().compareTo("register") == 0) {
				us.signUp(user);
				pw.write(1);
			} else {
				pw.write(-1);
			}
			pw.flush();
			socket.shutdownOutput();
		} catch (IOException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
				if (ois != null) {
					ois.close();
				}
				if (pw != null) {
					pw.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
