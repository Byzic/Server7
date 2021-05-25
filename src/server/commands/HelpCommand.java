package server.commands;


import common.User;
import common.data.Flat;
import exceptions.IncorrectValueException;
import server.utility.ResponseCreator;

/**
 * Command 'help'.
 */
public class HelpCommand extends AbstractCommand {

    public HelpCommand() {
        super("help", "вывести справку по доступным командам");
    }
    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Flat flat, User user) {
        System.out.println("Начало выполнения команды help");
        long start= System.nanoTime();
        long time=0;

        while (time<10){
            long finish= System.nanoTime();
            time=(finish-start)/ 1000000000;
        }
        try {

         if (!argument.isEmpty())throw new IncorrectValueException();
         System.out.println("Команда help выполнилась");
            return true;
        }
         catch (IncorrectValueException  e) {
            ResponseCreator.error("У этой команды нет параметров! Необходимо ввести: help");
        }
        return false;
    }
}

