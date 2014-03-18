import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import org.junit.Test;

/**
 * SamR's tests for Quicksort. Code based on earlier code from the sorting
 * laboratories.
 */
public class SamTest
{
  // +-----------+-------------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The number of tests we typically do when looking at the
   * permutations of a sorted array.
   */
  public static final int NUM_PERMUTATIONS = 20;

  /**
   * The number of randomized arrays we build.
   */
  public static final int NUM_RANDOM_ARRAYS = 20;

  /**
   * The number of permutations we do per randomized array.
   */
  public static final int TESTS_PER_RANDOM_ARRAY = 50;

  /**
   * The minimum size of a random array.
   */
  public static final int MIN_RANDOM_ARRAY_SIZE = 10;

  /**
   * The maximum size of a random array.
   */
  public static final int MAX_RANDOM_ARRAY_SIZE = 128;

  // +---------------+---------------------------------------------------
  // | Inner Classes |
  // +---------------+

  /**
   * A point in two-space.  Included so that we can do some tests
   * with things that do not implement the Comparable interface.
   */
  class Point
  {
    // +--------+------------------------------------------------------
    // | Fields |
    // +--------+

    Integer x;
    Integer y;

    // +--------------+------------------------------------------------
    // | Constructors |
    // +--------------+

    public Point(int x, int y)
    {
      this.x = x; 
      this.y = y;
    } // Point(int, int)

    // +------------------+--------------------------------------------
    // | Standard Methods |
    // +------------------+

    /**
     * Determine if this point equals another object.
     */
    public boolean equals(Object other)
    {
      return ((other instanceof Point) && (this.equals((Point) other)));
    } // equals(Object)

    /**
     * Determine if this point equals another point.
     */
    public boolean equals(Point other)
    {
      return (this.x == other.x) && (this.y == other.y);
    } // equals(Point)

    /**
     * We've implemented equals, so we need to implement hashCode.
     */
    public int hashCode()
    {
      return 5*(this.x.hashCode()) - 3*(this.y.hashCode());
    } // hashCode()

    /**
     * Convert to a string for printing.
     */
    public String toString()
    {
      return "<" + this.x + "," + this.y + ">";
    } // toString()

    // +--------------------+------------------------------------------
    // | Additional Methods |
    // +--------------------+

    /**
     * Compute the distance from the origin.
     *
     * @pre the distance from the origin is less than Int.MAX_VALUE
     */
    public double distance()
    {
      return Math.sqrt(this.x*this.x + this.y*this.y);
    } // distance()

  } // class Point

  // +---------------+---------------------------------------------------
  // | Static Fields |
  // +---------------+

  /**
   * A random number generator for use in permutations and such.
   */
  static Random generator = new Random();

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
  public Comparator<Integer> decreasing = new Comparator<Integer>()
    {
      public int compare(Integer left, Integer right)
      {
        return -(left.compareTo(right));
      } // compare(Integer, Integer)
    }; // new Comparator<Integer>

  /**
   * A comparator for points that orders them by distance from the
   * origin.  Only works for points that are within sqrt(maxint)
   * from the origin.
   */
  static Comparator<Point> byDistance = new Comparator<Point>()
    {
      public int compare(Point left, Point right)
      {
        double leftDistance = left.distance();
        double rightDistance = right.distance();
        if (leftDistance < rightDistance)
          return -1;
        else if (leftDistance > rightDistance)
          return 1;
        else
          return 0;
      } // compare(Point, Point)
    }; // new Comparator<Point>

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
   * Check the results of sorting an already sorted array.
   */
  public static <T> void checkSort(T[] sorted, Comparator<T> order)
  {
    checkSort(sorted, sorted.clone(), order);
  } // checkSort(T[])

  /**
   * Check the results of sorting.
   * 
   * @param unsorted
   *            the array to sort
   * @param expected
   *            the sorted version of that array
   * @param order
   *            the order in which elements should appear
   */
  public static <T> void checkSort(T[] unsorted, T[] expected,
                                   Comparator<T> order)
  {
    T[] result = unsorted.clone();
    Quicksorter.qsort(result, order);
    if (!Arrays.equals(result, expected))
      {
        System.err.println("Original: " + Arrays.toString(unsorted));
        System.err.println("Result:   " + Arrays.toString(result));
        System.err.println("Expected: " + Arrays.toString(expected));
        fail("Did not sort correctly.");
      } // if not equal
  } // checkResults(T[], T[], T[])

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
   * Generate a "random" sorted array of integers of size n.
   */
  public static Integer[] randomSortedInts(int n)
  {
    if (n == 0)
      {
        return new Integer[0];
      }
    Integer[] values = new Integer[n];
    // Start with a negative number so that we have a mix
    values[0] = generator.nextInt(10) - n;
    // Add remaining values. We use a random increment between
    // 0 and 3 so that there are some duplicates and some gaps.
    for (int i = 1; i < n; i++)
      {
        values[i] = values[i - 1] + generator.nextInt(4);
      } // for
    return values;
  } // randomSortedInts

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
   * A simple test using one permutation of an already sorted array.
   * 
   * @param, sorted A sorted array of values
   * @param, order The order used to generate that sorted array.
   */
  public static <T> void permutationTest(T[] sorted, Comparator<T> order)
  {
    // Create a permuted version of the array.
    T[] values = sorted.clone();
    permute(values);

    // Check the results.
    checkSort(values, sorted, order);
  } // permutationTest(PrintWriter, Sorter<T>, Comparator<T>

