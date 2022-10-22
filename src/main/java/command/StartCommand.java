package command;

import java.util.List;

public class StartCommand implements Command {

    private List<Integer> integerList;
    private int index;

    public StartCommand(List<Integer> integerList, int index) {
        this.integerList = integerList;
        this.index = index;
    }

    public List<Integer> getIntegerList() {
        return integerList;
    }

    public int getIndex() {
        return index;
    }
}
