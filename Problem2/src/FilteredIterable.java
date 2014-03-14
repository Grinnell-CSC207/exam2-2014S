import java.util.Iterator;

/**
 * Objects that build filtered iterators for other from other iterables.
 */
public class FilteredIterable<T>
  implements Iterable<T>
{
  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The underlying iterable object.
   */
  Iterable<T> base;

  /**
   * The predicate used for filtering.
   */
  Predicate<? super T> pred;

  // +--------------+----------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a new iterable whose iterators are the iterators of base,
   * filtered by pred.
   */
  public FilteredIterable(Iterable<T> base, Predicate<? super T> pred)
  {
    this.base = base;
    this.pred = pred;
  } // FilteredIterable(Iterable<T>, Predicate<? super T>)

  // +---------+---------------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Build a new iterator.
   */
  public Iterator<T> iterator()
  {
    return new FilteredIterator<T>(base.iterator(), pred);
  } // iterator()
} // class FilteredIterable
