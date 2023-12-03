// Copyright 28-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

import java.util.function.BiPredicate;
import java.util.ArrayList;
import kut.Arr;
import kut.Tp;
import kut.Js;

public class ArrTest {
  static BiPredicate<Integer, Integer> eqi = (n1, n2) -> n1 == n2;

  public static void run () {
    System.out.println("Arr Tests");
    ArrayList<Integer> ai = Arr.mk();
    Test.eqi(ai.size(), 0);

    ArrayList<Integer> ai2 = Arr.copy(ai);
    assert(Arr.eq(ai, ai2, eqi));

    assert(Arr.all(ai, (n) -> n > 2));
    assert(!Arr.any(ai, (n) -> n > 2));

    final int[] sum = new int[]{0};
    sum[0] = Arr.reduce(ai, 0, (s, e) -> s + e);
    Test.eqi(sum[0], 0);

    assert(Arr.reverse(ai).isEmpty());
    Arr.reverseIn(ai);

    ai = Arr.mk(1, 2);
    ai.addAll(ai2);
    assert(Arr.eq(ai, Arr.mk(1, 2), eqi));
    assert(Arr.eq(ai, Arr.concat(ai, ai2), eqi));

    assert(Arr.all(ai, (n) -> n > 0));
    assert(!Arr.all(ai, (n) -> n > 1));
    assert(!Arr.all(ai, (n) -> n > 100));
    assert(Arr.any(ai, (n) -> n > 0));
    assert(Arr.any(ai, (n) -> n > 1));
    assert(!Arr.any(ai, (n) -> n > 100));

    assert(Arr.take(ai, -1).isEmpty());
    assert(Arr.eq(Arr.take(ai, 1), Arr.mk(1), eqi));
    assert(Arr.eq(Arr.take(ai, 100), ai, eqi));

    assert(Arr.eq(Arr.drop(ai, -1), ai, eqi));
    assert(Arr.eq(Arr.drop(ai, 1), Arr.mk(2), eqi));
    assert(Arr.drop(ai, 100).isEmpty());

    assert(Arr.takeWhile(ai, (n) -> n > 100).isEmpty());
    assert(Arr.eq(Arr.takeWhile(ai, (n) -> n < 2), Arr.mk(1), eqi));
    assert(Arr.eq(Arr.takeWhile(ai, (n) -> n < 100), ai, eqi));

    assert(Arr.eq(Arr.dropWhile(ai, (n) -> n > 100), ai, eqi));
    assert(Arr.eq(Arr.dropWhile(ai, (n) -> n < 2), Arr.mk(2), eqi));
    assert(Arr.dropWhile(ai, (n) -> n < 100).isEmpty());

    ai2 = Arr.mk(1, 2, 3);
    assert (Arr.eq(Arr.concat(ai, ai2), Arr.mk(1, 2, 1, 2, 3), eqi));
    ai.addAll(ai2);
    assert(Arr.eq(ai, Arr.mk(1, 2, 1, 2, 3), eqi));

    Tp<ArrayList<Integer>, ArrayList<Integer>> tp =
      Arr.duplicates(ai, eqi);
    assert(Arr.eq(tp.e1, ai2, eqi));
    assert(Arr.eq(tp.e2, Arr.mk(1, 2), eqi));

    Arr.each(ai, (n) -> { sum[0] += n; });
    Test.eqi(sum[0], 9);
    sum[0] = 0;
    Arr.each(ai, (n, i) -> { sum[0] += i; });
    Test.eqi(sum[0], 10);
    sum[0] = Arr.reduce(ai, 0, (s, e) -> s + e);
    Test.eqi(sum[0], 9);

    ai2 = Arr.copy(ai);
    assert(Arr.eq(ai, ai2, eqi));

    Arr.eq(Arr.filter(ai, (n) -> n > 1), Arr.mk(2, 2, 3), eqi);
    Arr.eq(Arr.filter(ai, (n) -> n > 100), Arr.mk(), eqi);

    Arr.filterIn(ai2, (n) -> n > 1);
    Arr.eq(ai2, Arr.mk(2, 2, 3), eqi);
    Arr.filterIn(ai2, (n) -> n > 100);
    Arr.eq(ai2, Arr.mk(), eqi);

    assert(Arr.find(ai, (n) -> n == 12).isNone());
    Test.eqi(Arr.find(ai, (n) -> n > 1).getValue(), 2);

    Test.eqi(Arr.index(ai, (n) -> n == 12), -1);
    Test.eqi(Arr.index(ai, (n) -> n > 1), 1);

    assert(Arr.eq(Arr.map(ai2, (n) -> n * 2), ai2, eqi));
    assert(Arr.eq(Arr.map(ai, (n) -> n * 2), Arr.mk(2, 4, 2, 4, 6), eqi));

    Test.eqi(Arr.peek(ai), 3);
    Test.eqi(Arr.pop(ai), 3);
    assert(Arr.eq(ai, Arr.mk(1, 2, 1, 2), eqi));
    ai.add(3);

    ai2 = Arr.reverse(ai);
    assert(Arr.eq(ai2, Arr.mk(3, 2, 1, 2, 1), eqi));
    Arr.reverseIn(ai);
    assert(Arr.eq(ai, ai2, eqi));

    Arr.shuffle(ai);
    // System.out.println(ai);
    Arr.sort(ai, (n1, n2) -> n1 < n2);
    assert(Arr.eq(ai, Arr.mk(1, 1, 2, 2, 3), eqi));

    Test.eq(Arr.toStr(
      Arr.mk("a, b", "a\\b\"c", "c")),
      "[\"a, b\", \"a\\\\b\\\"c\", \"c\"]");

    assert(Arr.eq(
      Arr.fromJs(
        Arr.toJs(new ArrayList<Integer>(), (e) -> Js.w(e)),
        (e) -> Js.ri(e)
      ),
      new ArrayList<Integer>(), eqi
    ));
    assert(Arr.eq(
      Arr.fromJs(Arr.toJs(ai, (e) -> Js.w(e)), (e) -> Js.ri(e)),
      ai, eqi
    ));

    System.out.println("    Finished");
  }
}
