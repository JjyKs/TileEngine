package tileengine.quests;

public class Quest {

    boolean startable;
    boolean started;
    boolean finished;
    int state;

    public Quest() {
        startable = true;
        started = false;
        finished = false;
        state = 0;
    }

    public void setStartable(){
        startable = true;
    }
    
    public boolean isStartable(){
        return startable;
    }
    
    public void finish(){
        finished = true;
    }
    public boolean isFinished(){
        return finished;
    }
    
    
    public void start() {
        if (startable) {
            started = true;
            state++;
        }
    }
    
    public boolean isStarted(){
        return started;
    }

}
