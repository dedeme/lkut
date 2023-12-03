// Copyright 27-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package kut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.BiFunction;

/** ArrayList utilities */
public class Arr {
  Arr () {};

  /**
   * Returns 'true' if every element of 'a' returns 'true' with 'f'.
   * @param <T> Type of 'a' elements.
   * @param a Array.
   * @param f Function to test elements of 'a'.
   * @return 'true' if every element of 'a' returns 'true' with 'f'.
   */
  public static <T> boolean all (ArrayList<T> a, Predicate<T> f) {
    for (T e : a) if (!f.test(e)) return false;
    return true;
  }

  /**
   * Returns 'true' if at least an element of 'a' returns 'true' with 'f'.
   * @param <T> Type of 'a' elements.
   * @param a Array.
   * @param f Function to test elements of 'a'.
   * @return 'true' if at least an element of 'a' returns 'true' with 'f'.
   */
  public static <T> boolean any (ArrayList<T> a, Predicate<T> f) {
    for (T e : a) if (f.test(e)) return true;
    return false;
  }

  /**
   * Returns a new array adding elements of a2 after elements of a1.
   * @param <T> Tye of array elements.
   * @param a1 One array.
   * @param a2 Another array.
   * @return A new array adding elements of a2 after elements of a1.
   */
  public static <T> ArrayList<T> concat (ArrayList<T> a1, ArrayList<T> a2) {
    ArrayList<T> r = copy(a1);
    r.addAll(a2);
    return r;
  }

  /**
   * Returns a shallow copy of 'a'.
   * @param <T> Type of 'a' elements.
   * @param a Array.
   * @return A shallow copy of 'a'.
   */
  @SuppressWarnings("unchecked")
  public static <T> ArrayList<T> copy (ArrayList<T> a) {
    return (ArrayList<T>) a.clone();
  }

  /**
   * Returns a new array with elements resting after removing those ones returned
   *    by 'take'.
   * @param <T> Type of 'a' elements.
   * @param a Array.
   * @param n Elements counter.
   * @return A new array with elements resting after removing those ones returned
   *    by 'take'.
   */
  public static <T> ArrayList<T> drop (ArrayList<T> a, int n) {
    if (n <= 0) return copy(a);
    ArrayList<T> r = new ArrayList<>();
    for (int i = n; i < a.size(); ++i) r.add(a.get(i));
    return r;
  }

  /**
   * Returns a new array with elements resting after removing those ones returned
   *    by 'takeWhile'.
   * @param <T> Type of 'a' elements.
   * @param a Array.
   * @param f Function to test elements of 'a'.
   * @return A new array with elements resting after removing those ones returned
   *    by 'takeWhile'.
   */
  public static <T> ArrayList<T> dropWhile (ArrayList<T> a, Predicate<T> f) {
    int sz = a.size();
    int n = 0;
    while (n < sz && f.test(a.get(n))) ++n;
    ArrayList<T> r = new ArrayList<>();
    for (int i = n; i < sz; ++i) r.add(a.get(i));
    return r;
  }

  /**
   * Returns two arrays:<ul>
   *   <li>The first, with elements of 'A' without duplicates.
   *   <li>The second, with duplicates of 'A'. There is only one copy of each duplicate.
   * </ul>
   * @param <T> Type of 'a' elements.
   * @param a Array to split.
   * @param f: Function 'equals to". (e.g. (e1, e2) -> e1.equals(e2);)
   * @return Tuple.
   */
  public static <T> Tp<ArrayList<T>, ArrayList<T>> duplicates (
    ArrayList<T> a, BiPredicate<T, T> f
  ) {
    ArrayList<T> newA = new ArrayList<>();
    ArrayList<T> dup = new ArrayList<>();
    for (T e : a) {
      if (any(newA, (ne) -> f.test(e, ne))) {
        if (!any(dup, (ne) -> f.test(e, ne))) dup.add(e);
      } else {
        newA.add(e);
      }
    }
    return new Tp<>(newA, dup);
  }

  /**
   * Executes 'f' with each element of 'a'
   * @param <T> Type of 'a' elements.
   * @param a Array.
   * @param f Function to apply.
   */
  public static <T> void each (ArrayList<T> a, Consumer<T>f) {
    for (T e : a) f.accept(e);
  }

