package com.frame.util.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import com.pioneer.app.util.DateTimeUtil;

public class Signaturer {
	 
	 /**
	 * @desc:给电子卡签名
	 * @param plainText
	 * @return :
	 * @auther : chen
	 * @date : Jan 31, 2009
	 */
	public static String CardSign(String plainText){
		 byte[] rs=null;
		 try {
			RSAPrivateKey privateKey = CryptFile.getPrvKey("d:/crpt/sign/privateKey.scrpt");
			 java.security.Signature signet = java.security.Signature.getInstance("MD5withRSA");
			 signet.initSign(privateKey);
			 signet.update(plainText.getBytes());
			 rs=signet.sign();
			 String rs1=Base64.encodeToString(rs);
			 String rtStr=new String(rs);
//			 System.out.println("rtStr="+rtStr);
			 return rs1;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return Base64.encodeToString(rs);
	 }
	 
	 /**
	 * @desc:验证签名
	 * @param plainText
	 * @param signText
	 * @return :
	 * @auther : chen
	 * @date : Jan 31, 2009
	 */
	public static boolean cardValidate(String plainText,
	   String signText){
		 
		 try {
			 byte[] signTextb=Base64.decode(signText);
			RSAPublicKey publicKey = CryptFile.getPubKey("d:/crpt/sign/publicKey.scrpt");
			 java.security.Signature signatureChecker = java.security.Signature.getInstance("MD5withRSA");
			 signatureChecker.initVerify(publicKey);
			 signatureChecker.update(plainText.getBytes());
			 if (signatureChecker.verify(signTextb))
			    return true;
			   else
			    return false;
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return false;
	 }
	 public static void main(String[] args){
		 String signText=CardSign("123456");
		 boolean rs=cardValidate("123456",signText);
		 System.out.println("rs="+rs);
		 System.out.println(DateTimeUtil.getDateTime());
		 /*String s = "12345abcd";
		  byte b[] = s.getBytes();
		  try {
		   String t = new String(b);
		   System.out.print(t);
		  } catch (Exception e) {
		   e.printStackTrace();
		  }*/

		}
	}

