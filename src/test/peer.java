package test;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class peer {
	public static void main(String[] args) throws Exception{
        byte[] ct = CryptoTools.hexToBytes("7AA38A029E773CBBC188A9FCEADAE99DA560B784C99AFEF2");
        byte[] ky = CryptoTools.hexToBytes("4F75725269676874");
        byte[] iv = CryptoTools.hexToBytes("496E566563746F72");

        byte[] fhalf = new byte[ct.length/3];
        byte[] sechalf = new byte[ct.length/3];
        byte[] thirdhalf = new byte[ct.length/3];

        for(int i = 0; i < (ct.length/3); i++)
        {
            fhalf[i] = ct[i];
        }
        for(int i = ct.length/3; i < (ct.length/3 * 2); i++)
        {
            sechalf[i % 8] = ct[i];
        }
        for(int i = (ct.length/3 * 2); i < ct.length; i++)
        {
            thirdhalf[i % 8] = ct[i];
        }

        Key secret = new SecretKeySpec(ky,"DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        
        byte[] pt1  = new byte[8];
        for(int j = 0; j < fhalf.length; j++ )
        {
           pt1[j] =  (byte) (fhalf[j] ^ iv[j]);
        }
        byte[] pt2  = new byte[8];
        
        for(int j = 0; j < sechalf.length; j++ )
        {
            pt2[j] = (byte) (sechalf[j] ^ fhalf[j]);
        }
        
        byte[] pt3  = new byte[8];
        for(int j = 0; j < thirdhalf.length; j++ )
        {
            pt3[j] = (byte) (thirdhalf[j] ^ sechalf[j]);
        }



        ByteArrayOutputStream bt = new ByteArrayOutputStream();
        bt.write(pt1);
        bt.write(pt2);
        bt.write(pt3);
        byte[] message = bt.toByteArray();
        
        byte[] plain = cipher.doFinal(message);
        System.out.println(new String(plain));
    }
}

