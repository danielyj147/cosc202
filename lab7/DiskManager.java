import java.util.ArrayList;

public abstract class DiskManager {

    private ArrayList<Disk> disks;
    private int capacity;

    public DiskManager(int capacity) {
        this.capacity = capacity;
        this.disks = new ArrayList<>();
    }

    public Disk open() {
        Disk newDisk = new Disk(capacity);
        disks.add(newDisk);
        return newDisk;
    }

    public int count() {
        return disks.size();
    }

    public int getCapacity() {
        return capacity;
    }

    public String toString() {
        return "DiskManager(disks={\n" + disks + "\n})";
    }

    public abstract void assignFile(int fileID, int size);
}