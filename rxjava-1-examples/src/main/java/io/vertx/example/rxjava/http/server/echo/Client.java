package io.vertx.example.rxjava.http.server.echo;

import io.vertx.example.util.Runner;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.MultiMap;
import io.vertx.rxjava.core.buffer.Buffer;
import io.vertx.rxjava.core.http.HttpClient;
import rx.Observable;

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

    Observable<Buffer> payload = Observable.just(Buffer.buffer("hello", "UTF-8"));

    MultiMap headers = MultiMap.caseInsensitiveMultiMap().add("Content-Type", "text/plain");

    client.rxPut(8080, "localhost", "/", headers, payload)
      .flatMap(resp -> {
        System.out.println("Got response " + resp.statusCode());
        return resp.rxBody();
      })
      .subscribe(buf -> System.out.println(buf.toString("UTF-8")), Throwable::printStackTrace);
  }
}
