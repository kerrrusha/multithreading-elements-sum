package core.basesyntax;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.RecursiveTask;

import static java.lang.Math.abs;

@RequiredArgsConstructor
public class MyTask extends RecursiveTask<Long> {

    private final int startPoint;
    private final int finishPoint;

    @Override
    protected Long compute() {
        setRawResult(0L);
        if (abs(finishPoint - startPoint) > 10) {
            int middle = (startPoint + finishPoint) / 2;

            MyTask subtaskA = new MyTask(startPoint, middle);
            MyTask subtaskB = new MyTask(middle, finishPoint);

            subtaskA.fork();
            subtaskB.fork();

            Long newResult = getRawResult() + subtaskA.join() + subtaskB.join();
            setRawResult(newResult);
        } else {
            for (int i = startPoint; i < finishPoint; i++) {
                Long newResult = getRawResult() + i;
                setRawResult(newResult);
            }
        }
        return getRawResult();
    }

}
