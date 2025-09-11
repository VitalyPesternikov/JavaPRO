package homework3;

public class MainTest {
    public static void main(String[] args) {
        CustomThreadPool customThreadPool = new CustomThreadPool(5);
        for (int i = 0; i < 15; i++) {
            int taskNumber = i + 1;
            customThreadPool.execute(() -> {
                System.out.println("Поток " + Thread.currentThread().getName() + " НАЧАЛ выполнять задачу " + taskNumber);
                try {
                    Thread.sleep((long) (Math.random() * 5000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Поток " + Thread.currentThread().getName() + " ЗАКОНЧИЛ выполнять задачу " + taskNumber);
            });
        }
        customThreadPool.shutdown();
    }
}
