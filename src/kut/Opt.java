// Copyright 27-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package kut;

import java.util.function.Function;
import java.util.ArrayList;

/** Package of a value that can be null. */
public class Opt<T> {
  final T value;

  Opt (T value) {
    this.value = value;
  }

  /**
   * Creates an Opt of type 'some'.
   * @param <T> Value type.
   * @param value The value. It it is null, this functions is equals to 'none'.
   * @return Opt
   */
  public static <T> Opt<T> some (T value) {
    return new Opt<>(value);
  }

  /**
   * Creates an Opt of type 'none'.
   * @param <T> Value type.
   * @return Opt
   */
  public static <T> Opt<T> none () {
    return new Opt<>(null);
  }

  /**
   * Returns 'true' if 'this' is of type 'none'
   * @return 'true' if 'this' is of type 'none'
   */
  public boolean isNone () {
    return value == null;
  }

  /**
   * Returns the result value.
   * @return The result value.
   * @throws IllegalStateException y 'this' is of type 'none'.
   */
  public T getValue () {
    if (value == null)
      throw new IllegalStateException("Opt is 'none'");
    return value;
  }

  /**
   * Returns the result of apply 'f' to the value of 'this'.
   * If this is of type 'none', returns also an Opt of type 'none'.
   * @param <U> Opt returned type.
   * @param f Function to apply.
   * @return Opt.
   */  public <U> Opt<U> bind (Function<T, Opt<U>> f) {
    return value == null
      ? none()
      : f.apply(value)
    ;
  }

  @Override
  public String toString () {
    return value == null
      ? "Opt[None]"
      : "Opt[Some: " + value.toString() + "]"
    ;
  }

  /**
   * Returns Opt 'JSONized' in 'js'.
   * @param <T> Type of Opt element.
   * @param js Opt 'JSONized'.
   * @param eFromJs Function to restore the element of the Opt 'JSONized' in 'js'.
   * @return The Opt 'JSONized" in 'js'.
   */
  public static <T> Opt<T> fromJs (String js, Function<String, T> eFromJs) {
    ArrayList<String> a = Js.ra(js);
    if (a.size() > 1) {
      js = js.length() <= 50 ? js : js.substring(47) + "...";
      throw new IllegalArgumentException(
      "  \n'" + js + "' is not a valid Opt JSONization." +
      "  \n Opt JSONization type are: \"[]\" or \"[JS-element]\""
      );
    }
    return a.size() == 0
      ? Opt.none()
      : Opt.some(eFromJs.apply(a.get(0)))
    ;
  }

  /**
   * Returns JSON representation of 'o':<p>
   * "[]" for Opt.none() and <br>
   * "[JS-element]" for Opt.some(element).
   * @param <T> Type of 'o' element.
   * @param o Opt.
   * @param eToJs Function to 'JSONize' the element of 'o'.
   * @return JSON representation of 'o'.
   */
  public static <T> String toJs (Opt<T> o, Function<T, String> eToJs) {
    return o.isNone() ? Js.w(Arr.mk()) : Js.w(Arr.mk(eToJs.apply(o.value)));
  }
}
