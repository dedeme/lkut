// Copyright 28-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package kut;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.function.Predicate;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.BiFunction;

/** Iterator */
public class Iter<T> {
  final Supplier<Opt<T>> fnext;

  /**
   * Constructor.
   * @param next Function that returns the next element or 'Opt.none' if there
   * are no more elements.
   */
  public Iter (Supplier<Opt<T>> next) {
    fnext = next;
  }

  /**
   * Constructor.
   * @param es Elements.
   */
  @SuppressWarnings("unchecked")
  public Iter (T... es) {
    ArrayList<T> a = Arr.mk(es);
    fnext = () ->
      a.isEmpty()
        ? Opt.none()
        : Opt.some(a.remove(0))
    ;
  }

  /**
   * Constructor.
   * @param a Array.
   */
  public Iter (ArrayList<T> a) {
    ArrayList<T> a2 = Arr.copy(a);
    fnext = () ->
      a2.isEmpty()
        ? Opt.none()
        : Opt.some(a2.remove(0))
    ;
  }

  /**
   * Constructor.
   * @param l Lst.
   */
  public Iter (Lst<T> l) {
    Wrapper<Lst<T>> lw = new Wrapper<>(l);
    fnext = () -> {
      Lst<T> l2 = lw.e;
      if (l2.isEmpty()) return Opt.none();
      lw.e = l2.tail();
      return Opt.some(l2.head());
    };
  }



  /**
   * Returns 'true' if every element of 'this' returns 'true' with 'f'.
   * @param f Function to test elements.
   * @return 'true' if every element of 'this' returns 'true' with 'f'.
   */
  public boolean all (Predicate<T> f) {
    Opt<T> e;
    for (;;) {
      e = fnext.get();
      if (e.isNone()) return true;
      if (!f.test(e.getValue())) return false;
    }
  }

  /**
   * Returns 'true' if at least an element of 'this' returns 'true' with 'f'.
   * @param f Function to test elements.
   * @return 'true' if at least an element of 'this' returns 'true' with 'f'.
   */
  public boolean any (Predicate<T> f) {
    Opt<T> e;
    for (;;) {
      e = fnext.get();
      if (e.isNone()) return false;
      if (f.test(e.getValue())) return true;
    }
  }

  /**
   * Returns a new Iter adding elements of it after elements of this.
   * @param it Another Iter.
   * @return A new Iter adding elements of it after elements of this.
   */
  public Iter<T> concat (Iter<T> it) {
    return new Iter<>(() -> {
      Opt<T> e = fnext.get();
      if (e.isNone()) return it.fnext.get();
      return e;
    });
  }

  /**
   * Returns 'this' with elements resting after removing those ones returned
   *    by 'take'.
   * @param n Elements counter.
   * @return 'this' with elements resting after removing those ones returned
   *    by 'take'.
   */
  public Iter<T> drop (int n) {
    while (n > 0) {
      if (fnext.get().isNone()) break;
      --n;
    }
    return this;
  }

  /**
   * Returns a new Iter with elements resting after removing those ones returned
   *    by 'takeWhile'.
   * @param f Function to test elements.
   * @return A new Iter with elements resting after removing those ones returned
   *    by 'takeWhile'.
   */
  public Iter<T> dropWhile (Predicate<T> f) {
    Opt<T> e;
    for (;;) {
      e = fnext.get();
      if (e.isNone()) return this;
      if (!f.test(e.getValue())) break;
    }
    return unary(e.getValue()).concat(this);
  }

  /**
   * Executes 'f' with each element.
   * @param f Function to apply.
   */
  public void each (Consumer<T> f) {
    Opt<T> e;
    for (;;) {
      e = fnext.get();
      if (e.isNone()) break;
      f.accept(e.getValue());
    }
  }

  /**
   * Executes 'f' with each element and its index.
   * @param f Function to apply.
   */
  public void each (BiConsumer<T, Integer> f) {
    int n = 0;
    Opt<T> e;
    for (;;) {
      e = fnext.get();
      if (e.isNone()) break;
      f.accept(e.getValue(), n);
      ++n;
    }
  }

  /**
   * Returns the number of elements.
   * @return Number of elements.
   */
  public int count () {
    int n = 0;
    while (!fnext.get().isNone()) ++n;
    return n;
  }

  /**
   * Returns 'true' if 'this' and 'it' have the same elments in the same order.
   * @param it Another Iter.
   * @param f Equals function.
   * @return 'true' if 'this' and 'it' have the same elments in the same order.
   */
  public boolean eq (Iter<T> it, BiPredicate<T, T> f) {
    Opt<T> e;
    Opt<T> e2;
    for (;;) {
      e = fnext.get();
      e2 = it.fnext.get();
      if (e.isNone()){
         if (e2.isNone()) return true;
         else return false;
      }
      if (e2.isNone()) return false;
      if (!f.test(e.getValue(), e2.getValue())) return false;
    }
  }

  /**
   * Returns an Iter with the elements wich produce 'true' with 'filter'.
   * @param f Filter to apply.
   * @return Iter with the elements wich produce 'true' with 'filter'.
   */
  public Iter<T> filter (Predicate<T> f) {
    return new Iter<>(() -> {
      Opt<T> e;
      for (;;) {
        e = fnext.get();
        if (e.isNone() || f.test(e.getValue())) return e;
      }
    });
  }

