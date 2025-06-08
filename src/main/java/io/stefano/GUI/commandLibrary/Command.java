package io.stefano.GUI.commandLibrary;

public interface Command {
	boolean doIt();//return true puo essere annullato

	boolean undoIt();
}
