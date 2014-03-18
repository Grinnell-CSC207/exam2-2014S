import java.io.PrintWriter;

import java.util.Arrays;
import java.util.Iterator;

/**
 * A quick experiment with sorted skip lists.  We add a bunch of numbers
 * to a sorted skip list and see if they come out in order.  Then we
 * remove the odd numbers.  Then we remove the even numbers.
 */
public class SortedSkipListExpt
{
  public static void main(String[] args)
  {
    // Set up our basic list
    PrintWriter pen = new PrintWriter(System.out, true);
    SortedSkipList<Integer> lst = 
        new SortedSkipList<Integer>(SortedListTests.increasing);

    // Add some interesting values in random order
    Integer[] values = new Integer[] { 0,1,2,4,6,7,8,9,10,11,12,13,14,15,17,19,20 };
    pen.println("USING THESE VALUES");
    pen.println(Arrays.toString(values));
    SortedListTests.permute(values);
    for (Integer val : values)
      {
        pen.println("Adding " + val);
        lst.add(val);
      } // for

    // Print the resulting list
    pen.println("ORIGINAL LIST");
    pen.println(Arrays.toString(SortedListTests.toArray(lst.iterator())));

    // Remove the even numbers
    Iterator<Integer> it = lst.iterator();
    while (it.hasNext())
      {
        if ((it.next() % 2) == 0)
          it.remove();
      } // while
    pen.println("AFTER REMOVING EVEN NUMBERS");
    pen.println(Arrays.toString(SortedListTests.toArray(lst.iterator())));

    // Remove the remaining elements
    it = lst.iterator();
    while (it.hasNext())
      {
        it.next();
        it.remove();
      } // while
    pen.println("AFTER REMOVING REMAINING NUMBERS");
    pen.println(Arrays.toString(SortedListTests.toArray(lst.iterator())));

    // Clean up
    pen.close();
  } // main(String[])
} // class SortedSkipListExpt
