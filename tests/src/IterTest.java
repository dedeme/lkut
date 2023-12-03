// Copyright 29-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

import java.util.function.BiPredicate;
import java.util.ArrayList;
import kut.Arr;
import kut.Lst;
import kut.Iter;
import kut.Str;

public class IterTest {

  static BiPredicate<Integer, Integer> eqi = (n1, n2) -> n1 == n2;
  public static Iter<Integer> mk0(){
    return new Iter<>(Arr.mk());
  }
  public static Iter<Integer> mk2(){
    return new Iter<>(Arr.mk(1, 2));
  }
  public static Iter<Integer> mk3(){
    return new Iter<>(Arr.mk(1, 2, 3));
  }
  public static Iter<Integer> mk5(){
    return mk2().concat(mk3());
  }

  public static void run () {
    System.out.println("Iter Tests");

    int[] sum = new int[]{0};

    assert(mk0().eq(new Iter<>(new Lst<Integer>()), eqi));
    assert(mk2().eq(mk2(), eqi));
    assert(mk2().eq(new Iter<>(new Lst<Integer>(1, 2)), eqi));

    assert(mk0().eq(Iter.range(4, 4), eqi));
    assert(mk2().eq(Iter.range(1, 3), eqi));
    assert(mk0().eq(Iter.range(-1), eqi));
    assert(mk2().eq(Iter.range(2).map((e) -> e + 1), eqi));

    assert(Iter.unary(0).eq(Iter.range(1), eqi));

    assert(mk0().all((n) -> n > 2));
    assert(!mk0().any((n) -> n > 2));
    Test.eq(mk0().toString(), "Iter[]");
    assert(mk0().concat(mk0()).eq(mk0(), eqi));
    Test.eqi(Iter.range(0).count(), 0);
    assert(mk0().drop(-1).eq(mk0(), eqi));
    assert(mk0().drop(1).eq(mk0(), eqi));
    mk0().each((e) -> sum[0] += e);
    Test.eqi(sum[0], 0);
    mk0().each((e, i) -> sum[0] += i);
    Test.eqi(sum[0], 0);
    assert(mk0().filter((n) -> n > 0).eq(mk0(), eqi));
    assert(mk0().find((n) -> n > 0).isNone());
    assert(mk0().index((n) -> n > 0) == -1);
    assert(mk0().map((n) -> n * 2).eq(mk0(), eqi));
    Test.eqi(mk0().reduce(0, (r, e) -> r + e), 0);
    assert(mk0().take(-1).eq(mk0(), eqi));
    assert(mk0().take(1).eq(mk0(), eqi));

    assert(mk2().all((n) -> n > 0));
    assert(!mk2().all((n) -> n > 1));
    assert(!mk2().all((n) -> n > 100));
    assert(mk2().any((n) -> n > 0));
    assert(mk2().any((n) -> n > 1));
    assert(!mk2().any((n) -> n > 100));

    assert(mk0().concat(mk2()).eq(mk2(), eqi));
    assert(mk3().concat(mk0()).eq(mk3(), eqi));
    assert(mk2().concat(mk3()).eq(mk5(), eqi));

    Test.eqi(mk2().count(), 2);
    Test.eqi(mk3().count(), 3);

    assert(mk2().drop(-1).eq(mk2(), eqi));
    assert(mk2().drop(1).eq(new Iter<>(Arr.mk(2)), eqi));
    assert(mk2().drop(100).eq(new Iter<>(Arr.mk()), eqi));

    assert(mk2().dropWhile((n) -> n > 100).eq(mk2(), eqi));
    assert(mk2().dropWhile((n) -> n < 2).eq(new Iter<>(Arr.mk(2)), eqi));
    assert(mk2().dropWhile((n) -> n < 100).eq(new Iter<>(Arr.mk()), eqi));

    sum[0] = 0;
    mk2().each((e) -> sum[0] += e);
    Test.eqi(sum[0], 3);
    sum[0] = 0;
    mk3().each((e) -> sum[0] += e);
    Test.eqi(sum[0], 6);
    sum[0] = 0;
    mk2().each((e, i) -> sum[0] += i);
    Test.eqi(sum[0], 1);
    sum[0] = 0;
    mk3().each((e, i) -> sum[0] += i);
    Test.eqi(sum[0], 3);

    assert(mk5().filter((n) -> n > 1).eq(new Iter<>(new Lst<>(2, 2, 3)), eqi));
    assert(mk5().filter((n) -> n > 100).eq(mk0(), eqi));

    assert(mk5().find((n) -> n == 12).isNone());
    Test.eqi(mk5().find((n) -> n > 1).getValue(), 2);

    assert(mk5().index((n) -> n == 12) == -1);
    Test.eqi(mk5().index((n) -> n > 1), 1);

    assert(mk5().map((n) -> n * 2).eq(new Iter<>(new Lst<>(2, 4, 2, 4, 6)), eqi));

    Test.eqi(mk5().reduce(0, (r, e) -> r + e), 9);

    assert(mk2().take(-1).eq(mk0(), eqi));
    assert(mk2().take(1).eq(Iter.range(1, 2), eqi));
    assert(mk2().take(100).eq(mk2(), eqi));

    assert(mk2().takeWhile((n) -> n > 100).eq(mk0(), eqi));
    assert(mk2().takeWhile((n) -> n < 2).eq(Iter.range(1, 2), eqi));
    assert(mk2().takeWhile((n) -> n < 100).eq(mk2(), eqi));

    Test.eq(Iter.range(1).toString(), "Iter[0]");
    Test.eq(mk2().toString(), "Iter[1, 2]");

    String tx0 = "";
    String tx01 = ";";
    String tx1 = "ab";
    String tx2 = "ab;";
    String tx3 = "ab;c;de";
    String tx01b = ";--";
    String tx2b = "ab;--";
    String tx3b = "ab ;--  c \t ;--  de";
    String tx3c = "ab;--c;--de";
    Iter<String> a = new Iter<>(Str.split(tx0, ";"));
    String r = Iter.join(a, ";");
    Test.eq(r, tx0);
    a = new Iter<>(Str.split(tx01, ";"));
    r = Iter.join(a, ";");
    Test.eq(r, ";");
    a = new Iter<>(Str.split(tx1, ";"));
    r = Iter.join(a, ";");
    Test.eq(r, tx1);
    a = new Iter<>(Str.split(tx2, ";"));
    r = Iter.join(a, ";");
    Test.eq(r, tx2);
    a = new Iter<>(Str.split(tx3, ";"));
    r = Iter.join(a, ";");
    Test.eq(r, tx3);
    a = new Iter<>(Str.split("ab;cd;", ";"));
    Test.eq(Iter.join(a, ";"), "ab;cd;");
    a = new Iter<>(Str.split(tx0, ";--"));
    r = Iter.join(a, ";--");
    Test.eq(r, tx0);
    a = new Iter<>(Str.split(tx01b, ";--"));
    r = Iter.join(a, ";--");
    Test.eq(r, tx01b);
    a = new Iter<>(Str.split(tx1, ";--"));
    r = Iter.join(a, ";--");
    Test.eq(r, tx1);
    a = new Iter<>(Str.split(tx2b, ";--"));
    r = Iter.join(a, ";--");
    Test.eq(r, tx2b);
    a = new Iter<>(Str.split(tx3b, ";--"));
    r = Iter.join(a, ";--");
    Test.eq(r, tx3b);
    a = new Iter<>(Str.splitTrim(tx3b, ";--"));
    r = Iter.join(a, ";--");
    Test.eq(r, tx3c);
    a = new Iter<>(Str.split("", ""));
    r = Iter.join(a, "");
    Test.eq(r, "");
    a = new Iter<>(Str.split("a", ""));
    r = Iter.join(a, "");
    Test.eq(r, "a");
    a = new Iter<>(Str.split("abñ", ""));
    r = Iter.join(a, "");
    Test.eq(r, "abñ");

    Test.eq(new Iter<>("a, b", "a\\b\"c", "c").toString(),
      "Iter[\"a, b\", \"a\\\\b\\\"c\", \"c\"]");

    System.out.println("    Finished");
  }
}
