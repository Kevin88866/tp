package seedu.cuddlecare.command;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Wraps a {@link Command} along with its arguments.
 * <p>
 * This class allows the parser to separate command execution
 * from parsing input, storing the arguments for execution later.
 */
public class CommandWithArguments implements Command {

    /**
     * Logger instance for this class.
     */
    private static final Logger logger = Logger.getLogger(CommandWithArguments.class.getName());

    static {
        logger.setLevel(Level.FINE); // Only log detailed info for debugging
    }

    /**
     * The underlying command to execute.
     */
    private final Command command;

    /**
     * The arguments associated with the command.
     */
    private final String args;

    /**
     * Constructs a new CommandWithArguments.
     *
     * @param command the underlying {@link Command} to execute
     * @param args    the arguments to pass to the command when executed
     */
    public CommandWithArguments(Command command, String args) {
        assert command != null : "Command cannot be null";
        assert args != null : "Arguments cannot be null";
        this.command = command;
        this.args = args;
    }

    /**
     * Executes the underlying command using the stored arguments.
     * <p>
     * The {@code exec} parameter is ignored because arguments
     * are already stored in this object.
     *
     * @param ignored ignored input (not used)
     */
    @Override
    public void exec(String ignored) {
        logger.fine(() -> "Executing CommandWithArguments: " + command.getClass().getSimpleName() +
                " with args: \"" + args + "\"");

        command.exec(args);
    }
}
