package gen.io.grpc.auth;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50051).  // реалован сервер с паттерном Builder
                addService(new AuthImpl()).
                build();

        server.start();

        System.out.println("Server started");
        server.awaitTermination();  // метод будет работать пока работает сервер
    }
}
