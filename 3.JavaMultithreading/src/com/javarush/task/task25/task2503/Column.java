package com.javarush.task.task25.task2503;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public enum Column implements Columnable {
    Customer("Customer") {
        @Override
        public String getColumnName() {
            return Customer.columnName;
        }

        @Override
        public boolean isShown() {
            return realOrder[ordinal()] > -1 ? true : false;
        }

        @Override
        public void hide() {
            realOrder[ordinal()] = -1;

            hideColumn();
        }
    },
    BankName("Bank Name") {
        @Override
        public String getColumnName() {
            return BankName.columnName;
        }

        @Override
        public boolean isShown() {
            return realOrder[ordinal()] > -1 ? true : false;
        }

        @Override
        public void hide() {
            realOrder[ordinal()] = -1;
            hideColumn();
        }
    },
    AccountNumber("Account Number") {
        @Override
        public String getColumnName() {
            return AccountNumber.columnName;
        }

        @Override
        public boolean isShown() {
            return realOrder[ordinal()] > -1 ? true : false;
        }

        @Override
        public void hide() {
            realOrder[ordinal()] = -1;

            hideColumn();

        }
    },
    Amount("Available Amount") {
        @Override
        public String getColumnName() {
            return Amount.columnName;
        }

        @Override
        public boolean isShown() {
            return realOrder[ordinal()] > -1 ? true : false;
        }

        @Override
        public void hide() {
            realOrder[ordinal()] = -1;

            hideColumn();

        }
    };

    private String columnName;

    private static int[] realOrder;

    private Column(String columnName) {
        this.columnName = columnName;
    }

    /**
     * Задает новый порядок отображения колонок, который хранится в массиве realOrder.
     * realOrder[индекс в энуме] = порядок отображения; -1, если колонка не отображается.
     *
     * @param newOrder новая последовательность колонок, в которой они будут отображаться в таблице
     * @throws IllegalArgumentException при дубликате колонки
     */
    public static void configureColumns(Column... newOrder) {
        realOrder = new int[values().length];
        for (Column column : values()) {
            realOrder[column.ordinal()] = -1;
            boolean isFound = false;

            for (int i = 0; i < newOrder.length; i++) {
                if (column == newOrder[i]) {
                    if (isFound) {
                        throw new IllegalArgumentException("Column '" + column.columnName + "' is already configured.");
                    }
                    realOrder[column.ordinal()] = i;
                    isFound = true;
                }
            }
        }
    }

    /**
     * Вычисляет и возвращает список отображаемых колонок в сконфигурированом порядке (см. метод configureColumns)
     * Используется поле realOrder.
     *
     * @return список колонок
     */
    public static List<Column> getVisibleColumns() {
        List<Column> result = new LinkedList<>();

        for (int i = 0; i < Column.values().length; i++) {

            for (int j = 0; j < Column.values().length; j++) {

                if (realOrder[j] != -1) {

                    if (realOrder[j] == i) {

                        result.add(Column.values()[j]);

                    }

                }

            }

        }

        return result;
    }

    public static void hideColumn() {

        List<Column> columnList = new ArrayList<>();


        for (int i = 0; i < realOrder.length; i++) {

            for (int j = 0; j < realOrder.length; j++) {

                if (i == realOrder[j]) {
                    columnList.add(values()[j]);
                }
            }

        }

        Column[] columns = new Column[columnList.size()];

        int count = 0;
        for (Column column : columnList) {
            columns[count] = column;
            count++;
        }

        configureColumns(columns);
    }
}