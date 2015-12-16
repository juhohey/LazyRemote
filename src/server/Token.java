package server;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import main.Base64;

public class Token {
	
	//self: single
	public static Token token;
	private static Key secretKey;
	private Cipher cipher;
	
	private Token(){}
	public static Token getInstance(){
		if(token==null) token = new Token();
		//init stuff, keyGen
		
		//KeyGenerator keyGenz = KeyGenerator.getInstance("AES");
		//keyGenz.init(128);
		//aesKey = keyGenz.generateKey();
		String src = "secretS142345VAb";
		secretKey = new SecretKeySpec(src.getBytes(), "AES");
		
		return token;
	}
	
	public String generate(String s, String client){
		String tokenS = s+"@"+client;
		tokenS = tokenS.replaceAll("\\r|\\n", "");
		System.out.println("Token 45: generate "+tokenS);
       
		try {
			cipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // encrypt the text
        try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        byte[] encrypted = null;
		try {
			encrypted = cipher.doFinal(tokenS.getBytes());
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return Base64.encodeToString(encrypted, 0);
	}
      
    public boolean read(String read, String match){
    	
    	match = match.replaceAll("\\r|\\n", "");
    	// the encrypted String
        String enc = read;
       
        // now convert the string to byte array
        // for decryption
        byte[] bb = new byte[enc.length()];
        for (int i=0; i<enc.length(); i++) {
            bb[i] = (byte) enc.charAt(i);
        }

        // decrypt the text
        try {
			cipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String decrypted = null;
        String[] res = null;
        byte[] decodedValue = Base64.decode(enc.getBytes(), 0);
		try {
			decrypted = new String(cipher.doFinal(decodedValue)).replaceAll("\\r|\\n", "");
			res = decrypted.split("@");
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Token 100 match decrypted:" + res[0].equals(match) );
		return res[0].equals(match);
    	
    }
	
}
