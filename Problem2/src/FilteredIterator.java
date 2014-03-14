import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterators that filter elements.
 */
public class FilteredIterator<T>
  implements Iterator<T>
{
  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The iterator we're filtering.
   */
  Iterator<T> base;

  /**
   * The predicate used to do the filtering.
   */
  Predicate<? super T> pred;

  // +--------------+----------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a version of base which returns only the values for which
   * pred holds.
   */
  public FilteredIterator(Iterator<T> base, Predicate<? super T> pred)
  {
    this.base = base;
    this.pred = pred;
  } // FilteredIterator(Iterator<T>, Predicate<? super T>)

  // +---------+---------------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Determine if any elements remain for which pred holds.
   */
  public boolean hasNext()
  {
    // STUB
    return false;
  } // hasNext()

  /**
   * Get the next element for which pred holds.
   *
   * @throws NoSuchElementException
   *   when there are no more elements for which pred holds.
   */
  public T next()
    throws NoSuchElementException
  {
    // STUB
    return null;
  } // next()

  /**
   * Remove the element most recently returned by next.  
   *
   * @throws IllegalStateException
   *   If the next method has not yet been called, or the remove method
   *   has already been called after the last call to the next method,
   *   or if the hasNext method has been called after the last call to
   *   the next method.
   */
  public void remove()
    throws IllegalStateException
  {
    // STUB
  } // remove
} // class FilteredIterator<T>
