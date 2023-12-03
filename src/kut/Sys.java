// Copyright 24-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package kut;

import java.util.ArrayList;
import java.util.Random;
import java.util.Locale;
import java.text.Collator;
import java.io.BufferedReader;
import java.io.IOException;

/** System utilities */
public class Sys {
  Sys () {}

  /** Default colator used by Str */
  protected static Collator collator = Collator.getInstance();

  public static Process cmd (String[] params) {
    try {
      return Runtime.getRuntime().exec(params);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public static Rs<String> cmdJoin (Process pr, int millis) {
    Tp<String, String> r = cmdJoin2(pr, millis);
    return r.e2.equals("")
      ? Rs.ok(r.e1)
      : Rs.error(r.e2)
    ;
  }

  // out-err
  public static Tp<String, String> cmdJoin2 (Process pr, int millis) {
    try {
      try (BufferedReader out = pr.inputReader()) {
      try (BufferedReader err = pr.errorReader()) {
        pr.waitFor(millis, java.util.concurrent.TimeUnit.MILLISECONDS);

        ArrayList<String> outS = new ArrayList<>();
        String l = out.readLine();
        while (l != null) {
          outS.add(l);
          l = out.readLine();
        }

        ArrayList<String> errS = new ArrayList<>();
        l = err.readLine();
        while (l != null) {
          errS.add(l);
          l = out.readLine();
        }

        return new Tp<>(Arr.join(outS, "\n"), Arr.join(errS, "\n"));
      }}
    } catch (InterruptedException | IOException e) {
      return new Tp<>(e.getMessage(), "");
    }
  }



  /**
   * Return the default Locale.
   * @return The default Locale.
   */
  public static Locale getLocale () {
    return Locale.getDefault();
  }

  /**
   * Display 'os'.
   * @param os Elements to display.
   */
  public static void print (Object... os) {
    for (Object o: os) System.out.print(o);
  }

  /**
   * Display 'os' and then a line feed.
   * @param os Elements to display.
   */
  public static void println (Object... os) {
    for (Object o: os) System.out.print(o);
    System.out.println();
  }

  /**
   * Sets the default Locale and update the default collator.
   * @param lc Locale description ('es_ES', 'en', etc.)
   */
  public static void setLocale (String lc) {
    Locale.setDefault(new Locale(lc));
    collator = Collator.getInstance();
  }

  /**
   * Sets the default Locale and update the default collator.
   * @param lc Locale
   */
  public static void setLocale (Locale lc) {
    Locale.setDefault(lc);
    collator = Collator.getInstance();
  }

  /**
   * Stop the current thread.
   * @param millis Milliseconds to stop.
   */
  public static void sleep (long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * Display 'os' and its position in program.
   * @param os Elements to display.
   */
  public static void trace (Object... os) {
    try {
      throw new Exception("");
    } catch (Exception e) {
      System.out.print(e.getStackTrace()[1] + ": ");
      println(os);
    }
  }
}
