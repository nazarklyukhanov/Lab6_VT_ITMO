package org.example.commands;

import org.example.exceptions.NoCommandException;
import org.example.exceptions.RootException;
import org.example.recources.Request;

import java.io.IOException;

public interface BaseCommand {
    String execute(Request request) throws RootException, IOException, NoCommandException;
    String getName();
    String getDescription();
}
