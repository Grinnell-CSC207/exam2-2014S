import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

import org.junit.Test;

/**
 * Test for sorted skip lists.
 */
public class SortedSkipListTest
{
  // +---------------+---------------------------------------------------
  // | Static Fields |
  // +---------------+

  /**
   * A factory for skip lists of strings.
   */
  static SortedListFactory<String> stringSkipListFactory = 
      new SortedListFactory<String>()
        {
          public SortedList<String> build(Comparator<? super String> order)
          {
            return new SortedSkipList<String>(order);
          } // build(Comparator)
        }; // new SortedListFactory<String>

  /**
   * A factory for skip lists of integers.
   */
  static SortedListFactory<Integer> integerSkipListFactory =
      new SortedListFactory<Integer>()
        {
          public SortedList<Integer> build(Comparator<? super Integer> order)
          {
            return new SortedSkipList<Integer>(order);
          } // build(Comparator)
        }; // new SortedListFacotry<Integer>

  // +-------+-----------------------------------------------------------
  // | Tests |
  // +-------+

  @Test
  public void permutationTestS()
  {
    SortedListTests.permutationTestS(stringSkipListFactory);
  } // permutationTestS()

  @Test
  public void permutationTestI()
  {  
    SortedListTests.permutationTestI(integerSkipListFactory);
  } // permtuationTestI()


  @Test
  public void removeTestS1()
  {
    SortedListTests.removeTestS1(stringSkipListFactory);
  } // removeTestS1()

  @Test
  public void removeTestI1()
  {
    SortedListTests.removeTestI1(integerSkipListFactory);
  } // removeTestI1()

} // class SortedSkipListTest
