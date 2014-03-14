import java.util.Comparator;
import java.util.Iterator;

/**
 * An implementation of the SortedList interface using skip lists.
 */
public class SortedSkipList<T>
    extends
      SkipList<T>
    implements
      SortedList<T>
{
  // +--------------+----------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a sorted list that sorts values by order.
   */
  public SortedSkipList(Comparator<T> order)
  {
    super(order);
  } // SortedSkipList(Comparator<T>)

  /**
   * Create a sorted list that sorts values by order, using no more
   * than maxLevel levels.
   *
   * @pre maxLevel >= 1
   */
  public SortedSkipList(Comparator<T> order, int maxLevel)
  {
    super(order, maxLevel);
  } // SortedSkipList(Comparator<T>, int)

  // +---------+---------------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Add an element to the sorted list.
   */
  @Override
  public void add(T val)
  {
    // STUB;
  } // add(T)

  /**
   * Get an iterator for the list that iterates from "smallest" to
   * "largest".
   */
  public Iterator<T> iterator()
  {
    // STUB;
    return null;
  } // iterator()
} // class SortedSkipList
