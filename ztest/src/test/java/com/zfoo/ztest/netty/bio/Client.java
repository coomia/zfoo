package com.zfoo.ztest.netty.bio;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	private String name;

	private class SendThread implements Runnable {
		// 控制台输入流
		private BufferedReader console;
		// 管道输出流
		private DataOutputStream dataOut;
		// 控制线程
		private boolean isRunning;

		private SendThread() {
			console = new BufferedReader(new InputStreamReader(System.in));
			isRunning = true;
		}

		public SendThread(Socket client) {
			this();
			try {
				dataOut = new DataOutputStream(client.getOutputStream());
				send(name);
			} catch (IOException e) {
				e.printStackTrace();
				isRunning = false;
				Client.closeIO(console, dataOut);
			}
		}

		private String getMessageFromConsole() {
			try {
				System.out.println("please input message:");
				return console.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		public void send(String message) {
			if (null != message) {
				try {
					dataOut.writeUTF(message);
					dataOut.flush();
				} catch (IOException e) {
					isRunning = false;
					Client.closeIO(dataOut, console);
				}
			}
		}

		@Override
		public void run() {
			while (isRunning) {
				send(getMessageFromConsole());
			}
		}
	}

	private static class ReceiveThread implements Runnable {
		// 输入流
		private DataInputStream dataIn;
		// 线程标识
		private boolean isRunning= true;


		public ReceiveThread(Socket client) {
			try {
				dataIn = new DataInputStream(client.getInputStream());
			} catch (IOException e) {
				isRunning = false;
				Client.closeIO(dataIn);
			}
		}

		private String receive() {
			try {
				return dataIn.readUTF();
			} catch (IOException e) {
				isRunning = false;
				Client.closeIO(dataIn);
			}
			return null;
		}

		@Override
		public void run() {
			while (isRunning) {
				System.out.println(receive());
			}
		}
	}

	public void launchClient(String name) {
		// 1.创建客户端，必须指定服务器+端口，此时就在连接
		Socket client = null;
		Thread sendThread = null;
		Thread receiveThread = null;
		this.name=name; 
		try {

			client = new Socket("localhost", 9999);// 系统自动分配客户端的端口号
			// 2.发送数据+接受数据
			sendThread = new Thread(new SendThread(client));
			receiveThread = new Thread(new ReceiveThread(client));
			sendThread.start();
			receiveThread.start();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void closeIO(Closeable... io) {
		for (Closeable temp : io) {
			try {
				temp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		System.out.println("请输入名称：");
		String name=new Scanner(System.in).nextLine();
		
		new Client().launchClient(name);
	}


}
