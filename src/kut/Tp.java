// Copyright 27-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package kut;

import java.util.function.Function;
import java.util.ArrayList;

/** Tupla de dos elementos */
public class Tp<T, U> {
  /** Elemento 1 */
  public final T e1;
  /** Elemento 2 */
  public final U e2;

  /**
   * Constructor.
   * @param e1 Elemento 1.
   * @param e2 Elemento 2.
   */
  public Tp (T e1, U e2) {
    this.e1 = e1;
    this.e2 = e2;
  }

  @Override
  public String toString () {
    return "Tp(" + e1.toString() + ", " + e2.toString() + ")";
  }

  /**
   * Returns Tp 'JSONized' in 'js'.
   * @param <T> Type of Tp element 1.
   * @param <U> Type of Tp element 2.
   * @param js Tp 'JSONized'.
   * @param eFromJs1 Function to restore the element 1 of the Tp 'JSONized' in 'js'.
   * @param eFromJs2 Function to restore the element 2 of the Tp 'JSONized' in 'js'.
   * @return Tp 'JSONized" in 'js'.
   */
  public static <T, U> Tp<T, U> fromJs (
    String js, Function<String, T> eFromJs1, Function<String, U> eFromJs2
  ) {
    ArrayList<String> a = Js.ra(js);
    if (a.size() != 2) {
      js = js.length() <= 50 ? js : js.substring(47) + "...";
      throw new IllegalArgumentException(
      "  \n'" + js + "' is not a valid Tp JSONization." +
      "  \n Tp JSONization type is \"[JS-element1,JS-element2]\""
      );
    }
    return new Tp<>(eFromJs1.apply(a.get(0)), eFromJs2.apply(a.get(1)));
  }

  /**
   * Returns JSON representation of 'tp' ("[JS-element1, JS-element2]").
   * @param <T> Type of 'tp' element1.
   * @param <U> Type of 'tp' elemen2.
   * @param tp Tp.
   * @param eToJs1 Function to 'JSONize' the element 1 of 'tp'.
   * @param eToJs2 Function to 'JSONize' the element 2 of 'tp'.
   * @return JSON representation of 'tp'.
   */
  public static <T, U> String toJs (
    Tp<T, U> tp, Function<T, String> eToJs1, Function<U, String> eToJs2
  ) {
    return Js.w(Arr.mk(eToJs1.apply(tp.e1), eToJs2.apply(tp.e2)));
  }

}
