package server.utility;


import common.data.Flat;
import server.commands.AbstractCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для запуска команд
 */
public class CommandManager {
    private final int maxCommandSize = 14;

    private List<AbstractCommand> commands = new ArrayList<>();//массив с командами
    private AbstractCommand helpCommand;
    private AbstractCommand infoCommand;
    private AbstractCommand showCommand;
    private AbstractCommand insertCommand;
    private AbstractCommand updateIdCommand;
    private AbstractCommand removeKeyCommand;
    private AbstractCommand executeScriptCommand;
    private AbstractCommand saveCommand;
    private AbstractCommand clearCommand;
    private AbstractCommand replaceIfGreaterCommand;
    private AbstractCommand replaceIfLowerCommand;
    private AbstractCommand removeLowerKeyCommand;
    private AbstractCommand removeAllByNumberOfRoomsCommand;
    private AbstractCommand countFurnishCommand;
    private AbstractCommand filterNameCommand;
    private AbstractCommand exitCommand;


    public CommandManager(AbstractCommand exitCommand,AbstractCommand helpCommand, AbstractCommand infoCommand, AbstractCommand showCommand, AbstractCommand insertCommand, AbstractCommand updateIdCommand,
                          AbstractCommand removeKeyCommand, AbstractCommand saveCommand, AbstractCommand clearCommand, AbstractCommand executeScriptCommand,
                          AbstractCommand replaceIfGreaterCommand, AbstractCommand replaceIfLowerCommand, AbstractCommand removeLowerKeyCommand, AbstractCommand removeAllByNumberOfRoomsCommand,
                          AbstractCommand countFurnishCommand, AbstractCommand filterNameCommand) {
        this.exitCommand=exitCommand;
        commands.add(exitCommand);
        this.helpCommand = helpCommand;
        commands.add(helpCommand);
        this.infoCommand = infoCommand;
        commands.add(infoCommand);
        this.showCommand = showCommand;
        commands.add(showCommand);
        this.insertCommand = insertCommand;
        commands.add(insertCommand);
        this.updateIdCommand = updateIdCommand;
        commands.add(updateIdCommand);
        this.removeKeyCommand = removeKeyCommand;
        commands.add(removeKeyCommand);
        this.saveCommand = saveCommand;

        this.clearCommand = clearCommand;
        commands.add(clearCommand);
        this.executeScriptCommand = executeScriptCommand;
        commands.add(executeScriptCommand);
        this.replaceIfGreaterCommand = replaceIfGreaterCommand;
        commands.add(replaceIfGreaterCommand);
        this.replaceIfLowerCommand = replaceIfLowerCommand;
        commands.add(replaceIfLowerCommand);
        this.removeLowerKeyCommand = removeLowerKeyCommand;
        commands.add(removeLowerKeyCommand);
        this.removeAllByNumberOfRoomsCommand = removeAllByNumberOfRoomsCommand;
        commands.add(removeAllByNumberOfRoomsCommand);
        this.countFurnishCommand = countFurnishCommand;
        commands.add(countFurnishCommand);
        this.filterNameCommand = filterNameCommand;
        commands.add(filterNameCommand);
    }

    /**
     * Выводит все доступные команды с описанием
     * @param argument это аргумент
     * @return Состояние работы команды
     */

    public boolean help (String argument, Flat flat){
        if (helpCommand.execute(argument,flat)) {
            for (AbstractCommand command : commands) {
                ResponseCreator.appendln("\u001B[37m"+"\u001B[33m"+command.getName()+"\u001B[33m"+"\u001B[37m" + ": " + command.getDescription());
            }
            return true;
        } else return false;
    }
    public boolean exit(String argument, Flat flat){
        return exitCommand.execute(argument, flat);
    }

    /**
     * Запускает команду информации о коллекции
     * @param argument это переданный аргумент
     * @return состояние работы программы
     */
    public boolean info(String argument, Flat flat){
        return infoCommand.execute(argument, flat);
    }


    /**
     * Запускает команду показа всех элементов коллекции
     * @param argument это переданный аргумент
     * @return состояние работы программы
     */
    public boolean show(String argument, Flat flat){
        return showCommand.execute(argument,flat);
    }
    /**
     * Запускает команду очистки коллекции
     * @param argument это переданный аргумент
     * @return состояние работы программы
     */
    public boolean clear(String argument, Flat flat){
        return clearCommand.execute(argument, flat);
    }
    /**
     * Запускает команду сохранения коллекции в файл
     * @param argument это переданный аргумент
     * @return состояние работы программы
     */
    public boolean save(String argument, Flat flat){
        return saveCommand.execute(argument,  flat);
    }
    /**
     * Запускает команду добавления нового элемента
     * @param argument это переданный аргумент
     * @return состояние работы программы
     */
    public boolean insert(String argument, Flat flat){
        return insertCommand.execute(argument, flat);
    }
    /**
     * Запускает команду замены элемента по ключу
     * @param argument это переданный аргумент
     * @return состояние работы программы
     */
    public boolean update(String argument, Flat flat){
        return updateIdCommand.execute(argument, flat);
    }
    /**
     * Запускает команду удаления элемента по ключу
     * @param argument это переданный аргумент
     * @return состояние работы программы
     */
    public boolean removeKey(String argument, Flat flat){
        return removeKeyCommand.execute(argument, flat);
    }
    /**
     * Запускает команду удаления элементов по количеству комнат
     * @param argument это переданный аргумент
     * @return состояние работы программы
     */
    public boolean removeAllByNumber(String argument, Flat flat){
        return removeAllByNumberOfRoomsCommand.execute(argument,  flat);
    }
    /**
     * Запускает команду удаления элементов с ключом меньшим чем заданный
     * @param argument это переданный аргумент
     * @return состояние работы программы
     */
    public boolean removeLowerKey(String argument, Flat flat){
        return removeLowerKeyCommand.execute(argument, flat);
    }
    /**
     * Запускает команду, которая выводит элементы по имени
     * @param argument это переданный аргумент
     * @return состояние работы программы
     */
    public boolean filterName(String argument, Flat flat){
        return filterNameCommand.execute(argument,flat);
    }
    /**
     * Запускает команду выполнения скрипта
     * @param argument это переданный аргумент
     * @return состояние работы программы
     */
    public boolean executeScript(String argument, Flat flat){
        return executeScriptCommand.execute(argument,  flat);
    }
    /**
     * Запускает команду замены элемента, если он больше
     * @param argument это переданный аргумент
     * @return состояние работы программы
     */
    public boolean replaceIfGreater(String argument, Flat flat){
        return replaceIfGreaterCommand.execute(argument,  flat);
    }
    /**
     * Запускает команду замены элемента, если он меньше
     * @param argument это переданный аргумент
     * @return состояние работы программы
     */
    public boolean replaceIfLower(String argument, Flat flat){
        return replaceIfLowerCommand.execute(argument, flat);
    }
    /**
     * Запускает команду подсчета кол-ва элементов с определенной отделкой
     * @param argument это переданный аргумент
     * @return состояние работы программы
     */
    public boolean countFurnish(String argument, Flat flat){
        return countFurnishCommand.execute(argument,  flat);
    }



}
