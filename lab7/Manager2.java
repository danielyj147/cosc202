import java.util.TreeMap;
import java.util.ArrayList;

public class Manager2 extends DiskManager {

    private TreeMap<Integer, ArrayList<Disk>> t;

    public Manager2(int capacity) {
        super(capacity);
        t = new TreeMap<>();
        addDisk(open());
    }

    private void addDisk(Disk d) {
        int free = d.getFree();
        if (!t.containsKey(free)) {
            t.put(free, new ArrayList<>());
        }
        t.get(free).add(d);
    }

    private Disk removeDisk(int free) {
        ArrayList<Disk> list = t.get(free);
        Disk d = list.remove(list.size() - 1);
        if (list.isEmpty()) {
            t.remove(free);
        }
        return d;
    }

    @Override
    public void assignFile(int fileID, int size) {
        Integer key = t.ceilingKey(size);
        Disk target;

        if (key != null) {
            target = removeDisk(key);
        } else {
            target = open();
        }

        target.assign(fileID, size);

        if (target.getFree() > 0) {
            addDisk(target);
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
