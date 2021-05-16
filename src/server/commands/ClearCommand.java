package server.commands;

import common.data.Flat;
import exceptions.IncorrectValueException;
import server.utility.CollectionManager;
import server.utility.ResponseCreator;

/**
 * команда "clear". Удаляет все элементы коллекции
 */
public class ClearCommand extends AbstractCommand{
    private CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager){
        super("clear","очистить коллекцию");
        this.collectionManager=collectionManager;
    }

    /**
     * Выполнение команды
     * @param argument аргумент
     * @return Статус выполнения программы
     */
    @Override
    public boolean execute(String argument, Flat flat){
        try {
            if (!argument.isEmpty())throw new IncorrectValueException();
        collectionManager.clear();
        ResponseCreator.appendln("\u001B[37m"+"\u001B[33m"+"Коллекция удалена"+"\u001B[33m"+"\u001B[37m");
        return true;
    }
         catch (
                 IncorrectValueException e) {
        ResponseCreator.error("У этой команды нет параметров! Необходимо ввести: clear");
    }
        return false;
    }


}
