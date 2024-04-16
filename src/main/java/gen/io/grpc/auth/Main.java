package gen.io.grpc.auth;

import gen.io.grpc.auth.auth.UserAuthenticationService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Main {

    public static void main( String[] args ) throws IOException, InterruptedException {
        //  Configure Hibernate
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        //  Create UserAuthenticationService
        UserAuthenticationService authService = new UserAuthenticationService(sessionFactory);

        //  Start gRPC server
        Server server = ServerBuilder.forPort(50051).  // реалован сервер с паттерном Builder
                addService(new AuthImpl()).
                build();
        server.start();
        System.out.println("Server started, listening on port:" + server.getPort());
        server.awaitTermination();  // метод будет работать пока работает сервер
    }
}
