// Copyright 28-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

import java.util.function.BiPredicate;
import java.util.ArrayList;
import kut.Dic;
import kut.Arr;
import kut.Tp;
import kut.Str;
import kut.Js;

public class DicTest {
  static BiPredicate<String, String> eqi = (n1, n2) -> n1.equals(n2);

  public static void run () {
    System.out.println("Dic Tests");

    Dic<String> m = new Dic<>();
    Test.eqi(m.size(), 0);
    assert(m.eq(Dic.mk(), eqi));
    assert(m.eq(new Dic<>(m.toArr()), eqi));
    assert(m.eq(new Dic<>(m.toIter()), eqi));
    assert(m.eq(new Dic<>(m.toLst()), eqi));
    assert(m.eq(m.copy(), eqi));
    assert(m.isEmpty());
    Test.eq(m.toString(), "{}");

    m.put("uno", "2");
    Test.eqi(m.size(), 1);
    Test.eq(m.getOpt("uno").getValue(), "2");
    m.put("dos", "2");
    Test.eq(m.get("dos"), "2");
    m.set("uno", "1");
    Test.eq(m.get("uno"), "1");
    Test.eqi(m.size(), 2);
    assert(m.getOpt("tres").isNone());
    m.put("tres", "3");
    assert(m.eq(Dic.mk("uno", "1", "dos", "2", "tres", "3"), eqi));
    assert(m.eq(new Dic<>(m.toArr()), eqi));
    assert(m.eq(new Dic<>(m.toIter()), eqi));
    assert(m.eq(new Dic<>(m.toLst()), eqi));
    assert(m.eq(m.copy(), eqi));
    assert(!m.isEmpty());
    //System.out.println(m);

    m.remove("dos");
    Test.eq(m.get("uno"), "1");
    Test.eq(m.get("tres"), "3");
    assert(m.getOpt("dos").isNone());
    Test.eqi(m.size(), 2);

    m.remove("cero");
    Test.eq(m.get("uno"), "1");
    Test.eq(m.get("tres"), "3");
    assert(m.getOpt("dos").isNone());
    Test.eqi(m.size(), 2);

    ArrayList<String> keys = m.keys();
    Arr.sort(keys, (k1, k2) -> k1.compareTo(k2) > 0);
    Test.eq(Arr.join(keys, "-"), "uno-tres");
    Arr.sort(keys, (k1, k2) -> k1.compareTo(k2) < 0);
    Test.eq(Arr.join(keys, "-"), "tres-uno");

    ArrayList<String> values = m.values();
    Arr.sort(values, (v1, v2) -> v1.compareTo(v2) < 0);
    Test.eq(Arr.join(values, "-"), "1-3");

    ArrayList<Tp<String, String>> am = m.toArr();
    m = new Dic<>(am);
    keys = m.keys();
    Arr.sort(keys, (k1, k2) -> k1.compareTo(k2) < 0);
    Test.eq(Arr.join(keys, "-"), "tres-uno");

    m.put("él", "2");

    keys = m.keys();
    Arr.sort(keys, (k1, k2) -> k1.compareTo(k2) < 0);
    Test.eq(Arr.join(keys, "-"), "tres-uno-él");
    Arr.sort(keys, (k1, k2) -> Str.cmp(k1, k2) < 0);
    Test.eq(Arr.join(keys, "-"), "él-tres-uno");

    assert(
      Dic.fromJs(
        Dic.toJs(new Dic<String>(), (e) -> Js.w(e)),
        (e) -> Js.rs(e)
      ).eq(
        new Dic<String>(), eqi
      )
    );
    assert(
      Dic.fromJs(
        Dic.toJs(m, (e) -> Js.w(e)),
        (e) -> Js.rs(e)
      ).eq(
        m, eqi
      )
    );

    System.out.println("    Finished");
  }
}
