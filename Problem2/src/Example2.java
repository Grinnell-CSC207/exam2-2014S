import java.io.PrintWriter;

import java.util.Arrays;
import java.util.Iterator;

/**
 * An example of iteration and filtering with predicates by Phil and Holden.
 */
public class Example2
{
  public static void main(String[] args)
    throws Exception
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    Integer[] values = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 };
    Iterator<Integer> it = new FilteredIterator<Integer>(
            Arrays.asList(values).iterator(),
            new Predicate<Integer>()
                {
                  public boolean test(Integer val)
                  {
                    return (val % 2) == 0;
                  } // test(Integer)
                });
        
    while (it.hasNext())
      {
        pen.println(it.next());
      } // while

    pen.close();
  } // main(String[])
  
} // class Example2
