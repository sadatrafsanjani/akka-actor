package actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import command.Command;
import command.NumberCommand;
import command.StartCommand;
import command.StopCommand;

public class GuardianActor extends AbstractBehavior<Command> {

    public static Behavior<Command> create() {
        return Behaviors.setup(GuardianActor::new);
    }

    private GuardianActor(ActorContext<Command> context) {
        super(context);
    }

    @Override
    public Receive<Command> createReceive() {

        return newReceiveBuilder()
                .onMessage(StartCommand.class, this::test)
                .onMessage(StopCommand.class, this::shutdown)
                .build();
    }

    private Behavior<Command> test(StartCommand command){

        for(int number : command.getIntegerList()){

            System.out.println("Spawning: " + number);
            ActorRef<Command> child = getContext().spawn(TransactionActor.create(), "actor-" + number);
            child.tell(new NumberCommand(number, getContext().getSelf(), command.getIndex()));
        }

        return this;
    }

    private Behavior<Command> shutdown(StopCommand command){

        return Behaviors.stopped();
    }
}
