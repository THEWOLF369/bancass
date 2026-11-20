package com.star.Ba.commun;



import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CryptPassword
{
    private static String encryptionKey="starwevioobadiis";

    

    public static String encrypt(String plainText) {
    	
    	 byte[] encryptedBytes= null;
    	 if(plainText!=null){
    	try {
    		 Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
    	        encryptedBytes = cipher.doFinal(plainText.getBytes());
    	        return Base64.encodeBase64String(encryptedBytes);
		} catch (Exception e) {
			return null;
			// TODO: handle exception
		}
       
    	 }else {
			return null;
		}
       
    }

    public static String decrypt(String encrypted) {
    	 byte[] plainBytes = null;
    	if(encrypted!=null){
    	
    	try {
    		Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
             plainBytes = cipher.doFinal(Base64.decodeBase64(encrypted));
             return new String(plainBytes);
		} catch (Exception e) {
			return null;	
			}
    	}else {
        	return null;

		}
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
        String encryptionKey ;
        String plainText = "bnaagent";
     
        String cipherText = CryptPassword.encrypt(plainText);
        String decryptedCipherText =CryptPassword.decrypt("LWGOSWbh/y844VlSnWbV7w==");

        System.out.println(plainText);
        System.out.println(cipherText);
        System.out.println(decryptedCipherText);
    }
}