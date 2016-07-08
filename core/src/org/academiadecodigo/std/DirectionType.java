package org.academiadecodigo.std;

/**
 * Created by glitch
 * Bashtard$ Bootcamp @ Academia de Código
 * Fundão 08/07/16 --
 */
public enum DirectionType {
<<<<<<< HEAD
    UP("1"),
    DOWN("2"),
    LEFT("3"),
    RIGHT("4"),
    NULL("0");

    private String num;

    DirectionType(String num) {
        this.num = num;
    }

    public String getNumber() {
        return num;
=======
    UP(1),
    DOWN(2),
    LEFT(3),
    RIGHT(4),
    NULL(0);

    private int number;

    DirectionType(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
>>>>>>> server
    }

}
