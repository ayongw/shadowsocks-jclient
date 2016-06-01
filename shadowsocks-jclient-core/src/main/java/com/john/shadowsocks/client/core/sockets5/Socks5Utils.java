package com.john.shadowsocks.client.core.sockets5;

import java.nio.ByteBuffer;

/**
 * @author fsneak
 */
public final class Socks5Utils {
	private static final byte SOCKS5_VERSION = 0x05;
	private Socks5Utils() {
	}

	public static boolean verifyVersion(byte version) {
		return SOCKS5_VERSION == version;
	}

	public static int getByteLen(ByteBuffer buffer) {
		return 0xff & buffer.get();
	}
}
