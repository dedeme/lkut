// Copyright 03-Dic-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

import kut.*;

public class SysTest {
  public static void run () {
    Sys.println("Sys Tests");

    Process pr = Sys.cmd(new String[]{"ls"});
    Sys.println(Sys.cmdJoin(pr, 1000).getValue());

    Sys.println("    Finished");
  }
}
