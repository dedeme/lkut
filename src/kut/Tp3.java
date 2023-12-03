// Copyright 27-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package kut;

import java.util.function.Function;
import java.util.ArrayList;

/** Tupla de tres elementos */
public class Tp3<T, U, V> {
  /** Elemento 1 */
  public final T e1;
  /** Elemento 2 */
  public final U e2;
  /** Elemento 3 */
  public final V e3;

  /**
   * Constructor.
   * @param e1 Elemento 1.
   * @param e2 Elemento 2.
   * @param e3 Elemento 3.
   */
  public Tp3 (T e1, U e2, V e3) {
    this.e1 = e1;
    this.e2 = e2;
    this.e3 = e3;
  }

  @Override
  public String toString () {
    return "Tp(" + e1.toString() + ", " + e2.toString() +
      ", " + e3.toString() + ")";
  }

  /**
   * Returns Tp3 'JSONized' in 'js'.
   * @param <T> Type of Tp3 element 1.
   * @param <U> Type of Tp3 element 2.
   * @param <V> Type of Tp3 element 3.
   * @param js Tp3 'JSONized'.
   * @param eFromJs1 Function to restore the element 1 of the Tp3 'JSONized' in 'js'.
   * @param eFromJs2 Function to restore the element 2 of the Tp3 'JSONized' in 'js'.
   * @param eFromJs3 Function to restore the element 3 of the Tp3 'JSONized' in 'js'.
   * @return Tp3 'JSONized" in 'js'.
   */
  public static <T, U, V> Tp3<T, U, V> fromJs (
    String js,
    Function<String, T> eFromJs1,
    Function<String, U> eFromJs2,
    Function<String, V> eFromJs3
  ) {
    ArrayList<String> a = Js.ra(js);
    if (a.size() != 3) {
      js = js.length() <= 50 ? js : js.substring(47) + "...";
      throw new IllegalArgumentException(
      "  \n'" + js + "' is not a valid Tp JSONization." +
      "  \n Tp JSONization type is \"[JS-element1,JS-element2,JS-element3]\""
      );
    }
    return new Tp3<>(
      eFromJs1.apply(a.get(0)),
      eFromJs2.apply(a.get(1)),
      eFromJs3.apply(a.get(2))
    );
  }

  /**
   * Returns JSON representation of 'tp' ("[JS-element2,JS-element2,JS-element3]").
   * @param <T> Type of 'tp' element1.
   * @param <U> Type of 'tp' elemen2.
   * @param <V> Type of 'tp' elemen3.
   * @param tp Tp3.
   * @param eToJs1 Function to 'JSONize' the element 1 of 'tp'.
   * @param eToJs2 Function to 'JSONize' the element 2 of 'tp'.
   * @param eToJs3 Function to 'JSONize' the element 2 of 'tp'.
   * @return JSON representation of 'tp'.
   */
  public static <T, U, V> String toJs (
    Tp3<T, U, V> tp,
    Function<T, String> eToJs1,
    Function<U, String> eToJs2,
    Function<V, String> eToJs3
  ) {
    return Js.w(Arr.mk(
      eToJs1.apply(tp.e1),
      eToJs2.apply(tp.e2),
      eToJs3.apply(tp.e3)
    ));
  }

}
