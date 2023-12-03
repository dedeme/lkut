// Copyright 30-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package kut;

import java.util.Map;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.function.BiPredicate;
import java.util.function.Function;
import kut.Tp;
import kut.Lst;
import kut.Iter;

/** Hashtable&lt;String, T> */
public class Dic<T> {
  /** Wrapped hashtable. */
  public final Hashtable<String, T> h;

  /** Creates an empty Dic. */
  public Dic () {
    h = new Hashtable<>();
  }

  Dic (Hashtable<String, T> ht) {
    h = ht;
  }

  /**
   * Constructor
   * @param a Array.
   */
  public Dic (ArrayList<Tp<String, T>> a) {
    h = new Hashtable<>();
    for (Tp<String, T> tp : a) h.put(tp.e1, tp.e2);
  }

  /**
   * Constructor
   * @param l Lst.
   */
  public Dic (Lst<Tp<String, T>> l) {
    h = new Hashtable<>();
    l.each((tp) -> h.put(tp.e1, tp.e2));
  }

  /**
   * Constructor
   * @param it Iter.
   */
  public Dic (Iter<Tp<String, T>> it) {
    h = new Hashtable<>();
    it.each((tp) -> h.put(tp.e1, tp.e2));
  }

  /**
   * Creates a shallow copy.
   * @return A shallow copy.
   */
  @SuppressWarnings("unchecked")
  public Dic<T> copy () {
    return new Dic<>((Hashtable<String, T>)h.clone());
  }

  /**
   * Returns 'true' if 'd' is equals to 'this' with 'f' (Same keys-values,
   * but not same order).
   * @param d Dic to compare.
   * @param f Comparison function.
   * @return 'true' if 'd' is equals to 'this' with 'f'
   */
  public boolean eq (Dic<T> d, BiPredicate<T, T> f) {
    if (d.size() != size()) return false;
    return Arr.all(
      keys(),
      (k) -> d.hasKey(k) && f.test(get(k), d.get(k))
    );
  }

  /**
   * Returns the element of key 'k' or throw exception if 'k' is not found.
   * @param k Key to search.
   * @return Value.
   * @throws IllegalArgumentException if 'k' is not found.
   */
  public T get (String k) {
    T v = h.get(k);
    if (v == null)
      throw new IllegalArgumentException ("Key '" + k + "' not found");
    return v;
  }

  /**
   * Returns the element of key 'k' or Opt.none if 'k' is not found.
   * @param k Key to search.
   * @return Option of value.
   */
  public Opt<T> getOpt (String k) {
    T v = h.get(k);
    return v == null ? Opt.none() : Opt.some(v);
  }

  /**
   * Returns 'true' if this has the key 'k'.
   * @param k Key to find.
   * @return 'true' if this has the key 'k'.
   */
  public boolean hasKey (String k) {
    return h.containsKey(k);
  }

  /**
   * Returns 'true' if 'this' is empty.
   * @return 'true' if 'this' is empty.
   */
  public boolean isEmpty () {
    return h.isEmpty();
  }

  /**
   * Returns keys.
   * @return Keys.
   */
  public ArrayList<String> keys () {
    return new ArrayList<>(h.keySet());
  }

  /**
   * Adds or replaces the value of 'k'.
   * @param k Key.
   * @param value Value.
   */
  public void put (String k, T value) {
    h.put(k, value);
  }

  /**
   * Removes 'k' or do nothing if 'k' is not found.
   * @param k Key.
   */
  public void remove (String k) {
    h.remove(k);
  }

  /**
   * Replaces the value of 'k'.
   * @param k Key.
   * @param value Value.
   * @throws IllegalStateException if 'k' is not found.
   */
  public void set (String k, T value) {
    if (hasKey(k)) h.put(k, value);
    else throw new IllegalStateException("Key '" + k + "' not found.");
  }

  /**
   * Returns 'this' size.
   * @return 'this' size.
   */
  public int size () {
    return h.size();
  }

  /**
   * Returns an Array with pairs 'key-value'.
   * @return An Array with pairs 'key-value'.
   */
  public ArrayList<Tp<String, T>> toArr () {
    ArrayList<Tp<String, T>> r = new ArrayList<>();
    for (Map.Entry<String, T> e : h.entrySet())
      r.add(new Tp<>(e.getKey(), e.getValue()));
    return r;
  }

  /**
   * Returns an Iter with pairs 'key-value'.
   * @return An Iter with pairs 'key-value'.
   */
  public Iter<Tp<String, T>> toIter () {
    return new Iter<>(toArr());
  }

  /**
   * Returns a Lst with pairs 'key-value'.
   * @return A Lst with pairs 'key-value'.
   */
  public Lst<Tp<String, T>> toLst () {
    Lst<Tp<String, T>> r = new Lst<>();
    for (Map.Entry<String, T> e : h.entrySet())
      r = r.add(new Tp<>(e.getKey(), e.getValue()));
    return r.reverse();
  }

  @Override
  public String toString () {
    return "{" +
      Arr.join(Arr.map(
        toArr(),
        (tp) -> Str.quote(tp.e1) + ":" + tp.e2
      ), ", ") +
      "}"
    ;
  }

  /**
   * Returns values of 'this'.
   * @return Values of 'this'.
   */
  public ArrayList<T> values () {
    return new ArrayList<>(h.values());
  }

  // STATIC ------------------------------------------------

  /**
   * Returns the Dic 'JSONized' in 'js'.
   * @param <T> Type of Dic elements.
   * @param js Dic 'JSONized'.
   * @param eFromJs Function to restore elements of the Dic 'JSONized' in 'js'.
   * @return Dic 'JSONized".
   */
  public static <T> Dic<T> fromJs (
    String js, Function<String, T> eFromJs
  ) {
    Dic<T> r = new Dic<>();
    Js.ro(js).h.forEach((k, v) -> r.put(k, eFromJs.apply(v)));
    return r;
  }

  /**
   * Creates a literal Dic.<p>
   * Example:<pre>
   *    Dic d = Dic.mk("one", "1", "two", "2")
   * </pre>
   * @param pairs Pairs of key value.
   * @return A literal Dic.
   */
  public static Dic<String> mk (String... pairs) {
    int sz = pairs.length;
    if (sz % 2 != 0)
      throw new IllegalArgumentException("Pairs 'key-value' are odd");
    Dic<String> d = new Dic<String>();
    for (int i = 0; i < sz; i += 2) d.put(pairs[i], pairs[i + 1]);
    return d;
  }

  /**
   * Returns JSON representation of 'd'.
   * @param <T> Type of 'd' elements.
   * @param d Dictionary.
   * @param eToJs Function to 'JSONize' elements of 'd'.
   * @return JSON representation of 'd'.
   */
  public static <T> String toJs (Dic<T> d, Function<T, String> eToJs) {
    Dic<String> tmp = new Dic<>();
    d.h.forEach((k, v) -> tmp.put(k, eToJs.apply(v)));
    return Js.w(tmp);
  }
}

