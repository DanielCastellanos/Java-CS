package servidor;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
/**
 *
 * @author Ricar
 */
public class Cifrado {
    private static Cipher cipher;
    private static String llaves[] = {"holamundocruel12",
        "TGrjieH8g1AoWE6h",
        "vO5YSKb0Kqn3BWbf",
        "xZctw8LBBx2WZe9w",
        "fqWwGuxARcZUdaV2",
        "7c5WX9QUg96q9o77",
        "4Ia7GNW0LjVS28Rb",
        "EOCzdULS3NFtKmol",
        "RPt47ldbVXHqyuKw",
        "dMohuzhNxz7u37up",
        "gECOb32hrwr3uXFx",
        "XA4feooQS8dIh3El",
        "NmVuyHwrBfga37Ow",
        "8WimCIUupASoZt69",
        "ozIwZtRpfKq7MXA1",
        "2MZcrA19ol68aodh",
        "zWWPyamCSkqfq2Sk",
        "ESRFN54zSXhDuUBa",
        "gT8CNvYL1i2M9oCn",
        "3SdSE0A7LXsfjjgy",
        "RrElEMnVxqLt7JP1"};
    public static byte[] cifrarAES(byte texto[])
    {
        byte cifrado[]=null;
        try {
            cipher=Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE,new SecretKeySpec(llaves[0].getBytes(), "AES"));
            cifrado=cipher.doFinal(texto);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cifrado;
    }
    public static byte[] descifrarAES(byte cifrado[])
    {
        byte texto[]=null;
        try {
            cipher=Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(llaves[0].getBytes(),"AES"));
            texto=cipher.doFinal(cifrado);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return texto;
    }
    public static String cifrarMD5(String pass)
    {
        byte text[]=pass.getBytes();
        String md5 = "";
        try {
            MessageDigest md =MessageDigest.getInstance("MD5");
            md.reset();
            md.update(text);
            byte bytes[] = md.digest();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xff & bytes[i]);
                if (hex.length() == 1)
                    md5+='0';
                md5+=hex;
            }
            System.out.println(md5);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return md5;
    }
}
