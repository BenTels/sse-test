package sse.test;

import io.micronaut.http.sse.Event;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import org.reactivestreams.Subscriber;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool;

@Singleton
public class MessageQueue extends Flowable<Event<String>> implements Runnable {

    private final List<Subscriber<? super Event<String>>> observers = Collections.synchronizedList(new ArrayList<>());
    private final BlockingQueue<LocalDateTime> queue = new ArrayBlockingQueue<>(1_000);

    public MessageQueue() {
        ForkJoinPool.commonPool().execute(this);
    }

    public void add(LocalDateTime time) {
        queue.add(time);
    }

    @Override
    protected void subscribeActual(Subscriber<? super Event<String>> s) {
        observers.add(s);
    }

    @Override
    public void run() {
        while (true) {
            try {
                final LocalDateTime ldt = queue.take();
                observers.stream().forEach(o -> o.onNext(Event.of(String.format("%s: ...PONG!!", ldt.toString()))));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
