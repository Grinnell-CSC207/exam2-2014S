import java.io.PrintWriter;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Another example of iteration and filtering with predicates by 
 * Phil and Holden.
 */
public class Example3
{
  // +------------+------------------------------------------------------
  // | Predicates |
  // +------------+ 

  /**
   * Check for even numbers.
   */
  static Predicate<Integer> even = new Predicate<Integer>()
      {
        public boolean test(Integer i)
        {
          return (i % 2) == 0;
        } // test(Integer)
      }; // new Predicate<Integer>()

  // +------+------------------------------------------------------------
  // | Main |
  // +------+

  public static void main(String[] args)
    throws Exception
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    Iterable<Integer> ints = new Range(1,20);

    pen.println("A range of integers.");
    for (Integer i: ints)
      pen.print(i + " ");
    pen.println();

    pen.println("The even integers in that range.");
    for (Integer i : new FilteredIterable<Integer>(ints, even))
      pen.print(i + " ");
    pen.println();

    pen.println("The odd integers in that range.");
    for (Integer i : new FilteredIterable<Integer>(ints, Pred.not(even)))
      pen.print(i + " ");
    pen.println();

    pen.close();
  } // main(String[])
  
} // class Example3
