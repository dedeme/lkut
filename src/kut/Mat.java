// Copyright 24-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package kut;

import java.util.Random;
import java.util.Locale;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/** Mathematical functions */
public class Mat {
  Mat () {};

  static final Random random = new Random();

  /**
   * Returns a String from a double.
   * @param n A number
   * @return A Stringg.
   */
  public static String dtos (double n) {
    return String.valueOf(n);
  }

  /**
   * Returns a String type "nnnn.nnn"
   * @param n A number
   * @param dec Number of decimal positions between 0 and 9 (both inclusive).<p>
   *            'dec' is adjusted to that limits.
   * @return String type "nnnn.nnn".
   */
  public static String dtos (double n, int dec) {
    if (dec < 0) dec = 0;
    else if (dec > 9) dec = 9;
    return Str.fmt("%." + String.valueOf(dec) + "f", n).replace(",", ".");
  }

  /**
   * Returns "true" if n1 &lt; n2 + gap and n1 > n2 - gap<p>
   * 'gap' is forzed to a value between '0.0000000001' and '1' (both inclusive).
   * @param n1 Number.
   * @param n2 Number.
   * @param gap Gap.
   * @return Result.
   */
  public static boolean eq (double n1, double n2, double gap) {
    gap = gap > 1 ? 1 : gap < 0.0000000001 ? 0.0000000001 : gap;
    return n1 < n2 + gap && n1 > n2 - gap;
  }

  /**
   * Equals to 'eq(n1, n2, 0.0000000001)'
   * @param n1 Number.
   * @param n2 Number.
   * @return Result.
   */
  public static boolean eq (double n1, double n2) {
    return eq(n1, n2, 0.0000000001);
  }

  /**
   * Returns the double value of 'n' or 'Opt.none()' if 'n' is not a number.
   * @param n String in english format.
   * @return Double value of 'n'.
   */
  public static Opt<Double> fromEn (String n) {
    return stod(n.replace(",", ""));
  }

  /**
   * Returns the double value of 'n' or 'Opt.none()' if 'n' is not a number.
   * @param n String in ISO format.
   * @return Double value of 'n'.
   */
  public static Opt<Double> fromIso (String n) {
    return stod(n.replace(".", "").replace(",", "."));
  }

  /**
   * Returns 'true' if 's' is not empty and only contains digits.
   * @param s String to test.
   * @return Result of test.
   */
  public static boolean isDigits (String s) {
    int sz = s.length();
    if (sz == 0) return false;
    int i = 0;
    for (;;) {
      char ch = s.charAt(i++);
      if (ch < '0' || ch > '9') return false;
      if (i >= sz) return true;
    }
  }

  /**
   * Returns a String from a long.
   * @param n A number
   * @return A Stringg.
   */
  public static String itos (long n) {
    return String.valueOf(n);
  }

  /**
   * Returns a random number between 0 (inclusive) and 1 (exclusive).
   * @return Random number between 0 (inclusive) and 1 (exclusive).
   */
  public static double rnd () {
    return random.nextDouble();
  }

  /**
   * Returns a random number between 0 (inclusive) and 'bound' (exclusive).
   * @param bound Limit for generator (must be positive)
   * @return Random number between 0 (inclusive) and 'bound' (exclusive).
   */
  public static int rndi (int bound) {
    return random.nextInt(bound);
  }

  /**
   * Returns 'n' rounded with 'dec' decimals.
   * @param n Number to round.
   * @param dec Decimal positions. Forzed from '0' to '9', both inclusive.
   * @return 'n' rounded.
   */
  public static double round (double n, int dec) {
    switch (dec) {
      case 0: return Math.round(n);
      case 1: return Math.round(n * 10.0) / 10.0;
      case 2: return Math.round(n * 100.0) / 100.0;
      case 3: return Math.round(n * 1000.0) / 1000.0;
      case 4: return Math.round(n * 10000.0) / 10000.0;
      case 5: return Math.round(n * 100000.0) / 100000.0;
      case 6: return Math.round(n * 1000000.0) / 1000000.0;
      case 7: return Math.round(n * 10000000.0) / 10000000.0;
      case 8: return Math.round(n * 100000000.0) / 100000000.0;
      case 9: return Math.round(n * 1000000000.0) / 1000000000.0;
      default: if (dec < 0) return Math.round(n);
               else return Math.round(n * 1000000000.0) / 1000000000.0;
    }
  }

  /**
   * Returns the integer value of 'n' or 'Opt.none()' if 'n' is not a number.
   * @param n String.
   * @return Integer value of 'n'.
   */
  public static Opt<Integer> stoi (String n) {
    try {
      return Opt.some(Integer.valueOf(n));
    } catch (Exception e) {
      return Opt.none();
    }
  }

  /**
   * Returns the long value of 'n' or 'Opt.none()' if 'n' is not a number.
   * @param n String.
   * @return Long value of 'n'.
   */
  public static Opt<Long> stol (String n) {
    try {
      return Opt.some(Long.valueOf(n));
    } catch (Exception e) {
      return Opt.none();
    }
  }

  /**
   * Returns the float value of 'n' or 'Opt.none()' if 'n' is not a number.
   * @param n String.
   * @return Float value of 'n'.
   */
  public static Opt<Float> stof (String n) {
    try {
      return Opt.some(Float.valueOf(n));
    } catch (Exception e) {
      return Opt.none();
    }
  }

  /**
   * Returns the double value of 'n' or 'Opt.none()' if 'n' is not a number.
   * @param n String.
   * @return Double value of 'n'.
   */
  public static Opt<Double> stod (String n) {
    try {
      return Opt.some(Double.valueOf(n));
    } catch (Exception e) {
      return Opt.none();
    }
  }
}
