import java.io.PrintWriter;

import java.util.Comparator;

/**
 * A quick and dirty implementation of the legendary skip lists.
 */
public class SkipList<T>
{
  // +-------+-----------------------------------------------------------
  // | Notes |
  // +-------+

/*
  I make a lot of uses of arrays of nodes.  Unfortunately, array sof
  nodes are difficult to create because we're writing a generic type.
  Hence, I use arrays of objects, and cast where necessary.  For the
  collection of predecessors that we use for insert/remove, the
  ithNode method does the casting.  For nodes, the next method does
  the casting.

  There are two ways to handle duplicate elements.  We could say that
  an element only appears once, or we could allow the duplicates.  I've
  chosen the easier approach - this allows duplicates.  It would not be
  a lot of work to disallow duplicates.
 */

  // +-----------+-------------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default probability of increasing levels.
   */
  static double DEFAULT_PROBABILITY = 0.5;

  /**
   * The default height of the skip list.
   */
  static int DEFAULT_MAX_HEIGHT = 16;

  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The comparator that determines the ordering of elements.
   */
  Comparator<? super T> order;

  /**
   * The front of the list.
   */
  Node front;

  /**
   * The maximum height of any node.
   */
  int maxHeight;

  /**
   * The largest height generated so far.
   */
  int height;

  /**
   * The probability of making a bigger height
   */
  double p;

  /**
   * A pen for printing to stderr.
   */
  PrintWriter stderr = new PrintWriter(System.err, true);

  // +---------------+---------------------------------------------------
  // | Static Fields |
  // +---------------+

  /**
   * A count of the nodes we've created.  Used primarily for keeping
   * track of node id's.
   */
  static int nodes = 0;

  // +--------------+----------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new skip list with a specified maximum height and
   * given probability of increasing levels.
   *
   * @pre maxHeight >= 1
   * @pre 0 <= p < 1
   */
  public SkipList(Comparator<? super T> order, int maxHeight, double p)
  {
    this.order = order;
    this.maxHeight = maxHeight;
    this.p = p;
    this.front = new Node(maxHeight, null);
    this.height = 1;
  } // SkipList(Comparator, int, double)

  /**
   * Create a new skip list with the default maximum height and
   * given probability of increasing levels.
   *
   * @pre 0 <= p < 1
   */
  public SkipList(Comparator<? super T> order, double p)
  {
    this(order, DEFAULT_MAX_HEIGHT, p);
  } // SkipList(Comparator, double)

  /**
   * Create a new skip list with a specified maximum height and
   * default probability of increasing levels.
   *
   * @pre maxHeight >= 1
   */
  public SkipList(Comparator<? super T> order, int maxHeight)
  {
    this(order, maxHeight, DEFAULT_PROBABILITY);
  } // SkipList(Comparator, int)

  /**
   * Create a new skip list with the default maximum height and
   * default probability of increasing levels.
   */
  public SkipList(Comparator<? super T> order)
  {
    this(order, DEFAULT_MAX_HEIGHT, DEFAULT_PROBABILITY);
  } // SkipList(Comparator)

  // +---------+---------------------------------------------------------
  // | Helpers |
  // +---------+

  /**
   * Compute the height of the next node.
   */
  public int nextHeight()
  {
    int h = 1;
    while ((h < maxHeight) && (Math.random() < p))
      h++;
    if (h > height)
      height = h;
    return h;
  } // nextHeight()

  /**
   * Find all of the nodes that immediately precede the location of
   * val (whether or not val is actually there).
   *
   * @return pred
   *   an array of nodes (stored as an array of objects)
   * @post
   *   For all i, 0 <= i < height, 
   *     order.compare(pred[i].data, val) < 0 and
   *     [Either pred[i].next == null or 
   *      order.compare(pred[i].next.data, val) >= 0]
   */
  public Object[] predecessors(T val)
  {
    Object[] pred = new Object[this.maxHeight];
    pred[this.maxHeight-1] = this.front;
    for (int i = maxHeight-1; i >= 0; i--)
      {
        Node current = ithNode(pred, i);
        // Move forward at level i
        while ((current.next(i) != null) &&
               (order.compare(current.next(i).data, val) < 0))
          {
            current = current.next(i);
          } // while
        pred[i] = current;
        // Switch to the next level
        if (i > 0)
          {
            pred[i-1] = pred[i];
          } // if (i > 0)
      } // for
    return pred;
  } // predecessors(T)

