import actor.GuardianActor;
import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import command.Command;
import command.StartCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args){

        List<Integer> numberX = IntStream.rangeClosed(1, 1000)
                .boxed().collect(Collectors.toList());

        List<Integer> numberY = IntStream.rangeClosed(1, 1000)
                .boxed().collect(Collectors.toList());

        List<Integer> numberZ = IntStream.rangeClosed(1, 1000)
                .boxed().collect(Collectors.toList());

        List<List<Integer>> lists = new ArrayList<>();
        lists.add(numberX);
        lists.add(numberY);
        lists.add(numberZ);

        for(int i=0; i<3; i++){

            ActorRef<Command> guardian = ActorSystem.create(GuardianActor.create(), "Guardian-" + UUID.randomUUID());
            guardian.tell(new StartCommand(lists.get(0), i));
        }
    }
}
