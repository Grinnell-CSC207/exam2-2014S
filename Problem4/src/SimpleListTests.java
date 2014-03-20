import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

import org.junit.Test;

/**
 * A variety of things to support tests for simple lists.
 */
public class SimpleListTests
{
  // +-----------+-------------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The number of permutation tests we do.
   */
  public static final int NUM_PERMUTATION_TESTS = 20;

  /**
   * The numbers from 1 to 10, used for a variety of tests.
   */
  public static final Integer[] numbers = 
      new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

  public static final String[] words = 
      new String[] { "alpha", "bravo", "charles", "charlie",
            "delta", "echo", "epsilon", "foxtrot", "striped", 
            "waltz", "zebra" };

  // +---------------+---------------------------------------------------
  // | Static Fields |
  // +---------------+
 
  /**
   * A random number generator for use in permutations and such.
   */
  static Random generator = new Random();

  // +-------------+-----------------------------------------------------
  // | Comparators |
  // +-------------+

  /**
   * A comparator for integers that orders them from smallest to largest.
   */
  static Comparator<Integer> increasing = new Comparator<Integer>()
    {
      public int compare(Integer left, Integer right)
      {
        return left.compareTo(right);
      } // compare(Integer, Integer)
    }; // new Comparator<Integer>

  /**
   * A comparator for integers that orders them from largest to smallest.
   */
  static Comparator<Integer> decreasing = new Comparator<Integer>()
    {
      public int compare(Integer left, Integer right)
      {
        return -(left.compareTo(right));
      } // compare(Integer, Integer)
    }; // new Comparator<Integer>

  /**
   * A comparator for strings that uses an alphabetical policy.
   */
  static Comparator<String> alphabetical = new Comparator<String>()
    {
      public int compare(String left, String right)
      {
        return left.compareToIgnoreCase(right);
      } // compare(String, String)
    }; // new Comparator<String> 

  /**
   * A comparator for strings that uses a reverse alphabetical policy.
   */
  static Comparator<String> reverseAlphabetical = new Comparator<String>()
    {
      public int compare(String left, String right)
      {
        return -(left.compareToIgnoreCase(right));
      } // compare(String, String)
    }; // new Comparator<String> 

  // +-----------+-------------------------------------------------------
  // | Utilities |
  // +-----------+

  /**
   * "Randomly" permute an array in place.
   */
  public static <T> T[] permute(T[] values)
  {
    for (int i = 0; i < values.length; i++)
      {
        swap(values, i, generator.nextInt(values.length));
      } // for
    return values;
  } // permute(T)

  /**
   * Swap two elements in an array.
   * 
   * @param values
   *            the array
   * @param i
   *            one of the indices
   * @param j
   *            another index
   * @pre 0 <= i,j < values.length
   * @pre a = values[i]
   * @pre b = values[j]
   * @post values[i] = b
   * @post values[j] = a
   */
  public static <T> void swap(T[] values, int i, int j)
  {
    T tmp = values[i];
    values[i] = values[j];
    values[j] = tmp;
  } // swap(T[], int, int)

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
   * Convert a simple list to an array.
   */
  public static <T> T[] toArray(SimpleList<T> lst)
  {
    return toArray(lst.iterator());
  } // toArray(SimpleList<T>)

  // +-------+-----------------------------------------------------------
  // | Tests |
  // +-------+

  /**
   * Given an array, a comparator, and a factory for simple lists, build
   * a new sorted list, add the elements to the list in random order,
   * and then see if they come out in the appropriate order.
   *
   * Note that this does not check to ensure that elements are neither
   * added nor removed.
   */
  public static <T> void kthTests(final T[] original, 
      SimpleListFactory<T> factory,
      Comparator<T> order)
  {
    // Make a sorted copy of the original array
    T[] sorted = original.clone();
    Arrays.sort(sorted, order);

    // For each position
    for (int k = 0; k < sorted.length; k++)
      {
        // Make a permuted copy of the sorted array
        T[] values = sorted.clone();
        permute(values);

        // Create a new simple list and an iterator for the list.
        SimpleList<T> lst = factory.build();
        CloneableListIterator<T> it = lst.listIterator();

        // Add all of the elements to the list.
        for (T val : values)
          {
            it.add(val);
          } // for

        // Find the kth smallest element.
        T kth = SimpleListUtils.kthSmallest(lst, order, k);

        if (!kth.equals(sorted[k]))
          {
            fail("For kthSmallest(" + Arrays.toString(values) + ", " + k +
                ") expected " + sorted[k] + ", got " + kth);
          } // if we did not get what we expected
      } // for k
  } // kthTests

  /**
   * Test: A variety of sizes of arrays of strings, using two comparators.
   */
  public static void kthStringTests(SimpleListFactory<String> factory)
  {
    for (int size = 1; size < words.length; size++)
      {
        String[] vals = Arrays.copyOfRange(words, 0, size);
        kthTests(vals, factory, alphabetical);
        kthTests(vals, factory, reverseAlphabetical);
      } // for
  } // kthStringTests

  /**
   * Test: A variety of sizes of arrays of integers, using two comparators.
   */
  public static void kthIntegerTests(SimpleListFactory<Integer> factory)
  {
    for (int size = 1; size < numbers.length; size++)
      {
        Integer[] vals = Arrays.copyOfRange(numbers, 0, size);
        kthTests(vals, factory, increasing);
        kthTests(vals, factory, decreasing);
      } // for
  } // kthIntegerTests

} // class SimpleListTests
