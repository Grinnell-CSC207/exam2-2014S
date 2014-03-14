import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Simple doubly-linked lists.
 *
 * Note that these lists follow the same iterator policy as Java's
 * LinkedLists.  In particular, "The iterators returned by this class's
 * iterator and listIterator methods are fail-fast: if the list is
 * structurally modified at any time after the iterator is created,
 * in any way except through the iterator's own remove or add methods,
 * the iterator will throw a ConcurrentModificationException. Thus, in
 * the face of concurrent modification, the iterator fails quickly and
 * cleanly, rather than risking arbitrary, non-deterministic behavior at
 * an undetermined time in the future."
 */
public class DoublyLinkedList<T>
    implements
      SimpleList<T>
{
  // +-------+-------------------------------------------------------------
  // | Notes |
  // +-------+

/*
  We keep a dummy node at the beginning of the list so that we always
  have at least one node, which makes it easier to do a number of
  operations.

  Iterators are always on the node immediately prior to the node we'll
  return with next.
 */

  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The dummy node at the front/end of the list.
   */
  Node dummy;

  /**
   * The number of modifications to the list.  Used to determine
   * whether a cursor is valid.
   */
  long mods;

  // +--------------+------------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create an empty list.
   */
  public DoublyLinkedList()
  {
    this.dummy = new Node(null, null, null);
    this.dummy.prev = dummy;
    this.dummy.next = dummy;
    this.mods = 0;
  } // DoublyLinkedList()

  // +-----------+---------------------------------------------------------
  // | Iterators |
  // +-----------+

  public Iterator<T> iterator()
  {
    return listIterator();
  } // iterator()

  public CloneableListIterator<T> listIterator()
  {
    return new SimpleListIterator();
  } // listIterator()

  // +---------------+-----------------------------------------------------
  // | Inner Classes |
  // +---------------+

  /**
   * Nodes in the linked list.
   */
  class Node
  {
    // +--------+-----------------------------------------------------------
    // | Fields |
    // +--------+

    /**
     * The data stored in the node.
     */
    T data;

    /**
     * The next node in the list.  Set to dummy at the end of the list.
     */
    Node next;

    /**
     * The previous node in the list.  Set to dummy at the front of the
     * list.
     */
    Node prev;

    // +--------------+-----------------------------------------------------
    // | Constructors |
    // +--------------+

    /**
     * Create a new node with specified data and next.
     */
    public Node(Node prev, T data, Node next)
    {
      this.prev = prev;
      this.data = data;
      this.next = next;
    } // Node(Node, T, Node)
  } // class Node

  public class SimpleListIterator
    implements CloneableListIterator<T>
  {
    // +--------+----------------------------------------------------------
    // | Fields |
    // +--------+

    /**
     * The node that will be returned by next.
     */
    Node cursor = DoublyLinkedList.this.dummy;

    /**
     * The node with the data last returned by next/prev.
     */
    Node recent = null;

    /**
     * The position in the list (because the folks an Sun/Oracle
     * decided that list iterators need to be able to return
     * integer indices).
     */
    int pos = 0;

    /**
     * The number of modifications at the time this iterator was
     * created or last updated.
     */
    long mods = DoublyLinkedList.this.mods;

    // +--------------+----------------------------------------------------
    // | Constructors |
    // +--------------+

    SimpleListIterator()
    {
      this.cursor = DoublyLinkedList.this.dummy;
      this.recent = null;
      this.pos = 0;
      this.mods = DoublyLinkedList.this.mods;
    } // SimpleListIterator()


    // +---------+---------------------------------------------------------
    // | Helpers |
    // +---------+

    /**
     * Determine if the list has been updated since this iterator
     * was created or modified.
     */
    void failFast()
    {
      if (this.mods != DoublyLinkedList.this.mods)
        throw new ConcurrentModificationException();
    } // failFast

    // +---------+---------------------------------------------------------
    // | Methods |
    // +---------+

    public void add(T val)
      throws UnsupportedOperationException
    {
      failFast();

      // We add immediately after this node.
      this.cursor.next = new Node(this.cursor, val, this.cursor.next);

      // Advance over this node
      this.cursor = this.cursor.next;
      ++this.pos;

      // Note that we've updated so that the other iterators
      // will know.
      DoublyLinkedList.this.mods++;
      this.mods = DoublyLinkedList.this.mods;
    } // add(T)

    public SimpleListIterator clone()
    {
      SimpleListIterator clone = new SimpleListIterator();
      clone.mods = this.mods;
      clone.cursor = this.cursor;
      clone.recent = this.recent;
      clone.pos = this.pos;
      return clone;
    } // clone()

    public boolean hasNext()
    {
      failFast();
      return this.cursor.next != DoublyLinkedList.this.dummy;
    } // hasNext()

    public boolean hasPrevious()
    {
      failFast();
      return this.cursor != DoublyLinkedList.this.dummy;
    } // hasPrevious()

    public T next()
      throws NoSuchElementException
    {
      failFast();
      if (!this.hasNext())
        throw new NoSuchElementException();
      // Advance to the next node.
      this.cursor = this.cursor.next;
      this.pos++;
      // The next value is in the current node.
      this.recent = this.cursor;
      return this.cursor.data;
    } // next()

    public int nextIndex()
    {
      failFast();
      return this.pos;
    } // nextIndex()

    public int previousIndex()
    {
      failFast();
      return this.pos - 1;
    } // prevIndex

    public T previous()
      throws NoSuchElementException
    {
      failFast();
      if (!this.hasPrevious())
        throw new NoSuchElementException();
      this.recent = this.cursor;
      this.cursor = this.cursor.prev;
      --this.pos;
      return this.recent.data;
    } // previous()

    public void remove()
      throws UnsupportedOperationException,
        IllegalStateException
    {
      failFast();
      if (this.recent == null)
        throw new IllegalStateException();

      // Move the cursor if it's on this node.  (That should happen
      // if we're iterating forward.)
      if (this.cursor == this.recent)
        {
          this.cursor = this.cursor.prev;
          this.pos--;
        } // if

      // Splice out the element
      this.recent.prev.next = this.recent.next;
      this.recent.next.prev = this.recent.prev;

      // Updating edges helps catch errors
      this.recent.prev = null;
      this.recent.next = null;

      // We no longer have a recent element
      this.recent = null;

      // Note that we've updated so that the other iterators will know.
      DoublyLinkedList.this.mods++;
      this.mods = DoublyLinkedList.this.mods;
    } // remove()

    public void set(T val)
      throws UnsupportedOperationException,
        ClassCastException,
        IllegalArgumentException,
        IllegalStateException
    {
      failFast();
      if (this.recent == null)
        throw new IllegalStateException();
      this.recent.data = val;
    } // set(T)
  } // SimpleListIterator
} // class DoublyLinkedList<T>

