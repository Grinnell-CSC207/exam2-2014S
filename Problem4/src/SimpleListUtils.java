import java.util.Comparator;

/**
 * Utilities for simple lists.
 */
public class SimpleListUtils
{
  /**
   * Find the kth smallest element of a simple list.
   *
   * @pre
   *   No two values in the list are equal.  We have this precondition
   *   mostly to make it easier to state the postcondition.
   * @pre
   *   k < length of lst
   * @pre
   *   lst is not empty
   * @post
   *   There are exactly k values smaller than val in the list.  A
   *   value is "smaller" than val if order.compare(value, val) < 0.
   * @post
   *   The elements of the list may have been rearranged.
   * @post
   *   No elements have been added to or removed from the ilst.
   */
  public static <T> T kthSmallest(SimpleList<T> lst, Comparator<T> order, int k)
  {
    // STUB
    return lst.iterator().next();
  } // kthSmallest(SimpleList<T>, Comparator<T>, int)
} // class SimpleListUtils
