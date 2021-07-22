package ru.vsu.cs.parshina;

public class Filters {
    public static ApartmentFilter priceGreaterThan(int target) {
        return (Apartment obj) -> obj.getPrice() >= target;
    }

    public static ApartmentFilter priceLessThan(int target) {
        return (Apartment obj) -> obj.getPrice() <= target;
    }

    public static ApartmentFilter roomsGreaterThan(int target) {
        return (Apartment obj) -> obj.getRooms() >= target;
    }

    public static ApartmentFilter roomsLessThan(int target) {
        return (Apartment obj) -> obj.getRooms() <= target;
    }

    public static ApartmentFilter S_kitchenGreaterThan(int target) {
        return (Apartment obj) -> obj.getS_kitchen() >= target;
    }

    public static ApartmentFilter S_kitchenLessThan(int target) {
        return (Apartment obj) -> obj.getS_kitchen() <= target;
    }

    public static ApartmentFilter S_generalGreaterThan(int target) {
        return (Apartment obj) -> obj.getS_general() >= target;
    }

    public static ApartmentFilter S_generalLessThan(int target) {
        return (Apartment obj) -> obj.getS_general() <= target;
    }
}
