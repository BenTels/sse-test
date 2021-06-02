package sse.test;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.sse.Event;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;

@Controller("notices")
public class PongController {

    private MessageQueue queue;

    @Inject
    public PongController(MessageQueue queue) {
        this.queue = queue;
    }

    @ExecuteOn(TaskExecutors.IO)
    @Get(produces = MediaType.TEXT_EVENT_STREAM)
    public Publisher<Event<String>> index() {
        return queue.subscribeOn(Schedulers.computation());
    }
}
