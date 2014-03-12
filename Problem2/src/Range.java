import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A simple, iterable range of values.
 */
public class Range
  implements Iterable<Integer>
{
  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The lower bound of the range.
   */
  int lb;

  /**
   * The upper bound of the range.
   */
  int ub;

  // +--------------+----------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create an object to represent the range [lb .. ub)
   */
  public Range(int lb, int ub)
  {
    this.lb = lb;
    this.ub = ub;
  } // Range

  // +-----------+-------------------------------------------------------
  // | Iterators |
  // +-----------+

  /**
   * Create a new iterator that goes from lb to ub.
   */
  public Iterator<Integer> iterator()
  {
    return new Iterator<Integer>()
      {
        int i = lb;

        public boolean hasNext()
        {
          return i < ub;
        } // hasNext()

        public Integer next()
          throws NoSuchElementException
        {
          if (!hasNext())
            throw new NoSuchElementException();
          return i++;
        } // next()

        public void remove()
          throws UnsupportedOperationException
        {
          throw new UnsupportedOperationException();
        } // remove()

      }; // new Iterator<Integer>
  } // iterator()

} // class Range

