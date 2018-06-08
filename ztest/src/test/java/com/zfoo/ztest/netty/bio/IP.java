package com.zfoo.ztest.netty.bio;

import java.io.*;
import java.net.*;

public class IP {

    // 不包端口
    public void InetAddressTest() {
        try {

            // 获取本机的ip
            InetAddress ipLocal = InetAddress.getLocalHost();
            System.out.println(ipLocal.getHostName());// 计算机名称
            System.out.println(ipLocal.getHostAddress());// 本机ip

            InetAddress ipBaidu = InetAddress.getByName("www.baidu.com");

            // 如果解析不到name和address都为ip
            // InetAddress ipBaidu=InetAddress.getByName("115.239.211.112");

            System.out.println(ipBaidu.getHostName());// 计算机名称
            System.out.println(ipBaidu.getHostAddress());// ip

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    // 包含端口
    public void InetSocketAddressTest() {
        // localhost=127.0.0.1,本机ip
        InetSocketAddress address = new InetSocketAddress("www.baidu.com", 9999);
        System.out.println(address.getHostName());
        System.out.println(address.getPort());

        // InetSocketAddress和InetAddress的相互转换
        InetAddress ip = address.getAddress();
        System.out.println(ip.getHostName());// 计算机名称
        System.out.println(ip.getHostAddress());// ip
    }

    public void UrlTest() {
        // URI统一资源标识符，用来唯一的标识一个资源
        // URL统一资源定位符，一种具体的URI
        // 四部分组成：协议 存放资源的主机名称 端口 资源文件名称
        URL url = null;

        // 读取网页的资源
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            // 绝对路径
            url = new URL("http://www.baidu.com:80/index.html#aa?uname=sss");
            System.out.println(url.getProtocol());
            System.out.println(url.getHost());
            System.out.println(url.getPort());
            System.out.println(url.getFile());// 资源
            System.out.println(url.getPath());// 相对路径
            System.out.println(url.getRef());// 锚点
            System.out.println(url.getQuery());// 存在锚点，参数为null；如果不存在锚点，返回正确

            inputStream = url.openStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);

            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(//D:\MyProfessionalProject\project\Net
                                    new File(
                                            "D:/MyProfessionalProject/project/Net/bio/src/百度.html")),
                            "utf-8"));

            String message;
            while ((message = bufferedReader.readLine()) != null) {
                System.out.println(message);// 输出到控制台
                bufferedWriter.append(message);//输出到文件
                bufferedWriter.newLine();
            }

            bufferedWriter.flush();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                bufferedWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    public static void main(String[] args) {
        new IP().UrlTest();
    }

}
