package examples;

import physics.MainWindow;

public class Five {
    public static void main(String[] args) {
        new MainWindow() {

            @Override
            public void initEntities() {
                giveBirth(150, 300, 0, -200, 30);
                giveBirth(20, 20, 200, 200, 10);
                giveBirth(300, 20, -200, 200, 20);
                giveBirth(30, 100, 400, 50, 10);
                giveBirth(400, 100, -400, 200, 10);
            }
        }.start();
    }
}
