import java.util.ArrayList;

public class Disk {
    static private int nextID = 0;
    private int ID;
    private int capacity;
    private int used;
    private ArrayList<Integer> files;
    private ArrayList<Integer> fileSizes;
    private boolean open;

    public Disk(int capacity) {
        this.ID = nextID++;
        this.capacity = capacity;
        this.used = 0;
        this.files = new ArrayList<>();
        this.fileSizes = new ArrayList<>();
        this.open = true;
    }

    public String toString() {
        return "Disk(ID=" + ID + ",\ncapacity=" + capacity + ",\nused=" + used + ",\nfree=" + getFree() + ",\nopen=" + open + ",\nfileIDs=" + files + ",\nfileSizes=" + fileSizes + "\n)";
    }

    public int getID() {
        return ID;
    }

    public boolean isOpen() {
        return open;
    }

    public void close() {
        this.open = false;
    }

    public boolean assign(int fileID, int size) {
        if (open && used + size <= capacity) {
            used += size;
            files.add(fileID);
            fileSizes.add(size);
            if (used == capacity) {
                close();
            }
            return true;
        }
        return false;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getUsed() {
        return used;
    }

    public int getFree() {
        return capacity - used;
    }

    public boolean equals(Object other) {
        if (other instanceof Disk) {
            Disk that = (Disk) other;
            return this.ID == that.ID;
        }
        return false;
    }

    public int hashCode() {
        return ID;
    }
}
