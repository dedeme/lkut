// Copyright 27-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package kut;

import java.util.function.Function;
import java.util.ArrayList;

/** Wrapper of one element */
public class Wrapper<T> {
  /** Elemento. */
  public T e;

  /**
   * Constructor.
   * @param e Elemento.
   */
  public Wrapper (T e) {
    this.e = e;
  }

  @Override
  public String toString () {
    return "Wrapper(" + e.toString() + ")";
  }

  /**
   * Returns Wrapper 'JSONized' in 'js'.
   * @param <T> Type of Wrapper element.
   * @param js Wrapper 'JSONized'.
   * @param eFromJs Function to restore the element of the Wrapper 'JSONized' in 'js'.
   * @return The Wrapper 'JSONized" in 'js'.
   */
  public static <T> Wrapper<T> fromJs (String js, Function<String, T> eFromJs) {
    ArrayList<String> a = Js.ra(js);
    if (a.size() != 1) {
      js = js.length() <= 50 ? js : js.substring(47) + "...";
      throw new IllegalArgumentException(
      "  \n'" + js + "' is not a valid Wrapper JSONization." +
      "  \n Wrapper JSONization type is \"[JS-element]\""
      );
    }
    return new Wrapper<>(eFromJs.apply(a.get(0)));
  }

  /**
   * Returns JSON representation of 'w' ("[JS-element]").
   * @param <T> Type of 'w' element.
   * @param w Wrapper.
   * @param eToJs Function to 'JSONize' the element of 'w'.
   * @return JSON representation of 'w'.
   */
  public static <T> String toJs (Wrapper<T> w, Function<T, String> eToJs) {
    return Js.w(Arr.mk(eToJs.apply(w.e)));
  }

}
