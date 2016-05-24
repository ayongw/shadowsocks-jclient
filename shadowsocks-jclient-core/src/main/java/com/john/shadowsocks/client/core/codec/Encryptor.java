package com.john.shadowsocks.client.core.codec;

/**
 *
 * @author jiangguangtao on 2016/5/24.
 */
public class Encryptor {

    private String key;
    private EncryptMethod encryptMethod;

    public Encryptor(String key, EncryptMethod encryptMethod) {
        this.key = key;
        this.encryptMethod = encryptMethod;
    }

    /**
     * encrypt input bytes
     * @param input
     * @return
     */
    public byte[] encrypt(byte[] input){
        return input;
    }

    /**
     * decrypt input bytes
     * @param input
     * @return
     */
    public byte[] decrypt(byte[] input) {
        return input;
    }

}
