package servidor;
import java.security.InvalidKeyException;
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
    public static byte[] cifrar(byte texto[])
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
    public static byte[] descifrar(byte cifrado[])
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
}
