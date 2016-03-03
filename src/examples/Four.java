package examples;

import physics.MainWindow;

public class Four {
    public static void main(String[] args) {
        new MainWindow() {

            @Override
            public void initEntities() {
                giveBirth(30, 100, 400, 0, 10);
                giveBirth(400, 100, -100, 0, 20);
                giveBirth(200, 50, 100, 100, 20);
                giveBirth(200, 150, -100, -100, 20);
            }
        }.start();
    }
}
