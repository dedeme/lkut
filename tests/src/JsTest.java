// Copyright 01-Dic-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

import java.util.function.BiPredicate;
import java.util.ArrayList;
import kut.Arr;
import kut.Dic;
import kut.Js;

public class JsTest {
  static BiPredicate<String, String> eqs = (n1, n2) -> n1.equals(n2);

  public static void run () {
    System.out.println("Js Tests");

    assert(Js.isNull("null"));
    assert(Js.isNull("  null   "));
    assert(!Js.isNull(""));
    assert(!Js.isNull("nuln"));
    String s = Js.w();
    Test.eq(s, "null");
    assert(Js.rb(" true "));
    assert(Js.rb(" false") == false);

//    Js.rb("true and"); // error
//    Js.rb("xx"); // error

    s = Js.w(true);
    assert(Js.rb(s));
    s = Js.w(false);
    assert(!Js.rb(s));

    assert(Js.ri(" 0 ") == 0);
    assert(Js.rn(" 0.23 ") == 0.23);
    assert(Js.ri(" -0 ") == 0);
    assert(Js.rn(" -0.0 ") == 0);
    assert(Js.rn("-0.012 ") == -0.012);
    assert(Js.rn(" -12.11 ") == -12.11);
    assert(Js.rn(" -12.11e22 ") == -12.11e22);
    assert(Js.rn(" -0. ") == 0.0);
    assert(Js.rn(" 3. ") == 3.0);
    assert(Js.rn(" -123. ") == -123.0);
    assert(Js.rn("  1e2 ") == 100.0);
    assert(Js.rn("211e+2") == 21100.0);
    assert(Js.rn("211e-02") == 2.11);
    assert(Js.rn("211e0") == 211.0);

//    Js.ri(" 12abc "); // error
//    Js.ri(" 12] "); // error
//    Js.ri(" 12 }"); // error
//    Js.rn(" .12"); // error
//    Js.rn(" z.12"); // error

    s = Js.w(0);
    assert(Js.ri(s) == 0);
    s = Js.w(254);
    assert(Js.ri(s) == 254);
    s = Js.w(-1100);
    assert(Js.ri(s) == -1100);
    s = Js.w(0.0, 0);
    assert(Js.rn(s) == 0);
    s = Js.w(-0.0, 3);
    Test.eq(s, "0");
    assert(Js.rn(s) == 0);
    s = Js.w(-.0, 2);
    Test.eq(s, "0");
    assert(Js.rn(s) == 0);
    s = Js.w(-0.012, 2);
    assert(Js.rn(s) == -0.01);
    Test.eq(s, "-0.01");
    s = Js.w(1.045, 8);
    assert(Js.rn(s) == 1.045);
    s = Js.w(-21.045, 3);
    assert(Js.rn(s) == -21.045);
    s = Js.w(-21.04, 6);
    assert(Js.rn(s) == -21.04);

    String str = Js.rs("  \"\" ");
    Test.eq(str, "");
    str = Js.rs("  \"a\\u0065\\u222B\" ");
    Test.eq(str, "ae∫");
    str = Js.rs("  \"a\\t\\n\\\"\" ");
    Test.eq(str, "a\t\n\"");

//    Js.rs(" \""); // Error
//    Js.rs(" \"a\" l"); // Error
//    Js.rs(" \" \\ \" "); // Error
//    Js.rs(" \" \\@ \" "); // Error
//    Js.rs(" \" \\u30 \" "); // Error
//    Js.rs(" \" \\u30x3 \" "); // Error

    ArrayList<String> a = Js.ra("[]");
    assert(a.size() == 0);
    a = Js.ra("[123]");
    assert(a.size() == 1);
    double rsd = Js.rn(a.get(0));
    assert(rsd == 123);
    a = Js.ra("[-123.56, true]");
    assert(a.size() == 2);
    rsd = Js.rn(a.get(0));
    assert(rsd == -123.56);
    boolean rs = Js.rb(a.get(1));
    assert(rs);
    a = Js.ra("[-123.56, true, \"a\"]");
    assert(a.size() == 3);
    str = Js.rs(a.get(2));
    Test.eq(str, "a");

    a = Js.ra("[-123.56, true, [], 56]");
    assert(a.size() == 4);
    rsd = Js.rn(a.get(3));
    assert(rsd == 56);
    ArrayList<String> a2 = Js.ra(a.get(2));
    assert(a2.size() == 0);

    a = Js.ra(" [-123.56, true, [\"azf\", false], 56]  ");
    assert(a.size() == 4);
    rsd = Js.rn(a.get(3));
    assert(rsd == 56);
    a2 = Js.ra(a.get(2));
    assert(a2.size() == 2);
    rs = Js.rb(a2.get(1));
    assert(!rs);
    str = Js.rs(a2.get(0));
    Test.eq(str, "azf");

//    Js.ra("[-123.56, true, [], 56] h"); // Error
//    Js.ra(" "); // Error
//    Js.ra("[-123.56, true, [], true   "); // Error

    a = new ArrayList<>();
    s = Js.w(a);
    ArrayList<String> a3 = Js.ra(s);
    assert(Arr.eq(a, a3, eqs));

    a.add(Js.w(true));
    s = Js.w(a);
    a3 = Js.ra(s);
    assert(Arr.eq(a, a3, eqs));

    a.add(Js.w(-16));
    s = Js.w(a);
    a3 = Js.ra(s);
    assert(Arr.eq(a, a3, eqs));

    a.add(Js.w(1, 2));
    s = Js.w(a);
    a3 = Js.ra(s);
    assert(Arr.eq(a, a3, eqs));

    a.add(Js.w("caf"));
    s = Js.w(a);
    a3 = Js.ra(s);
    assert(Arr.eq(a, a3, eqs));

    a2 = new ArrayList<>();
    a.add(Js.w(a2));
    s = Js.w(a);
    a3 = Js.ra(s);
    assert(Arr.eq(a, a3, eqs));

    a2.add(Js.w("a\n\tzzð"));
    a.add(Js.w(a2));
    s = Js.w(a);
    a3 = Js.ra(s);
    assert(Arr.eq(a, a3, eqs));

    Dic<String> m = Js.ro("{}");
    assert(m.size() == 0);

    m = Js.ro(" {\"a\":123 } ");
    assert(m.size() == 1);
    rsd = Js.rn(m.get("a"));
    assert(rsd == 123);

    m = Js.ro(" {\"a\":123, \"b\":true } ");
    assert(m.size() == 2);
    rs = Js.rb(m.get("b"));
    assert(rs);

    m = Js.ro(" {\"a\":123, \"a\":true } ");
    assert(m.size() == 1);
    rs = Js.rb(m.get("a"));
    assert(rs);

    m = new Dic<>();
    s = Js.w(m);
    Dic<String> m2 = Js.ro(s);
    assert(m.eq(m2, eqs));

    m.put("A", Js.w(0));
    s = Js.w(m);
    m2 = Js.ro(s);
    assert(m.eq(m2, eqs));

    m.put("A", Js.w(0));
    s = Js.w(m);
    m2 = Js.ro(s);
    assert(m.eq(m2, eqs));

    m.put("B", Js.w(-34516));
    s = Js.w(m);
    m2 = Js.ro(s);
    assert(m.eq(m2, eqs));

    m.put("C", Js.w(321.19, 2));
    s = Js.w(m);
    m2 = Js.ro(s);
    assert(m.eq(m2, eqs));

    m.put("D", Js.w("caf"));
    s = Js.w(m);
    m2 = Js.ro(s);
    assert(m.eq(m2, eqs));

    m.put("F", Js.w(m));
    s = Js.w(m);
    m2 = Js.ro(s);
    assert(m.eq(m2, eqs));

    System.out.println("  Finished");
  }
}

