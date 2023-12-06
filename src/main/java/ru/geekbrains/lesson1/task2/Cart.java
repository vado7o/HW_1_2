package ru.geekbrains.lesson1.task2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Cart<T extends Food> {

    //region Поля

    /**
     * Товары в магазине
     */
    private final ArrayList<T> foodstuffs;
    private final UMarket market;
    private final Class<T> clazz;

    //endregion

    //region Конструкторы

    /**
     * Создание нового экземпляра корзины
     *
     * @param market принадлежность к магазину
     */
    public Cart(Class<T> clazz, UMarket market) {
        this.clazz = clazz;
        this.market = market;
        foodstuffs = new ArrayList<>();
    }

    //endregion

    /**
     * Балансировка корзины
     */
    public void cardBalancing() {
        AtomicReference<Boolean> proteins = new AtomicReference<>(false);
        AtomicReference<Boolean> fats = new AtomicReference<>(false);
        AtomicReference<Boolean> carbohydrates = new AtomicReference<>(false);

        foodstuffs.forEach(food -> { if (food.getProteins()) proteins.set(true); if (food.getFats()) fats.set(true);
            if (food.getCarbohydrates()) carbohydrates.set(true); });

        System.out.println(proteins);
        System.out.println(fats);
        System.out.println(carbohydrates);

        if(proteins.get()&&fats.get()&&carbohydrates.get()) { System.out.println("Корзина уже сбалансирована по БЖУ.");
            return; }

        market.getThings(clazz).forEach(thing -> {
            if (!proteins.get() && thing.getProteins()) { foodstuffs.add(thing); proteins.set(true); }
            if (!fats.get() && thing.getFats()) { foodstuffs.add(thing); fats.set(true); }
            if (!carbohydrates.get() && thing.getCarbohydrates()) { foodstuffs.add(thing); carbohydrates.set(true); }
        });

        if(proteins.get()&&fats.get()&&carbohydrates.get())
                System.out.println("Корзина сбалансирована по БЖУ.");
        else
                System.out.println("Невозможно сбалансировать корзину по БЖУ.");
}

    public Collection<T> getFoodstuffs() {
        return foodstuffs;
    }

    /**
     * Распечатать список продуктов в корзине
     */
    public void printFoodstuffs() {
        AtomicInteger index = new AtomicInteger(1);
        foodstuffs.forEach(food -> System.out.printf("[%d] %s (Белки: %s Жиры: %s Углеводы: %s)\n",
                index.getAndIncrement(), food.getName(),
                food.getProteins() ? "Да" : "Нет",
                food.getFats() ? "Да" : "Нет",
                food.getCarbohydrates() ? "Да" : "Нет"));
    }

}
