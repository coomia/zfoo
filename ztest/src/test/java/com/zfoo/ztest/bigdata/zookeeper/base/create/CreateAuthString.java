package com.zfoo.ztest.bigdata.zookeeper.base.create;

import java.security.NoSuchAlgorithmException;

import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;


public class CreateAuthString {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		System.out.println(DigestAuthenticationProvider.generateDigest("jike:123456"));
	}
}
