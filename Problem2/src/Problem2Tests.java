import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;

/**
 * A variety of tests for problem 2 on the exam.
 */
public class Problem2Tests
{
  // +-----------+-------------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The numbers from 1 to 10, used for a variety of tests.
   */
  public static final Integer[] vals = 
      new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

  // +------------+------------------------------------------------------
  // | Predicates |
  // +------------+

  /**
   * Check for even numbers.
   */
  Predicate<Integer> even = new Predicate<Integer>()
      {
        public boolean test(Integer i)
        {
          return (i % 2) == 0;
        } // test(Integer)
      }; // new Predicate<Integer>()

  /**
   * Check for "large" numbers
   */
  Predicate<Integer> atLeastFive = new Predicate<Integer>()
      {
        public boolean test(Integer i)
        {
          return (i >= 5);
        } // test(Integer)
      }; // new Predicate<Integer>()

  // +-----------+-------------------------------------------------------
  // | Utilities |
  // +-----------+

  /**
   * Convert an iterator to an array.
   */
  @SuppressWarnings({"unchecked"})
  public static <T> T[] toArray(Iterator<T> it)
  {
    ArrayList<T> array = new ArrayList<T>();
    while (it.hasNext())
      {
        array.add(it.next());
      } // while
    return (T[]) array.toArray();
  } // toArray(Iterator<T>)

  /**
   * Create an iterator for an array.
   */
  public static <T> Iterator<T> arrayIterator(T[] array)
  {
    return Arrays.asList(array).iterator();
  } // arrayIterator(T[])

  /**
   * Test whether a particular filter succeeds.
   */
  public static <T> void test(String name, T[] original, Predicate<T> pred,
      T[] expected)
  {
    T[] result = 
        toArray(new FilteredIterator<T>(arrayIterator(original), pred));
    if (!Arrays.equals(result, expected))
      {
        System.err.println("Failed filter: " + name);
        System.err.println("Input:    " + Arrays.toString(original));
        System.err.println("Filtered: " + Arrays.toString(result));
        System.err.println("Expected: " + Arrays.toString(expected));
        fail(name);
      } // if (!Arrays.equals(result, expected))
  } // test(Iterator<T>, Predicate<T>, T[])

  // +-------+-----------------------------------------------------------
  // | Tests |
  // +-------+

  /**
   * Test that when we accept everything in the empty list, we get the
   * empty list.
   */
  @Test
  public void testEmpty()
  {
    // Some simple filters.
    test("accept/empty", new Integer[] { }, Pred.ACCEPT, new Integer[] { });
  } // testEmpty()

  /**
   * A few more tests of accepting everything.
   */
  @Test
  public void testAccept()
  {
    test("accept/singleton", new Integer[] { 1 }, Pred.ACCEPT, 
        new Integer[] { 1 });
    test("accept/pair", new Integer[] { 1, 2 }, Pred.ACCEPT, 
        new Integer[] { 1, 2 });
  } // testAccept

  /**
   * A test of rejection.
   */
  @Test
  public void testReject()
  {
    test("reject/misc", new Integer[] { 1, 2, 3, 4, 5 }, Pred.REJECT,
        new Integer[] { });
  } // testReject

  /**
   * Some tests looking for even integers.
   */
  @Test
  public void testEven()
  {
    test("even/1", vals, even, new Integer[] { 2, 4, 6, 8, 10 });
    test("even/2", new Integer[] { 1, 3, 5, 7, 9 }, even, new Integer[] { });
  } // testEven()

  /**
   * Some tests of negation.
  @Test
  public void testNot()
  {
    test("not/1", vals, Pred.not(even), new Integer[] { 1, 3, 5, 7, 9 });
    test("not/2", vals, Pred.not(Pred.ACCEPT), new Integer[] { });
    test("not/3", vals, Pred.not(Pred.REJECT), vals);
    test("not/4", vals, Pred.not(atLeastFive), new Integer[] { 1, 2, 3, 4 });
  } // testNot()

  /**
   * Some tests using or.
   */
  @Test
  public void testOr()
  {
    test("or/1", vals, Pred.or(Pred.ACCEPT,Pred.REJECT), vals);
    test("or/2", vals, Pred.or(even,atLeastFive), 
        new Integer[] { 2, 4, 5, 6, 7, 8, 9, 10 });
    test("or/3", vals, Pred.or(even,Pred.not(atLeastFive)),
        new Integer[] { 1, 2, 3, 4, 6, 8, 10 });
    test("or/4", vals, Pred.or(Pred.not(even),atLeastFive), 
        new Integer[] { 1, 3, 5, 6, 7, 8, 9, 10 });
    test("or/5", vals, Pred.not(Pred.or(even,atLeastFive)), 
        new Integer[] { 1, 3 });
  } // testOr()

  /**
   * Some tests using and
   */
  @Test
  public void testAnd()
  {
    test("and/1", vals, Pred.and(Pred.ACCEPT,Pred.REJECT), new Integer[] { });
    test("and/2", vals, Pred.and(even,atLeastFive), new Integer[] { 6, 8, 10 });
    test("and/3", vals, Pred.and(even,Pred.not(atLeastFive)),
        new Integer[] { 2, 4 });
    test("and/4", vals, Pred.and(Pred.not(even),atLeastFive), 
        new Integer[] { 5, 7, 9 });
    test("and/5", vals, Pred.not(Pred.and(even,atLeastFive)), 
        new Integer[] { 1, 2, 3, 4, 5, 7, 9 });
  } // testAnd()

} // class Problem2Tests
