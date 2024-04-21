package gen.io.grpc.auth;

import gen.io.grpc.auth.AuthOuterClass;
import gen.io.grpc.auth.auth.UserAuthenticationService;
import io.grpc.stub.StreamObserver;

public class AuthImpl extends AuthGrpc.AuthImplBase {  // класс наследуется(реализует) методы сгенерированного прото файлом джава класса AuthGrpc.AuthImplBase

//    @Override  // переопределяем метод authorize, вызываемый когда клиент отправляет запрос на авторизацию
//    public void authorize(AuthOuterClass.AuthRequest request, StreamObserver<AuthOuterClass.AuthResult> responseObserver) {
//
//        String token = "1111";  // типо сгенерировали токен в виде строки, он будет возвращен клиенту в ответ на запрос
//        AuthOuterClass.AuthResult result =  // конструируем объект AuthResult для формирования ответа клиенту
//                AuthOuterClass.AuthResult.newBuilder().  // строитель для создания объекта AuthResult
//                setToken(AuthOuterClass.Token.newBuilder().setToken(token).build()).  // установка токена в объект
//                build();  // собственно построение обьекта AuthResult
//        responseObserver.onNext(result);  // отправка объекта AuthResult (содержащего токен) клиенту
//        responseObserver.onCompleted();   // завершаем стрим (сообщаем клиенту о том, что ответ полностью отправлен)
//
//    }

    private final UserAuthenticationService userAuthenticationService;

    public AuthImpl(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @Override
    public void authorize(AuthOuterClass.AuthRequest request, StreamObserver<AuthOuterClass.AuthResult> responseObserver) {
        String login = request.getLogin();
        String password = request.getPassword();

        boolean userExists = userAuthenticationService.verifyUserExistence(login, password);

        AuthOuterClass.AuthResult result;
        if (userExists) {
            String token = generateToken(); // Generate an authentication token
            result = AuthOuterClass.AuthResult.newBuilder()
                    .setToken(AuthOuterClass.Token.newBuilder().setToken(token).build())
                    .build();
        } else {
            String errorMessage = "Invalid credentials"; // Generate an error message
//            AuthOuterClass.AuthFailError authFailError = AuthOuterClass.AuthFailError.newBuilder()
//                    .setErrorMessage(errorMessage)
//                    .build();
//            result = AuthOuterClass.AuthResult.newBuilder()
//                    .setError(authFailError)
//                    .build();
        }


        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    private String generateToken() {
    // Define secret key for signing the token (replace with your actual secret key)
    String secretKey = "YOUR_SECRET_KEY";

    // Set expiration time (in seconds) for the token
    long expirationTimeInSeconds = 300; // 5 minutes

    // Create a JWT builder instance
    JwtBuilder jwtBuilder = Jwts.builder()
            .setSubject("authenticatedUser") // Set the subject of the token
            .setIssuedAt(new Date()) // Set the issued at timestamp
            .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInSeconds * 1000)) // Set the expiration timestamp
            .signWith(SignatureAlgorithm.HS256, secretKey); // Sign the token using HS256 algorithm

    // Generate the compact JWT string
    String jwtToken = jwtBuilder.compact();

    // Return the generated token
    return jwtToken;
}
}