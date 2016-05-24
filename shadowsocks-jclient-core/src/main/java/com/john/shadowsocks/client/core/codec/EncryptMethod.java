package com.john.shadowsocks.client.core.codec;

/**
 * encrypt method enum
 *
 * @author jiangguangtao on 2016/5/24.
 */
public enum EncryptMethod {
    /**
     * accept client connect
     */
    RC4_MD5("rc4-md5", 16, 16);
    private final String name;
    private final int keyLen;
    private final int ivLen;

    EncryptMethod(String name, int keyLen, int ivLen) {
        this.name = name;
        this.keyLen = keyLen;
        this.ivLen = ivLen;
    }

    /**
     * get the encryptMethod according to the name
     *
     * @param name
     * @return null or the encryptMethod
     */
    public static EncryptMethod getMethod(String name) {
        for (EncryptMethod cryptoMethod : values()) {
            if (cryptoMethod.name.equalsIgnoreCase(name)) {
                return cryptoMethod;
            }
        }

        return null;
    }

    /**
     * the key length
     * @return
     */
    public int getKeyLen() {
        return keyLen;
    }

    /**
     * the Iv length
     * @return
     */
    public int getIvLen() {
        return ivLen;
    }
}
