/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci152.fss;

import csci152.adt.Queue;
import csci152.adt.Set;
import csci152.adt.Stack;
import csci152.impl.BSTSet;
import csci152.impl.LinkedListStack;

/**
 * 
 * @author nurzhan.sakenov
 */
public class FileSystem implements FileSystemInterface {
    
    private final Stack<Command> history;
    private final Folder root;
    private Folder currentFolder;

    FileSystem(String name) {
        root = new Folder(name, null);
        currentFolder = root;
        history = new LinkedListStack();
    }
    
    @Override
    public void doCommand(Command cmd) {
        
        boolean success = commandHelper(cmd);
        
        if (success){
            // if a command was executed properly, we can undo it
            // this is to eliminate problems with undoCommand and commandHelper
            history.push(cmd);
        }
    }

    // this handles both doCommand() and undoCommand()
    private boolean commandHelper(Command cmd) {
        
        boolean success = false;
        
        switch (cmd.getCommandCode()) {
            
            case Command.MAKE_FOLDER:
                
                if (!currentFolder.isNameInFolder(cmd.getName())) {
                    
                    FolderOrDocument newFolder = new Folder(cmd.getName(), currentFolder);
                    currentFolder.addToFolder(newFolder);
                    System.out.println("Created folder: '" + cmd.getName() 
                            + "' inside '" + currentFolder.getName() + "'.");
                    success = true;
                    
                } else {
                    
                    System.out.println("There is already a folder named '" + cmd.getName() 
                            + "' inside '" + currentFolder.getName() + "'.");
                }
                break;
                
            case Command.MAKE_DOCUMENT:
                
                if (!currentFolder.isNameInFolder(cmd.getName())) {
                    
                    FolderOrDocument newDoc = new Document(cmd.getName(), currentFolder);
                    currentFolder.addToFolder(newDoc);
                    System.out.println("Created document: '" + cmd.getName() 
                            + "' inside '" + currentFolder.getName() + "'.");
                    success = true;
                    
                } else {
                    
                    System.out.println("There is already a document named '" + cmd.getName() 
                            + "' inside '" + currentFolder.getName() + "'.");
                }
                break;
                
            case Command.REMOVE_EMPTY_FOLDER:
                
                if (currentFolder.isNameInFolder(cmd.getName())) {
                    
                    Set<FolderOrDocument> tempSet = new BSTSet<>();
                    Folder doppelganger = new Folder(cmd.getName(), null); // doppelganger folder for searching
                    Folder folderToRemove = new Folder(null, null);
                    boolean documentAlert = false; // triggered if the object is a Document
                    
                    while (currentFolder.getContents().getSize() > 0) {
                        // preserve everything from the current folder in a temporary set
                        FolderOrDocument temp = currentFolder.removeAnyValue();
                        tempSet.add(temp);
                        // find the reference to the folder we want to delete
                        if (temp.equals(doppelganger)) {
                            if (temp.isFolder())
                                folderToRemove = (Folder)temp;
                            else
                                documentAlert = true; // this is a Document, and we abort later
                        }
                    }
                    
                    while (tempSet.getSize() > 0) {
                        // return the contents back to the current folder
                        FolderOrDocument temp = tempSet.removeAny();
                        currentFolder.addToFolder(temp);
                    }
                    
                    if (documentAlert){
                        // ABORT THE MISSION
                        System.out.println("'" + cmd.getName() 
                            + "' inside '" + currentFolder.getName() + "' is not a folder.");
                        break;
                    }
                    
                    if (folderToRemove.getContents().getSize() == 0) {
                        // the successful outcome if the folder is empty
                        currentFolder.deleteFromFolder(folderToRemove);
                        System.out.println("Empty folder removed: '" + cmd.getName() 
                            + "' from '" + currentFolder.getName() + "'.");
                        success = true;
                    } else {
                        System.out.println("The folder '" + cmd.getName() + "' is not empty.");
                    }
                    
                } else System.out.println("There is no folder named '" + cmd.getName() 
                            + "' inside '" + currentFolder.getName() + "'."); // folder not found
                break;
                
            case Command.REMOVE_DOCUMENT:
                
                if (currentFolder.isNameInFolder(cmd.getName())) {
                    
                    Set<FolderOrDocument> tempSet = new BSTSet<>();
                    Document doppelganger = new Document(cmd.getName(), null); // doppelganger folder for searching
                    Document docToRemove = null;
                    boolean folderAlert = false;
                    
                    while (currentFolder.getContents().getSize() > 0) {
                        // preserve everything from the current folder in a temporary set
                        FolderOrDocument temp = currentFolder.removeAnyValue();
                        tempSet.add(temp);
                        // find the reference to the folder we want to delete
                        if (temp.equals(doppelganger)) {
                            if (!temp.isFolder())
                                docToRemove = (Document)temp;
                            else
                                folderAlert = true;
                        }
                    }
                    
                    while (tempSet.getSize() > 0) {
                        // return the contents back to the current folder
                        FolderOrDocument temp = tempSet.removeAny();
                        currentFolder.addToFolder(temp);
                    }
                    
                    if (folderAlert){
                        System.out.println("'" + cmd.getName() 
                            + "' inside '" + currentFolder.getName() + "' is not a document.");
                        break;
                    }
                    
                    currentFolder.deleteFromFolder(docToRemove);
                    System.out.println("Document removed: '" + cmd.getName() 
                            + "' from '" + currentFolder.getName() + "'.");
                    success = true;
                } else System.out.println("There is no document named '" + cmd.getName() 
                            + "' inside '" + currentFolder.getName() + "'.");
                break;
                
            case Command.GO_UP_ONE_FOLDER:
                
                if (currentFolder != root) {
                    // storing the name of the current folder, so we can return later
                    cmd.setName(currentFolder.getName());
                    currentFolder = currentFolder.getParent();
                    System.out.println("Went up one folder from '" + cmd.getName() + "' to '" +
                            currentFolder.getName() + "'.");
                    success = true;
                } else {
                    System.out.println("Can't go up from the root folder.");
                }
                break;
                
            case Command.GO_INTO_FOLDER:
                
                if (currentFolder.isNameInFolder(cmd.getName())) {
                    
                    Set<FolderOrDocument> tempSet = new BSTSet<>();
                    Folder doppelganger = new Folder(cmd.getName(), null); // doppelganger folder for searching
                    Folder destination = null; // reference to the destination folder
                    boolean documentAlert = false;
                    
                    while (currentFolder.getContents().getSize() > 0) {
                        // preserve everything from the current folder in a temporary set
                        FolderOrDocument temp = currentFolder.removeAnyValue();
                        tempSet.add(temp);
                        // find the reference to the folder we want to enter
                        if (temp.equals(doppelganger)) {
                            if (temp.isFolder())
                                destination = (Folder)temp;
                            else
                                documentAlert = true;
                        }
                    }
                    
                    while (tempSet.getSize() > 0) {
                        // return the contents back to the current folder
                        FolderOrDocument temp = tempSet.removeAny();
                        currentFolder.addToFolder(temp);
                    }
                    
                    if (documentAlert) {
                        System.out.println("'" + cmd.getName() 
                            + "' inside '" + currentFolder.getName() + "' is not a folder.");
                        break;
                    }
                    
                    currentFolder = destination;
                    System.out.println("Went from '" + currentFolder.getParent().getName() + "' to '" +
                            currentFolder.getName() + "'.");
                    success = true;
                } else {
                    System.out.println("There is no folder named '" + cmd.getName() 
                            + "' inside '" + currentFolder.getName() + "'.");
                }
                break;       
        }
        
        return success;
    }

