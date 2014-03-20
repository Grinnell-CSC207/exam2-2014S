/**
 * Helpers for working with predicates.
 */
public class Pred
{
  // +-----------+-------------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * A method that accepts any parameter.
   */
  public static Predicate<Object> ACCEPT = new Predicate<Object>()
         {
           public boolean test(Object val)
           {
             return true;
           }
         }; // new Predicate<Object>()

  /**
   * A method that rejects any parameter.
   */
  public static Predicate<Object> REJECT = new Predicate<Object>()
         {
           public boolean test(Object val)
           {
             return false;
           } // test(Object)
         }; // new Predicate<Object>

  // +----------------+--------------------------------------------------
  // | Static Methods |
  // +----------------+

  /**
   * Create a new predicate that holds when both of two predicates hold.
   *
   * @return
   *   pred a predicate
   * @post
   *   pred.test(val) holds if and only if left.test(val) holds
   *   and right.test(val) holds.
   */
  public static <T> Predicate<T> and(final Predicate<? super T> left, 
      final Predicate<? super T> right)
  {
    // STUB
    return munge(ACCEPT);
  } // and(Predicate<T>, Predicate<T>)

  /**
   * Create a new predicate that holds when another predicate does not
   * hold, and vice versa.
   *
   * @return
   *   pred a predicate
   * @post
   *   pred.test(val) holds if and only if derp.test(val) does not hold.
   */
  public static <T> Predicate<T> not(final Predicate<T> derp)
  {
    // STUB
    return munge(ACCEPT);
  } // not(Predicate<T>)

  /**
   * Create a new predicate that holds when either or both of two
   * predicates hold.
   *
   * @return
   *   pred a predicate
   * @post
   *   pred.test(val) holds if and only if left.test(val) holds or
   *   right.test(val) holds.
   */
  public static <T> Predicate<T> or(final Predicate<? super T> left, 
          final Predicate<? super T> right)
  {
    // STUB
    return munge(ACCEPT);
  } // or(Predicate<T>, Predicate<T>)

  /**
   * Convert a predicate that works on a superclass to a predicate
   * that works on a subclass.
   */
  public static <T> Predicate<T> munge(final Predicate<? super T> pred)
  {
    return new Predicate<T>() 
        {
          public boolean test(T val)
          {
            return pred.test(val);
          } // test(T)
        }; // new Predicate<T>
  } // munge(Predicate)
} // class Pred
