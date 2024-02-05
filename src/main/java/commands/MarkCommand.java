package commands;

import java.io.IOException;

import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Represents the command used to mark tasks in the task list as done.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";
    private static final String SUCCESS_MESSAGE = "Nice! Uncle marked this task as done:\n\t\t %s";
    private final String message;

    /**
     * Creates a new MarkCommand object with the provided message.
     * @param message Input message containing index to mark.
     */
    public MarkCommand(String message) {
        this.message = message;
    }

    /**
     * Executes the MarkCommand, marking a task from the task list as done based on the input index.
     * If the input index is out of range of the task list, an IndexOutOfBoundsException is thrown.
     * @param tasks   The TaskList representing the collection of tasks.
     * @param storage The Storage object handling storage operations.
     * @param ui      The Ui object responsible for user interface interactions.
     */
    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) {
        try {
            int index = Integer.parseInt(message);
            tasks.get(index - 1).mark();
            ui.showToUser(String.format(SUCCESS_MESSAGE, tasks.get(index - 1)));
        } catch (IndexOutOfBoundsException e) {
            ui.showErrorMessage(e.getMessage());
        }

        try {
            storage.appendToFile(tasks);
        } catch (IOException e) {
            ui.showErrorMessage(e.getMessage());
        }
    }
}
