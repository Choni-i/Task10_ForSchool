package ru.vsu.cs.parshina;

public class Apartment {
    String District;
    int Rooms;
    int S_general;
    int S_kitchen;
    int Price;

    public Apartment(String District, int Rooms, int S_general, int S_kitchen, int Price) {
        this.District = District;
        this.Rooms = Rooms;
        this.S_general = S_general;
        this.S_kitchen = S_kitchen;
        this.Price = Price;
    }

    public String getDistrict() {
        return District;
    }

    public int getRooms() {
        return Rooms;
    }

    public int getS_kitchen() {
        return S_kitchen;
    }

    public int getS_general() {
        return S_general;
    }

    public int getPrice() {
        return Price;
    }

    public void setRooms(int Rooms) { this.Rooms = Rooms;
    }
    public void setS_kitchen(int S_kitchen) { this.S_kitchen = S_kitchen;;
    }
    public void setS_general(int S_general) { this.S_general = S_general;
    }
    public void setPrice(int Price) { this.Price = Price;
    }
}
