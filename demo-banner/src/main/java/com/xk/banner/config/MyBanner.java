package com.xk.banner.config;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2021/10/30 13:57
 */
public class MyBanner implements Banner {
    public static final String BANNER = "  _____  ________  ________  ________     \n" +
            " / __  \\|\\_____  \\|\\_____  \\|\\  ___  \\    \n" +
            "|\\/_|\\  \\|____|\\ /_\\|___/  /\\ \\____   \\   \n" +
            "\\|/ \\ \\  \\    \\|\\  \\   /  / /\\|____|\\  \\  \n" +
            "     \\ \\  \\  __\\_\\  \\ /  / /     __\\_\\  \\ \n" +
            "      \\ \\__\\|\\_______Y__/ /     |\\_______\\\n" +
            "       \\|__|\\|_______|__|/      \\|_______|\n";

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.println(BANNER);
        out.println();
    }
}
