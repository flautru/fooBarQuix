package com.fabien.kata.fooBarQuix.service.batch;

import com.fabien.kata.fooBarQuix.model.FooBarQuixResult;
import com.fabien.kata.fooBarQuix.model.Input;
import com.fabien.kata.fooBarQuix.service.FooBarQuixService;
import com.fabien.kata.fooBarQuix.service.batch.FooBarQuixBatchProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class FooBarQuixBatchProcessorTest {

    @Mock
    private FooBarQuixService fooBarQuixService;

    @InjectMocks
    private FooBarQuixBatchProcessor fooBarQuixBatchProcessor;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processFooBarQuixWith3(){
        Input input = new Input(3);
        String expected = "FOOFOO";
        when(fooBarQuixService.getFooBarQuix(input.number())).thenReturn(expected);

        FooBarQuixResult result = fooBarQuixBatchProcessor.process(input);

        assertEquals(new FooBarQuixResult(input.number(),expected),result);
    }


    @Test
    void processFooBarQuixWith0(){
        Input input = new Input(0);
        String expected = "FOOBAR";
        when(fooBarQuixService.getFooBarQuix(input.number())).thenReturn(expected);

        FooBarQuixResult result = fooBarQuixBatchProcessor.process(input);

        assertEquals(new FooBarQuixResult(input.number(),expected),result);
    }

    @Test
    void processFooBarQuixWithNullInput(){
        Input input = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,() -> fooBarQuixBatchProcessor.process(input));

        assertEquals("Invalid input : cant be null",exception.getMessage());
    }

    @Test
    void processFooBarQuixWith101(){
        Input input = new Input(101);

        when(fooBarQuixService.getFooBarQuix(101))
                .thenThrow(new IllegalArgumentException("Invalid input : Number must be between 0 and 100"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,() -> fooBarQuixBatchProcessor.process(input));

        assertEquals("Invalid input : Number must be between 0 and 100",exception.getMessage());
    }


}
