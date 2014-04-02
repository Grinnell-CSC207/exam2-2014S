import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import org.junit.Test;

/**
 * A variety of additional tests for problem 2 on the exam.
 */
public class Problem2RemoveTests
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
  static Predicate<Integer> even = modpred(2, 0);

  /**
   * Check for odd numbers.
   */
  static Predicate<Integer> odd = modpred(2, 1);

  /**
   * Check for numbers that are zero mod 4.
   */
  static Predicate<Integer> zeroModFour = modpred(4, 0);

  /**
   * A random number generator.
   */
  static Random generator = new Random();

  // +-----------+-------------------------------------------------------
  // | Utilities |
  // +-----------+

  /**
   * Build a predicate that returns true only if input % modulus == result
   */
  public static Predicate<Integer> modpred(final int modulus, final int result)
  {
    return new Predicate<Integer>()
      {
        public boolean test(Integer i)
        {
          return i % modulus == result;
        } // test(Integer)
      }; // new Predicate
  } // modpred(int, int)

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
   * Remove all elements that meet a predicate.
   */
  public static <T> void remove(Iterator<T> vals, Predicate<T> pred)
  {
    T val;
    while (vals.hasNext())
      {
        if (pred.test(val = vals.next()))
          {
            vals.remove();
          } // if pred.test
      } // while
  } // remove(Iterator<T>, Predicate<T>)

  /**
   * Build a random array of a given size, and see how well we succeed at
   * removing elements.
   */
  @SuppressWarnings({"unchecked"})
  public static void removeTest(int size)
  {
    ArrayList<Integer> values = new ArrayList<Integer>(size);
    for (int i = 0; i < size; i++)
      values.add(generator.nextInt());

    // Make two copies of the original list of values
    ArrayList<Integer> filterme = (ArrayList<Integer>) values.clone();
    ArrayList<Integer> copied = (ArrayList<Integer>) values.clone();

    // Remove all the values that are 0 mod 4 from a filtered iterator
    // for the first list.
    remove(new FilteredIterator<Integer>(filterme.iterator(), even), zeroModFour);

    // Remove all the values that are 0 mod 4 from an unfiltered iterator
    // for the first list.
    remove(copied.iterator(), zeroModFour);

    // See if they are the same
    if (! Arrays.equals(filterme.toArray(), copied.toArray()))
      {
        System.err.println("Starting with " + values.toString());
        System.err.println("Removing from filtered iterator:   " + filterme.toString());
        System.err.println("Removing from unfiltered iterator: " + copied.toString());
        fail("Did not delete properly.");
      } // if
  } // removeTest(int)

  // +-------+-----------------------------------------------------------
  // | Tests |
  // +-------+

  /**
   * Try removing from a few random arrays.
   */
  @Test
  public void removeTest1()
  {
    for (int i = 0; i < 20; i++)
      {
        for (int j = 0; j < 10; j++)
          {
            removeTest(i);
          } 
      } // for
  } // removeTest1()


} // class Problem2RemoveTests
