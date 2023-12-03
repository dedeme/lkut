// Copyright 24-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

import java.util.Arrays;
import java.util.ArrayList;
import kut.Bytes;

public class BytesTest {
  public static void run () {
    System.out.println("Bytes Tests");

    String s1 = "ab";
    String s2 = "c";
    String s3 = "";

    byte[] b1;
    byte[] b2;

    b1 = Bytes.mk(0);
    assert(b1.length == 0);

    b2 = Bytes.fromStr(s2);
    assert(b2.length == 1);

    b1 = Bytes.add(b1, Bytes.fromStr(s1));
    assert(b1.length == 2);
    Test.eqi(b1[0], 97);
    Test.eqi(b1[1], 98);

    b1 = Bytes.add(b1, Bytes.fromStr(s3));
    assert(b1.length == 2);
    Test.eqi(b1[0], 97);
    Test.eqi(b1[1], 98);

    b1 = Bytes.add(b1, b2);
    assert(b1.length == 3);
    Test.eqi(b1[0], 97);
    Test.eqi(b1[1], 98);
    Test.eqi(b1[2], 99);

    assert(Arrays.equals(Bytes.take(b1, 2), Bytes.fromStr(s1)));
    assert(Arrays.equals(Bytes.take(b1, -2), Bytes.mk(0)));
    assert(Arrays.equals(Bytes.take(b1, 200), b1));

    assert(Arrays.equals(Bytes.drop(b1, 2), Bytes.fromStr(s2)));
    assert(Arrays.equals(Bytes.drop(b1, -2), b1));
    assert(Arrays.equals(Bytes.drop(b1, 200), Bytes.mk(0)));

    Test.eq(Bytes.toStr(Bytes.fromStr("")), "");
    Test.eq(Bytes.toStr(Bytes.fromStr("cañón")), "cañón");

    ArrayList<Byte> a0 = Bytes.toArr(Bytes.mk(0));
    assert(a0.size() == 0);

    ArrayList<Byte> a1 = Bytes.toArr(b1);
    assert(a1.size() == 3);
    Test.eqi(a1.get(0), 97);
    Test.eqi(a1.get(1), 98);
    Test.eqi(a1.get(2), 99);

    b1 = Bytes.fromArr(a0);
    assert(b1.length == 0);

    b1 = Bytes.fromArr(a1);
    assert(b1.length == 3);
    Test.eqi(b1[0], 97);
    Test.eqi(b1[1], 98);
    Test.eqi(b1[2], 99);

    System.out.println("    Finished");
  }
}
