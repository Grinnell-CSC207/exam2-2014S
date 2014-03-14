import java.util.Iterator;

/**
 * Collections of values that are iterated from smallest to largest.
 */
public interface SortedList<T>
    extends
      Iterable<T>
{
  /**
   * Add an element to the list.
   */
  public void add(T value);

  /**
   * Get an iterator for the list.
   */
  public Iterator<T> iterator();
} // interface SortedList<T>
