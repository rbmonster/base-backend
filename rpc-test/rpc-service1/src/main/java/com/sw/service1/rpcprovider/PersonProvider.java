package com.sw.service1.rpcprovider;

import com.sw.api.IHelloWorld;
import com.sw.api.model.Person;
import com.sw.rpc.annotation.SwRpcProvider;

import java.util.UUID;

@SwRpcProvider(interfaceClass = IHelloWorld.class)
public class PersonProvider implements IHelloWorld{

    @Override
    public Person findPersonByName(String name) {
        Person person = new Person();
        person.setName("xiaoming");
        person.setHobby("aaa");
        person.setId(UUID.randomUUID().toString());
        return person;
    }
}
