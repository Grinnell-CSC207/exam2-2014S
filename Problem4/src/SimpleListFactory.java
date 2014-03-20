import java.util.Comparator;

/**
 * Something that builds simple lists.
 */
public interface SimpleListFactory<T>
{
  public SimpleList<T> build();
} // SortedListFactory<T>
