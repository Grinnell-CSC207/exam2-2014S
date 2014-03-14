import java.util.Iterator;
import java.util.ListIterator;

/**
 * Very simple lists.
 */
public interface SimpleList<T>
    extends
      Iterable<T>
{
  public Iterator<T> iterator();

  public CloneableListIterator<T> listIterator();
} // interface SimpleList<T>
