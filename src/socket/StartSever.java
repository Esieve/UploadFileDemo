package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class StartSever {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(8888);
			Socket socket = null;
			System.out.println("服务器已启动");
			while (true) {
				socket = serverSocket.accept();
				SeverThread thread = new SeverThread(socket);
				thread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
