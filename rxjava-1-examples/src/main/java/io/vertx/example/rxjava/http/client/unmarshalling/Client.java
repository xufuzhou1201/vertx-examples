package io.vertx.example.rxjava.http.client.unmarshalling;

import io.vertx.example.util.Runner;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.http.HttpClient;
import io.vertx.rxjava.core.http.HttpClientResponse;

/*
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class Client extends AbstractVerticle {

  // Convenience method so you can run it in your IDE
  public static void main(String[] args) {
    Runner.runExample(Client.class);
  }

  // Unmarshalled response from server
  static class Data {

    public String message;

  }

  @Override
  public void start() throws Exception {
    HttpClient client = vertx.createHttpClient();

    client.rxGet(8080, "localhost", "/")

      .flatMapObservable(HttpClientResponse::toObservable)

      // Unmarshall the response to the Data object via Jackon
      .lift(io.vertx.rxjava.core.RxHelper.unmarshaller(Data.class))

      .subscribe(data -> System.out.println("Got response " + data.message), Throwable::printStackTrace);
  }
}
