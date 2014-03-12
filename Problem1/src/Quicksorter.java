import java.util.Comparator;

/**
 * An implementation of Quicksort without the baggage of interfaces and such.
 * Uses DNF to partition.
 */
public class Quicksorter
{
  // +----------------+--------------------------------------------------
  // | Static Methods |
  // +----------------+

  /**
   * Sort values in place using order to compare values.
   */
  public static <T> void qsort(T[] values, Comparator<T> order)
  {
    qsort(values, order, 0, values.length);
  } // qsort(T[], Comparator<T>)

  /**
   * Sort the subarray of values from lb (inclusive) to ub (exclusive) using
   * order to compare values.
   */
  public static <T> void qsort(T[] values, Comparator<T> order, int lb, int ub)
  {
    // STUB
  } // qsort(T[], Comparator<T>, int, int)

  // +----------------+--------------------------------------------------
  // | Helper Classes |
  // +----------------+

  /**
   * A simple representation of a range of integers.
   */
  class Range
  {
    int lb;
    int ub;

    public Range(int lb, int ub)
    {
      this.lb = lb;
      this.ub = ub;
    } // Range(int, int)
  } // class IntPair
} // class Quicksorter
