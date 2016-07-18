package hr.fer.zemris.java.hw15.crypto;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * {@code Sha1Utils} is a utility class that simplify common
 * {@link MessageDigest} tasks.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public class Sha1Utils {

    /**
     * Returns an SHA-1 digest.
     * 
     * @return SHA-1 digest instance.
     */
    public static MessageDigest getSha1Digest() {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.reset();
        } catch (NoSuchAlgorithmException ignore) {
        }

        return messageDigest;
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a {@code byte}
     * array.
     * 
     * @param data
     *            the data to digest
     * @return SHA-1 digest
     */
    public static byte[] sha1(byte[] data) {
        MessageDigest messageDigest = getSha1Digest();
        messageDigest.update(data);
        return messageDigest.digest();
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a {@code byte}
     * array.
     * 
     * @param data
     *            the data to digest
     * @param charset
     *            coding pages used to convert {@code data} to {@code byte}
     *            array
     * @return SHA-1 digest
     */
    public static byte[] sha1(String data, Charset charset) {
        return sha1(data.getBytes(charset));
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a {@code byte}
     * array.
     * 
     * @param data
     *            the data to digest; converted to {@code data} to {@code byte}
     *            array using UTF-8 coding pages
     * @return SHA-1 digest
     */
    public static byte[] sha1(String data) {
        return sha1(data, StandardCharsets.UTF_8);
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a hex string.
     * 
     * @param str
     *            the data to digest
     * @return SHA-1 digest as a hex string
     */
    public static String sha1Hex(String str) {
        return new BigInteger(1, sha1(str)).toString(16);
    }

}