package server.commands;


import common.User;
import common.data.Flat;
import exceptions.IncorrectValueException;
import server.utility.CollectionManager;
import server.utility.ResponseCreator;

/**
* Класс для команды "exit". Проверяет аргумент и дальше ничего не делает
 **/
public class ExitCommand extends AbstractCommand {
    CollectionManager collectionManager;
        public ExitCommand(CollectionManager collectionManager){
            super("exit","завершить программу ")  ;
            this.collectionManager=collectionManager;

        }
        /**
         * Выполнение команды
         * @param argument аргумент
         * @return состояние выполнения команды
         */
        @Override
        public boolean execute(String argument, Flat flat, User user){
            try {
                if (!argument.isEmpty())throw new IncorrectValueException();
                ResponseCreator.appendln("Клиент "+user.getLogin()+" завершил свою работу");

                return true;
            }
            catch (
                    IncorrectValueException e) {
                ResponseCreator.error("У этой команды нет параметров! Необходимо ввести: exit");

            }
            return false;
        }
    }

