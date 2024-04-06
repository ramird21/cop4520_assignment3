import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Designing the Concurrent Linked List:
class Present
{
    int tag;
    Present next;

    public Present(int tag)
    {
        this.tag = tag;
        this.next = null;
    }
}

// The actual Linked List:
class ConcurrentLinkedList
{
    private final Lock[] locks;
    private final Present[] heads;

    public ConcurrentLinkedList(int numServants)
    {
        this.locks = new ReentrantLock[numServants];
        for (int i = 0; i < numServants; i++)
            locks[i] = new ReentrantLock();

        this.heads = new Present[numServants];
    }

    // Adding a present with a given tag
    public void addPresent(int servantId, int tag)
    {
        Present newPresent = new Present(tag);
        locks[servantId].lock();

        try {
            if (heads[servantId] == null || heads[servantId].tag > tag)
            {
                newPresent.next = heads[servantId];
                heads[servantId] = newPresent;
            }
            else
            {
                Present current = heads[servantId];
                while (current.next != null && current.next.tag < tag)
                {
                    current = current.next;
                }
                newPresent.next = current.next;
                current.next = newPresent;
            }
        } finally {
            locks[servantId].unlock();
        }
    }

    // Remove present
    public void removePresent(int servantId, int tag)
    {
        locks[servantId].lock();
        try {
        Present current = heads[servantId];
        Present prev = null;

        while (current != null && current.tag != tag)
        {
            prev = current;
            current = current.next;
        }
        if (current != null)
        {
            if (prev == null)
                heads[servantId] = current.next;
            else
                prev.next = current.next;
        }
    } finally {
        locks[servantId].unlock();
    }
}

public boolean containsPresent(int servantId, int tag)
{
    locks[servantId].lock();
    try {
        Present current = heads[servantId];
        while (current != null)
        {
            if (current.tag == tag)
                return true;

            current = current.next;
        }
        return false;
    } finally {
        locks[servantId].unlock();
    }
}

public class BirthdayPresentsParty
{
    public static void main(String [] args)
    {
        System.out.println("hi, hi, hi");
    }
}
}