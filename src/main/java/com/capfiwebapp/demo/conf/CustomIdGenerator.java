package com.capfiwebapp.demo.conf;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import java.io.Serializable;

public class CustomIdGenerator implements IdentifierGenerator {

    private static Long sequence = 102L;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {

        String prefix = "tt";
        Long nextId = getNextId();
        String formattedId = String.format("%s%07d", prefix, nextId);
        return formattedId;
    }

    private synchronized Long getNextId() {
        return ++sequence;
    }
}