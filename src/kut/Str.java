// Copyright 26-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package kut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/** String management. */
public class Str {
  Str () {}

  /**
   * Compares two strings using the Sys.collator.
   * @param s1 First String.
   * @param s2 Second String.
   * @return -1 if s1 &lt; s1, 0 if s1 == s2 and 1 if s1 > s2.
   */
  public static int cmp (String s1, String s2) {
    return Sys.collator.compare(s1, s2);
  }

  /**
   * Returns a String using 'fm'.<br>
   * See <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Formatter.html">
   * Formatter</a>.
   * @param fm Format string.
   * @param args 'fm' arguments.
   * @return 'fm' sustitutiyng variables by arguments.
   */
  public static String fmt (String fm, Object... args) {
    return new java.util.Formatter().format(fm, args).toString();
  }

  /**
   * Returns String from 'byte[]' codified in ISO-8859-15.
   * @param bs String codified in ISO-8859-15.
   * @return String
   */
  public static String fromIso (byte[] bs) {
    try {
      return new String(bs, "ISO-8859-15");
    } catch (java.io.UnsupportedEncodingException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * Returns String from 'byte[]' codified in UTF-8.
   * @param bs String codified in UTF-8.
   * @return String
   */
  public static String fromUtf8 (byte[] bs) {
    try {
      return new String(bs, "UTF-8");
    } catch (java.io.UnsupportedEncodingException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * Equals to sub(s, 0, n).
   * @param s String to cut.
   * @param n Number of characters.
   * @return 'sub(s, 0, n)'.
   */
  public static String left (String s, int n) {
    return sub(s, 0, n);
  }

  /**
   * Removes left blanks.
   * @param s String to cut.
   * @return A new String.
   */
  public static String ltrim (String s) {
    return left((s + "|").trim(), -1);
  }

  /**
   * Returns a String with the character 'ch'
   * @param ch Character.
   * @return String
   */
  public static String newC (char ch) {
    return String.valueOf(ch);
  }

  /**
   * Quotes 's', replacing '"' by '\"' and '\' by '\\'. (e.g. a"bc -> "a\"bc")
   * @param s String to quote.
   * @return 's' quoted.
   */
  public static String quote (String s) {
    return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
  }

  /**
   * Equals to sub(s, n, s.length()).
   * @param s String to cut.
   * @param n Number of characters.
   * @return 'sub(s, 0, n)'.
   */
  public static String right (String s, int n) {
    return sub(s, n, s.length());
  }

  /**
   * Removes right blanks.
   * @param s String to cut.
   * @return String.
   */
  public static String rtrim (String s) {
    return ("|" + s).trim().substring(1);
  }

  /**
   * Splits a String with the 'sep'.<p>
   * Examples:<p>
   *   sys.test(arr.size(str.split("", "")), 0);<br>
   *   sys.test(arr.join(str.split("", ""), ""), "");<br>
   *   sys.test(arr.size(str.split("a", "")), 1);<br>
   *   sys.test(arr.join(str.split("a", ""), ""), "a");<br>
   *   sys.test(arr.size(str.split("añ", "")), 2);<br>
   *   sys.test(arr.join(str.split("añ", ""), ""), "añ");<br>
   *   sys.test(arr.size(str.split("", ";")), 1;<br>
   *   sys.test(arr.join(str.split("", ";"), ";"), "");<br>
   *   sys.test(arr.size(str.split("ab;cd;", ";")), 3);<br>
   *   sys.test(arr.join(str.split("ab;cd;", ";"), ";"), "ab;cd;");<br>
   *   sys.test(arr.size(str.split("ab;cd", ";")), 2);<br>
   *   sys.test(arr.join(str.split("ab;cd", ";"), ";"), "ab;cd")
   * @param s String to split.
   * @param sep Separator to use.
   * @return Array.
   */
  public static ArrayList<String> split (String s, String sep) {
    if (s.equals("")) {
      if (sep.equals("")) return new ArrayList<>();
      return new ArrayList<>(Arrays.asList(""));
    }
    ArrayList<String> r =
      new ArrayList<>(Arrays.asList(s.split(Pattern.quote(sep))));
    if (!sep.equals("") && s.endsWith(sep)) {
      r.add("");
      if (s.equals(sep)) r.add("");
    }
    return r;
  }

  /**
   * Equals to 'split' but 'trimming' strings.
   * @param s String to split.
   * @param sep Separator to use.
   * @return Array.
   */
  public static ArrayList<String> splitTrim (String s, String sep) {
    ArrayList<String> r = split(s, sep);
    for (int i = 0; i < r.size(); ++i) r.set(i, r.get(i).trim());
    return r;
  }

  /**
   * Returns characters of 's' between 'begin' inclusive and 'end' exclusive.
   * @param s String to cut.
   * @param begin If it is less than 0, it is set to s.length() + begin. If it
   *  is less than 0 yet, it is set to 0. If it is greater than s.length(),
   *  it is set to s.length().
   * @param end If it is less than 0, it is set to s.length() + begin. If then
   *  it is less than begin, it is set to begin. If it is greater than s.length(),
   *  it is set to s.length().
   * @return String.
   */
  public static String sub (String s, int begin, int end) {
    int sz = s.length();
    if (begin < 0) {
      begin = sz + begin;
      if (begin < 0) begin = 0;
    }
    if (begin > sz) begin = sz;
    if (end < 0) end = sz + end;
    if (end < begin) end = begin;
    if (end > sz) end = sz;

    return s.substring(begin, end);
  }

  /**
   *  Returns 'byte[]' codified in ISO-8859-15 from String.
   * @param s String
   * @return Bytes.
   */
  public static byte[] toIso (String s) {
    try {
      return s.getBytes("ISO-8859-15");
    } catch (java.io.UnsupportedEncodingException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   *  Returns 'byte[]' codified in UTF-8 from String.
   * @param s String
   * @return Bytes.
   */
  public static byte[] toUtf8 (String s) {
    try {
      return s.getBytes("UTF-8");
    } catch (java.io.UnsupportedEncodingException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
