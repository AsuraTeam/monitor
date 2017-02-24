package com.asura.framework.base.util;

import java.security.MessageDigest;

public class MD5Util {

	private static String byteArrayToHexString(final byte b[]) {
		final StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(final byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		final int d1 = n / 16;
		final int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(final String origin, final String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			final MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
		} catch (final Exception exception) {
		}
		return resultString;
	}

	public static String MD5Encode(final String origin) {
		return MD5Encode(origin, "UTF-8");
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

}
