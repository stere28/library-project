package io.stefano.GUI.commandLibrary;

public class NaiveCommandHandler implements CommandHandler{
    @Override
    public void handle(Command cmd) {
        cmd.doIt();
    }
}
