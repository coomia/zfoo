package com.zfoo.ztest.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

	ServerSocket server;

	public void start() {
		// 1.穿件服务器，指定端口

		try {
			server = new ServerSocket(8888);
			receive();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void receive() {
		while (true) {
			// 2.接受客户端连接，阻塞式
			try {
				Socket client = server.accept();
				// 3.发送数据+接受数据
				System.out
						.println("server started! Connect to client successfully");

				StringBuilder str=new StringBuilder();
				String message = null;
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(client.getInputStream()));
				while((message=bufferedReader.readLine())!=null){
					str.append(message);
					str.append("\r\n");
				}
				System.out.println(str.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void stop() {

	}

	public static void main(String[] args) {
		HttpServer server = new HttpServer();
		server.start();
	}

}
