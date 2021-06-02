package sse.test;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;

@Controller
public class PingResource {

    private MessageQueue queue;

    @Inject
    public PingResource(MessageQueue queue) {
        this.queue = queue;
    }

    @Post
    public void ping() {
        queue.add(LocalDateTime.now());
    }
}