  /**
   * Do some number of permutation tests.
   */
  public static <T> void permutationTests(T[] sorted, Comparator<T> compare,
                                          int n)
  {
    for (int i = 0; i < n; i++)
      {
        permutationTest(sorted, compare);
      } // for
  } // permutationTests(T[], compare, int)

  // +-------+-----------------------------------------------------------
  // | Tests |
  // +-------+

  /**
   * Make sure that it works correctly on some simple arrays.
   */
  @Test
  public void testSimple()
  {
    // Empty array
    checkSort(new Integer[] {}, increasing);
    // Singleton array
    checkSort(new Integer[] { 0 }, increasing);
    // Two-element arrays
    checkSort(new Integer[] { 0, 1 }, increasing);
    checkSort(new Integer[] { 0, 0 }, increasing);
    // Three-element arrays
    checkSort(new Integer[] { 0, 0, 0 }, increasing);
    checkSort(new Integer[] { 0, 0, 1 }, increasing);
    checkSort(new Integer[] { 0, 1, 1 }, increasing);
    // Four-element arrays
    checkSort(new Integer[] { 0, 0, 0, 0 }, increasing);
    checkSort(new Integer[] { 0, 0, 0, 1 }, increasing);
    checkSort(new Integer[] { 0, 0, 1, 1 }, increasing);
    checkSort(new Integer[] { 0, 1, 1, 1 }, increasing);
  } // testSimple()

  /**
   * Make sure that some larger, predesigned, arrays sort correctly.
   */
  @Test
  public void testVarious()
  {
    // Mostly the same value
    permutationTests(new Integer[] { 0, 0, 0, 0, 0, 0, 1, 2, 3, 4 }, increasing,
                     NUM_PERMUTATIONS);
    // Only two different values
    permutationTests(new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1,
                                    1, 1 }, increasing, NUM_PERMUTATIONS);

    // Three different values
    permutationTests(new Integer[] { 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 5, 5, 5, 5,
                                    5 }, increasing, NUM_PERMUTATIONS);
  } // testVarious()

  /**
   * Lots of randomized tests.
   */
  @Test
  public void testRandomized()
  {
    for (int i = 0; i < NUM_RANDOM_ARRAYS; i++)
      {
        Integer[] sorted =
            randomSortedInts(1 + generator.nextInt(1 + MAX_RANDOM_ARRAY_SIZE
                                                   - MIN_RANDOM_ARRAY_SIZE));
        permutationTests(sorted, increasing, TESTS_PER_RANDOM_ARRAY);
      } // for
  } // testRandomized

  /**
   * Make sure that we can sort values in other ways.  We don't need
   * a lot of these tests, because the increasing tests get most of
   * the corner cases.  
   */
  @Test
  public void testDecreasing()
  {
    permutationTests(new Integer[] { 5, 4, 3, 2, 1, 0 }, decreasing, 
                     NUM_PERMUTATIONS);
    permutationTests(new Integer[] { 5, 5, 5, 5, 5, 3, 3, 3, 3, 0, 0, 0 }, 
                     decreasing, NUM_PERMUTATIONS);
  } // testDecreasing()

  /**
   * Make sure that we can sort some strings, too.
   */
  @Test
  public void testStrings()
  {
    checkSort(new String[] { }, reverseAlphabetical);
    checkSort(new String[] { "alphabet" }, reverseAlphabetical);
    checkSort(new String[] { "alpha", "alpha" }, reverseAlphabetical);
    checkSort(new String[] { "beta", "alpha" }, reverseAlphabetical);

    permutationTests(new String[] { "zebra", "yellow", "xerox", "whatEVER", 
                                    "whatEVER", "VIOLIN", "Umbrella",
                                    "tango" }, 
                     reverseAlphabetical, NUM_PERMUTATIONS);
  } // testStrings
                         
  /**
   * Tests with values that do not implement Comparable.
   */
  @Test
  public void testPoints()
  {
    checkSort(new Point[] { }, byDistance);
    checkSort(new Point[] { new Point(0,0) }, byDistance);
    checkSort(new Point[] { new Point(1,1), new Point(1,1) }, byDistance);
    checkSort(new Point[] { new Point(0,0), new Point(1,1) }, byDistance);

    permutationTests(new Point[] { new Point(0,0), new Point(0,0),
                                   new Point(1,0), new Point(-1,1),
                                   new Point(0,-2), new Point(1,2),
                                   new Point(-2,-2), new Point(-2,-2),
                                   new Point(-2,-2), new Point(-2,-2),
                                   new Point(3,2) },
                     byDistance, NUM_PERMUTATIONS);
    permutationTests(new Point[] { new Point(0,0), new Point(0,0),
                                   new Point(0,0), new Point(0,0),
                                   new Point(0,0), new Point(0,0),
                                   new Point(0,1), new Point(0,1),
                                   new Point(0,1), new Point(0,1),
                                   new Point(0,2), new Point(0,2),
                                   new Point(0,3), new Point(0,4) },
                     byDistance, NUM_PERMUTATIONS);
  } // testPoints()

} // SamTest
