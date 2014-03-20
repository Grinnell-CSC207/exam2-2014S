import java.io.PrintWriter;

import java.util.Arrays;
import java.util.ListIterator;

/**
 * A few quick experiments with the kthSmallest method.
 */
public class KthSmallestExpt
{
  // +-------------+-----------------------------------------------------
  // | Experiments |
  // +-------------+

  /**
   * Given a list and a k, find the kth-smallest element and print it
   * out.
   */
  public static void expt(PrintWriter pen, SimpleList<Integer> lst, int k)
  {
    String call = "kthSmallest(" + k + ")";
    pen.println("Before calling " + call);
    pen.println("  " + Arrays.toString(SimpleListTests.toArray(lst)));
    Integer kthSmallest = SimpleListUtils.kthSmallest(lst, 
         SimpleListTests.increasing, k);
    pen.println(call + " = " + kthSmallest);
    pen.println("After calling " + call);
    pen.println("  " + Arrays.toString(SimpleListTests.toArray(lst)));
    pen.println();
  } // expt(PrintWriter, SimpleList<Integer>, int)

  /**
   * Given an array, build a list corresponding to that list and find
   * the kth smallest element.
   */
  public static void expt(PrintWriter pen, Integer[] vals, int k)
  {
    // Put the values in a list
    DoublyLinkedList<Integer> lst = new DoublyLinkedList<Integer>();
    ListIterator<Integer> it = lst.listIterator();
    for (int i = 0; i < vals.length; i++)
      it.add(vals[i]);
    // Do the real experiment
    expt(pen, lst, k);
  } // expt(PrintWriter, Integer[], int)

  /**
   * Given a size, build a list of that size, and find a few of the
   * kth smallest elements.
   *
   * @pre size >= 3
   */
  public static void expt(PrintWriter pen, int size)
  {
    // Build the array of 0 .. size-1 in random order.
    Integer[] vals = new Integer[size];
    for (int i = 0; i < size; i++)
      vals[i] = i;
    SimpleListTests.permute(vals);

    // Put the values in a list
    DoublyLinkedList<Integer> lst = new DoublyLinkedList<Integer>();
    ListIterator<Integer> it = lst.listIterator();
    for (int i = 0; i < size; i++)
      it.add(vals[i]);

    // Find the median value
    expt(pen, lst, size/2);

    // And again
    expt(pen, lst, size/2);

    // Find the smallest value
    expt(pen, lst, 0);

    // Find the largest value
    expt(pen, lst, size-1);

    // Find some things near the median
    expt(pen, lst, size/2-1);
    expt(pen, lst, size/2+1);
  } // expt(PrintWriter, size)

  // +------+------------------------------------------------------------
  // | Main |
  // +------+

  public static void main(String[] args)
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    // A few general experiments.
    expt(pen, 20);
    expt(pen, 23);
    // An experiment that broke Sam's code.
    expt(pen, new Integer[] { 2, 3, 1, 4, 5 }, 2);
    pen.close();
  } // main(String[]
} // class KthSmallestExpt
