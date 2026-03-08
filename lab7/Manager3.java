import java.util.Comparator;
import java.util.PriorityQueue;

public class Manager3 extends DiskManager {

    private DiskComparator c;
    private PriorityQueue<Disk> q;

    public Manager3(int capacity) {
        super(capacity);
        c = new DiskComparator();
        q = new PriorityQueue<>(c);
        q.add(open());
    }

    @Override
    public void assignFile(int fileID, int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("File size must be positive.");
        }

        if (size > getCapacity()) {
            throw new IllegalArgumentException("File size exceeds disk capacity.");
        }

        Disk curr = q.peek();

        
        if (curr != null && curr.getFree() >= size) {
            curr = q.poll();
        } else {
            curr = open();
        }

        if (!curr.assign(fileID, size)) {
            throw new IllegalStateException(String.format("Failed to assign file (ID=%d, size=%d)\nto disk %s", fileID, size, curr.toString()));
        }

        if (curr.getFree() > 0) q.add(curr); // add back if free
    }
    public class DiskComparator implements Comparator<Disk> {
        @Override
        public int compare(Disk firstDisk, Disk seconDisk) {
            int cmp  = Integer.compare(seconDisk.getFree(), firstDisk.getFree());
            if (cmp != 0) return cmp;
            return Integer.compare(firstDisk.getID(), seconDisk.getID()); // when equal, return smaller id first
        }

    }

    public static void main(String[] args) {
        Manager3 manager = new Manager3(100);
        manager.assignFile(1, 50);
        manager.assignFile(2, 30);
        manager.assignFile(3, 40);
        manager.assignFile(4, 10);
        System.out.println(manager);
    }
    
}
