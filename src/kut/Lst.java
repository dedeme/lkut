// Copyright 29-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package kut;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.BiFunction;

/** Linked list. */
public class Lst<T> {
  final T value;
  final Lst<T> next;

  /** Creates an empty Lst */
  public Lst () {
    value = null;
    next = null;
  }

  private Lst (T e, Lst<T> nxt) {
    value = e;
    next = nxt;
  }

  /**
   * Creates a Lst with elements.
   * @param es Elements for the Lst.
   */
  @SuppressWarnings("unchecked")
  public Lst(T... es) {
    Lst<T> l = new Lst<T>();
    for (int i = es.length - 1; i >= 0; --i) l = new Lst<T>(es[i], l);
    value = l.value;
    next = l.next;
  }

  /**
   * Constructor.
   * @param a Array.
   */
  public Lst (ArrayList<T> a) {
    Lst<T> l = new Lst<>();
    for (int i = a.size() - 1; i >= 0; --i) l = new Lst<>(a.get(i), l);
    value = l.value;
    next = l.next;
  }

  /**
   * Returns new Lst adding 'e' to front of 'this'.
   * @param e Element.
   * @return New Lst adding 'e' to front of 'this'.
   */
  public Lst<T> add (T e) {
    return new Lst<>(e, this);
  }

  /**
   * Returns 'true' if every element of 'this' returns 'true' with 'f'.
   * @param f Function to test elements.
   * @return 'true' if every element of 'this' returns 'true' with 'f'.
   */
  public boolean all (Predicate<T> f) {
    for (Lst<T> l = this; l.next != null; l = l.next)
      if (!f.test(l.value)) return false;
    return true;
  }

  /**
   * Returns 'true' if at least an element of 'this' returns 'true' with 'f'.
   * @param f Function to test elements.
   * @return 'true' if at least an element of 'this' returns 'true' with 'f'.
   */
  public boolean any (Predicate<T> f) {
    for (Lst<T> l = this; l.next != null; l = l.next)
      if (f.test(l.value)) return true;
    return false;
  }

  /**
   * Returns a new Lst adding elements of 'l' after elements of 'this'.
   * @param l Another Lst.
   * @return A new Lst adding elements of 'l' after elements of 'this'.
   */
  public Lst<T> concat (Lst<T> l) {
    Lst<T> r = new Lst<>();
    for (Lst<T> ls = this; ls.next != null; ls = ls.next) r = r.add(ls.value);
    for (; l.next != null; l = l.next) r = r.add(l.value);
    return r.reverse();
  }

  /**
   * Returns a shallow copy of 'this'.
   * @return A shallow copy of 'this'.
   */
  public Lst<T> copy () {
    Lst<T> r = new Lst<>();
    for (Lst<T> l = this; l.next != null; l = l.next) r = r.add(l.value);
    return r.reverse();
  }

  /**
   * Returns a new Lst with elements resting after removing those ones returned
   *  by 'take'.
   * @param n Elements counter.
   * @return A new Lst with elements resting after removing those ones returned
   *  by 'take'.
   */
  public Lst<T> drop (int n) {
    Lst<T> r = this;
    for (int i = 0; i < n; ++i) if (r.next != null) r = r.next;
    return r;
  }

  /**
   * Returns a new Lst with elements resting after removing those ones returned
   * by 'takeWhile'.
   * @param f Function to test elements.
   * @return A new Lst with elements resting after removing those ones returned
   *    by 'takeWhile'.
   */
  public Lst<T> dropWhile (Predicate<T> f) {
    Lst<T> r = this;
    while (r.next != null) {
      if (!f.test(r.value)) break;
      r = r.next;
    }
    return r;
  }

  /**
   * Returns the number of elements.
   * @return The number of elements.
   */
  public int count () {
    int n = 0;
    for (Lst<T>l = this; l.next != null; l = l.next) ++n;
    return n;
  }

  /**
   * Executes 'f' with each element.
   * @param f Function to apply.
   */
  public void each (Consumer<T>f) {
    for (Lst<T>l = this; l.next != null; l = l.next) f.accept(l.value);
  }

  /**
   * Executes 'f' with each element and its index.
   * @param f Function to apply.
   */
  public void each (BiConsumer<T, Integer>f) {
    int n = 0;
    for (Lst<T> l = this; l.next != null; l = l.next) f.accept(l.value, n++);
  }

  /**
   * Returns 'true' if 'this' and 'l' have the same elments in the same order.
   * @param lst Lst.
   * @param f Equals function.
   * @return 'true' if 'this' and 'l' have the same elments in the same order.
   */
  public boolean eq (Lst<T> lst, BiPredicate<T, T> f) {
    for (Lst<T> l = this; l.next != null; l = l.next) {
      if (lst.next == null) return false;
      if (!f.test(lst.value, l.value)) return false;
      lst = lst.next;
    }
    if (lst.next != null) return false;
    return true;
  }

  /**
   * Returns a new Lst with the elements wich produce 'true' with 'filter'.
   * @param f Filter to apply.
   * @return A new Lst with the elements wich produce 'true' with 'filter'.
   */
  public Lst<T> filter (Predicate<T> f) {
    Lst<T> r = new Lst<>();
    for (Lst<T> l = this; l.next != null; l = l.next)
      if (f.test(l.value)) r = r.add(l.value);
    return r.reverse();
  }

