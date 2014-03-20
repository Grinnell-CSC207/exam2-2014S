import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

import org.junit.Test;

/**
 * A few quick tests of our wonderful kthSmallest method, using the
 * doubly linked list implementation of sorted lists.
 */
public class KthSmallestTests
{
  // +-----------+-------------------------------------------------------
  // | Constants |
  // +-----------+
  /**
   * The number of times we repeat a test.
   */
  static final int TEST_REPETITIONS = 3;

  /**
   * A factory for simple lists of strings.
   */
  static final SimpleListFactory<String> stringSLF =
      new SimpleListFactory<String>()
        {
          public SimpleList<String> build()
          {
            return new DoublyLinkedList<String>();
          } // build
        }; // new SimpleListFactory<String>

  static final SimpleListFactory<Integer> integerSLF =
      new SimpleListFactory<Integer>()
        {
          public SimpleList<Integer> build()
          {
            return new DoublyLinkedList<Integer>();
          } // build
        }; // new SimpleListFactory<Integer>

  // +-------+-----------------------------------------------------------
  // | Tests |
  // +-------+

  @Test
  public void kthStringTests()
  {
    for (int i = 0; i < TEST_REPETITIONS; i++)
      SimpleListTests.kthStringTests(stringSLF);
  } // kthStringTests()

  @Test
  public void kthIntegerTests()
  {
    for (int i = 0; i < TEST_REPETITIONS; i++)
      SimpleListTests.kthIntegerTests(integerSLF);
  } // kthIntegerTests()
} // class KthSmallestTests

