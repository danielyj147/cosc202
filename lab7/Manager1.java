public class Manager1 extends DiskManager {

    private Disk current;

    public Manager1(int capacity) {
        super(capacity);
        current = open(); // Open the first disk to start
    }

    @Override
    public void assignFile(int fileID, int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("File size must be positive.");
        }

        if (size > getCapacity()) {
            throw new IllegalArgumentException("File size exceeds disk capacity.");
        }

        if (size <= current.getFree()) {
            current.close();
            current = open();
        }

        if (!current.assign(fileID, size)) {
            throw new IllegalStateException(String.format("Failed to assign file (ID=%d, size=%d)\nto disk %s", fileID, size, current.toString()));
        }
    }

    public static void main(String[] args) {
        Manager1 manager = new Manager1(100);
        manager.assignFile(1, 50);
        manager.assignFile(2, 30);
        manager.assignFile(3, 40);
        manager.assignFile(4, 10);
        System.out.println(manager);
    }
    
}
