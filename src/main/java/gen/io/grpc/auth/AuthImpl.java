package gen.io.grpc.auth;

//import gen.io.grpc.AuthGrpc;

import io.grpc.stub.StreamObserver;

public class AuthImpl extends AuthGrpc.AuthImplBase {  // класс наследуется(реализует) методы сгенерированного прото файлом джава класса AuthGrpc.AuthImplBase

    @Override  // переопределяем метод authorize, вызываемый когда клиент отправляет запрос на авторизацию
    public void authorize(AuthOuterClass.AuthRequest request, StreamObserver<AuthOuterClass.AuthResult> responseObserver) {

        String token = "1111";  // типо сгенерировали токен в виде строки, он будет возвращен клиенту в ответ на запрос
        AuthOuterClass.AuthResult result =  // конструируем объект AuthResult для формирования ответа клиенту
                AuthOuterClass.AuthResult.newBuilder().  // строитель для создания объекта AuthResult
                setToken(AuthOuterClass.Token.newBuilder().setToken(token).build()).  // установка токена в объект
                build();  // собственно построение обьекта AuthResult
        responseObserver.onNext(result);  // отправка объекта AuthResult (содержащего токен) клиенту
        responseObserver.onCompleted();   // завершаем стрим (сообщаем клиенту о том, что ответ полностью отправлен)

    }
}