  /**
   * Returns the first element of 'this' such that 'f(e)' is 'true' or None if
   * it is not found.
   * @param f Function to test.
   * @return Option.
   */
  public Opt<T> find (Predicate<T> f) {
    Opt<T> e;
    for (;;) {
      e = fnext.get();
      if (e.isNone() || f.test(e.getValue())) return e;
    }
  }

  /**
   * Returns the index ot the first element of 'this' such that 'f(e)' is 'true'
   * or -1 if it is not found.
   * @param f Function to test.
   * @return Option.
   */
  public int index (Predicate<T> f) {
    int n = 0;
    Opt<T> e;
    for (;;) {
      e = fnext.get();
      if (e.isNone()) return -1;
      if (f.test(e.getValue())) return n;
      ++n;
    }
  }

  /**
   * Returns a new Iter after applying 'f' to each element of 'this'.
   * @param <U> Type of 'f' results.
   * @param f Function to apply.
   * @return Iter.
   */
  public <U> Iter<U> map (Function<T, U> f) {
    return new Iter<>(() -> fnext.get().bind((e) -> Opt.some(f.apply(e))));
  }

  /**
   * Returns the next element or 'Opt.none' if there are no more elements.
   * @return The next element or 'Opt.none' if there are no more elements.
   */
  public Opt<T> next () {
    return fnext.get();
  }

  /**
   * Returns the result of apply 'f' for each element of 'this'.
   * @param <R> Type of seed and 'f' results.
   * @param seed First value to 'f'.
   * @param f Function to apply succesively to '(seed, element)'.
   * @return The result of apply 'f' for each element of 'this'.
   */
  public <R> R reduce (R seed, BiFunction<R, T, R> f) {
    Opt<T> e;
    for (;;) {
      e = fnext.get();
      if (e.isNone()) break;
      seed = f.apply(seed, e.getValue());
    }
    return seed;
  }

  /**
   * returns a new Iter with the first 'n' elements of 'this'.
   * @param n Elements counter.
   * @return A new Iter with the first 'n' elements of 'this'.<ul>
   *    <li>if n &lt; 0, the new Iter is empty.
   *    <li>If n >= a.size(), the new Iter is equals to 'this'.
   * </ul>
   */
  public Iter<T> take (int n) {
    int[] ix = new int[]{0};
    return new Iter<>(() -> {
      Opt<T> e = fnext.get();
      if (e.isNone() || ix[0]++ >= n) return Opt.none();
      return e;
    });
  }

  /**
   * Returns a new Iter with elements of 'this' while f returns true.
   * @param f Function to test elements of 'a'.
   * @return A new Iter with elements of 'this' while f returns true.
   */
  public Iter<T> takeWhile (Predicate<T> f) {
    boolean[] fail = new boolean[]{false};
    return new Iter<>(() -> {
      if (fail[0]) return Opt.none();

      Opt<T> e = fnext.get();
      if (e.isNone() || !f.test(e.getValue())) {
        fail[0] = true;
        return Opt.none();
      }
      return e;
    });
  }

  /**
   * Feturns an Array with elements of 'this'.
   * @return Array with elements of 'this'.
   */
  public ArrayList<T> toArr () {
    ArrayList<T> r = new ArrayList<>();
    for (;;) {
      Opt<T> e = fnext.get();
      if (e.isNone()) break;
      r.add(e.getValue());
    }
    return r;
  }

  /**
   * Feturns a Lst with elements of 'this'.
   * @return Lst with elements of 'this'.
   */
  public Lst<T> toLst () {
    Lst<T> l = new Lst<>();
    for (;;) {
      Opt<T> e = fnext.get();
      if (e.isNone()) break;
      l = l.add(e.getValue());
    }
    return l.reverse();
  }

  @Override
  public String toString() {
    return "Iter[" +
      join(map((e) ->
        e instanceof String ? Str.quote((String)e) : e.toString()
      ), ", ") +
    "]";
  }

  // STATIC ------------------------------------------------

  /**
   * Returns an Iter from 'begin' to 'end'.
   * @param begin Begin index inclusive.
   * @param end End index exclusive.
   * @return Iter from 'begin' to 'end'.
   */
  public static Iter<Integer> range (int begin, int end) {
    int[] n = new int[]{begin};
    return new Iter<>(() ->
      n[0] >= end
        ? Opt.none()
        : Opt.some(n[0]++)
    );
  }

  /**
   * Returns an Iter from 0 to 'end'.
   * @param end End index exclusive.
   * @return Iter from 0 to 'end'.
   */
  public static Iter<Integer> range (int end) {
    return range(0, end);
  }

  /**
   * Returns an Iter with only one element.
   * @param <T> Iter type.
   * @param e Element.
   * @return Iter with only one element.
   */
  public static <T> Iter<T> unary (T e) {
    int[] n = new int[]{1};
    return new Iter<>(() ->
      n[0]-- > 0
        ? Opt.some(e)
        : Opt.none()
    );
  }

  /**
   * Joins elements of 'it' with 'sep'.
   * @param it Iter to join.
   * @param sep Separator.
   * @return A String.
   */
  public static String join (Iter<String> it, String sep) {
    boolean isFirst = true;
    StringBuilder sb = new StringBuilder();
    Opt<String> e;
    for (;;) {
      e = it.fnext.get();
      if (e.isNone()) break;
      if (isFirst) isFirst = false;
      else sb.append(sep);
      sb.append(e.getValue());
    }
    return sb.toString();
  }
}
