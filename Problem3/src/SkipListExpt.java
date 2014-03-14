import java.io.PrintWriter;

import java.util.Comparator;

/**
 * A quick experiment with SkipLists
 */
public class SkipListExpt
{
  public static void main(String[] args)
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    String[] values =
        new String[] { "ape", "zebra", "gibbon", "baboon", "yak", "turkey",
                      "chicken", "fox", "ant", "gibbon" };
    String[] remove = new String[] { "ant", // front
                                    "zebra", // back
                                    "pizza", // not in list
                                    "turkey", // middle
                                    "turkey", // not in list, but was in list
                                    "gibbon", // duplicated, first copy
                                    "gibbon" }; // duplicated, second copy

    SkipList<String> sl = new SkipList<String>(new Comparator<String>()
      {
        public int compare(String left, String right)
        {
          return left.compareTo(right);
        } // compare(String, String)
      }, 4);

    pen.println("Original list");
    sl.dump(pen);
    pen.println();

    // Practice adding
    for (String val : values)
      {
        sl.add(val);
        pen.println("After adding " + val);
        sl.dump(pen);
        pen.println();
      } // for

    // Practice removing
    for (String val : remove)
      {
        sl.remove(val);
        pen.println("After removing " + val);
        sl.dump(pen);
        pen.println();
      } // for

    // Check status
    pen.println("Final list");
    sl.dump(pen);
    pen.println();

    // Determine which elements the list still contains.
    for (String val : values)
      {
        if (sl.contains(val))
          pen.println("Contains " + val);
        else
          pen.println("Missing  " + val);
      } // for

    // Clean up
    pen.close();
  } // main(String[])
} // class SkipListExpt

