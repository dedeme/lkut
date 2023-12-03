// Copyright 30-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package kut;

import java.util.ArrayList;
import kut.Dic;

/** JSON encode and decode */
public class Js {

  // Returns a exception in position 'pos' of 'js'.
  static IllegalArgumentException exc (String js, int pos, String msg) {
    String js2 = js.substring(pos);
    return new IllegalArgumentException (
      "  \nJS error: " + msg +
      "  \n      in: |" + (js.length() <= 50 ? js : Str.left(js, 47) + "...") +
      "  \n      at: |(character " + String.valueOf(pos) + ") ->" +
      "  \n          |" + (js2.length() <= 50 ? js2 : Str.left(js2, 47) + "...")
    );
  }

  // Skip blanks.
  // Returns the index of first not space in 'js' from 'from' or 'js.length()'
  // if end of String is reached.
  static int sBlanks (String js, int from) {
    int sz = js.length();
    for (;;) {
      if (from == sz || !Character.isWhitespace(js.charAt(from))) return from;
      ++from;
    }
  }

  // Skip string.
  // Returns the index of the second quote of 'js' from 'js'.
  // If end of String is reached, returns -1.
  // When starting 'from' is the index of the first character after the first
  // quote.
  static int sString (String js, int from) {
    int sz = js.length();
    boolean isEsc = false;
    for (;;) {
      if (from == sz) return -1;
      if (isEsc) isEsc = false;
      else {
        char ch = js.charAt(from);
        if (ch == '"') return from;
        if (ch == '\\') isEsc = true;
      }
      ++from;
    }
  }

  // Skip container.
  // Returns the position of 'target' and fill 'elements' with JSON strings.
  // If end of String is reached, returns -1.
  // When starting 'from' is the index of the first character after the open
  // symbol ('[' or '{').
  static int sContainer (
    ArrayList<String> elements, String js, int from, char target
  ) {
    int sz = js.length();
    if (from == sz) return -1;
    if (js.charAt(from) == target) return from;

    int eStart = from;
    for (;;) {
      if (from == sz) return -1;
      char ch = js.charAt(from);
      if (ch == target) {
        elements.add(js.substring(eStart, from));
        return from;
      }
      else if (ch == '"') {
        int pos = sString(js, from + 1);
        if (pos == -1)
          throw exc(js, from, "Quotes not closed");
        from = pos;
      } else if (ch == '[') {
        from = sContainer(new ArrayList<String>(), js, from + 1, ']');
      } else if (ch == '{') {
        from = sContainer(new ArrayList<String>(), js, from + 1, '}');
      } else if (ch == ',') {
        elements.add(js.substring(eStart, from));
        eStart = from + 1;
      }
      ++from;
    }
  }

  /**
   * Returns 'true' if 'js' is null.
   * @param js JSON string.
   * @return 'true' if 'js' is null.
   */
  public static boolean isNull (String js) {
    return js.trim().equals("null");
  }

  /**
   * Returns the value of 'js'.
   * @param js JSON string.
   * @return Value of 'js'.
   */
  public static boolean rb (String js) {
    int pos = sBlanks(js, 0);
    String js2 = js.substring(pos);
    if (js2.startsWith("true")) {
      int pos2 = sBlanks(js, pos + 4);
      if (pos2 == js.length()) return true;
      throw exc(js, pos2, "Spare characters.");
    }
    if (js2.startsWith("false")) {
      int pos2 = sBlanks(js, pos + 5);
      if (pos2 == js.length()) return false;
      throw exc(js, pos2, "Spare characters.");
    }
    throw exc(js, pos, "Boolean expression not found.");
  }

  /**
   * Returns the value of 'js'.
   * @param js JSON string.
   * @return Value of 'js'.
   */
  public static int ri (String js) {
    return (int)rn(js);
  }

  /**
   * Returns the value of 'js'.
   * @param js JSON string.
   * @return Value of 'js'.
   */
  public static double rn (String js) {
    int pos = sBlanks(js, 0);
    try {
      return Double.valueOf(js);
    } catch (Exception e) {
      throw exc(js, pos, "Bad number.");
    }
  }

  /**
   * Returns the value of 'js'.
   * @param js JSON string.
   * @return Value of 'js'.
   */
  public static String rs (String js) {
    int pos = sBlanks(js, 0);
    if (pos == js.length() || js.charAt(pos) != '"')
      throw exc(js, pos, "First quote of String expression not found.");

    int pos2 = sString(js, pos + 1);
    if (pos2 == -1)
      throw exc(js, pos, "Quotes not closed.");
    int pos3 = sBlanks(js, pos2 + 1);
    if (pos3 != js.length())
      throw exc(js, pos3, "Spare characters.");

    StringBuilder sb = new StringBuilder();
    ++pos;
    for (;;) {
      if (pos == pos2) return sb.toString();
      char ch = js.charAt(pos++);
      if (ch <= ' ') sb.append(' ');
      else if (ch == '\\') {
        if (pos == pos2)
          throw exc(js, pos - 1, "Illegal escape sequence in String");
        ch = js.charAt(pos++);
        ch = switch (ch) {
          case '"' -> '"';
          case '\\' -> '\\';
          case 'b' -> '\b';
          case 'f'-> '\f';
          case 'n'-> '\n';
          case 'r'-> '\r';
          case 't'-> '\t';
          case 'u'-> {
            if (pos + 4 > pos2)
              throw exc(js, pos - 2, "Illegal unicode in String");
            try {
              pos += 4;
              yield (char)(int)Integer.decode("0x" + js.substring(pos - 4, pos));
            } catch (Exception e) {
              throw exc(js, pos - 6, "Illegal unicode in String");
            }
          }
          case '/'-> '/';
          default ->
            throw exc(js, pos - 1, "Illegal escape sequence in String");
        };
        sb.append(ch);
      } else {
        sb.append(ch);
      }
    }
  }

