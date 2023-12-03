// Copyright 24-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package kut;

import java.util.ArrayList;
import java.util.Arrays;

/** 'byte[]' management. */
public class Bytes {
  Bytes () {}

  /**
   * Creates a 'byte[]' of length 'len', intialized with 0's.<p>
   * If 'len' &lt; 0, it is set to 0.
   * @param len Array size.
   * @return A new array.
   */
  public static byte[] mk (int len) {
    if (len < 0) len = 0;
    byte[] r = new byte[len];
    Arrays.fill(r, (byte)0);
    return r;
  }

  /**
   * Converts an ArrayList to 'byte[]'.
   * @param a Array to convert.
   * @return A new array.
   */
  public static byte[] fromArr (ArrayList<Byte> a) {
    int sz = a.size();
    Byte[] bs = a.toArray(new Byte[sz]);
    byte[] r = new byte[sz];
    for (int i = 0; i < sz; ++i) r[i] = bs[i];
    return r;
  }

  /**
   * Converts a 'byte[]' to ArrayList.
   * @param bs Array to convert.
   * @return A new array.
   */
  public static ArrayList<Byte> toArr (byte[] bs) {
    int sz = bs.length;
    Byte[] bbs = new Byte[sz];
    for (int i = 0; i < sz; ++i) bbs[i] = bs[i];
    return new ArrayList<>(Arrays.asList(bbs));
  }

  /**
   * Converts a String to 'byte[]' codified in "UTF-8".
   * @param s String to convert.
   * @return A new array.
   */
  public static byte[] fromStr (String s) {
    try {
      return s.getBytes("UTF-8");
    } catch (java.io.UnsupportedEncodingException e) {
      throw new IllegalArgumentException (e);
    }
  }

  /**
   * Converts a 'byte[]' codified in "UTF-8" to String.
   * @param bs Array to convert.
   * @return A new String.
   */
  public static String toStr (byte[] bs) {
    try {
      return new String(bs, "UTF-8");
    } catch (java.io.UnsupportedEncodingException e) {
      throw new IllegalArgumentException (e);
    }
  }

  /**
   * Adds two 'byte[]'.
   * @param bs1 First array.
   * @param bs2 Second array.
   * @return A new array equals to bs1 + bs2.
   */
  public static byte[] add (byte[] bs1, byte[] bs2) {
    int sz1 = bs1.length;
    int sz2 = bs2.length;
    byte[] r = Arrays.copyOf(bs1, sz1 + sz2);
    System.arraycopy(bs2, 0, r, sz1, sz2);
    return r;
  }

  /**
   * Returns the n first elements of 'bs'.<p>
   * If 'len' &lt; 0, it is set to 0.<p>
   * If 'len' &gt; bs.length, all the array is returned.
   * @param bs Array to truncate.
   * @param n Bytes to keep in.
   * @return A new array.
   */
  public static byte[] take (byte[] bs, int n) {
    if (n < 0) n = 0;
    else if (n > bs.length) n = bs.length;
    return Arrays.copyOf(bs, n);
  }

  /**
   * Returns the remains elements after removing those witch are returned by
   * 'take'.
   * @param bs Array to truncate.
   * @param n Bytes to remove.
   * @return A new array.
   */
  public static byte[] drop (byte[] bs, int n) {
    int sz = bs.length;
    if (n < 0) n = 0;
    else if (n > sz) n = sz;
    return Arrays.copyOfRange(bs, n, sz);
  }
}
