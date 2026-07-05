package sba301.fe.controller;

// ponytail: entry-point KHÔNG extends Application để chạy được qua `java -jar` (fat jar).
// Nếu main class trực tiếp là Application, JavaFX báo "runtime components are missing".
public class Launcher {
    public static void main(String[] args) {
        MainApplication.main(args);
    }
}
