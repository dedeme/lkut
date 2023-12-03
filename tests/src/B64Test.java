// Copyright 24-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

import java.util.Arrays;
import kut.B64;
import kut.Bytes;

public class B64Test {
  public static void run () {
    System.out.println("B64 Tests");

    String b64 = B64.encode("Cañónç䍆");
    assert(b64.equals("Q2HDscOzbsOn5I2G"));

    b64 = B64.encode("");
    assert(b64.equals(""));

    b64 = B64.encode("Cañónç䍆");
    String s = B64.decode(b64);
    Test.eq(s, "Cañónç䍆");

    b64 = B64.encode("");
    s = B64.decode(b64);
    assert(s.equals(""));

    for (int len = 120; len < 130; ++len) {
      byte[] bs = Bytes.mk(len);
      for (int i = 0; i < len; ++i) {
        bs[i] = (byte)(i +  10);
      }
      b64 = B64.encodeBytes(bs);
      byte[] bs2 = B64.decodeBytes(b64);
      assert(Arrays.equals(bs, bs2));
    }

    System.out.println("    Finished");
  }
}
