import java.util.Comparator;

/**
 * Something that builds sorted lists.
 */
public interface SortedListFactory<T>
{
  public SortedList<T> build(Comparator<? super T> order);
} // SortedListFactory<T>
