package tileengine.quests;

import tileengine.GameHandler;
import tileengine.items.*;

public class Introduction extends Quest{
    @Override
    public void finish(){        
        GameHandler.inventory.list.add(new Kissa());
        finished = true;
    }
}
