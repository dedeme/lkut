// Copyright 28-Nov-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

import kut.Opt;
import kut.Rs;
import kut.Tp;
import kut.Tp3;
import kut.Wrapper;
import kut.Js;

public class ContainersTest {
  public static void run () {
    System.out.println("Containers Tests");

    Wrapper<Integer> w = new Wrapper<>(33);
    Wrapper<Integer> w2 =
      Wrapper.fromJs(Wrapper.toJs(w, (e) -> Js.w(e)), (e) -> Js.ri(e));
    Test.eqi(w.e, w2.e);

    Tp<Integer, String> tp = new Tp<>(33, "34");
    Tp<Integer, String> tp2 =
      Tp.fromJs(
        Tp.toJs(tp, (e) -> Js.w(e), (e) -> Js.w(e)),
        (e) -> Js.ri(e), (e) -> Js.rs(e)
      );
    Test.eqi(tp.e1, tp2.e1);
    Test.eq(tp.e2, tp2.e2);

    Tp3<Integer, String, Boolean> ttp = new Tp3<>(33, "34", true);
    Tp3<Integer, String, Boolean> ttp2 =
      Tp3.fromJs(
        Tp3.toJs(ttp, (e) -> Js.w(e), (e) -> Js.w(e), (e) -> Js.w(e)),
        (e) -> Js.ri(e), (e) -> Js.rs(e), (e) -> Js.rb(e)
      );
    Test.eqi(ttp.e1, ttp2.e1);
    Test.eq(ttp.e2, ttp2.e2);
    assert(ttp2.e3);

    assert(
      !Opt.some("zap").bind(
        (v) -> Opt.some("zip-" + v)
      ).isNone()
    );
    Test.eq(
      Opt.some("zap").bind(
        (v) -> Opt.some("zip-" + v)
      ).getValue(),
      "zip-zap"
    );
    assert(
      Opt.some("zap").bind(
        (v) -> Opt.none()
      ).isNone()
    );
    assert(
      Opt.none().bind(
        (v) -> Opt.some("zip-" + v)
      ).isNone()
    );
    assert(
      Opt.none().bind(
        (v) -> Opt.none()
      ).isNone()
    );

    Opt<Integer> o = Opt.some(33);
    Opt<Integer> o2 =
      Opt.fromJs(Opt.toJs(o, (e) -> Js.w(e)), (e) -> Js.ri(e));
    Test.eqi(o.getValue(), o2.getValue());

    Opt<Integer> oo = Opt.none();
    Opt<Integer> oo2 =
      Opt.fromJs(Opt.toJs(oo, (e) -> Js.w(e)), (e) -> Js.ri(e));
    assert(oo2.isNone());

    assert(
      !Rs.ok("zap").bind(
        (v) -> Rs.ok("zip-" + v)
      ).isError()
    );
    Test.eq(
      Rs.ok("zap").bind(
        (v) -> Rs.ok("zip-" + v)
      ).getValue(),
      "zip-zap"
    );
    assert(
      Rs.ok("zap").bind(
        (v) -> Rs.error("Bad function")
      ).isError()
    );
    Test.eq(
      Rs.ok("zap").bind(
        (v) -> Rs.error("Bad function")
      ).getError(),
      "Bad function"
    );
    assert(
      Rs.error("Bad argument").bind(
        (v) -> Rs.ok("zip-" + v)
      ).isError()
    );
    Test.eq(
      Rs.error("Bad argument").bind(
        (v) -> Rs.ok("zip-" + v)
      ).getError(),
      "Bad argument"
    );
    assert(
      Rs.error("Bad argument").bind(
        (v) -> Rs.error("Bad function")
      ).isError()
    );
    Test.eq(
      Rs.error("Bad argument").bind(
        (v) -> Rs.error("Bad function")
      ).getError(),
      "Bad argument"
    );

    Rs<Integer> r = Rs.ok(33);
    Rs<Integer> r2 =
      Rs.fromJs(Rs.toJs(r, (e) -> Js.w(e)), (e) -> Js.ri(e));
    Test.eqi(r.getValue(), r2.getValue());

    Rs<Integer> rr = Rs.error("bad");
    Rs<Integer> rr2 =
      Rs.fromJs(Rs.toJs(rr, (e) -> Js.w(e)), (e) -> Js.ri(e));
    Test.eq(rr2.getError(), "bad");

    System.out.println("    Finished");
  }
}

