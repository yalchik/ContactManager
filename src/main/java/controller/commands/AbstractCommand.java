package controller.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains logger for all concrete commands
 * @author Yalchyk Ilya
 */
public abstract class AbstractCommand implements ICommand {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

}
