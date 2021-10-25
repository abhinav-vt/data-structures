/**
 * The purpose of this class is to parse a text file into its appropriate, line
 * by line commands for the format specified in the project spec.
 *
 * @author CS Staff
 * @version 2021-08-23
 */
public class CommandProcessor {

    // the database object to manipulate the
    // commands that the command processor
    // feeds to it
    private Database data;

    /**
     * The constructor for the command processor requires a database instance
     * to exist, so the only constructor takes a database class object to feed
     * commands to.
     */
    public CommandProcessor() {
        data = new Database();
    }


    /**
     * This method identifies keywords in the line and calls methods in the
     * database as required. Each line command will be specified by one of the
     * keywords to perform the actions within the database required. These
     * actions are performed on specified objects and include insert, remove,
     * regionsearch, search, duplicates, and dump. If the command in the
     * file line is not one of these, an appropriate message will be written
     * in the console. This processor method is called for each line in the
     * file. Note that the methods called will themselves write to the console,
     * this method does not, only calling methods that do.
     *
     * @param line a single line from the text file
     */
    public void processor(String line) {
        String[] commands = line.split("\\s+");
        Command keyWord = Command.valueOf(commands[0].toUpperCase());

        switch (keyWord) {
            case DUMP:
                data.dump();
                break;
            case INSERT:
                data.insert(new Point(commands[1],
                        Integer.parseInt(commands[2]),
                        Integer.parseInt(commands[3])));
                break;
            case SEARCH:
                data.search(commands[1]);
                break;
            case REMOVE:
                if (commands.length == 3) {
                    data.remove(Integer.parseInt(commands[1]),
                            Integer.parseInt(commands[2]));
                }
                else {
                    data.remove(commands[1]);
                }
                break;
            case REGIONSEARCH:
                data.regionsearch(Integer.parseInt(commands[1]),
                        Integer.parseInt(commands[2]),
                        Integer.parseInt(commands[3]),
                        Integer.parseInt(commands[4]));
                break;
            case DUPLICATES:
                data.duplicates();
                break;
            default:
                //do nothing
        }
    }

}
