package server.commands;


import common.data.Flat;
import exceptions.IncorrectValueException;
import server.utility.CollectionManager;
import server.utility.ResponseCreator;

import java.time.LocalDateTime;

/**
 * Команда "info". Выводит информацию о коллекции
 */
public class InfoCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    public InfoCommand(CollectionManager cm){
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.collectionManager=cm;
    }
    /**
     * Выполнение команды
     * @param argument аргумент
     * @return состояние выполнения команды
     */


    @Override
    public boolean execute(String argument, Flat flat) {
        try {
            if (!argument.isEmpty())throw new IncorrectValueException();
            ResponseCreator.appendln("\u001B[37m"+"\u001B[33m"+"Информация о коллекции"+"\u001B[33m"+"\u001B[37m");
            LocalDateTime lastInitTime = collectionManager.getLastInitTime();
            String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
                    lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();
            ResponseCreator.appendln(" Тип: " + collectionManager.collectionType());
            ResponseCreator.appendln(" Дата последней инициализации: " + lastInitTimeString);
            ResponseCreator.appendln(" Количество элементов: " + collectionManager.collectionSize());
            return true;


        }
        catch (
                IncorrectValueException e) {
            ResponseCreator.error("У этой команды нет параметров! Необходимо ввести: info" );
        }
        return false;

    }
}