  // +-----------------+-------------------------------------------------
  // | Primary Methods |
  // +-----------------+

  /**
   * Add an element to the skip list.
   */
  public void add(T val)
  {
    Object[] pred = predecessors(val);
    int h = nextHeight();
    Node newnode = new Node(h, val);

    // Note: To disallow duplicates, check if the next element 
    // of pred[0] contains val.

    for (int i = 0; i < h; i++)
      {
        Node predi = ithNode(pred, i);
        newnode.next[i] = predi.next(i);
        predi.next[i] = newnode;
      } // for
  } // add(T)

  /**
   * Determine if the list contains a particular value.  (We sometimes
   * use skip lists to implement the set interface.)
   */
  public boolean contains(T val)
  {
    // Pred the predecessors.
    Object[] pred = predecessors(val);

    // Get the next node at level 0, which is where the node that
    // contains val should be.
    Node temp = ithNode(pred,0).next(0);

    // Determine if it contains the value
    return ((temp != null) && (order.compare(temp.data, val) == 0));
  } // contains(T)

  /**
   * Remove one copy of a value from the skip list.
   */
  public void remove(T val)
  {
    // Find the predecessors.
    Object[] pred = predecessors(val);

    // Get the next node at level 0, which is where the node that
    // contains val should be.
    Node temp = ithNode(pred,0).next(0);

    // If the value is not in the list, we're done.
    if ((temp == null) || (order.compare(temp.data, val) != 0))
      return;

    // Fix any links that use this node.
    for (int i = 0; i < temp.next.length; i++)
      {
        Node predi = ithNode(pred, i);
        if (predi.next(i) == temp)
          predi.next[i] = temp.next[i];
      } // for
  } // remove(T)

  // +--------------------+----------------------------------------------
  // | Additional Methods |
  // +--------------------+

  /**
   * Print all the elements in the skip list, in verbose mode.
   */
  public void dump(PrintWriter pen)
  {
    Node here = this.front;
    while (here != null)
      {
        here.dump(pen);
        here = here.next(0);
      } // while
  } // dump(PrintWriter)

  /**
   * Get the ith element of an array of objects that represent nodes.
   * (Inserted to deal with the wonders of generics.)
   */
  @SuppressWarnings({"unchecked"})
  public Node ithNode(Object[] nodes, int i)
  {
    return (Node) nodes[i];
  } // ithNode(Object[], int)

  /**
   * Print all the elements in the skip list.
   */
  public void print(PrintWriter pen)
  {
    Node here = this.front;
    while (here != null)
      {
        pen.print(here.data);
        pen.print(" ");
        here = here.next(0);
      } // while
  } // print(PrintWriter)

  // +---------------+---------------------------------------------------
  // | Inner Classes |
  // +---------------+

  /**
   * Nodes in the skip list.
   */
  class Node
  {
    // +--------+------------------------------------------------------
    // | Fields |
    // +--------+
  
    /**
     * An integer identifier, used mostly for exploration and debugging.
     */
    int id;
  
    /**
     * The following elements.
     */
    Object[] next;
  
    /**
     * The data.
     */
    T data;
  
    // +--------------+------------------------------------------------
    // | Constructors |
    // +--------------+
  
    /**
     * Create a new node of the specified height, with all of the
     * next elements pointing to null..
     *
     * @pre height >= 1
     */
    public Node(int height, T data)
    {
      this.next = new Object[height];
      this.data = data;
      this.id = nodes++;
    } // Node(int, T)
  
    // +---------+-----------------------------------------------------
    // | Methods |
    // +---------+
  
    /**
     * Dump a the node (giving a verbose version)
     */
    public void dump(PrintWriter pen)
    {
      pen.format("%04d\t", this.id);
      pen.print(this.data + "\tLevel: " + this.next.length + "\tNext: ");
      for (int i = 0; i < this.next.length; i++)
        {
          if (this.next[i] == null)
            pen.print("null ");
          else
            pen.format("%04d ", this.next(i).id);
        } // for
      pen.println();
    } // dump(PrintWriter)
  
    /**
     * Get the next node at level i.
     */
    @SuppressWarnings({"unchecked"})
    public Node next(int i)
    {
      return (Node) this.next[i];
    } // next(int)
  } // class Node

} // class SkipList<T>

