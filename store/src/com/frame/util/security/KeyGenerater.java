package com.frame.util.security;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

public class KeyGenerater {
	 private byte[] priKey;
	 private byte[] pubKey;

	 /**
	 * 
	 */
	public void generater() {
	  try {
	   java.security.KeyPairGenerator keygen = java.security.KeyPairGenerator
	     .getInstance("RSA");
	   SecureRandom secrand = new SecureRandom();
	   secrand.setSeed("syj".getBytes()); // 初始化随机产生器
	   keygen.initialize(1024, secrand);
	   KeyPair keys = keygen.genKeyPair();

	   PublicKey pubkey = keys.getPublic();
	   PrivateKey prikey = keys.getPrivate();
//	   pubKey=pubkey.getEncoded();
//	   priKey=prikey.getEncoded();
	   pubKey = Base64.encodeToByte(pubkey.getEncoded());
	   priKey = Base64.encodeToByte(prikey.getEncoded());

	   System.out.println("pubKey = " + new String(pubKey));
	   System.out.println("priKey = " + new String(priKey));
	  } catch (java.lang.Exception e) {
	   System.out.println("生成密钥对失败");
	   e.printStackTrace();
	  }
	 }

	 public byte[] getPriKey() {
	  return priKey;
	 }
	 
	 public byte[] getPubKey() {
	  return pubKey;
	 }
	 public static void main(String[] args){
		 /*KeyGenerater gen=new KeyGenerater();
		 gen.generater();
		 byte [] rs=Signaturer.sign(gen.getPriKey(),"这是我的签名");
		 boolean vrs=SignProvider.verify(gen.getPubKey(),"这是我的签名",rs);
		 System.out.println("vrs="+vrs);
		 */
	 }
	}