  /**
   * Returns the first element of this such that 'f(e)' is 'true' or None if
   * it is not found.
   * @param f Function to test.
   * @return Option.
   */
  public Opt<T> find (Predicate<T> f) {
    for (Lst<T> l = this; l.next != null; l = l.next)
      if (f.test(l.value)) return Opt.some(l.value);
    return Opt.none();
  }

  /**
   * Returns the first element of this.
   * @return The first element of this.
   * @throws IllegalStateException if 'this' is empty.
   */
  public T head () {
    if (next == null) throw new IllegalStateException("List is empty");
    return value;
  }

  /**
   * Returns 'true' if 'this' is empty.
   * @return 'true' if 'this' is empty.
   */
  public boolean isEmpty () {
    return next == null;
  }

  /**
   * Returns the index of the first element of this such that 'f(e)' is 'true'
   * or -1 if it is not found.
   * @param f Function to test.
   * @return Option.
   */
  public int index (Predicate<T> f) {
    int ix = 0;
    for (Lst<T> l = this; l.next != null; l = l.next) {
      if (f.test(l.value)) return ix;
      ++ix;
    }

    return -1;
  }

  /**
   * Returns a new Lst after applying 'f' to each element.
   * @param <R> Type of 'f' results.
   * @param f Function to apply.
   * @return A Lst.
   */
  public <R> Lst<R> map (Function<T, R> f) {
    Lst<R> r = new Lst<>();
    for (Lst<T> l = this; l.next != null; l = l.next)
      r = r.add(f.apply(l.value));
    return r.reverse();
  }

  /**
   * Returns the result of apply 'f' for each element.
   * @param <R> Type of seed and 'f' results.
   * @param seed First value to 'f'.
   * @param f Function to apply succesively to '(seed, element)'.
   * @return The result of apply 'f' for each element.
   */
  public  <R> R reduce (R seed, BiFunction<R, T, R> f) {
    for (Lst<T> l = this; l.next != null; l = l.next)
      seed = f.apply(seed, l.value);
    return seed;
  }

  /**
   * Returns a new Lst with elements of 'this' in reverse order.
   * @return A new Lst with elements of 'this' in reverse order.
   */
  public Lst<T> reverse () {
    Lst<T> newL = new Lst<>();
    for (Lst<T>l = this; l.next != null; l = l.next) newL = newL.add(l.value);
    return newL;
  }

  /**
   * Returns a new Lst removing the first element of 'this'.
   * @return A new Lst removing the first element of 'this'.
   * @throws IllegalStateException if 'this' is empty.
   */
  public Lst<T> tail () {
    if (next == null) throw new IllegalStateException("List is empty");
    return next;
  }

  /**
   * Returns a new Lst with the first 'n' elements of 'this'.
   * @param n Elements counter.
   * @return A new Lst with the first 'n' elements of 'this'.<ul>
   *    <li>if n &lt; 0, the new Lst is empty.
   *    <li>If n >= a.size(), the new Lst is equals to 'this'.
   * </ul>
   */
  public Lst<T> take (int n) {
    Lst<T> r = new Lst<>();
    int ix = 0;
    for (Lst<T>l = this; l.next != null; l = l.next) {
      if (ix >= n || l.next == null) break;
      r = r.add(l.value);
      ++ix;
    }
    return r.reverse();
  }

  /**
   * Returns a new Lst with elements of 'this' while f returns true.
   * @param f Function to test elements.
   * @return A new Lst with elements of 'this' while f returns true.
   */
  public Lst<T> takeWhile (Predicate<T> f) {
    Lst<T> r = new Lst<>();
    for (Lst<T>l = this; l.next != null; l = l.next) {
      if (l.next == null || !f.test(l.value)) break;
      r = r.add(l.value);
    }
    return r.reverse();
  }

  /**
   * Returns this as Array.
   * @return this as Array.
   */
  public ArrayList<T> toArr () {
    ArrayList<T> r = new ArrayList<>();
    for (Lst<T>l = this; l.next != null; l = l.next) r.add(l.value);
    return r;
  }

  @Override
  public String toString () {
    return "Lst[" +
      join(map((e) ->
        e instanceof String ? Str.quote((String)e) : e.toString()
      ), ", ") +
    "]";
  }

  // STATIC ------------------------------------------------

  /**
   * Returns the Lst 'JSONized' in 'js'.
   * @param <T> Type of Lst elements.
   * @param js Lst 'JSONized'.
   * @param eFromJs Function to restore elements of the Lst 'JSONized' in 'js'.
   * @return The Lst 'JSONized" in 'js'.
   */
  public static <T> Lst<T> fromJs (String js, Function<String, T> eFromJs) {
    return new Lst<>(Arr.fromJs(js, eFromJs));
  }

  /**
   * Joins a Lst with 'sep'.
   * @param l Lst.
   * @param sep Separator.
   * @return A String.
   */
  public static String join (Lst<String> l, String sep) {
    boolean isFirst = true;
    StringBuilder sb = new StringBuilder();
    for (;;) {
      if (l.isEmpty()) break;
      if (isFirst) isFirst = false;
      else sb.append(sep);
      sb.append(l.head());
      l = l.tail();
    }
    return sb.toString();
  }

  /**
   * Returns JSON representation of 'l'.
   * @param <T> Type of 'l' elements.
   * @param l Lst.
   * @param eToJs Function to 'JSONize' elements of 'l'.
   * @return JSON representation of 'l'.
   */
  public static <T> String toJs (Lst<T> l, Function<T, String> eToJs) {
    return Arr.toJs(l.toArr(), eToJs);
  }

}
