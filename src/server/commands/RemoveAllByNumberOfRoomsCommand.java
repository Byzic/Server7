package server.commands;

import common.data.Flat;
import exceptions.EmptyArgumentException;
import server.utility.CollectionManager;
import server.utility.ResponseCreator;


/**
 * Команда "remove_all_by_number_of_rooms". Удаляет эл-ты с определенным кол-ом комнат
 */
public class RemoveAllByNumberOfRoomsCommand extends AbstractCommand {
    CollectionManager collectionManager;
    public RemoveAllByNumberOfRoomsCommand(CollectionManager collectionManager){

        super("remove_all_by_number_of_rooms numberOfRooms"," удалить из коллекции все элементы, значение поля numberOfRooms которого эквивалентно заданному");
        this.collectionManager=collectionManager;
    }
    /**
     * Выполнение команды
     * @param argument аргумент
     * @return состояние выполнения команды
     */

    @Override
    public boolean execute(String argument, Flat flat) {
        try{
            if (argument.isEmpty()) throw new EmptyArgumentException();
            Integer numberOfRooms=Integer.parseInt(argument);
            int i=collectionManager.removeAllByNumber(numberOfRooms);
            ResponseCreator.appendln("\u001B[37m"+"\u001B[33m"+"Было успешно удалено "+i+" квартир с количеством комнат "+numberOfRooms+"\u001B[33m"+"\u001B[37m");
            return true;
        }
        catch (EmptyArgumentException e) {
            ResponseCreator.error("У этой команды должен быть аргумент(количество комнат в квартире)" );
        }catch (NumberFormatException e){
            ResponseCreator.error("Формат введенного аргумента неверен. Он должен быть целым.");
        }
        return false;
    }
}
