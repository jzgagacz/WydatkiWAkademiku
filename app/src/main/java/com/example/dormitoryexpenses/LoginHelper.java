package com.example.dormitoryexpenses;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class LoginHelper {

    private static int iterations = 100;

    public static void addNewUser( String uname, String name, String sname, String password, DataBaseHelper dbh, Context context){
        SecureRandom sr = null;
        SecretKeyFactory skf = null;
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] bsalt = new byte[16];
        sr.nextBytes(bsalt);
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), bsalt, iterations, 64 * 8);
        String salt = toHex(bsalt);
        String hash = null;
        try {
            hash = toHex(skf.generateSecret(spec).getEncoded());
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        AddUser(uname,  name,  sname,  salt,  hash, dbh, context);

    }

    public static boolean validatePassword(String user, String password, DataBaseHelper dbh){
        Cursor data = dbh.getUser(user);
        data.moveToFirst();
        byte[] hash = fromHex(data.getString(4));
        byte[] salt = fromHex(data.getString(3));
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = null;
        byte[] testHash = null;
        try {
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            testHash = skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        System.out.println(hash);
        System.out.println(testHash);
        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private static void AddUser(String uname, String name, String sname, String salt, String hash, DataBaseHelper dbh, Context context){
        boolean res = dbh.addUser( uname,  name,  sname,  salt,  hash);
        if (res)
            Toast.makeText(context, "Added to db", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();

    }

    private static String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

    private static byte[] fromHex(String hex)
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
