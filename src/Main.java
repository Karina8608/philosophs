import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public static void main(String[] args) {
    final int PHILOSOPHER_COUNT = 5;
    DiningPhilosophers.Philosopher[] philosophers = new DiningPhilosophers.Philosopher[PHILOSOPHER_COUNT];
    Lock[] forks = new Lock[PHILOSOPHER_COUNT];

    // Инициализируем вилки (ReentrantLock для каждой вилки)
    for (int i = 0; i < PHILOSOPHER_COUNT; i++) {
        forks[i] = new ReentrantLock();
    }

    // Создаем философов и запускаем потоки
    for (int i = 0; i < PHILOSOPHER_COUNT; i++) {
        Lock leftFork = forks[i];
        Lock rightFork = forks[(i + 1) % PHILOSOPHER_COUNT];

        // Чтобы избежать взаимной блокировки, последний философ сначала берет правую вилку
        if (i == PHILOSOPHER_COUNT - 1) {
            philosophers[i] = new DiningPhilosophers.Philosopher(i, rightFork, leftFork);
        } else {
            philosophers[i] = new DiningPhilosophers.Philosopher(i, leftFork, rightFork);
        }

        new Thread(philosophers[i]).start();
    }
}
