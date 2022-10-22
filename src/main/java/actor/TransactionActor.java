package actor;

import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import command.Command;
import command.NumberCommand;
import command.StopCommand;

public class TransactionActor extends AbstractBehavior<Command> {

    private NumberCommand command;

    private TransactionActor(ActorContext<Command> context) {
        super(context);
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(TransactionActor::new);
    }

    @Override
    public Receive<Command> createReceive() {

        return newReceiveBuilder()
                .onMessage(NumberCommand.class, this::handleTransaction)
                .onSignal(PostStop.class, signal -> onPostStop())
                .build();
    }

    private Behavior<Command> handleTransaction(NumberCommand numberCommand){
        command = numberCommand;

        System.out.println("------------Processing: " + command.getNumber() + " ------------");

        try{
            Thread.sleep(5000);
        }
        catch (Exception e){}

        return Behaviors.stopped();
    }

    private Behavior<Command> onPostStop() {
        System.out.println("<<<Finished>>> " + command.getNumber());

        Singleton.trace[command.getIndex()]++;
        int index = Singleton.trace[command.getIndex()];

        if(index == 1000){
            System.out.println("======= Stopping =======" + command.getIndex() + " Processed Total: " + index);
            command.getParent().tell(new StopCommand());
        }else{
            System.out.println("Guardian: " + command.getIndex() + " Processed So far: " + index);
        }

        return this;
    }
}