  /**
   * Executes 'f' with each element of 'a' and its index.
   * @param <T> Type of 'a' elements.
   * @param a Array.
   * @param f Function to apply.
   */
  public static <T> void each (ArrayList<T> a, BiConsumer<T, Integer>f) {
    for (int i = 0; i < a.size(); ++i) f.accept(a.get(i), i);
  }

  /**
   * Returns 'true' if 'a1' and 'a2' have the same elments in the same order.
   * @param <T> Type of 'a1' and 'a2' elements.
   * @param a1 Array.
   * @param a2 Array.
   * @param f Equals function.
   * @return 'true' if 'a1' and 'a2' have the same elments in the same order.
   */
  public static <T> boolean eq (
    ArrayList<T> a1, ArrayList<T> a2, BiPredicate<T, T> f
  ) {
    int sz = a1.size();
    if (sz != a2.size()) return false;
    for (int i = 0; i < sz; ++i)
      if (!f.test(a1.get(i), a2.get(i))) return false;
    return true;
  }

  /**
   * Returns a fresh array with the elements wich produce 'true' with 'filter'.
   * @param <T> Type of 'a' elements.
   * @param a Array to filter.
   * @param f Filter to apply.
   * @return A fresh array.
   */
  public static <T> ArrayList<T> filter (ArrayList<T> a, Predicate<T> f) {
    ArrayList<T> r = new ArrayList<>();
    for (T e : a) if (f.test(e)) r.add(e);
    return r;
  }

  /**
   * Retains the elements wich produce 'true' with 'filter'.
   * @param <T> Type of 'a' elements.
   * @param a Array to filter.
   * @param filter Filter to apply.
   */
  public static <T> void filterIn (ArrayList<T> a, Predicate<T> filter) {
    a.removeIf(filter.negate());
  }

  /**
   * Returns the first element of 'a' such that 'f(e)' is 'true' or None if
   * it is not found.
   * @param <T> Type of 'a' elements.
   * @param a Array to filter.
   * @param f Function to test.
   * @return Option.
   */
  public static <T> Opt<T> find (ArrayList<T> a, Predicate<T> f) {
    for (T e : a) if (f.test(e)) return Opt.some(e);
    return Opt.none();
  }

  /**
   * Returns the Array 'JSONized' in 'js'.
   * @param <T> Type of Array elements.
   * @param js Array 'JSONized'.
   * @param eFromJs Function to restore elements of the Array 'JSONized' in 'js'.
   * @return The Array 'JSONized" in 'js'.
   */
  public static <T> ArrayList<T> fromJs (
    String js, Function<String, T> eFromJs
  ) {
    return map(Js.ra(js), eFromJs);
  }

  /**
   * Returns the index of the first element of 'a' such that 'f(e)' is 'true' or
   * -1 if it is not found.
   * @param <T> Type of 'a' elements.
   * @param a Array to filter.
   * @param f Function to test.
   * @return Index.
   */
  public static <T> int index (ArrayList<T> a, Predicate<T> f) {
    for (int i = 0; i < a.size(); ++i) if (f.test(a.get(i))) return i;
    return -1;
  }

  /**
   * Joins an array with 'sep'.
   * @param a Array.
   * @param sep Separator.
   * @return A String.
   */
  public static String join (ArrayList<String> a, String sep) {
    return String.join(sep, a);
  }

  /**
   * Creates an Array with 'es' as elements.
   * @param <T> Type of 'a' elements.
   * @param es Elements to pack.
   * @return ArrayList.
   */
  @SuppressWarnings("unchecked")
  public static <T> ArrayList<T> mk (T... es) {
    return new ArrayList<>(Arrays.asList(es));
  }

  /**
   * Returns a new array after applying 'f' to each element of 'a'.
   * @param <T> Type of 'a' elements.
   * @param <R> Type of 'f' results.
   * @param a Intial array.
   * @param f Function to apply.
   * @return A fresh array.
   */
  public static <T, R> ArrayList<R> map (ArrayList<T> a, Function<T, R> f) {
    ArrayList<R> r = new ArrayList<>();
    for (T e : a) r.add(f.apply(e));
    return r;
  }

  /**
   * Returns the last element of 'a'.
   * @param <T> Type of 'a' elements.
   * @param a Array.
   * @return The last element of 'a'.
   * @throws IllegalStateException if array is empty.
   */
  public static <T> T peek (ArrayList<T> a) {
    int ix = a.size() - 1;
    if (ix == -1) throw new IllegalStateException("Array is empty");
    return a.get(ix);
  }

