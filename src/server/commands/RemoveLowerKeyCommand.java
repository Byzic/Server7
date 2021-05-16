package server.commands;

import common.data.Flat;
import exceptions.EmptyArgumentException;
import server.utility.CollectionManager;
import server.utility.ResponseCreator;


/**
 * Команда "remove_lower_key". Удаляет эл-ты с меньшим ключом
 */
public class RemoveLowerKeyCommand extends AbstractCommand {
    CollectionManager collectionManager;
    public RemoveLowerKeyCommand(CollectionManager collectionManager){
        super("remove_lower_key null","удалить из коллекции все элементы, ключ которых меньше, чем заданный");
        this.collectionManager=collectionManager;
    }
    /**
     * Выполнение команды
     * @param argument аргумент
     * @return состояние выполнения команды
     */
    @Override
    public boolean execute(String argument, Flat flat){
        try{
            if (argument.isEmpty()) throw new EmptyArgumentException();
            Integer key=Integer.parseInt(argument);
            int i=collectionManager.removeLowerKey(key);
            ResponseCreator.appendln("\u001B[37m"+"\u001B[33m"+"Было удалено "+i+" квартир с ключом меньше "+key+"\u001B[33m"+"\u001B[37m");
            return true;
        }catch (EmptyArgumentException e) {
            ResponseCreator.error("У этой команды должен быть аргумент(ключ для удаления элементов)" );
        }catch (NumberFormatException e){
            ResponseCreator.error("Формат введенного аргумента неверен. Он должен быть целым.");
        }
        return false;
    }
}
