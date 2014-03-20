import java.util.ListIterator;

/**
 * List iterators that we can clone.
 *
 * Many of the sorting algorithms benefit from being able to create
 * a second iterator at the same location as an existing iterator.
 * For example, when we're partitioning in Quicksort, we want to be
 * able to keep track of the bounds of the subarray of interest, but
 * we also want to be able to iterate that subarray from back to front
 * and front to back.
 */
public interface CloneableListIterator<T>
  extends Cloneable, ListIterator<T>
{
  public CloneableListIterator<T> clone();
} // interface CloneableListIterator<T>
