import java.io.PrintWriter;

/**
 * Some simple experiments with SimpleLinkedLists.
 */
public class DLLExpt
{
  public static void main(String[] args)
    throws Exception
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    SimpleListExpt.expt(pen, new DoublyLinkedList<String>());
    SimpleListExpt.prevExpt(pen, new DoublyLinkedList<String>());
    SimpleListExpt.removeForwardExpt(pen, new DoublyLinkedList<String>());
    SimpleListExpt.removeBackwardExpt(pen, new DoublyLinkedList<String>());
  } // main(String[]
} // DLLExpt
