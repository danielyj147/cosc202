import java.util.TreeSet;
import java.util.Comparator;

public class Manager2 extends DiskManager {

    // private Disk current;
    private DiskComparator c;
    private TreeSet<Disk> t;

    public Manager2(int capacity) {
        super(capacity);
        c = new DiskComparator();
        t = new TreeSet<>(c);
        t.add(open());
    }

    @Override
    public void assignFile(int fileID, int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("File size must be positive.");
        }

        if (size > getCapacity()) {
            throw new IllegalArgumentException("File size exceeds disk capacity.");
        }

        Disk ceiling = t.ceiling(new Disk(size)); // find least & enough space

        if (ceiling != null) {
            t.remove(ceiling);
        } else {
            ceiling = open();
        }
        
        if (!ceiling.assign(fileID, size)) {
            throw new IllegalStateException(String.format("Failed to assign file (ID=%d, size=%d)\nto disk %s", fileID, size, ceiling.toString()));
        }

        if (ceiling.getFree() > 0) {
            t.add(ceiling);
        }    
    }


    public class DiskComparator implements Comparator<Disk> {
        @Override
        public int compare(Disk firstDisk, Disk seconDisk) {
            int cmp  = Integer.compare(firstDisk.getFree(), seconDisk.getFree());
            if (cmp != 0) return cmp;
            return Integer.compare(firstDisk.getID(), seconDisk.getID()); // when equal, return smaller id first
        }

    }

    public static void main(String[] args) {
        Manager2 manager = new Manager2(100);
        manager.assignFile(1, 50);
        manager.assignFile(2, 30);
        manager.assignFile(3, 40);
        manager.assignFile(4, 10);
        System.out.println(manager);
    }
    
}