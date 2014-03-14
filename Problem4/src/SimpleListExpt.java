import java.io.PrintWriter;

import java.util.ConcurrentModificationException;
import java.util.ListIterator;

/**
 * Some simple experiments with SimpleLists
 */
public class SimpleListExpt
{
  // +-----------+--------------------------------------------------------
  // | Utilities |
  // +-----------+

  /**
   * Add an element using an iterator.
   */
  static void add(PrintWriter pen, ListIterator<String> it, String val)
    throws Exception
  {
    pen.println("Add \"" + val + "\" at position " + it.nextIndex());
    it.add(val);
  } // add(PrintWriter)

  /**
   * Add a bunch of elements to a list at the position given by a 
   * list iterator.
   */
  static void add(PrintWriter pen, ListIterator<String> lit, String[] vals)
    throws Exception
  {
    for (String val : vals)
      add(pen, lit, val);
  } // add(PrintWriter, SimpleList<String>, String[])

  /**
   * Add a bunch of elements to the front of a list.
   */
  static void add(PrintWriter pen, SimpleList<String> lst, String[] vals)
    throws Exception
  {
    add(pen, lst.listIterator(), vals);
  } // add(PrintWriter, SimpleList<String>, String[])

  /**
   * Add a bunch of elements to the end of a list.
   */
  static void addToEnd(PrintWriter pen, SimpleList<String> lst, String[] vals)
    throws Exception
  {
    add(pen, toEnd(lst.listIterator()), vals);
  } // addToEnd(PrintWriter, SimpleList<String>, vals)

  /**
   * Print a list from back to front.  Intended primarily as an
   * exercise in moving backwards through the list.
   */
  static void printBackwards(PrintWriter pen, SimpleList<String> lst)
  {
    ListIterator<String> lit = lst.listIterator();
    toEnd(lit);
    // Print the elements from back to front
    while (lit.hasPrevious())
      {
        pen.print(lit.previousIndex());
        pen.print(": ");
        pen.println(lit.previous());
      } // while
  } // printBackwards

  /**
   * Print a list.
   */
  static void printList(PrintWriter pen, SimpleList<String> lst)
  {
    int i = 0;
    for (String val : lst)
      {
        pen.println(i++ + ": " + val);
      } // for
  } // printList(PrintWriter, SimpleList<String>)

  /**
   * Remove a value from an iterator.
   */
  static void remove(PrintWriter pen, ListIterator<String> lit)
  {
    pen.println("Removing element at position " + lit.previousIndex() +
        " (iterating forward) or " + lit.nextIndex() + 
        " (iterating backwards)" );
    lit.remove();
  } // remove(PrintWriter, ListIterator<String>)

  /**
   * Move an iterator to the end of the list.
   */
  static ListIterator<String> toEnd(ListIterator<String> lit)
  {
    while (lit.hasNext())
      lit.next();
    return lit;
  } // toEnd(ListIterator<String>

  // +-------------+------------------------------------------------------
  // | Experiments |
  // +-------------+

  /**
   * Add a variety of elements.
   */
  static void expt(PrintWriter pen, SimpleList<String> lst, String[] vals)
    throws Exception
  {
    pen.println("Current list: ");
    printList(pen, lst);
    pen.println();

    ListIterator<String> lit = lst.listIterator();

    for (int i = 0; i < vals.length; i++)
      {
        add(pen, lit, vals[i]);
        printList(pen, lst);
        pen.println();
      } // for
  } // expt(PrintWriter, SimpleList<String>, String[])

  /**
   * My default experiment.
   */
  static void expt(PrintWriter pen, SimpleList<String> lst)
    throws Exception
  {
    expt(pen, lst, new String[] { "alpha", "bravo", "charlie" });
    expt(pen, lst, new String[] { "aardvark", "anteater", "antelope" });
  } // expt(PrintWriter, SimpleList<String>)

  /**
   * An experiment in which we try to change a list and then iterate
   * it.
   */
  static void failFastExpt(PrintWriter pen, SimpleList<String> lst)
    throws Exception
  {
    ListIterator<String> lit1 = lst.listIterator();
    ListIterator<String> lit2 = lst.listIterator();
    add(pen, lit1, "addition");
    try
      {
        pen.println("Attempting additional add with alternate iterator.");
        add(pen, lit2, "additional");
        pen.println("Did not fail fast.");
      } // try
    catch (ConcurrentModificationException cme)
      {
        pen.println("Failed fast!");
      } // catch (ConcurrentModificationException)
  } // failFastExpt(PrintWriter, SimpleList<String>)

  /**
   * An experiment that exercises the previous method.
   */
  static void prevExpt(PrintWriter pen, SimpleList<String> lst)
    throws Exception
  {
    expt(pen, lst, new String[] { "alert", "asleep", "awake", "aware" });
    pen.println("Printing backwards.");
    printBackwards(pen, lst);
  } // prevExpt(PrintWriter, SimpleList<String>)

  /**
   * An experiment that exercises the set method, moving from back
   * to front.
   */
  static void setBackwardExpt(PrintWriter pen, SimpleList<String> lst)
  {
    ListIterator<String> lit = lst.listIterator();
    toEnd(lit);
    pen.println("lowercasing");
    while (lit.hasPrevious())
      {
        String str = lit.previous();
        lit.set(str.toLowerCase());
      } // while
    printList(pen, lst);
  } // setBackwardsExpt(PrintWriter, SimpleList<String>)

  /**
   * An experiment the exercises the set method, moving from front
   * to back.
   */
  static void setForwardExpt(PrintWriter pen, SimpleList<String> lst)
  {
    ListIterator<String> lit = lst.listIterator();
    pen.println("UPPERCASING");
    while (lit.hasNext())
      {
        String str = lit.next();
        lit.set(str.toUpperCase());
      } // while
    printList(pen, lst);
  } // setForwardExpt

  /**
   * Removing elements forward.
   */
  static void removeForwardExpt(PrintWriter pen, SimpleList<String> lst)
      throws Exception
  {
    add(pen, lst, new String[] 
        { "b", "1", "b", "2", "b", "b", "3", "b", "b", "b", "0", "1", "b"});
    pen.println("Starting with: ");
    printList(pen, lst);
    pen.println("Removing b's: ");
    ListIterator<String>lit = lst.listIterator();
    while (lit.hasNext())
      {
        if (lit.next().equals("b"))
          {
            remove(pen, lit);
          } // if
      } // while
    printList(pen, lst);
    pen.println();
  } // removeForwardExpt

  /**
   * Removing elements backward.
   */
  static void removeBackwardExpt(PrintWriter pen, SimpleList<String> lst)
      throws Exception
  {
    add(pen, lst, new String[] 
        { "b", "1", "b", "2", "b", "b", "3", "b", "b", "b", "0", "1", "b"});
    pen.println("Starting with: ");
    printList(pen, lst);
    pen.println("Removing b's: ");
    ListIterator<String>lit = lst.listIterator();
    toEnd(lit);
    while (lit.hasPrevious())
      {
        if (lit.previous().equals("b"))
          {
            remove(pen, lit);
          } // if
      } // while
    printList(pen, lst);
    pen.println();
  } // removeBackwardExpt(PrintWriter pen, SimpleList<String> lst)

} // class SimpleListExpt
