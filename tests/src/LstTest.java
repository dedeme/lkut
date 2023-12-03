// Copyright 29-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

import java.util.function.BiPredicate;
import java.util.ArrayList;
import kut.Lst;
import kut.Str;
import kut.Js;

public class LstTest {
  static BiPredicate<Integer, Integer> eqi = (n1, n2) -> n1 == n2;
  public static Lst<Integer> l0 = new Lst<>();
  public static Lst<Integer> l2 = new Lst<>(1, 2);
  public static Lst<Integer> l3 = new Lst<>(1, 2, 3);
  public static Lst<Integer> l5 = l2.concat(l3);

  public static void run () {
    System.out.println("Lst Tests");

    int[] sum = new int[]{0};

    assert(l0.eq(new Lst<Integer>(), eqi));
    assert(l0.reverse().eq(l0, eqi));
    assert(l0.all((n) -> n > 2));
    assert(!l0.any((n) -> n > 2));
    assert(l0.concat(l0).eq(l0, eqi));
    Test.eqi(new Lst<Integer>().count(), 0);
    assert(l0.copy().eq(l0, eqi));
    assert(l0.drop(-1).isEmpty());
    assert(l0.drop(1).isEmpty());
    l0.each((e) -> sum[0] += e);
    Test.eqi(sum[0], 0);
    l0.each((e, i) -> sum[0] += i);
    Test.eqi(sum[0], 0);
    assert(l0.filter((n) -> n > 0).isEmpty());
    assert(l0.find((n) -> n > 0).isNone());
    assert(l0.index((n) -> n > 0) == -1);
    assert(l0.map((n) -> n * 2).isEmpty());
    Test.eqi(l0.reduce(0, (r, e) -> r + e), 0);
    assert(l0.take(-1).isEmpty());
    assert(l0.take(1).isEmpty());
    Test.eq(l0.toString(), "Lst[]");

    Test.eqi(l2.count(), 2);
    Test.eqi(l3.count(), 3);

    assert(l2.all((n) -> n > 0));
    assert(!l2.all((n) -> n > 1));
    assert(!l2.all((n) -> n > 100));
    assert(l2.any((n) -> n > 0));
    assert(l2.any((n) -> n > 1));
    assert(!l2.any((n) -> n > 100));

    assert(l0.concat(l2).eq(l2, eqi));
    assert(l3.concat(l0).eq(l3, eqi));
    assert(l2.concat(l3).eq(l5, eqi));

    assert(l2.copy().eq(l2, eqi));

    assert(l2.drop(-1).eq(l2, eqi));
    assert(l2.drop(1).eq(new Lst<>(2), eqi));
    assert(l2.drop(100).isEmpty());

    assert(l2.dropWhile((n) -> n > 100).eq(l2, eqi));
    assert(l2.dropWhile((n) -> n < 2).eq(new Lst<>(2), eqi));
    assert(l2.dropWhile((n) -> n < 100).isEmpty());

    sum[0] = 0;
    l2.each((e) -> sum[0] += e);
    Test.eqi(sum[0], 3);
    sum[0] = 0;
    l3.each((e) -> sum[0] += e);
    Test.eqi(sum[0], 6);
    sum[0] = 0;
    l2.each((e, i) -> sum[0] += i);
    Test.eqi(sum[0], 1);
    sum[0] = 0;
    l3.each((e, i) -> sum[0] += i);
    Test.eqi(sum[0], 3);

    assert(l2.eq(new Lst<>(1, 2), eqi));
    assert(l3.eq(new Lst<>(1, 2, 3), eqi));
    assert(l5.eq(new Lst<>(1, 2, 1, 2, 3), eqi));

    assert(l5.filter((n) -> n > 1).eq(new Lst<>(2, 2, 3), eqi));
    assert(l5.filter((n) -> n > 100).isEmpty());

    assert(l5.find((n) -> n == 12).isNone());
    Test.eqi(l5.find((n) -> n > 1).getValue(), 2);

    assert(l5.index((n) -> n == 12) == -1);
    Test.eqi(l5.index((n) -> n > 1), 1);

    assert(l5.map((n) -> n * 2).eq(new Lst<>(2, 4, 2, 4, 6), eqi));

    Test.eqi(l5.reduce(0, (r, e) -> r + e), 9);


    assert(l2.reverse().eq(new Lst<>(2, 1), eqi));
    assert(l3.reverse().eq(new Lst<>(3, 2, 1), eqi));

    assert(l2.take(-1).isEmpty());
    assert(l2.take(1).eq(new Lst<>(1), eqi));
    assert(l2.take(100).eq(l2, eqi));

    assert(l2.takeWhile((n) -> n > 100).isEmpty());
    assert(l2.takeWhile((n) -> n < 2).eq(new Lst<>(1), eqi));
    assert(l2.takeWhile((n) -> n < 100).eq(l2, eqi));

    Test.eq(new Lst<>(1).toString(), "Lst[1]");
    Test.eq(l2.toString(), "Lst[1, 2]");

    String tx0 = "";
    String tx01 = ";";
    String tx1 = "ab";
    String tx2 = "ab;";
    String tx3 = "ab;c;de";
    String tx01b = ";--";
    String tx2b = "ab;--";
    String tx3b = "ab ;--  c \t ;--  de";
    String tx3c = "ab;--c;--de";
    Lst<String> a = new Lst<>(Str.split(tx0, ";"));
    Test.eqi(a.count(), 1);
    String r = Lst.join(a, ";");
    Test.eq(r, tx0);
    a = new Lst<>(Str.split(tx01, ";"));
    r = Lst.join(a, ";");
    Test.eq(r, ";");
    a = new Lst<>(Str.split(tx1, ";"));
    Test.eqi(a.count(), 1);
    r = Lst.join(a, ";");
    Test.eq(r, tx1);
    a = new Lst<>(Str.split(tx2, ";"));
    r = Lst.join(a, ";");
    Test.eq(r, tx2);
    a = new Lst<>(Str.split(tx3, ";"));
    Test.eqi(a.count(), 3);
    r = Lst.join(a, ";");
    Test.eq(r, tx3);
    a = new Lst<>(Str.split("ab;cd;", ";"));
    Test.eqi(a.count(), 3);
    Test.eq(Lst.join(a, ";"), "ab;cd;");
    a = new Lst<>(Str.split(tx0, ";--"));
    r = Lst.join(a, ";--");
    Test.eq(r, tx0);
    a = new Lst<>(Str.split(tx01b, ";--"));
    r = Lst.join(a, ";--");
    Test.eq(r, tx01b);
    a = new Lst<>(Str.split(tx1, ";--"));
    r = Lst.join(a, ";--");
    Test.eq(r, tx1);
    a = new Lst<>(Str.split(tx2b, ";--"));
    r = Lst.join(a, ";--");
    Test.eq(r, tx2b);
    a = new Lst<>(Str.split(tx3b, ";--"));
    r = Lst.join(a, ";--");
    Test.eq(r, tx3b);
    a = new Lst<>(Str.splitTrim(tx3b, ";--"));
    r = Lst.join(a, ";--");
    Test.eq(r, tx3c);
    a = new Lst<>(Str.split("", ""));
    Test.eqi(a.count(), 0);
    r = Lst.join(a, "");
    Test.eq(r, "");
    a = new Lst<>(Str.split("a", ""));
    Test.eqi(a.count(), 1);
    r = Lst.join(a, "");
    Test.eq(r, "a");
    a = new Lst<>(Str.split("abñ", ""));
    Test.eqi(a.count(), 3);
    r = Lst.join(a, "");
    Test.eq(r, "abñ");

    Test.eq(new Lst<>("a, b", "a\\b\"c", "c").toString(),
      "Lst[\"a, b\", \"a\\\\b\\\"c\", \"c\"]");

    assert(
      Lst.fromJs(
        Lst.toJs(new Lst<Integer>(), (e) -> Js.w(e)),
        (e) -> Js.ri(e)
      ).eq(
        new Lst<Integer>(), eqi
      )
    );
    assert(
      Lst.fromJs(
        Lst.toJs(l5, (e) -> Js.w(e)),
        (e) -> Js.ri(e)
      ).eq(
        l5, eqi
      )
    );

    System.out.println("    Finished");
  }
}