  /**
   * Returns the value of 'js'.
   * @param js JSON string.
   * @return Value of 'js'.
   */
  public static ArrayList<String> ra (String js) {
    int pos = sBlanks(js, 0);
    if (pos == js.length() || js.charAt(pos) != '[')
      throw exc(js, pos, "First square of Array expression not found.");

    ArrayList<String> elements = new ArrayList<>();
    int pos2 = sContainer(elements, js, pos + 1, ']');
    if (pos2 == -1)
      throw exc(js, pos, "Square not closed.");

    int pos3 = sBlanks(js, pos2 + 1);
    if (pos3 != js.length())
      throw exc(js, pos3, "Spare characters.");

    return elements;
  }

  /**
   * Returns the value of 'js'.
   * @param js JSON string.
   * @return Value of 'js'.
   */
  public static Dic<String> ro (String js) {
    int pos = sBlanks(js, 0);
    if (pos == js.length() || js.charAt(pos) != '{')
      throw exc(js, pos, "First bracket of Object expression not found.");

    ArrayList<String> elements = new ArrayList<>();
    int pos2 = sContainer(elements, js, pos + 1, '}');
    if (pos2 == -1)
      throw exc(js, pos, "Bracket not closed.");

    int pos3 = sBlanks(js, pos2 + 1);
    if (pos3 != js.length())
      throw exc(js, pos3, "Spare characters.");

    Dic<String> dic = new Dic<>();
    for (String e : elements) {
      int ix = sBlanks(e, 0);
      if (ix == e.length() || e.charAt(ix) != '"')
        throw exc(e, pos + ix, "First quote of Object key not found.");

      int ix2 = sString(e, ix + 1);
      if (ix2 == -1)
        throw exc(e, pos + ix, "Quotes not closed.");
      int ix3 = sBlanks(e, ix2 + 1);
      if (ix3 == js.length() || e.charAt(ix3) != ':')
        throw exc(js, pos + ix3, "Object separator ':' not found.");

      dic.put(rs(e.substring(ix, ix2 + 1)), e.substring(ix3 + 1));

      pos += e.length() + 1; // passing the comma separator.
    }

    return dic;
  }

  /**
   * Returns JSON representation of 'null'.
   * @return JSON value.
   */
  public static String w () {
    return "null";
  }

  /**
   * Returns JSON representation of 'v'.
   * @param v Value to serialize.
   * @return JSON value.
   */
  public static String w (boolean v) {
    return v ? "true" : "false";
  }

  /**
   * Returns JSON representation of 'v'.
   * @param v Value to serialize.
   * @return JSON value.
   */
  public static String w (int v) {
    return String.valueOf(v);
  }

  static String stripZeroes (String s) {
    if (s.indexOf('.') == -1) return s;

    if (s.startsWith(".")) s = "0" + s;
    else if (s.startsWith("-.")) s = "-0" + s.substring(1);

    for (;;) {
      int ix = s.length() - 1;
      if (s.charAt(ix) != '0') break;
      s = s.substring(0, ix);
    }

    int ix = s.length() - 1;
    s = (s.charAt(ix) == '.') ? s.substring(0, ix) : s;
    return s.equals("-0") ? "0" : s;
  }

  /**
   * Returns JSON representation of 'v'.
   * @param v Value to serialize.
   * @return JSON value.
   */
  public static String w (double v) {
    return stripZeroes(Mat.dtos(v));
  }

  /**
   * Returns JSON representation of 'v'.
   * @param v Value to serialize.
   * @param dec Number of decimal positions.
   * @return JSON value.
   */
  public static String w (double v, int dec) {
    return stripZeroes(Mat.dtos(v, dec));
  }

  /**
   * Returns JSON representation of 'v'.
   * @param v Value to serialize.
   * @return JSON value.
   */
  public static String w (String v) {
    v = Str.quote(v);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < v.length(); ++i) {
      char ch = v.charAt(i);
      sb.append(
        ch < ' '
          ? switch (ch) {
              case '\n' -> "\\n";
              case '\t' -> "\\t";
              case '\r' -> "\\r";
              case '\b' -> "\\b";
              case '\f' -> "\\f";
              default -> ch;
            }
          : ch
      );
    }
    return sb.toString();
  }

  /**
   * Returns JSON representation of 'v'.
   * @param v Value to serialize.
   * @return JSON value.
   */
  public static String w (ArrayList<String> v) {
    return "[" + Arr.join(v, ",") + "]";
  }

  /**
   * Returns JSON representation of 'v'.
   * @param v Value to serialize.
   * @return JSON value.
   */
  public static String w (Dic<String> v) {
    return "{" +
      Arr.join(Arr.map(v.toArr(), (tp) -> w(tp.e1) + ":" + tp.e2), ",") +
    "}";
  }
}
