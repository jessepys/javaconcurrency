package com.eyesee.designpattern.activeobject;

import org.junit.Test;

import java.util.LinkedList;

public class ActiveObjectEngine {
    private LinkedList<Command> list = new LinkedList<>();

    public void addCommand(Command command) {
        list.add(command);
    }

    public void run() throws Exception {
        while (!list.isEmpty()) {
            Command command = list.getFirst();
            list.removeFirst();
            command.execute();
        }
    }


    public interface Command {
        public void execute() throws Exception;
    }

    @Test
    public void testExecute() throws Exception {
        final boolean[] commandExecuted = {false};
        Command command = new Command() {
            @Override
            public void execute() throws Exception {
                commandExecuted[0] = true;
            }
        };

        ActiveObjectEngine activeObjectEngine = new ActiveObjectEngine();
        activeObjectEngine.addCommand(new SleepCommand(1000l, activeObjectEngine, command));
        long currentTime = System.currentTimeMillis();
        activeObjectEngine.run();
        long endTime = System.currentTimeMillis();
        long sleepTime = endTime - currentTime;
        System.out.println(sleepTime);
    }

    public class SleepCommand implements Command {
        private Command wakeupCommand;
        private ActiveObjectEngine activeObjectEngine;
        private long sleepTime;
        private long startTime;
        private boolean started = false;

        public SleepCommand(long milliSeconds, ActiveObjectEngine activeObjectEngine,
                            Command wakeupCommand) {
            this.sleepTime = milliSeconds;
            this.activeObjectEngine = activeObjectEngine;
            this.wakeupCommand = wakeupCommand;
        }

        @Override
        public void execute() throws Exception {
            long currentTime = System.currentTimeMillis();

            if (!started) {
                started = true;
                startTime = currentTime;
                activeObjectEngine.addCommand(this);
            } else if ((currentTime - startTime) < sleepTime ){
                activeObjectEngine.addCommand(this);
            } else {
                activeObjectEngine.addCommand(wakeupCommand);
            }
        }
    }
}


