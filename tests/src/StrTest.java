// Copyright 27-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

import java.util.Locale;
import java.util.ArrayList;
import kut.Str;
import kut.Sys;
import kut.Arr;

public class StrTest {
  public static void run () {
    System.out.println("Str Tests");

    // starts - ends

    assert("ab".startsWith(""));
    assert("".startsWith(""));
    assert("ab".startsWith("a"));
    assert("ab".startsWith("ab"));
    assert(!"".startsWith("b"));
    assert(!"ab".startsWith("b"));
    assert(!"ab".startsWith("abc"));

    assert("ab".endsWith(""));
    assert("".endsWith(""));
    assert("ab".endsWith("b"));
    assert("ab".endsWith("ab"));
    assert(!"".endsWith("b"));
    assert(!"ab".endsWith("a"));
    assert(!"ab".endsWith("cab"));

    // index

    Test.eqi("ab".indexOf('a'), 0);
    Test.eqi("ab".indexOf('a', 1), -1);
    Test.eqi("".indexOf('a'), -1);
    Test.eqi("ab".indexOf('b'), 1);
    Test.eqi("ab".indexOf('b', 1), 1);
    Test.eqi("ab".indexOf('c'), -1);

    Test.eqi("ab".indexOf("a"), 0);
    Test.eqi("".indexOf("a"), -1);
    Test.eqi("ab".indexOf("b"), 1);
    Test.eqi("ab".indexOf("c"), -1);
    Test.eqi("ab".indexOf(""), 0);
    Test.eqi("".indexOf(""), 0);
    Test.eqi("ab".indexOf("abc"), -1);
    Test.eqi("abcd".indexOf("bc"), 1);
    Test.eqi("abcd".indexOf("bcd"), 1);
    Test.eqi("abcd".indexOf("d"), 3);
    Test.eqi("abcd".indexOf("d", 0), 3);
    Test.eqi("abcd".indexOf("d", 2), 3);
    Test.eqi("abcde".indexOf("d", 4), -1);
    Test.eqi("abcd".indexOf("ad"), -1);

    Test.eqi("ab".lastIndexOf('a'), 0);
    Test.eqi("aba".lastIndexOf('a'), 2);
    Test.eqi("".lastIndexOf('a'), -1);
    Test.eqi("ab".lastIndexOf('b'), 1);
    Test.eqi("ab".lastIndexOf('c'), -1);

    Test.eqi("ab".lastIndexOf("a"), 0);
    Test.eqi("abac".lastIndexOf("a"), 2);
    Test.eqi("".lastIndexOf("a"), -1);
    Test.eqi("ab".lastIndexOf("b"), 1);
    Test.eqi("abcb".lastIndexOf("b"), 3);
    Test.eqi("ab".lastIndexOf("c"), -1);
    Test.eqi("ab".lastIndexOf(""), 2);
    Test.eqi("".lastIndexOf(""), 0);
    Test.eqi("ab".lastIndexOf("abc"), -1);
    Test.eqi("abcd".lastIndexOf("bc"), 1);
    Test.eqi("abcdbc".lastIndexOf("bc"), 4);
    Test.eqi("abcd".lastIndexOf("bcd"), 1);
    Test.eqi("abcd".lastIndexOf("d"), 3);
    Test.eqi("abcddd".lastIndexOf("d"), 5);
    Test.eqi("abcd".lastIndexOf("ad"), -1);

    // copy / new / cmp

    Test.eq(Str.newC('n'), "n");

    String s0 = "";
    String s1 = "1";

    String r;

    r = new String(s0);
    Test.eq(r, "");
    Test.eqi(r.length(), 0);
    assert (r.compareTo("") == 0);
    assert (r.equals(""));
    assert (r.compareTo("1") != 0);
    assert (!r.equals("1"));

    r = new String(s1);
    Test.eq(r, "1");
    Test.eqi(r.length(), 1);
    assert (r.compareTo("") != 0);
    assert (!r.equals(""));
    assert (r.compareTo("1") == 0);
    assert (r.equals("1"));

    Test.eqi("avc".length(), 3);
    assert("n".compareTo("z") < 0);
    assert("z".compareTo("n") > 0);
    assert("é".compareTo("n") > 0);

    Locale lc = Sys.getLocale();
    Sys.setLocale("es_ES");
    assert(Str.cmp("é", "n") < 0);
    Sys.setLocale(lc);

    r = Str.fmt("%s%s", s0, s1);
    Test.eq(r, "1");

    String r2 = Str.fmt("%s%s", r, "ab");
    Test.eq(r2, "1ab");
    r = Str.fmt("%s%s%s%s%s%s%s", r2, "ab", "", "cd", "ab", s1, "cd");
    Test.eq(r, "1ababcdab1cd");

    // quote

    Test.eq(Str.quote(""), "\"\"");
    Test.eq(Str.quote("abc"), "\"abc\"");
    Test.eq(Str.quote("a\\b\"c"), "\"a\\\\b\\\"c\"");

    // sub

    r = Str.left("ab", 1);
    Test.eq(r, "a");
    r = Str.fmt("%s%s", r, "b");

    r = Str.sub(r, -2, -1);
    Test.eq(r, "a");
    r = Str.fmt("%s%s", r, "b");
    r = Str.sub(r, 0, 0);
    Test.eq(r, "");
    r = Str.fmt("%s%s", r, "ab");
    r = Str.sub(r, -1, 0);
    Test.eq(r, "");
    r = Str.fmt("%s%s", r, "ab");
    r = Str.sub(r, 0, 35);
    r = Str.fmt("%s%s", r, "ab");
    r = Str.sub(r, 3, 3);
    Test.eq(r, "");
    r = Str.fmt("%s%s", r, "ab");
    Test.eq(r, "ab");
    r = Str.sub(r, 2, 2);
    Test.eq(r, "");
    r = Str.fmt("%s%s", r, "ab");
    r = Str.sub(r, 0, 2);
    Test.eq(r, "ab");
    r = Str.sub(r, 1, 2);
    Test.eq(r, "b");
    r = Str.right(r, 20);
    r = Str.left(r, 0);
    Test.eq(r, "");
    r = Str.fmt("%s%s", r, "ab");
    r = Str.right(r, 0);
    Test.eq(r, "ab");
    r = Str.right(r, -1);
    Test.eq(r, "b");

    // trim

    r = "nothing to trim".trim();
    Test.eq(r, "nothing to trim");
    r = "trim the back     ".trim();
    Test.eq(r, "trim the back");
    r = " trim one char front and back ".trim();
    Test.eq(r, "trim one char front and back");
    r = " trim one char front".trim();
    Test.eq(r, "trim one char front");
    r = "trim one char back ".trim();
    Test.eq(r, "trim one char back");
    r = "                   ".trim();
    Test.eq(r, "");
    r = " ".trim();
    Test.eq(r, "");
    r = "a".trim();
    Test.eq(r, "a");
    r = "".trim();
    Test.eq(r, "");

    r = Str.ltrim("nothing to trim");
    Test.eq(r, "nothing to trim");
    r = Str.ltrim("trim the back     ");
    Test.eq(r, "trim the back     ");
    r = Str.ltrim(" trim one char front and back ");
    Test.eq(r, "trim one char front and back ");
    r = Str.ltrim(" trim one char front");
    Test.eq(r, "trim one char front");
    r = Str.ltrim("trim one char back ");
    Test.eq(r, "trim one char back ");
    r = Str.ltrim("                   ");
    Test.eq(r, "");
    r = Str.ltrim(" ");
    Test.eq(r, "");
    r = Str.ltrim("a");
    Test.eq(r, "a");
    r = Str.ltrim("");
    Test.eq(r, "");

    r = Str.rtrim("nothing to trim");
    Test.eq(r, "nothing to trim");
    r = Str.rtrim("trim the back     ");
    Test.eq(r, "trim the back");
    r = Str.rtrim(" trim one char front and back ");
    Test.eq(r, " trim one char front and back");
    r = Str.rtrim(" trim one char front");
    Test.eq(r, " trim one char front");
    r = Str.rtrim("trim one char back ");
    Test.eq(r, "trim one char back");
    r = Str.rtrim("                   ");
    Test.eq(r, "");
    r = Str.rtrim(" ");
    Test.eq(r, "");
    r = Str.rtrim("a");
    Test.eq(r, "a");
    r = Str.rtrim("");
    Test.eq(r, "");

    // split / join

    // <char>
    ArrayList<String> a;
    String tx0 = "";
    String tx01 = ";";
    String tx1 = "ab";
    String tx2 = "ab;";
    String tx3 = "ab;c;de";
    String tx01b = ";--";
    String tx2b = "ab;--";
    String tx3b = "ab ;--  c \t ;--  de";
    String tx3c = "ab;--c;--de";

    a = Str.split(tx0, ";");
    Test.eqi(a.size(), 1);
    r = Arr.join(a, ";");
    Test.eq(r, tx0);
    a = Str.split(tx01, ";");
    r = Arr.join(a, ";");
    Test.eq(r, ";");
    a = Str.split(tx1, ";");
    Test.eqi(a.size(), 1);
    r = Arr.join(a, ";");
    Test.eq(r, tx1);
    a = Str.split(tx2, ";");
    r = Arr.join(a, ";");
    Test.eq(r, tx2);
    a = Str.split(tx3, ";");
    Test.eqi(a.size(), 3);
    r = Arr.join(a, ";");
    Test.eq(r, tx3);
    a = Str.split("ab;cd;", ";");
    Test.eqi(a.size(), 3);
    Test.eq(Arr.join(a, ";"), "ab;cd;");
    a = Str.split(tx0, ";--");
    r = Arr.join(a, ";--");
    Test.eq(r, tx0);
    a = Str.split(tx01b, ";--");
    r = Arr.join(a, ";--");
    Test.eq(r, tx01b);
    a = Str.split(tx1, ";--");
    r = Arr.join(a, ";--");
    Test.eq(r, tx1);
    a = Str.split(tx2b, ";--");
    r = Arr.join(a, ";--");
    Test.eq(r, tx2b);
    a = Str.split(tx3b, ";--");
    r = Arr.join(a, ";--");
    Test.eq(r, tx3b);
    a = Str.splitTrim(tx3b, ";--");
    r = Arr.join(a, ";--");
    Test.eq(r, tx3c);
    a = Str.split("", "");
    Test.eqi(a.size(), 0);
    r = Arr.join(a, "");
    Test.eq(r, "");
    a = Str.split("a", "");
    Test.eqi(a.size(), 1);
    r = Arr.join(a, "");
    Test.eq(r, "a");
    a = Str.split("abñ", "");
    Test.eqi(a.size(), 3);
    r = Arr.join(a, "");
    Test.eq(r, "abñ");

    // replace

    r = "";
    r = r.replace('.', ',');
    Test.eq(r, "");
    r = ".";
    r = r.replace('.', ',');
    Test.eq(r, ",");
    r = "f.j";
    r = r.replace('.', ',');
    Test.eq(r, "f,j");
    r = "f.j.";
    r = r.replace('.', ',');
    Test.eq(r, "f,j,");

    r = "".replace(".", "");
    Test.eq(r, "");
    r = ".".replace(".", "");
    Test.eq(r, "");
    r = "f.j".replace(".", "");
    Test.eq(r, "fj");
    r = "f.j.".replace(".", "");
    Test.eq(r, "fj");

    r = "".replace("..", "---");
    Test.eq(r, "");
    r = "..".replace("..", "---");
    Test.eq(r, "---");
    r = "f..j".replace("..", "---");
    Test.eq(r, "f---j");
    r = "f.j..".replace("..", "---");
    Test.eq(r, "f.j---");

    // fmt

    r = Str.fmt("");
    Test.eq(r, "");
    r = Str.fmt("ab");
    Test.eq(r, "ab");
    r = Str.fmt("Result is %d", 1);
    Test.eq(r, "Result is 1");
    r = Str.fmt("Your %s number %d", "book", 1);
    Test.eq(r, "Your book number 1");
    r = Str.fmt("%s%s%s", "1", "ab", "");
    Test.eq(r, "1ab");
    r = Str.fmt("%s%s%s", "1", "ab", "c");
    Test.eq(r, "1abc");

    // Encoding

    Test.eq(Str.fromIso(Str.toIso("")), "");
    Test.eq(Str.fromIso(Str.toIso("a")), "a");
    Test.eq(Str.fromIso(Str.toIso("ñ")), "ñ");
    Test.eq(Str.fromIso(Str.toIso(" a\tb €\n")), " a\tb €\n");
    Test.eq(Str.fromUtf8(Str.toUtf8("")), "");
    Test.eq(Str.fromUtf8(Str.toUtf8("a")), "a");
    Test.eq(Str.fromUtf8(Str.toUtf8("ñ")), "ñ");
    Test.eq(Str.fromUtf8(Str.toUtf8(" a\tb €\n")), " a\tb €\n");

    System.out.println("    Finished");
  }
}
