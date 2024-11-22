package gsu.by;

import gsu.by.generator.TicketsGenerator;

import static gsu.by.constants.CommonConstants.PATH_TO_CONFIG_FILE;

public class GeneratorExecutor {

    public static void main(String[] args) throws Exception {
        TicketsGenerator ticketsGenerator = new TicketsGenerator(PATH_TO_CONFIG_FILE);
        ticketsGenerator.generateTicketsAsSingleDocument();
    }
}
