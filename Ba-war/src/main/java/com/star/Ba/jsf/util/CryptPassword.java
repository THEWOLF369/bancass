package com.star.Ba.jsf.util;



import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CryptPassword
{
    private static String encryptionKey="starwevioobadiis";

    

    public static String encrypt(String plainText) {
    	 byte[] encryptedBytes = null;
    	try {
    		 Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
    	        encryptedBytes = cipher.doFinal(plainText.getBytes());
		} catch (Exception e) {
			// TODO: handle exception
		}
       

        return Base64.encodeBase64String(encryptedBytes);
    }

    public static String decrypt(String encrypted) {
    	 byte[] plainBytes = null;
    	try {
    		Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
             plainBytes = cipher.doFinal(Base64.decodeBase64(encrypted));

		} catch (Exception e) {
			// TODO: handle exception
		}
        
        return new String(plainBytes);
    }

    private static Cipher getCipher(int cipherMode)
            throws Exception
    {
        String encryptionAlgorithm = "AES";
        SecretKeySpec keySpecification = new SecretKeySpec(
                encryptionKey.getBytes("UTF-8"), encryptionAlgorithm);
        Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
        cipher.init(cipherMode, keySpecification);

        
        
        return cipher;
    }
    public static void main(String[] arguments) throws Exception
    {
        String encryptionKey = "DoyrFE7Mb2NdotBRBKUJLv9xy0QULwNdVceJYgipUNA=" ;
//        String plainText = "souscripteurubc";
//        String cipherText = CryptPassword.encrypt(plainText);
        String decryptedCipherText =CryptPassword.decrypt(encryptionKey);

//        System.out.println(plainText);
//        System.out.println(cipherText);
        System.out.println(decryptedCipherText);
    }
}