package hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class sha1 {
	public static String applyHash(String input) {
		MessageDigest mDigest = null;
		try {
			mDigest = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error retreiving SHA1 hash");
			System.err.println(e.getMessage());
			System.exit(1);
		}
		byte[] result = mDigest.digest(input.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}
}
