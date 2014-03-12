import java.io.PrintWriter;

import java.util.Arrays;
import java.util.Iterator;

/**
 * An example of iteration and filtering with predicates by Phil and Holden.
 */
public class Example1
{
  public static void main(String[] args)
    throws Exception
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    Integer[] values = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 };
    Iterator<Integer> it = Arrays.asList(values).iterator();
    Predicate<Integer> even = new Predicate<Integer>()
        {
          public boolean test(Integer val)
          {
            return (val % 2) == 0;
          } // test(Integer)
        }; // new Predicate<Integer>()
        
    while (it.hasNext())
      {
        Integer i = it.next();
        if (even.test(i))
          pen.println(i);
      } // while

    pen.close();
  } // main(String[])
  
} // class Example1