    @Override
    public void undoLastCommand() {
        
        try {
            // trying to pop
            Command cmd = history.pop();
            
            switch (cmd.getCommandCode()) {
            
            case Command.MAKE_FOLDER:
                
                try {
                    // undo by deleting the last created folder (it should be empty)
                    System.out.print("UNDO: ");
                    commandHelper(new Command(Command.REMOVE_EMPTY_FOLDER, cmd.getName()));
                } catch (Exception ex) {
                    System.out.println("Something went wrong.");
                }
                break;
                
            case Command.MAKE_DOCUMENT:
                
                try {
                    // undo by deleting the last created document
                    System.out.print("UNDO: ");
                    commandHelper(new Command(Command.REMOVE_DOCUMENT, cmd.getName()));
                } catch (Exception ex) {
                    System.out.println("Something went wrong.");
                }
                break;
                
            case Command.REMOVE_EMPTY_FOLDER:
                
                try {
                    // undo by re-creating the deleted folder
                    System.out.print("UNDO: ");
                    commandHelper(new Command(Command.MAKE_FOLDER, cmd.getName()));
                } catch (Exception ex) {
                    System.out.println("Something went wrong.");
                }
                break;
                
            case Command.REMOVE_DOCUMENT:
                
                try {
                    // undo by re-creating the deleted document
                    System.out.print("UNDO: ");
                    commandHelper(new Command(Command.MAKE_DOCUMENT, cmd.getName()));
                } catch (Exception ex) {
                    System.out.println("Something went wrong.");
                }
                break;
                
            case Command.GO_UP_ONE_FOLDER:
                
                /* we need to return to the previous folder we were in,
                and for that we used the setName() method above to store
                the then-current destination - now we just GO_INTO that folder
                */
                try {
                    System.out.print("UNDO: ");
                    commandHelper(new Command(Command.GO_INTO_FOLDER, cmd.getName()));
                } catch (Exception ex) {
                    System.out.println("Something went wrong.");
                }
                break;
                
            case Command.GO_INTO_FOLDER:
                
                // undo by going up
                try {
                    System.out.print("UNDO: ");
                    commandHelper(new Command(Command.GO_UP_ONE_FOLDER));
                } catch (Exception ex) {
                    System.out.println("Something went wrong.");
                }
                break;
            }
            
        } catch (Exception ex) {
            
            System.out.println("Nothing to undo.");
        }

    }

    @Override
    public void listContents() {
        System.out.println("-----------------------");
        System.out.println("CURRENT FOLDER CONTENTS");
        System.out.println("Current folder: " + currentFolder.getName());
        System.out.println(currentFolder.getContentNames());
    }

    @Override
    public Queue<String> getAllPaths() {
        return root.getAllPaths();
    }
    
}
