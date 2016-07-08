package org.academiadecodigo.std;

/**
 * Created by glitch
 * Bashtard$ Bootcamp @ Academia de Código
 * Fundão 08/07/16 --
 */
public enum DirectionType {
    UP("1"),
    DOWN("2"),
    LEFT("3"),
    RIGHT("4"),
    NULL("0");

    private String num;

    DirectionType(String num) {
        this.num = num;
    }

    public String getNum() {
        return num;
    }

    private int number;

    public int getNumber() {
        return Integer.parseInt(num);
    }

}
