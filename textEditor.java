package TextEditorProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class textEditor<AnyType> extends LinkedList<AnyType> {

    /*  ************************* COMMANDS *********************************

        a - Add text after current line until . on its own line
        d - Delete current line.
        f name - Change name of the current file (for next write).
        mr num num num - Move several lines as a unit after some other line.
        p - Print current line.
        q! - Abort without write.
        s text text - Substitute text with other text.
        tr num num num - Copy several lines to after some other line.
        w - Write file to disk.
        $ - Go to the last line.
        + - Go down one line.
        = - Print current line number.
        ? text - Search backward for a pattern.

        ********************************************************************    */
    public static void main(String[] args) throws NoSuchElementException, IOException{
        LinkedList<String> myList = new LinkedList<>();
        addFileToList(myList);
        myList.showList();
        
        Scanner commandSC = new Scanner(System.in);
        Scanner AttributeSC = new Scanner(System.in);
        Scanner contSC = new Scanner(System.in);
        try (FileWriter myWriter = new FileWriter("texteditorproject/src/File.text", true)) {
            System.out.println("Do you want to make any changes to the text? T/F:  ");
            
            System.out.println("********************************* Commands *********************************");
                System.out.println(" a - Add text after current line until . on its own line." +
                "\n d - Delete current line. " + 
                "\n f name - Change name of the current file (for next write)." +
                "\n mr num num num - Move several lines as a unit after some other line." +
                "\n p - Print current line." +
                "\n q! - Abort without write." +
                "\n s text text - Substitute text with other text." +
                "\n tr num num num - Copy several lines to after some other line." +
                "\n w - Write file to disk." +
                "\n $ - Go to the last line." +
                "\n + - Go down one line." +
                "\n = - Print current line number." +
                "\n ? text - Search backward for a pattern."); 
                System.out.println("****************************************************************************");

            Boolean Cont = contSC.nextBoolean();

            while(Cont){


                System.out.println("Pick your command: ");

                String Command = commandSC.nextLine();

                if(Command.equals("a")){

                    System.out.println("Type what you want to add: ");
                    String Text = AttributeSC.nextLine();
                    myWriter.write(System.lineSeparator());
                    myWriter.write(Text);
                    myList.addLast(Text);

                }

                else if(Command.equals("d")){

                    myList.removeFirst();

                }
 
                else if(Command.equals("f")){

                    System.out.println("Enter the File Name: ");
                    String newFileName = AttributeSC.nextLine();
                    Path source = Paths.get("texteditorproject/src/File.text");
                    Files.move(source, source.resolveSibling(newFileName));

                }

                else if(Command.equals("mr")){

                    myList.cutPasteAndWrite();

                }

                else if(Command.equals("dAll")){

                    for(int i = 0; i < myList.size(); i++){

                        myList.remove();

                    }

                }

                else if(Command.equals("p")){

                    System.out.println(myList.getFirst());

                }

                else if(Command.equals("!q")){

                    break;

                }

                else if(Command.equals("s")){

                    myList.substituteAndWrite();

                }

                else if(Command.equals("tr")){

                    myList.copyPasteAndWrite();

                }

                else if(Command.equals("$")){

                  myList.toLast();

                }

                else if(Command.equals("+")){

                    myList.toNext();

                }

                else if(Command.equals("=")){

                   myList.currentLine();

                }

                else if(Command.equals("?")){

                    myList.SearchBackwards();

                }


                System.out.println("Do you want to make other changes? T/F ");
                Cont = contSC.nextBoolean(); 
                if(Cont == false && !Command.equals("a")){

                    System.out.println("To save your changes press w ");
                    Command = commandSC.nextLine();

                    if(Command.equals("w")){
                        myList.addTextFromLL();
                    }
                    else{
                        break;
                    }
                }

            }
            myWriter.close();
        } 
        System.out.println();
        myList.showList();
    }      
}
