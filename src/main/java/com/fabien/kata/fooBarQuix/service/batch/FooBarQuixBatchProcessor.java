package com.fabien.kata.fooBarQuix.service.batch;

import com.fabien.kata.fooBarQuix.model.FooBarQuixResult;
import com.fabien.kata.fooBarQuix.model.Input;
import com.fabien.kata.fooBarQuix.service.FooBarQuixService;
import org.springframework.batch.item.ItemProcessor;

public class FooBarQuixBatchProcessor implements ItemProcessor<Input, FooBarQuixResult> {
    private final FooBarQuixService fooBarQuixService;

    public FooBarQuixBatchProcessor(FooBarQuixService fooBarQuixService) {
        this.fooBarQuixService = fooBarQuixService;
    }

    @Override
    public FooBarQuixResult process(Input input){
        if(input == null){
            throw new IllegalArgumentException("Invalid input : cant be null");
        }
        return new FooBarQuixResult(input.number(), fooBarQuixService.getFooBarQuix(input.number()));
    }
}
