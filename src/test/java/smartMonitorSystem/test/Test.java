package smartMonitorSystem.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 8089);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = null;
			System.out.println("111");
			while((line = in.readLine()) != null) {
				System.out.println("000");
				System.out.println(line);
//				PrintWriter pw = new PrintWriter(socket.getOutputStream());
//				pw.println("rsp:m");
//				pw.flush();
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
