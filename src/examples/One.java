package examples;

import physics.MainWindow;

public class One {
    public static void main(String[] args) {
        new MainWindow() {

            @Override
            public void initEntities() {
                giveBirth(30, 100, 400, -300, 10);
            }
        }.start();
    }
}
