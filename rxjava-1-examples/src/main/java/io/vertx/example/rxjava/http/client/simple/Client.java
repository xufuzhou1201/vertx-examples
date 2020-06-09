package io.vertx.example.rxjava.http.client.simple;

import io.vertx.example.util.Runner;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.http.HttpClient;
import rx.Single;

/*
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class Client extends AbstractVerticle {

  // Convenience method so you can run it in your IDE
  public static void main(String[] args) {
    Runner.runExample(Client.class);
  }

  @Override
  public void start() throws Exception {
    HttpClient client = vertx.createHttpClient();
    client.rxGet(8080, "localhost", "/").flatMap(resp -> {
      // Status code check and -> Single<Buffer>
      if (resp.statusCode() != 200) {
        return Single.error(new RuntimeException("Wrong status code " + resp.statusCode()));
      }
      return resp.rxBody();
    }).subscribe(data -> System.out.println("Server content " + data.toString("UTF-8")), Throwable::printStackTrace);
  }
}
