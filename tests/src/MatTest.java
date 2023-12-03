// Copyright 02-Dic-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

import kut.*;

public class MatTest {
  public static void run () {
    System.out.println("Mat Tests");

    Test.eq(Mat.itos(12), "12");
    int i = -33;
    Test.eq(Mat.itos(i), "-33");

    Test.eq(Mat.dtos(12), "12.0");
    Test.eq(Mat.dtos(12, 0), "12");
    Test.eq(Mat.dtos(12, 4), "12.0000");
    Test.eq(Mat.dtos(265.335), "265.335");
    Test.eq(Mat.dtos(265.335, 2), "265.34");
    Test.eq(Js.w(12.0), "12");
    Test.eq(Js.w(12, 0), "12");
    Test.eq(Js.w(12, 4), "12");
    Test.eq(Js.w(265.335), "265.335");
    Test.eq(Js.w(265.335, 2), "265.34");

    i = Mat.stoi("12").getValue();
    Test.eqi(i, 12);
    assert(Mat.stol("12a").isNone());
    Test.eqi(Mat.stol("12").getValue(), 12);
    assert(Mat.stof("12a").isNone());
    Test.eqi(Math.round(Mat.stof("12.28").getValue() + 1.72), 14);
    assert(Mat.stod("12a").isNone());
    Test.eqi(Math.round(Mat.stod("12.28").getValue() + 1.72), 14);

    assert (!Mat.isDigits(""));
    assert (Mat.isDigits("1"));
    assert (Mat.isDigits("019"));
    assert (!Mat.isDigits("z019"));
    assert (!Mat.isDigits("01f9"));
    assert (!Mat.isDigits("019d"));

    assert(Mat.eq(3.0, 3.0));
    assert(Mat.eq(3.0, 3.0, 0.01));
    assert(Mat.eq(3.023456, 3.023456));
    assert(Mat.eq(3.023456, 3.023456, 0.01));
    assert(!Mat.eq(3.024, 3.023456));
    assert(Mat.eq(3.024, 3.023456, 0.01));

    assert(Mat.eq(Mat.fromEn("3").getValue(), 3));
    assert(Mat.eq(Mat.fromEn("3.12").getValue(), 3.12));
    assert(Mat.eq(Mat.fromEn("-115,323.02").getValue(), -115323.02));
    assert(Mat.eq(Mat.fromIso("3").getValue(), 3));
    assert(Mat.eq(Mat.fromIso("3,12").getValue(), 3.12));
    assert(Mat.eq(Mat.fromIso("-115.323,02").getValue(), -115323.02));

    System.out.println("    Finished");
  }
}
