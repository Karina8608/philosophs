import java.util.concurrent.locks.Lock;

public class DiningPhilosophers {

    // Класс философа
    static class Philosopher implements Runnable {
        private final int id;
        private final Lock leftFork;
        private final Lock rightFork;

        public Philosopher(int id, Lock leftFork, Lock rightFork) {
            this.id = id;
            this.leftFork = leftFork;
            this.rightFork = rightFork;
        }

        private void think() throws InterruptedException {
            System.out.println("Философ " + id + " размышляет.");
            Thread.sleep(((int) (Math.random() * 100)));
        }

        private void eat() throws InterruptedException {
            System.out.println("Философ " + id + " ест.");
            Thread.sleep(((int) (Math.random() * 100)));
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 3; i++) {  // Каждый философ должен поесть 3 раза
                    think();

                    // Блокируем вилки
                    leftFork.lock();
                    rightFork.lock();

                    try {
                        eat();
                    } finally {
                        // Освобождаем вилки
                        rightFork.unlock();
                        leftFork.unlock();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Философ " + id + " был прерван.");
            }
        }
    }
}