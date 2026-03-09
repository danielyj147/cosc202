public class Manager4 extends DiskManager {

    private DiskTree t;

    public Manager4(int capacity) {
        super(capacity);
        t = new DiskTree(); // tree by id(= age)
        t.put(open());
    }

    @Override
    public void assignFile(int fileID, int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("File size must be positive.");
        }

        if (size > getCapacity()) {
            throw new IllegalArgumentException("File size exceeds disk capacity.");
        }

        Disk oldest = t.getOldest(size);

        if (oldest != null){
            t.delete(oldest.getID());
            oldest.assign(fileID, size);
            if(oldest.getFree() > 0){
                t.put(oldest);
            }
        } else {
            Disk newDisk = open();
            newDisk.assign(fileID, size);
            if(newDisk.getFree() > 0){
                t.put(newDisk);
            }        
        }
    }

    public static void main(String[] args) {
        Manager4 manager = new Manager4(100);
        manager.assignFile(1, 50);
        manager.assignFile(2, 30);
        manager.assignFile(3, 40);
        manager.assignFile(4, 10);
        manager.assignFile(5, 10);
        System.out.println(manager);
    }
    
}
