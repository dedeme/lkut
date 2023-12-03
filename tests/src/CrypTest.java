// Copyright 24-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

import kut.Cryp;
import kut.B64;

public class CrypTest {
  public static void run () {
    System.out.println("Cryp Tests");

    String b64 = Cryp.genk(6);
    Test.eqi(b64.length(), 6);

    b64 = Cryp.key("deme", 6);
    Test.eq(b64, "wiWTB9");
    b64 = Cryp.key("Generaro", 5);
    Test.eq(b64, "Ixy8I");
    b64 = Cryp.key("Generara", 5);
    Test.eq(b64, "0DIih");

    b64 = Cryp.encode("abc", "01");
    assert(!b64.equals("01"));
    b64 = Cryp.decode("abc", b64);
    Test.eq(b64, "01");
    b64 = Cryp.encode("abcd", "11");
    assert(!b64.equals("11"));
    b64 = Cryp.decode("abcd", b64);
    Test.eq(b64, "11");
    b64 = Cryp.encode("abc", "");
    Test.eq(b64, "");
    b64 = Cryp.decode("abc", b64);
    Test.eq(b64, "");
    b64 = Cryp.encode("c", "a");
    assert(!b64.equals("a"));
    b64 = Cryp.decode("c", b64);
    Test.eq(b64, "a");
    b64 = Cryp.encode("xxx", "ab c");
    assert(!b64.equals("ab c"));
    b64 = Cryp.decode("xxx", b64);
    Test.eq(b64, "ab c");
    b64 = Cryp.encode("abc", "\n\ta€b c");
    assert(!b64.equals("\n\ta€b c"));
    b64 = Cryp.decode("abc", b64);
    Test.eq(b64, "\n\ta€b c");

    System.out.println("    Finished");
  }
}
