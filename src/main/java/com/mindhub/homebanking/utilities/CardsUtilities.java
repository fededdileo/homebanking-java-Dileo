package com.mindhub.homebanking.utilities;

public final class CardsUtilities {
    private CardsUtilities() {}
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
