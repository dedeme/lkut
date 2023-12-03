// Copyright 24-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

public class Test {
  public static <T> void eq (T actual, T expected) {
    if (!actual.equals(expected))
      throw new IllegalArgumentException(
        "Test failed:" +
        "\n  Expected: " + expected.toString() +
        "\n    Actual: " + actual.toString()
      );
  }

  public static void eqi (long actual, long expected) {
    if (actual != expected)
      throw new IllegalArgumentException(
        "Test failed:" +
        "\n  Expected: " + String.valueOf(expected) +
        "\n    Actual: " + String.valueOf(actual)
      );
  }

  public static void eqf (double actual, double expected) {
    if (actual != expected)
      throw new IllegalArgumentException(
        "Test failed:" +
        "\n  Expected: " + String.valueOf(expected) +
        "\n    Actual: " + String.valueOf(actual)
      );
  }
}

