import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

import org.junit.Test;

/**
 * A variety of tests for simple sorted lists.
 */
public class SortedListTests
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
  public static final Integer[] vals = 
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
   * Determine if the contents of an iterator match an array.
   */
  public static <T> void assertElements(SortedList<T> lst, T[] values)
  {
    T[] elements = toArray(lst.iterator());
    if (!Arrays.equals(elements, values))
      {
        System.err.println("List contains incorrect elements.");
        System.err.println("Expected: " + Arrays.toString(values));
        System.err.println("Actual:   " + Arrays.toString(elements));
        fail();
      } // if (!Arrays.equals(elements, values)
  } // assertElements(SortedList<T>, T[])

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
   * Given an array, a comparator, and a factory for sorted lists, build
   * a new sorted list, add the elements to the list in random order,
   * and then see if they come out in the appropriate order.
   */
  public static <T> void permutationTest(final T[] original, 
      SortedListFactory<T> factory,
      Comparator<? super T> order)
  {
    // Make a sorted copy of the original array
    T[] sorted = original.clone();
    Arrays.sort(sorted, order);

    // Make a permuted copy of the sorted array
    T[] values = sorted.clone();
    permute(values);

    // Create a new sorted list.
    SortedList<T> lst = factory.build(order);

    // Add all of the elements to the list.
    for (T val : values)
      {
        lst.add(val);
      } // for

    // Check if the result is in the same order.
    T[] result = toArray(lst.iterator());
    if (!Arrays.equals(sorted, result))
      {
        System.err.println("Original: " + Arrays.toString(values));
        System.err.println("Iterated: " + Arrays.toString(result));
        System.err.println("Expected: " + Arrays.toString(sorted));
        fail("Permutation Test");
      } // if
  } // permutationTest

  /**
   * A bunch of permutation tests using strings.  (Since we're using
   * generic parameters, I can't overload the name.)  These tests
   * primarily check whether the sorted list iterates the values in
   * order.
   */
  public static void permutationTestS(SortedListFactory<String> factory)
  {
    for (int i = 0; i < NUM_PERMUTATION_TESTS; i++)
      {
        permutationTest(words, factory, alphabetical);
        permutationTest(words, factory, reverseAlphabetical);
        int lb = generator.nextInt(words.length);
        int ub = lb + generator.nextInt(words.length - lb);
        permutationTest(Arrays.copyOfRange(words, lb, ub), factory, 
            alphabetical);
      } // for
  } // permutationTestS(SortedListFactory<String>)

  /**
   * A bunch of permutation tests using Integers.  (Since we're using
   * generic parameters, I can't overload the name.)  As in the previous
   * case, these tests primarily check that the resulting lists are in
   * the appropriate order.
   */
  public static void permutationTestI(SortedListFactory<Integer> factory)
  {
    for (int i = 0; i < NUM_PERMUTATION_TESTS; i++)
      {
        permutationTest(vals, factory, increasing);
        permutationTest(vals, factory, decreasing);
      } // for
  } // permutationTestI(SortedListFactory<Integer>)

  /**
   * A simple test of adding and removing elements.
   */
  public static void removeTestS1(SortedListFactory<String> factory)
  {
    // The list we're working with
    SortedList<String> lst = factory.build(alphabetical);

    // An iterator for that list
    Iterator<String> it;

    // Create a one-element list
    lst.add("alpha");
    assertElements(lst, new String[] { "alpha" });

    // Remove the one element.
    it = lst.iterator();
    assertEquals("alpha", it.next());
    it.remove();
    assertElements(lst, new String[] { });

    // Add a few elements
    lst.add("ant");
    lst.add("zebra");
    lst.add("chimp");
    assertElements(lst, new String[] { "ant", "chimp", "zebra" });

    // Remove and reinsert the first element
    it = lst.iterator();
    assertEquals("ant", it.next());
    it.remove();
    assertElements(lst, new String[] { "chimp", "zebra" });
    lst.add("ant");
    assertElements(lst, new String[] { "ant", "chimp", "zebra" });

    // Remove and reinsert the middle element
    it = lst.iterator();
    assertEquals("ant", it.next());
    assertEquals("chimp", it.next());
    it.remove();
    assertElements(lst, new String[] { "ant", "zebra" });
    lst.add("chimp");
    assertElements(lst, new String[] { "ant", "chimp", "zebra" });

    // Remove and reinsert the last element
    it = lst.iterator();
    assertEquals("ant", it.next());
    assertEquals("chimp", it.next());
    assertEquals("zebra", it.next());
    it.remove();
    assertElements(lst, new String[] { "ant", "chimp" });
    lst.add("zebra");
    assertElements(lst, new String[] { "ant", "chimp", "zebra" });
  } // removeTestS1(SortedListFactory<String>)

  /**
   * A test of adding and removing elements.
   */
  public static void removeTestI1(SortedListFactory<Integer> factory)
  {
    // Build the list.
    SortedList<Integer> lst = factory.build(increasing);
    Iterator<Integer> it;

    // We do the same set of work three times because past history
    // suggests that clearing out a list can leave it in a weird
    // state.
    for (int turns = 0; turns < 3; turns++)
      {
        // Add a basic set of values.
        for (int i = 1; i <= 10; i++)
          {
            lst.add(i);
          } // for
        assertElements(lst, new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 });
    
        // Remove all of the even numbers
        it = lst.iterator();
        while (it.hasNext())
          {
            if ((it.next() % 2) == 0)
              it.remove();
          } // while
        assertElements(lst, new Integer[] { 1, 3, 5, 7, 9 });
    
        // Add an element in the middle
        lst.add(8);
        assertElements(lst, new Integer[] { 1, 3, 5, 7, 8, 9 });
    
        // Remove all of the odd numbers
        it = lst.iterator();
        while (it.hasNext())
          {
            if ((it.next() % 2) == 1)
              it.remove();
          } // while
        assertElements(lst, new Integer[] { 8 });

        // Add values at the front and end of the list.
        lst.add(0);
        lst.add(11);
        assertElements(lst, new Integer[] { 0, 8, 11 });

        // Remove everything
        it = lst.iterator();
        while (it.hasNext())
          {
            it.next();
            it.remove();
          } // while
        assertElements(lst, new Integer[] { });

    } // for turns
  } // removeTestI1

} // class SortedListTests
