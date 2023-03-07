package be.pxl.superhero.service;

public class MyService {

    private final OtherService otherService;

    public MyService(OtherService otherService) {
        this.otherService = otherService;
    }

    public int doCalculation(int value) {
        return value * otherService.getValue();
    }
}
