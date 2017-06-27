package com.plan.my.mytoolslibrary.toolutils;





import com.baidu.android.security.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * ``
 * @author wudl
 *
 */
public class DesUtil {
	private static String strDefaultKey = "itjy";
	private Cipher encryptCipher = null;
	private Cipher decryptCipher = null;

	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	public DesUtil() throws Exception {
		this(strDefaultKey);
	}

	public DesUtil(String strKey) {
		// Security.addProvider(null);
		Key key;
		try {
			key = getKey(strKey.getBytes());
			encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
			decryptCipher = Cipher.getInstance("DES");
			decryptCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	/** 加密 **/
	public String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	public byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	/** 解密 **/
	public String decrypt(String strIn) throws Exception {
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}

	private Key getKey(byte[] arrBTmp) throws Exception {
		byte[] arrB = new byte[8];
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
		return key;
	}

	
	
	/**
	 * 跟C#后台交互用的，本地加密，C#后台解密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	 public static String encrypt1(String data) throws Exception {
	        try {
//	            String data = "Test String";
	            String key = "1234567812345678";
	            String iv = "1234567812345678";
	            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
	            int blockSize = cipher.getBlockSize();
	            byte[] dataBytes = data.getBytes();
	            int plaintextLength = dataBytes.length;
	            if (plaintextLength % blockSize != 0) {
	                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
	            }
	            byte[] plaintext = new byte[plaintextLength];
	            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
	             
	            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
	            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
	            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
	            byte[] encrypted = cipher.doFinal(plaintext);
	           
	            return new String(Base64.encode(encrypted));
	        } catch (Exception e) {//sun.misc.BASE64Encoder().encode
	            e.printStackTrace();
	            return null;
	        }
	    }
	 
	 /***************************C#端加解密******跟上面方法对应********************************************
		public static string Encrypt(string toEncrypt, string key, string iv)
        {
            byte[] keyArray = UTF8Encoding.UTF8.GetBytes(key);
            byte[] ivArray = UTF8Encoding.UTF8.GetBytes(iv);
            byte[] toEncryptArray = UTF8Encoding.UTF8.GetBytes(toEncrypt);
            RijndaelManaged rDel = new RijndaelManaged();
            rDel.Key = keyArray;
            rDel.IV = ivArray;
            rDel.Mode = CipherMode.CBC;
            rDel.Padding = PaddingMode.Zeros;
            ICryptoTransform cTransform = rDel.CreateEncryptor();
            byte[] resultArray = cTransform.TransformFinalBlock(toEncryptArray, 0, toEncryptArray.Length);
            return Convert.ToBase64String(resultArray, 0, resultArray.Length);
        }

           // String key = "1234567812345678";
           // String iv = "1234567812345678";

        public static string Decrypt(string toDecrypt, string key, string iv)
        {
            byte[] keyArray = UTF8Encoding.UTF8.GetBytes(key);
            byte[] ivArray = UTF8Encoding.UTF8.GetBytes(iv);
            byte[] toEncryptArray = Convert.FromBase64String(toDecrypt);
            RijndaelManaged rDel = new RijndaelManaged();
            rDel.Key = keyArray;
            rDel.IV = ivArray;
            rDel.Mode = CipherMode.CBC;
            rDel.Padding = PaddingMode.Zeros;
            ICryptoTransform cTransform = rDel.CreateDecryptor();
            byte[] resultArray = cTransform.TransformFinalBlock(toEncryptArray, 0, toEncryptArray.Length);
            return UTF8Encoding.UTF8.GetString(resultArray);
        }
	  *******************************************************************************************/
	 
}
