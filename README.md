# QueueBasedChat

### Important
1. The client needs to login to the application before, so the Principal object on the server-side is initialized.
1. After principal setup the ` stompClient.subscribe('/user/queue/private', function (greeting) {` the principal name will be automatically appended
1. Here I have used List instead of database or Redis to store the online clients list

> Subscriber mapping acts like message mapping be directly send data without going through the messagebroker (RabbitMQ)

```java
@SubscribeMapping("/participants")
    public List<String> retrieveParticipants() {
        return container.getAll();
    }
```
> The subsciber is easily subscribe using the code below

```javascript
    stompClient.subscribe('/user/queue/private', function (greeting) {
        alert(greeting.body)
    });
```

> Sending the message is also easy as it looks.

```java
@MessageMapping("/private/message/{username}")
    public void filterPrivateMessage(@Payload String message, @DestinationVariable("username") String username, Principal principal) {
        simpMessagingTemplate.convertAndSend("/user/" + username + "/queue/private", message);
    }
```

> clientside javascript triggers the message mapping by code given below.

```javascript
    stompClient.send(`/app/private/message/${user}`, {}, msg);
```

> Receiving the message simply needs a subscription to the given queue 

```javascript
    stompClient.subscribe('/user/queue/private', function (greeting) {
        alert(greeting.body)
    });
```

> After connected is detected the UserPresenceListener adds the user to the List

```java
public class UserPresenceListener {
    
    private ClientContainer container;

    public UserPresenceListener(ClientContainer container) {
        this.container = container;
    }
      
    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {
        String user = event.getUser().getName();
        
        container.add(user);
    }
    
    @EventListener
    private void handleSessionDisconnected(SessionDisconnectEvent event) {
        String user = event.getUser().getName();
        container.remove(user);
    }
}
```
> The container or list is loaded on startup with the configuration

```java
@Configuration
public class ChatConfig {

    @Bean
    public ClientContainer getContainer() {
        return new ClientContainer();
    }

    @Bean
    public UserPresenceListener presenceEventListener() {
        UserPresenceListener listener = new UserPresenceListener(getContainer());
        return listener;
    }
}

public class ClientContainer {

    List<String> users = new ArrayList<>();

    public void add(String user) {
        users.add(user);
    }

    public void remove(String user) {
        users.remove(user);
    }

    public List<String> getAll() {
        return users;
    }
}
```
>Instead of logging in i have use a simple name generator and used it in principal
```java

@Override
public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws").setHandshakeHandler(new RandomUsernameHandshakeHandler()).withSockJS();
}

class RandomUsernameHandshakeHandler extends DefaultHandshakeHandler {

    final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    SecureRandom rnd = new SecureRandom();

    public String generateString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        return new UsernamePasswordAuthenticationToken(generateString(4), null);
    }

}
```

