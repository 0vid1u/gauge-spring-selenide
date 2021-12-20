package my.company.env;

import my.company.env.mapper.RunProperties;

import java.io.PrintStream;

public interface Configuration extends RunProperties {

    void list(PrintStream out);

}
