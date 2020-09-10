package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static int MATRIX_HEIGHT = 10;
    public static int MATRIX_WIDTH = 15;

    public static void main(String[] args) {
        Element[][] matrix = new Element[MATRIX_HEIGHT][MATRIX_WIDTH];

        // Заполняем матрицу, при этом начало координатной сетки находится в левом верхнем углу
        for (int y = 0; y < MATRIX_HEIGHT; y++) {
            for (int x = 0; x < MATRIX_WIDTH; x++) {
                matrix[y][x] = new Element(x, y);
            }
        }

        print(matrix);

        // Генерируем "иголку", которую будем искать
        Element needle = generateNeedle();
        System.out.printf("\nИскомый элемент = (%d;%d)\n", needle.x, needle.y);

        // Ищем соседей
        List<Element> neighbors = search(needle, matrix);

        if (neighbors == null) {
            System.out.println("\nСоседей нет");
            return;
        }

        System.out.print("\nСоседи: ");
        for (Element item : neighbors) {
            System.out.printf("(%d;%d)", item.x, item.y);
        }
    }

    /**
     * Печать матрицы в консоль
     *
     * @param matrix Матрица
     */
    private static void print(Element[][] matrix) {
        for (int i = 0; i < MATRIX_HEIGHT; i++) {
            for (int j = 0; j < MATRIX_WIDTH; j++) {
                Element current = matrix[i][j];
                System.out.printf("(%d;%d) ", current.x, current.y);
            }
            System.out.println();
        }
    }

    /**
     * Сгенерировать рандомную "иголку" для поиска
     *
     * @return Элемент, выступающий в качестве искомой иголки
     */
    private static Element generateNeedle() {
        int needleX = ThreadLocalRandom.current().nextInt(0, MATRIX_WIDTH);
        int needleY = ThreadLocalRandom.current().nextInt(0, MATRIX_HEIGHT);

        return new Element(needleX, needleY);
    }

    /**
     * Поиск соседей элемента needle в матрице
     *
     * @param needle Искомый элемент
     * @param source Матрица
     * @return Список соседей
     */
    private static List<Element> search(Element needle, Element[][] source) {
        if (needle.x >= MATRIX_WIDTH || needle.x < 0) {
            return null;
        }

        if (needle.y >= MATRIX_HEIGHT || needle.y < 0) {
            return null;
        }

        // Находим верхнюю и нижнюю границы искомой области соседей
        int upperBoundY = needle.y > 0 ? needle.y - 1 : 0;
        int bottomBoundY = needle.y < MATRIX_HEIGHT - 1 ? needle.y + 1 : MATRIX_HEIGHT - 1;

        // Находим левую и правую границы искомой области соседей
        int leftBoundX = needle.x > 0 ? needle.x - 1 : 0;
        int rightBoundX = needle.x < MATRIX_WIDTH - 1 ? needle.x + 1 : MATRIX_WIDTH - 1;

        // Все элементы внутри этой области (за исключением "иголки") - искомые соседи
        List<Element> result = new ArrayList<>();
        for (int y = upperBoundY; y <= bottomBoundY; y++) {
            for (int x = leftBoundX; x <= rightBoundX; x++) {
                if (y == needle.y && x == needle.x) {
                    continue;
                }
                result.add(source[y][x]);
            }
        }

        return result;
    }
}
