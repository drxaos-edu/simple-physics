package examples;

import physics.MainWindow;

public class Two {
    public static void main(String[] args) {
        new MainWindow() {

            @Override
            public void initEntities() {
                giveBirth(30, 100, 400, 0, 10);
                giveBirth(400, 100, -300, 0, 20);
            }
        }.start();
    }
}