  /**
   * Returns and remove the last element of 'a'.
   * @param <T> Type of 'a' elements.
   * @param a Array.
   * @return The last element of 'a'.
   * @throws IllegalStateException if array is empty.
   */
  public static <T> T pop (ArrayList<T> a) {
    int ix = a.size() - 1;
    if (ix == -1) throw new IllegalStateException("Array is empty");
    return a.remove(ix);
  }

  /**
   * Returns the result of apply 'f' for each element of 'a'.
   * @param <T> Type of 'a' elements.
   * @param <R> Type of seed and 'f' results.
   * @param a Array.
   * @param seed First value to 'f'.
   * @param f Function to apply succesively to '(seed, element)'.
   * @return The result of apply 'f' for each element of 'a'.
   */
  public static <T, R> R reduce (ArrayList<T> a, R seed, BiFunction<R, T, R> f) {
    for (T e : a) seed = f.apply(seed, e);
    return seed;
  }

  /**
   * Returns a new array with elements of 'a' reversed.
   * @param <T> Type of 'a' elements.
   * @param a Array.
   * @return A new array with elements of 'a' reversed.
   */
  public static <T> ArrayList<T> reverse (ArrayList<T> a) {
    ArrayList<T> r = new ArrayList<>();
    for (int i = a.size() - 1; i >= 0; --i) r.add(a.get(i));
    return r;
  }

  /**
   * Reverses elements of 'a'.
   * @param <T> Type of 'a' elements.
   * @param a Array.
   */
  public static <T> void reverseIn (ArrayList<T> a) {
    Collections.reverse(a);
  }

  /**
   * Resets randomly elements of 'a'.
   * @param <T> Type of 'a' elements.
   * @param a Array.
   */
  public static <T> void shuffle (ArrayList<T> a) {
    Collections.shuffle(a);
  }

  /**
   * Sort elements of 'a' in mode ascending.<p>
   * For sorting in descending mode you can use a function 'less' which
   * returns a 'greater' value. (e.g. (n1, n2) -> n1 > n2)
   * @param <T> Type of 'a' elements.
   * @param a Array.
   * @param less Function. 'less(a, b) returns 'true' if 'a &lt; b'.
   */
  public static <T> void sort (ArrayList<T> a, BiPredicate<T, T> less) {
    Collections.sort(a, (a1, a2) -> less.test(a1, a2) ? -1 : 1);
  }

  /**
   * Returns a new array with the first 'n' elements of 'a'
   * @param <T> Type of 'a' elements.
   * @param a Array.
   * @param n Elements counter.
   * @return A new array with the first 'n' elements of 'a'.<ul>
   *    <li>if n &lt; 0, the new array is empty.
   *    <li>If n >= a.size(), the new array is equals to 'a'.
   * </ul>
   */
  public static <T> ArrayList<T> take (ArrayList<T> a, int n) {
    if (n >= a.size()) return copy(a);
    ArrayList<T> r = new ArrayList<>();
    for (int i = 0; i < n; ++i) r.add(a.get(i));
    return r;
  }

  /**
   * Returns a new array with elements of 'a' while f returns true.
   * @param <T> Type of 'a' elements.
   * @param a Array.
   * @param f Function to test elements of 'a'.
   * @return A new array with elements of 'a' while f returns true.
   */
  public static <T> ArrayList<T> takeWhile (ArrayList<T> a, Predicate<T> f) {
    ArrayList<T> r = new ArrayList<>();
    for (T e : a) if (f.test(e)) r.add(e); else break;
    return r;
  }

  /**
   * Returns JSON representation of 'a'.
   * @param <T> Type of 'a' elements.
   * @param a Array.
   * @param eToJs Function to 'JSONize' elements of 'a'.
   * @return JSON representation of 'a'.
   */
  public static <T> String toJs (ArrayList<T> a, Function<T, String> eToJs) {
    return Js.w(map(a, eToJs));
  }

  /**
   * Returns a representation of 'a'.
   * @param <T> Type of 'a' elements.
   * @param a Array
   * @return A representation of 'a'.
   */
  public static <T> String toStr (ArrayList<T> a) {
    return "[" +
      Arr.join(
        Arr.map(
          a,
          (e) -> e instanceof String
            ? Str.quote((String)e)
            : e.toString()
        ), ", "
      ) +
      "]"
    ;

  }
}
