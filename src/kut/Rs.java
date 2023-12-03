// Copyright 27-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package kut;

import java.util.function.Function;
import java.util.ArrayList;

/** Package of a result. */
public class Rs<T> {
  final String error;
  final T value;

  Rs (String error, T value) {
    this.error = error;
    this.value = value;
  }

  /**
   * Creates a Rs of type 'ok'.
   * @param <T> Value type.
   * @param value The value.
   * @return Rs
   */
  public static <T> Rs<T> ok (T value) {
    return new Rs<>(null, value);
  }

  /**
   * Creates a Rs of type 'error'.
   * @param <T> Value type.
   * @param msg The error message.
   * @return Rs
   */
  public static <T> Rs<T> error (String msg) {
    return new Rs<>(msg, null);
  }

  /**
   * Returns 'true' if 'this' is of type 'error'
   * @return 'true' if 'this' is of type 'error'
   */
  public boolean isError () {
    return error != null;
  }

  /**
   * Returns the error message.
   * @return The error message.
   * @throws IllegalStateException y 'this' is of type 'ok'.
   */
  public String getError () {
    if (error == null)
      throw new IllegalStateException("Rs is 'ok'");
    return error;
  }

  /**
   * Returns the result value.
   * @return The result value.
   * @throws IllegalStateException y 'this' is of type 'error'.
   */
  public T getValue () {
    if (error != null)
      throw new IllegalStateException("Rs is 'error'");
    return value;
  }

  /**
   * Returns the result of apply 'f' to the value of 'this'.
   * If this is of type 'error', returns also a Rs of type 'error'.
   * @param <U> Rs returned type.
   * @param f Function to apply.
   * @return Rs.
   */
  public <U> Rs<U> bind (Function<T, Rs<U>> f) {
    return this.error != null
      ? Rs.error(this.error)
      : f.apply(value)
    ;
  }

  @Override
  public String toString () {
    return error != null
      ? "Rs[Error: " + error + "]"
      : "Rs[Ok: " + value.toString() + "]"
    ;
  }

  /**
   * Returns Rs 'JSONized' in 'js'.
   * @param <T> Type of Rs element 1.
   * @param js Rs 'JSONized'.
   * @param eFromJs Function to restore the element of the Rs 'JSONized' in 'js'.
   * @return Rs 'JSONized" in 'js'.
   */
  public static <T> Rs<T> fromJs (String js, Function<String, T> eFromJs) {
    ArrayList<String> a = Js.ra(js);
    if (a.size() != 2) {
      js = js.length() <= 50 ? js : js.substring(47) + "...";
      throw new IllegalArgumentException(
      "  \n'" + js + "' is not a valid Rs JSONization." +
      "  \n Rs JSONization type are \"[\"error\",null]\" or \"[\"\",JS-element]\""
      );
    }
    return Js.rb(a.get(1))
      ? ok(eFromJs.apply(a.get(0)))
      : error(Js.rs(a.get(0)))
    ;
  }

  /**
   * Returns JSON representation of 'Rs' ("["error", null]" or "["",JS-element2]").
   * @param <T> Type of 'trs' element.
   * @param rs Rs.
   * @param eToJs Function to 'JSONize' the element of 'tp'.
   * @return JSON representation of 'rs'.
   */
  public static <T> String toJs (Rs<T> rs, Function<T, String> eToJs) {
    return rs.isError()
      ? Js.w(Arr.mk(Js.w(rs.error), Js.w(false)))
      : Js.w(Arr.mk(eToJs.apply(rs.value), Js.w(true)))
    ;
  }
}
