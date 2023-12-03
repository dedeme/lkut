// Copyright 02-Dic-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package kut;

/** Thread management */
public class Thr {
  Thr () {}

  /**
   * Start 'fn' in thread apart.<p>
   * Example: <pre>
   *    Thr.run(() -> Task.process());
   * </pre>
   * @param fn Action to run.
   */
  public static void run (Runnable fn) {
    new Thread(fn).start();
  }

  /**
   * Start 'fn' in thread apart, waiting at most 'millis' milliseconds. After
   * this time the thread will be interrupted and 'onInterrupt' executed.<p>
   * Example: <pre>
   *    Thr.run(
   *      () -> Task.process(),
   *      2000,
   *      () -> System.out.println("LOG--0")
   *    );
   * </pre>
   * @param fn Action to run.
   * @param millis Milliseconds for wait.
   * @param onInterrupt Action if the thread is interrupted.
   */
  public static void run (Runnable fn, long millis, Runnable onInterrupt) {
    new Thread(() -> {
      try {
        Thread th = new Thread(() -> {
          try {
            fn.run();
          } catch (Exception e) {
            onInterrupt.run();
          }
        });
        th.start();
        th.join(millis);
        th.interrupt();
      } catch (Exception e) {
        onInterrupt.run();
      }
    }).start();
  }


  /**
   * Start 'fn' in thread apart and returns its identifier to use with 'join'.<p>
   * Example: <pre>
   *    Thread th = Thr.start(() -> Task.process());
   *    ...
   *    th.join();
   *    ...
   * </pre>
   * @param fn Action to run.
   * @return The Thread created.
   */
  public static Thread start (Runnable fn) {
    Thread th = new Thread(fn);
    th.start();
    return th;
  }

  /**
   * Start 'fn' in thread apart, waiting at most 'millis' milliseconds. After
   * this time the thread will be interrupted and 'onInterrupt' executed.<p>
   * 'start' returns the thread identifier to use with 'join'.<p>
   * Example: <pre>
   *    Thread th = Thr.start(
   *      () -> Task.process(),
   *      2000,
   *      () -> System.out.println("LOG--0")
   *    );
   *    ...
   *    th.join();
   *    ...
   * </pre>
   * @param fn Action to run.
   * @param millis Milliseconds for wait.
   * @param onInterrupt Action if the thread is interrupted.
   * @return The Thread created.
   */
  public static Thread start (Runnable fn, long millis, Runnable onInterrupt) {
    Thread th0 = new Thread(() -> {
      try {
        Thread th = new Thread(() -> {
          try {
            fn.run();
          } catch (Exception e) {
            onInterrupt.run();
          }
        });
        th.start();
        th.join(millis);
        th.interrupt();
      } catch (Exception e) {
        onInterrupt.run();
      }
    });
    th0.start();
    return th0;
  }


  // SYNCHRONIZATION -----------------------------------------------------------

  static Thread locked = null;

  synchronized static void sync2 (Runnable fn) {
    locked = Thread.currentThread();
    fn.run();
  }

  /**
   * Synchronizes 'fn'.
   * @param fn Function to synchronize.
   */
  public static void sync (Runnable fn) {
    if (Thread.currentThread() == locked) fn.run();
    else sync2 (fn);
  }

}
