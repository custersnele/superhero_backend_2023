package be.pxl.superhero.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MyServiceTest {

    @Mock
    private OtherService otherService;
    @InjectMocks
    private MyService myService;

    @Test
    public void doCalculationTest() {
        when(otherService.getValue()).thenReturn(5);
        assertEquals(500, myService.doCalculation(100));
    }
}
