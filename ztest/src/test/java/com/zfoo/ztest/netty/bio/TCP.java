package com.zfoo.ztest.netty.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCP {

	// 不需要关闭流，TCP安全
	public void Server() {
		// 1.穿件服务器，指定端口
		ServerSocket server = null;
		BufferedWriter bufferedWriter = null;
		try {
			server = new ServerSocket(8888);
			// 2.接受客户端连接，阻塞式
			Socket socket = server.accept();
			System.out.println("hello http!!!!!!");

			// 3.发送数据+接受数据
			String message = "welocme to internet!!!";
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			bufferedWriter.write(message);
			bufferedWriter.newLine();
			bufferedWriter.flush();

		} catch (IOException e) {
		}
	}

	public void Client() {
		// 1.创建客户端，必须指定服务器+端口，此时就在连接
		Socket client = null;
		BufferedReader bufferedReader = null;
		try {
			// 同一个协议下TCP，端口不能重复，UDP和TCP可以公用一个端口
			// 端口两个字节，1024一下为系统级别的端口，不能使用
			client = new Socket("localhost", 8888);

			// 2.发送数据+接受数据
			bufferedReader = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			String receiveMes = bufferedReader.readLine();
			System.out.println(receiveMes);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		
		
		
	}

}
