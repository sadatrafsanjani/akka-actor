import akka.actor.typed.ActorRef;

public class NumberCommand implements Command {

    private int number;
    private ActorRef<Command> parent;
    private int index;

    public NumberCommand(int number, ActorRef<Command> parent, int index) {
        this.number = number;
        this.parent = parent;
        this.index = index;
    }

    public int getNumber() {
        return number;
    }

    public int getIndex() {
        return index;
    }

    public ActorRef<Command> getParent() {
        return parent;
    }
}
